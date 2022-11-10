package com.tomcat_hello_world.Operations.Administration;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import com.tomcat_hello_world.Entity.User;
import org.json.simple.JSONObject;

import com.tomcat_hello_world.Entity.Cab;
import com.tomcat_hello_world.Entity.Trip;
import com.tomcat_hello_world.Operations.Authentication.UserOperations;
import com.tomcat_hello_world.Operations.Booking.CabOperations;
import com.tomcat_hello_world.Operations.Booking.TripOperations;
import com.tomcat_hello_world.Storage.DatabaseConnection;
import com.tomcat_hello_world.Utility.Constants;

public class SQLQueries extends DatabaseConnection{
   protected static JSONObject getDashboardDetails() throws ClassNotFoundException, SQLException {
	   JSONObject dashboardDetails=new JSONObject();
	   JSONObject tripsByStatus=new JSONObject();
	   JSONObject cabsByStatus=new JSONObject();
	   JSONObject cabsByLoc=new JSONObject();
	   JSONObject usersByRole=new JSONObject();
	   Connection con=DatabaseConnection.initializeDatabase();
	   PreparedStatement ps=con.prepareStatement(" SELECT DISTINCT t.Status,Count(t.id) AS Count FROM Trips t GROUP BY t.Status  UNION SELECT DISTINCT c.Status,Count(c.id) AS count FROM Cabs c GROUP BY c.Status UNION SELECT u.Role,COUNT(u.id) FROM Users u GROUP BY u.Role UNION SELECT l.Point,COUNT(c2.id) FROM Location l LEFT JOIN Cabs c2 ON c2.locid=l.id GROUP BY l.id;");
	   ResultSet rs=ps.executeQuery();
	   while(rs.next()) {
		   if(rs.getString("Status").equals("Completed") || rs.getString("Status").equals("Cancelled") || rs.getString("Status").equals("Underway")) {
			   tripsByStatus.put(rs.getString("Status"), rs.getInt("Count"));
		   }
		   else if(rs.getString("Status").equals("Admin") || rs.getString("Status").equals("Customer") || rs.getString("Status").equals("Driver")) {
			   usersByRole.put(rs.getString("Status"), rs.getInt("Count"));
		   }
		   else if(rs.getString("Status").equals("Available") || rs.getString("Status").equals("Booked")) {
			   cabsByStatus.put(rs.getString("Status"), rs.getInt("Count"));
		   }
		   else {
			   cabsByLoc.put(rs.getString("Status"), rs.getInt("Count"));
		   }
		   dashboardDetails.put("tripsByStatus",tripsByStatus);
		   dashboardDetails.put("cabsByStatus",cabsByStatus);
		   dashboardDetails.put("cabsByLoc",cabsByLoc);
		   dashboardDetails.put("usersByRole",usersByRole);
	   }
	   return dashboardDetails;
   }
   
   protected static int getLastLocId() throws ClassNotFoundException, SQLException {
	   int lastLoc=0;
	   Connection con=DatabaseConnection.initializeDatabase();
	   PreparedStatement ps=con.prepareStatement("SELECT COUNT(id) AS locCount FROM Location");
	   ResultSet rs=ps.executeQuery();
	   if(rs.next()) {
		   lastLoc=rs.getInt("locCount");
	   }
	   return lastLoc;
   }
   
   protected static boolean insertDistances(String query) throws ClassNotFoundException, SQLException {
	   boolean distancesInserted=false;
	   System.out.println(query);
	   Connection con=DatabaseConnection.initializeDatabase();
	   PreparedStatement ps=con.prepareStatement(query);
	   ps.executeUpdate();
	   distancesInserted=true;
	   return distancesInserted;
   }
   
   protected static boolean doesLocExist(int locid) throws ClassNotFoundException, SQLException {
	   boolean locExists=false;
	   Connection con=DatabaseConnection.initializeDatabase();
	   PreparedStatement ps=con.prepareStatement("Select Count(id) AS locCount FROM Location WHERE id=?");
	   ps.setInt(1, locid);
	   ResultSet rs=ps.executeQuery();
	   if(rs.next()) {
		   if(rs.getInt("locCount")==1) {
			   locExists=true;
		   }
	   }
	   con.close(); 	   
	   return locExists;
   }
   
   protected static Cab getCabById(int cabid) throws SQLException, ClassNotFoundException {
   	Cab c=null;
   	Connection con=DatabaseConnection.initializeDatabase();
   	PreparedStatement ps=con.prepareStatement("Select Cabs.uid,Cabs.Type,Location.Point,Cabs.wallet,Cabs.Status,Users.Name FROM Cabs INNER JOIN Users ON Cabs.uid=Users.id INNER JOIN Location ON Cabs.locid=Location.id WHERE Cabs.id=? AND Cabs.uid=Users.id;");
   	ps.setInt(1, cabid);
   	ResultSet rs=ps.executeQuery();
   	if(rs.next()) {
   		int uid=rs.getInt("uid");
   		String type=rs.getString("Type");
   		String locid=rs.getString(Constants.Point);
   		BigDecimal wallet=rs.getBigDecimal("wallet");
   		String status=rs.getString("Status");
   		String name=rs.getString("Name");
   		c=new Cab(cabid,uid,type,locid,wallet,status,name);
   	}
   	return c;
   }

   protected static HashMap<Integer,String> getAdminLocations() throws SQLException, ClassNotFoundException{
	 HashMap<Integer,String> locs=new HashMap<Integer,String>();
	 Connection con=DatabaseConnection.initializeDatabase();
   	 PreparedStatement ps=con.prepareStatement("Select * FROM Location");
	 ResultSet rs=ps.executeQuery();
	 while(rs.next()){
		int id=rs.getInt("id");
		String point=rs.getString("Point");
		locs.put(id,point);
	 }
	 return locs;
   }
   
   protected static ArrayList<UserOperations> getAllUsers(String filter,int limit) throws ClassNotFoundException, SQLException{
	   ArrayList<UserOperations> users=new ArrayList<UserOperations>();
	   Connection con=DatabaseConnection.initializeDatabase();
	   PreparedStatement ps;
	   System.out.println(filter.equals("AllUsers"));
	   if(filter.equals("AllUsers")) {
		   ps=con.prepareStatement("SELECT u.id,u.Name,u.Email,u.Role FROM Users u WHERE limit ?,5;");
		   ps.setInt(1,limit*5);
	   }
	   else {
         ps=con.prepareStatement("SELECT u.id,u.Name,u.Email,u.Role FROM Users u WHERE u.Role=? limit ?,5;");
         ps.setString(1,filter);
         ps.setInt(2, limit*5);
	   } 
       ResultSet rs=ps.executeQuery();
       while(rs.next()) {
    	   int id=rs.getInt("id");
    	   String name=rs.getString(Constants.name);
    	   String email=rs.getString(Constants.email);
    	   String role=rs.getString(Constants.role);
    	   users.add(new UserOperations(new User(id,name,email,role)));
       }
       con.close();
       return users;
   }
   
   protected static ArrayList<CabOperations> getAllCabs() throws ClassNotFoundException, SQLException{
	   ArrayList<CabOperations> cabs=new ArrayList<CabOperations>();
	   Connection con=DatabaseConnection.initializeDatabase();
       PreparedStatement ps=con.prepareStatement("SELECT c.id,c.uid,c.Type,l.Point,c.wallet,c.Status,u.Name FROM Cabs c INNER JOIN Users u ON  c.uid=u.id INNER JOIN Location l ON c.locid=l.id ;");
       ResultSet rs=ps.executeQuery();
       while(rs.next()) {
    	   int id=rs.getInt("id");
    	   int uid=rs.getInt("uid");
    	   String type=rs.getString("Type");
    	   String point=rs.getString("Point");
    	   String name=rs.getString("Name");
    	   String status=rs.getString("Status");
    	   BigDecimal wallet=rs.getBigDecimal("wallet");
    	   cabs.add(new CabOperations(new Cab(id,uid,type,point,wallet,status,name)));
       }
       con.close();
       return cabs;
   }
   
   protected static ArrayList<TripOperations> getAllTrips() throws ClassNotFoundException, SQLException {
	   ArrayList<TripOperations> trips=new ArrayList<TripOperations>();
       
       Connection con=DatabaseConnection.initializeDatabase();
       PreparedStatement ps=con.prepareStatement("SELECT t.id,t.uid,t.cabid,c.type,l.Point AS src,l2.Point AS dest,t.otp,t.Status,t.time_started,t.time_ended,d.distance,u.Name FROM Trips t INNER JOIN Cabs c  ON t.cabid=c.id INNER JOIN Users u ON c.uid=u.id  INNER JOIN Distance d ON ((t.src=d.src AND t.dest=d.dest) OR (t.src=d.dest AND t.dest=d.src)) INNER JOIN Location l on t.src=l.id INNER JOIN location l2 ON t.dest=l2.id  ORDER BY t.id desc;");
       ResultSet rs=ps.executeQuery();
       
       while(rs.next()){
         int id=rs.getInt(Constants.id);
         int uid=rs.getInt("uid");
         int cabid=rs.getInt(Constants.cabid);
         String otp=String.valueOf(rs.getInt(Constants.otp));
         String status=rs.getString(Constants.status);
         Timestamp timeCreated=rs.getTimestamp(Constants.timeStarted);
         Timestamp timeEnded=rs.getTimestamp(Constants.timeEnded); 
         String src=rs.getString(Constants.src);
         String dest=rs.getString(Constants.dest);
         String cabType=rs.getString("Type");
         BigDecimal distance=rs.getBigDecimal("distance");
         String driverName=rs.getString("Name");
         Trip  trip=new Trip(id,uid,cabid,otp,status,timeCreated,timeEnded,src,dest,cabType,distance,driverName);
         CabOperations cab=new CabOperations(getCabById(cabid));
         TripOperations temp=new TripOperations();
         temp.setTrip(trip);
         temp.setCab(cab);
         trips.add(temp);
       }
   
    return trips;
   }
   
   public static  ArrayList<TripOperations> tripsLazyLoader(String filter,int limit) throws ClassNotFoundException, SQLException {
	   ArrayList<TripOperations> trips=new ArrayList<TripOperations>();
	   Connection con=DatabaseConnection.initializeDatabase();
	   PreparedStatement ps=null;
	   if(filter.equals("today")) {
		   ps=con.prepareStatement("SELECT t.id,t.uid,t.cabid,c.type,l.Point AS src,l2.Point AS dest,t.otp,t.Status,t.time_started,t.time_ended,d.distance,u.Name FROM Trips t INNER JOIN Cabs c  ON t.cabid=c.id INNER JOIN Users u ON c.uid=u.id  INNER JOIN Distance d ON ((t.src=d.src AND t.dest=d.dest) OR (t.src=d.dest AND t.dest=d.src)) INNER JOIN Location l on t.src=l.id INNER JOIN location l2 ON t.dest=l2.id WHERE t.time_started>=CURRENT_DATE ORDER BY t.id desc LIMIT ?,5;");
	   }
	   else if(filter.equals("yesterday")) {
		   ps=con.prepareStatement("SELECT t.id,t.uid,t.cabid,c.type,l.Point AS src,l2.Point AS dest,t.otp,t.Status,t.time_started,t.time_ended,d.distance,u.Name FROM Trips t INNER JOIN Cabs c  ON t.cabid=c.id INNER JOIN Users u ON c.uid=u.id  INNER JOIN Distance d ON ((t.src=d.src\n AND t.dest=d.dest) OR (t.src=d.dest AND t.dest=d.src)) INNER JOIN Location l on t.src=l.id INNER JOIN location l2 ON t.dest=l2.id WHERE t.time_started>=SUBDATE(curdate(),1) AND t.time_started<CURRENT_DATE ORDER BY t.id desc LIMIT ?,5;");
	   }
	   else if(filter.equals("week")) {
		   ps=con.prepareStatement("SELECT t.id,t.uid,t.cabid,c.type,l.Point AS src,l2.Point AS dest,t.otp,t.Status,t.time_started,t.time_ended,d.distance,u.Name FROM Trips t INNER JOIN Cabs c  ON t.cabid=c.id INNER JOIN Users u ON c.uid=u.id  INNER JOIN Distance d ON ((t.src=d.src AND t.dest=d.dest) OR (t.src=d.dest AND t.dest=d.src)) INNER JOIN Location l on t.src=l.id INNER JOIN location l2 ON t.dest=l2.id WHERE YEARWEEK(t.time_started, 1) = YEARWEEK(CURDATE(), 1) ORDER BY t.id desc LIMIT ?,5");
	   }
	   else {
		   ps=con.prepareStatement("SELECT t.id,t.uid,t.cabid,c.type,l.Point AS src,l2.Point AS dest,t.otp,t.Status,t.time_started,t.time_ended,d.distance,u.Name FROM Trips t INNER JOIN Cabs c  ON t.cabid=c.id INNER JOIN Users u ON c.uid=u.id  INNER JOIN Distance d ON ((t.src=d.src AND t.dest=d.dest) OR (t.src=d.dest AND t.dest=d.src)) INNER JOIN Location l on t.src=l.id INNER JOIN location l2 ON t.dest=l2.id ORDER BY t.id desc LIMIT ?,5;");
	   }
	   ps.setInt(1, limit*5);
	   System.out.println(ps.toString());
       ResultSet rs=ps.executeQuery();
       while(rs.next()){
         int id=rs.getInt(Constants.id);
         int uid=rs.getInt("uid");
         int cabid=rs.getInt(Constants.cabid);
         String otp=String.valueOf(rs.getInt(Constants.otp));
         String status=rs.getString(Constants.status);
         Timestamp timeCreated=rs.getTimestamp(Constants.timeStarted);
         Timestamp timeEnded=rs.getTimestamp(Constants.timeEnded); 
         String src=rs.getString(Constants.src);
         String dest=rs.getString(Constants.dest);
         String cabType=rs.getString("Type");
         BigDecimal distance=rs.getBigDecimal("distance");
         String driverName=rs.getString("Name");
         Trip  trip=new Trip(id,uid,cabid,otp,status,timeCreated,timeEnded,src,dest,cabType,distance,driverName);
         CabOperations cab=new CabOperations(getCabById(cabid));
         TripOperations temp=new TripOperations();
         temp.setTrip(trip);
         temp.setCab(cab);
         trips.add(temp);
       }
	   return trips;
   }
   
   protected static ArrayList<CabOperations> cabsLazyLoader(String filter,int limit) throws ClassNotFoundException, SQLException{
	   ArrayList<CabOperations> cabs=new ArrayList<CabOperations>();
	   Connection con=DatabaseConnection.initializeDatabase();
       PreparedStatement ps;
       if(filter.equals("AllCabs")) {
    	   ps=con.prepareStatement("SELECT c.id,c.uid,c.Type,l.Point,c.wallet,c.Status,u.Name FROM Cabs c INNER JOIN Users u ON  c.uid=u.id INNER JOIN Location l ON c.locid=l.id limit ?,5;");
    	   ps.setInt(1, limit*5);
       }
       else {
    	   ps=con.prepareStatement("SELECT c.id,c.uid,c.Type,l.Point,c.wallet,c.Status,u.Name FROM Cabs c INNER JOIN Users u ON  c.uid=u.id INNER JOIN Location l ON c.locid=l.id WHERE c.Status=? limit ?,5;");
    	   ps.setString(1, filter);
    	   ps.setInt(2, limit);
       }
       ResultSet rs=ps.executeQuery();
       while(rs.next()) {
    	   int id=rs.getInt("id");
    	   int uid=rs.getInt("uid");
    	   String type=rs.getString("Type");
    	   String point=rs.getString("Point");
    	   String name=rs.getString("Name");
    	   String status=rs.getString("Status");
    	   BigDecimal wallet=rs.getBigDecimal("wallet");
    	   cabs.add(new CabOperations(new Cab(id,uid,type,point,wallet,status,name)));
       }
       con.close();
       return cabs;
   }

    public static boolean checkLocsExist(String[] locations) throws SQLException, ClassNotFoundException {
	  boolean locExists=true;
	  String query="SELECT COUNT(id) AS locCount FROM Location WHERE Point IN (";
	  for(int i=0;i<locations.length;i++) {
		  if(i==locations.length-1) {
			  query=query+"?)";
		  }
		  else {
			  query=query+"?,";
		  }
	  }
	  Connection con=DatabaseConnection.initializeDatabase();
      PreparedStatement ps=con.prepareStatement(query);
      for(int i=0;i<locations.length;i++) {
    	  ps.setString(i+1, locations[i]);
      }
      ResultSet rs=ps.executeQuery();
      if(rs.next()) {
    	  if(rs.getInt("locCount")==0) {
    		  locExists=false;
    	  }
      }
      con.close();
	  return locExists;
    }
    
    protected static boolean executeStringQuery(String query) throws ClassNotFoundException, SQLException{
        boolean queryExecuted=false;
        Connection con=DatabaseConnection.initializeDatabase();
        PreparedStatement ps=con.prepareStatement(query);
        ps.executeUpdate();
        queryExecuted=true;
        return queryExecuted;
    }
}
