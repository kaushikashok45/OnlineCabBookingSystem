package com.tomcat_hello_world.Operations.Booking;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.tomcat_hello_world.Entity.Cab;
import com.tomcat_hello_world.Entity.Trip;
import com.tomcat_hello_world.Exceptions.NoUnderwayTripsException;
import com.tomcat_hello_world.Exceptions.TripTimeOutException;
import com.tomcat_hello_world.Storage.DatabaseConnection;
import com.tomcat_hello_world.Utility.Constants;

public class SQLQueries extends DatabaseConnection{
	
	protected static boolean checkLocPairExists(String src,String dest) throws SQLException,ClassNotFoundException,NullPointerException{
        boolean locExists=false;
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Select Count(l.Point) AS locCheck from Location l where (l.Point=? OR l.Point=?);");
            ps.setString(1,src);
            ps.setString(2, dest);
            ResultSet rs=ps.executeQuery();
            int count=0;
            if(rs.next()){
                count=rs.getInt("locCheck");
            }
            if(count==2){
                locExists=true;
            }
            con.close();
  
        return locExists;
    }
	
	protected static void changeCabStatus(int cabid,String status) throws SQLException,ClassNotFoundException,NullPointerException {
    	
        Connection con=DatabaseConnection.initializeDatabase();
        PreparedStatement ps=con.prepareStatement("Update Cabs set Status=? where id=?");
        ps.setString(1,status);
        ps.setInt(2,cabid);
        ps.executeUpdate();
        con.close();

   
    }
	
	protected static void changeCabType(String cabid,String type) throws SQLException,ClassNotFoundException,NullPointerException{
  	  
        Connection con=DatabaseConnection.initializeDatabase();
        PreparedStatement ps=con.prepareStatement("Update Cabs set Type=? where id=?");
        ps.setString(1,type);
        ps.setString(2,cabid);
        ps.executeUpdate();
        
        con.close();
   
    }

    protected static void changeCabLoc(int cabid,int locid) throws SQLException,ClassNotFoundException,NullPointerException{
  
        Connection con=DatabaseConnection.initializeDatabase();
        PreparedStatement ps=con.prepareStatement("Update Cabs set locid=? where id=?");
        ps.setInt(1,locid);
        ps.setInt(2,cabid);
        ps.executeUpdate();
        con.close();
    
    }

    protected static void changeCabWallet(int cabid,BigDecimal newWallet) throws SQLException,ClassNotFoundException,NullPointerException{
	
	  
        Connection con=DatabaseConnection.initializeDatabase();
        PreparedStatement ps=con.prepareStatement("Update Cabs set wallet=? where id=?");
        ps.setBigDecimal(1,newWallet);
        ps.setInt(2,cabid);
        ps.executeUpdate();
        
        con.close();
    
    }

    protected static void changeTripTimeEnded(int cabid) throws SQLException,ClassNotFoundException,NullPointerException{
	  Timestamp ts=new Timestamp(System.currentTimeMillis());
	   
        Connection con=DatabaseConnection.initializeDatabase();
        PreparedStatement ps=con.prepareStatement("Update Trips set time_ended=? where id=?");
        ps.setTimestamp(1,ts);
        ps.setInt(2,cabid);
        ps.executeUpdate();
        
        con.close();
    
    }

    protected static int getLocId(String loc) throws SQLException,ClassNotFoundException,NullPointerException{
	  int locid=0;
	
        Connection con=DatabaseConnection.initializeDatabase();
        PreparedStatement ps=con.prepareStatement("Select * from Location where Point=?;");
        ps.setString(1,loc);
        ResultSet rs=ps.executeQuery();
        if(rs.next()){
        	locid=rs.getInt("id");  	
        }
        con.close();

  
	return locid;
   }
    
    protected static BigDecimal getCabWallet(int cabid) throws SQLException,ClassNotFoundException,NullPointerException{
    	BigDecimal wallet=new BigDecimal("1000");
    	  
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Select wallet from Cabs where id=?;");
            ps.setInt(1,cabid);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
            	  	wallet=rs.getBigDecimal(Constants.wallet);
            }
            con.close();
   
       
    	return wallet;
    }
    	
    	protected static String getLocName(int id) throws SQLException,ClassNotFoundException,NullPointerException{
        	String loc=null;
        	   
                Connection con=DatabaseConnection.initializeDatabase();
                PreparedStatement ps=con.prepareStatement("Select * from Location where id=?;");
                ps.setInt(1,id);
                ResultSet rs=ps.executeQuery();
                if(rs.next()){
                	loc=rs.getString(Constants.Point);  	
                }
                
                con.close();
           
        	return loc;
        } 	
    
    protected static ArrayList<Cab> getCabs() throws SQLException,ClassNotFoundException,NullPointerException{
    	ArrayList<Cab> cabs=new ArrayList<Cab>();
    	  
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Select c.id,c.uid,c.Type,l.Point,c.wallet,c.Status,u.Name from Cabs c INNER JOIN Users u ON c.uid=u.id INNER JOIN Location l ON c.locid=l.id WHERE locid=? AND Type=? AND Status=?;");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
            	int id=rs.getInt(Constants.id);
            	int uid=rs.getInt(Constants.uid);
            	String type=rs.getString(Constants.type);
            	String loc= rs.getString(Constants.Point);
            	BigDecimal wallet=rs.getBigDecimal(Constants.wallet);
            	String status=rs.getString(Constants.status);
            	String driverName=rs.getString("Name");
            	Cab c=new Cab(id,uid,type,loc,wallet,status,driverName);
            	cabs.add(c);
            }
            con.close();
   
        
    	return cabs;
    }
    
    protected static ArrayList<Cab> getFreeCabs(String cloc,String carType) throws SQLException,ClassNotFoundException,NullPointerException{
    	ArrayList<Cab> cabs=new ArrayList<Cab>();
    	int locid=getLocId(cloc);
    	   
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Select c.id,c.uid,c.Type,l.Point,c.wallet,c.Status,u.Name from Cabs c INNER JOIN Users u ON c.uid=u.id INNER JOIN Location l ON c.locid=l.id WHERE locid=? AND Type=? AND Status=?;");
            ps.setInt(1,locid);
            ps.setString(2,carType);
            ps.setString(3,"Available");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
            	int id=rs.getInt(Constants.id);
            	int uid=rs.getInt(Constants.uid);
            	String type=rs.getString(Constants.type);
            	String loc= rs.getString(Constants.Point);
            	BigDecimal wallet=rs.getBigDecimal(Constants.wallet);
            	String status=rs.getString(Constants.status);
            	String driverName=rs.getString(Constants.name);
            	Cab c=new Cab(id,uid,type,loc,wallet,status,driverName);
            	cabs.add(c);
            }
            con.close();
   
    	return cabs;
    }
    
    protected static ArrayList<Cab> getUnderwayCabs(String cloc,String carType) throws SQLException,ClassNotFoundException,NullPointerException{
    	ArrayList<Cab> cabs=new ArrayList<Cab>();
    	int locid=getLocId(cloc);
    	   
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Select c.id,c.uid,c.Type,l.Point,c.wallet,c.Status,u.Name from Cabs c INNER JOIN Users u ON c.uid=u.id INNER JOIN Trips t ON c.id=t.cabid INNER JOIN Location l ON c.locid=l.id WHERE c.locid=? AND c.Type=? AND t.Status=? AND ROUND(time_to_sec(TIMEDIFF(CURRENT_TIMESTAMP(),t.time_started))/60)>5;");
            ps.setInt(1,locid);
            ps.setString(2,carType);
            ps.setString(3,"Underway");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
            	int id=rs.getInt(Constants.id);
            	int uid=rs.getInt(Constants.uid);
            	String type=rs.getString(Constants.type);
            	String loc= rs.getString(Constants.Point);
            	BigDecimal wallet=rs.getBigDecimal(Constants.wallet);
            	String status=rs.getString(Constants.status);
            	String driverName=rs.getString(Constants.name);
            	Cab c=new Cab(id,uid,type,loc,wallet,status,driverName);
            	cabs.add(c);
            }
            con.close();
   
    	return cabs;
    }
    
    protected static ArrayList<Cab> getUnderwayCabs(String cloc,String carType,int range) throws SQLException,ClassNotFoundException,NullPointerException{
    	ArrayList<Cab> cabs=new ArrayList<Cab>();
    	Connection con=DatabaseConnection.initializeDatabase();
        PreparedStatement ps=con.prepareStatement(" SELECT c.id,c.uid,c.Type,c.locid,c.wallet,c.Status,d.Distance,u.Name,l.Point FROM Cabs c INNER JOIN Users u ON c.uid=u.id INNER JOIN Location l2 ON l2.Point=? INNER JOIN Distance d ON ((d.src=l2.id AND c.locid=d.dest) OR(c.locid=src AND d.dest=l2.id)) INNER JOIN Location l ON c.locid=l.id INNER JOIN Trips t ON c.id=t.cabid WHERE c.locid!=l2.id AND d.Distance=? AND c.status=? AND c.Type=? AND t.Status=? AND  ROUND(time_to_sec(TIMEDIFF(CURRENT_TIMESTAMP(),t.time_started))/60)>5");
        ps.setString(1,cloc);
        ps.setBigDecimal(2,new BigDecimal(range));
        ps.setString(3,Constants.cabStatus2);
        ps.setString(4, carType);
        ps.setString(5,Constants.tripStatus1);
        ResultSet rs=ps.executeQuery();
        while(rs.next()) {
        	int id=rs.getInt(Constants.id);
        	int uid=rs.getInt(Constants.uid);
        	String type=rs.getString(Constants.type);
        	String loc= rs.getString(Constants.Point);
        	BigDecimal wallet=rs.getBigDecimal(Constants.wallet);
        	String status=rs.getString(Constants.status);
        	String driverName=rs.getString(Constants.name);
        	Cab c=new Cab(id,uid,type,loc,wallet,status,driverName);
        	cabs.add(c);
        }
    	return cabs;
    }
    
    protected static ArrayList<Integer> getNearByLocs(int locid,int range) throws SQLException,ClassNotFoundException,NullPointerException{
    	ArrayList<Integer> locs=new ArrayList<Integer>();
    	 
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Select * from Distance WHERE (src=?  OR dest=?) AND Distance=?;");
            BigDecimal dist=new BigDecimal(range);
            ps.setInt(1,locid);
            ps.setInt(2,locid);
            ps.setBigDecimal(3,dist);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
            	int src=rs.getInt(Constants.src);
            	int dest=rs.getInt(Constants.dest);
            	if(src!=locid) {
            		locs.add(src);
            	}
            	else if(dest!=locid){
            		locs.add(dest);
            	}
            }
            con.close();
   
       
    	return locs;
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
    
    protected static ArrayList<Cab> getFreeCabs(String cloc,String carType,int range) throws SQLException,ClassNotFoundException,NullPointerException{
    	ArrayList<Cab> cabs=new ArrayList<Cab>();
    	Connection con=DatabaseConnection.initializeDatabase();
        PreparedStatement ps=con.prepareStatement("SELECT c.id,c.uid,c.Type,c.locid,c.wallet,c.Status,d.Distance,u.Name,l.Point FROM Cabs c INNER JOIN Users u ON c.uid=u.id INNER JOIN Location l2 ON l2.Point=? INNER JOIN Distance d ON ((d.src=l2.id AND c.locid=d.dest) OR(c.locid=src AND d.dest=l2.id)) INNER JOIN Location l ON c.locid=l.id WHERE c.locid!=l2.id AND d.Distance=? AND c.status=? AND c.Type=?;");
        ps.setString(1,cloc);
        ps.setBigDecimal(2,new BigDecimal(range));
        ps.setString(3,Constants.cabStatus1);
        ps.setString(4, carType);
        ResultSet rs=ps.executeQuery();
        while(rs.next()) {
        	int id=rs.getInt(Constants.id);
        	int uid=rs.getInt(Constants.uid);
        	String type=rs.getString(Constants.type);
        	String loc= rs.getString(Constants.Point);
        	BigDecimal wallet=rs.getBigDecimal(Constants.wallet);
        	String status=rs.getString(Constants.status);
        	String driverName=rs.getString(Constants.name);
        	Cab c=new Cab(id,uid,type,loc,wallet,status,driverName);
        	cabs.add(c);
        }
    	return cabs;
    }
    
    protected static ArrayList<String> getLocations() throws SQLException,ClassNotFoundException,NullPointerException{
    	ArrayList<String> locs=new ArrayList<String>();
    	
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Select * from Location;");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
            	String loc=rs.getString(Constants.Point);
            	locs.add(loc);
            }
            con.close();
   
         
        
    	return locs;
    }
    
    protected static BigDecimal getDistance(String src,String dest) throws SQLException,ClassNotFoundException,NullPointerException{
    	BigDecimal dist=null;
    	  
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Select Distance from Distance INNER JOIN Location l1 ON l1.Point=? INNER JOIN Location l2 ON l2.Point=?  where (src=l1.id AND dest=l2.id) OR (src=l2.id AND dest=l1.id);");
            ps.setString(1,src);
            ps.setString(2,dest);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
            	dist=rs.getBigDecimal(Constants.distance);
            	
            }
            con.close();
   
      
    	return dist;
    }
    
    protected static boolean insertTrip(int uid,int cabid,int src,int dest,String otp,String status,int time) throws SQLException,ClassNotFoundException,NullPointerException{
    	boolean insertedTrip=false;
    	
    		int OTP=Integer.parseInt(otp);
    		Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("insert into Trips(uid,cabid,src,dest,otp,Status,time_ended) VALUES(?,?,?,?,?,?,?)");
            ps.setInt(1,uid);
            ps.setInt(2, cabid);
            ps.setInt(3, src);
            ps.setInt(4, dest);
            ps.setInt(5,OTP);
            ps.setString(6,status);
            Date date = new Date();
            Timestamp timestamp2 = new Timestamp(date.getTime());
            ps.setTimestamp(7,new Timestamp(timestamp2.getTime()+(TimeUnit.MINUTES.toMillis(time))));
            ps.executeUpdate();
            con.close();
            insertedTrip=true;
        
    	return insertedTrip;
    } 
    
    protected static int getPointsId(String src,String dest) throws SQLException,ClassNotFoundException,NullPointerException{
    	int pid=0;
    	
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Select d.id from Distance d INNER JOIN Location l1 ON l1.Point=? INNER JOIN Location l2 ON l2.Point=?  where (src=l1.id AND dest=l2.id) OR (src=l2.id AND dest=l1.id);");
            ps.setString(1,src);
            ps.setString(2,dest);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
            	pid=rs.getInt("id");
            	
            }
            con.close();
   
      
    	return pid;
    }
    
    protected static int getLastTripId(int uid) throws SQLException,ClassNotFoundException,NullPointerException{
    	int id=0;
       
          Connection con=DatabaseConnection.initializeDatabase();
          PreparedStatement ps=con.prepareStatement("SELECT MAX(id) AS id FROM Trips WHERE uid=?");
          ps.setInt(1,uid);
          ResultSet rs=ps.executeQuery();
          if(rs.next()){
            id=rs.getInt(1);
          }
      
       return id;
    }
    
    protected static Trip getTrip(int id) throws SQLException,ClassNotFoundException,NullPointerException{
    	Trip trip=null;
       
          Connection con=DatabaseConnection.initializeDatabase();
          PreparedStatement ps=con.prepareStatement("Select * from Trips where id=?");
          ps.setInt(1, id);
          ResultSet rs=ps.executeQuery();
          
          if(rs.next()){
            int uid=rs.getInt(Constants.uid);
            int cabid=rs.getInt(Constants.cabid);
            int src=rs.getInt(Constants.src);
            int dest=rs.getInt(Constants.dest);
            String otp=String.valueOf(rs.getInt(Constants.otp));
            String status=rs.getString(Constants.status);
            Timestamp timeCreated=rs.getTimestamp(Constants.timeStarted);
            Timestamp timeEnded=rs.getTimestamp(Constants.timeEnded); 
            trip=new Trip(id,uid,cabid,otp,status,timeCreated,timeEnded,getLocName(src),getLocName(dest));
          }
      
       return trip;
    }
    
    protected static ArrayList<TripOperations> getTrips(int uid) throws SQLException,ClassNotFoundException,NullPointerException{
    	ArrayList<TripOperations> trips=new ArrayList<TripOperations>();
       
          Connection con=DatabaseConnection.initializeDatabase();
          PreparedStatement ps=con.prepareStatement("SELECT t.id,t.uid,t.cabid,c.type,l.Point AS src,l2.Point AS dest,t.otp,t.Status,t.time_started,t.time_ended,d.distance,u.Name FROM Trips t INNER JOIN Cabs c  ON t.cabid=c.id INNER JOIN Users u ON c.uid=u.id  INNER JOIN Distance d ON ((t.src=d.src AND t.dest=d.dest) OR (t.src=d.dest AND t.dest=d.src)) INNER JOIN Location l on t.src=l.id INNER JOIN location l2 ON t.dest=l2.id WHERE t.uid=? ORDER BY t.id desc;");
          ps.setInt(1, uid);
          ResultSet rs=ps.executeQuery();
          
          while(rs.next()){
            int id=rs.getInt(Constants.id);
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
    
    protected static boolean validateAssignedCab(int cabid,int tid) throws ClassNotFoundException, SQLException {
    	 boolean isCabValid=false;
    	 Connection con=DatabaseConnection.initializeDatabase();
    	 PreparedStatement ps=con.prepareStatement("SELECT COUNT(t.id) AS tripCount,c.Status,l.Point FROM Trips t INNER JOIN Cabs c ON t.cabid=c.id INNER JOIN Location l ON c.locid=l.id WHERE t.status=? AND t.cabid=? AND t.id>?;");
    	 ps.setString(1,Constants.tripStatus1);
         ps.setInt(2,cabid);
         ps.setInt(3,tid);
         ResultSet rs=ps.executeQuery();
         int tripCount=0;
         String status=null;
         if(rs.next()) {
          tripCount=rs.getInt("tripCount");
          status=rs.getString("Status");
         } 
         con.close();
         if(status!=null) {
          if((tripCount==0) && (status.equals("Booked"))){
        	 isCabValid=true;
          }
         }
         else {
        	 isCabValid=true;
         }
         return isCabValid;
    }
    
    protected static boolean validateCabAvailability(int cabid) throws ClassNotFoundException, SQLException {
   	 boolean isCabValid=false;
   	 Connection con=DatabaseConnection.initializeDatabase();
   	 PreparedStatement ps=con.prepareStatement("SELECT * FROM ( SELECT c.Status FROM Cabs c WHERE c.id=?) AS A CROSS JOIN (SELECT Count(t.id) AS tripCount FROM Trips t WHERE t.Status=? AND ROUND(time_to_sec(TIMEDIFF(CURRENT_TIMESTAMP(),t.time_started))/60)<5 AND t.cabid=?) AS B;");
        ps.setInt(1,cabid);
        ps.setString(2,Constants.tripStatus1);
        ps.setInt(3, cabid);
        ResultSet rs=ps.executeQuery();
        String status=null;
        int tripCount=1;
        if(rs.next()) {
        	status=rs.getString("Status");
        	tripCount=rs.getInt("tripCount");
        }
        con.close();
        if(status.equals("Available")){
          	 isCabValid=true;
           }
        else if(status.equals("Booked")&&(tripCount==0)) {
        	isCabValid=true;
        }
        return isCabValid;
   }
    
    protected static void changeTripStatus(int tid,String status) throws SQLException,ClassNotFoundException,NullPointerException{
    	    
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Update Trips set Status=? where id=?");
            ps.setString(1,status);
            ps.setInt(2,tid);
            ps.executeUpdate();
            
            con.close();
        
    }
    
    protected static TripOperations getTripsByStatus(int uid,String status,int tid,int cabid) throws SQLException, ClassNotFoundException, TripTimeOutException, NoUnderwayTripsException {
    	TripOperations tripOp=null;
    	Connection con=DatabaseConnection.initializeDatabase();
        PreparedStatement ps=con.prepareStatement("SELECT * FROM(SELECT t.id,t.uid,t.cabid,t.src AS srcid,t.dest AS destid,l.Point AS src,l2.Point AS dest,t.otp,t.Status AS tripStatus,t.time_started,t.time_ended,ROUND(time_to_sec(TIMEDIFF(CURRENT_TIMESTAMP(),t.time_started))/60) AS time_lapsed,c.Status AS cabStatus,c.wallet FROM Trips t INNER JOIN Location l ON t.src=l.id INNER JOIN Location l2 ON t.dest=l2.id INNER JOIN Cabs c ON t.cabid=c.id WHERE t.Status=? AND t.uid=? ) AS A CROSS JOIN (SELECT COUNT(t.id) AS tripCount FROM Trips t WHERE t.id>? AND t.cabid=? AND t.status=?) AS B;");
        ps.setString(1,status);
        ps.setInt(2, uid);
        ps.setInt(3, tid);
        ps.setInt(4, cabid);
        ps.setString(5, status);
        ResultSet rs=ps.executeQuery();
        if(rs.next()) {
        	int id=rs.getInt("id");
        	int timeLapsed=rs.getInt("time_lapsed");
        	BigDecimal fare=rs.getBigDecimal("wallet").add(new BigDecimal("100"));
        	int tripCount=rs.getInt("tripCount");
        	String cabStatus=rs.getString("cabStatus");
        	int srcid=rs.getInt("srcid");
        	String otp=rs.getString("otp");
        	Timestamp ts=rs.getTimestamp("time_started");
        	String src=rs.getString("src");
        	String dest=rs.getString("dest");
        	Timestamp te=rs.getTimestamp("time_ended");
        	if(timeLapsed>5 && cabStatus.equals(Constants.cabStatus2) && (tripCount==0)) {
        		cancelTrip(fare,srcid,id);
        		throw new TripTimeOutException();
        	}
        	else if((timeLapsed>5 && cabStatus.equals("Available"))||(timeLapsed>5 && cabStatus.equals(Constants.cabStatus2) && (tripCount>0))) {
        		cancelUnderwayTrip(fare,id);
        		throw new TripTimeOutException();
        	}
        	else {
        		tripOp=new TripOperations();
        		Trip trip=new Trip(id,uid,cabid,otp,status,ts,te,src,dest);
        		tripOp.setTrip(trip);
        		CabOperations cab=new CabOperations(getCabById(cabid));
        		tripOp.setCab(cab);
        		tripOp.getTrip().setDistance(SQLQueries.getDistance(tripOp.getTrip().getSrc(),tripOp.getTrip().getDest()));
        		tripOp.getTrip().setFare(tripOp.getCab().getAssignedCab().getType(),tripOp.getTrip().getDistance());
        	
            }
        }
        else {
        	throw new NoUnderwayTripsException();
        }
        con.close();
    	return tripOp;
    }

    protected static boolean checkDriverExists(String email) throws SQLException, ClassNotFoundException{
        boolean doesDriverExist=true;
        Connection con=DatabaseConnection.initializeDatabase();
        PreparedStatement ps=con.prepareStatement("SELECT COUNT(c.id) AS driverCount FROM Cabs c INNER JOIN Users u ON c.uid=u.id WHERE u.email='"+email+"';");
        ResultSet rs=ps.executeQuery();
        System.out.println(rs.getStatement().toString());
        if(rs.next()){
            int count=rs.getInt("driverCount");
            System.out.println(email);
            System.out.println(count);
            if(count==0){
                doesDriverExist=false;
            }
        }
        con.close();
        return doesDriverExist;
    }

    protected static boolean executeStringQuery(String query) throws ClassNotFoundException, SQLException{
        boolean queryExecuted=false;
        Connection con=DatabaseConnection.initializeDatabase();
        PreparedStatement ps=con.prepareStatement(query);
        ps.executeUpdate();
        queryExecuted=true;
        return queryExecuted;
    }
    
    protected static boolean startTrip(BigDecimal fare,int locid,int id) throws SQLException,ClassNotFoundException,NullPointerException{
   	    boolean isTripStarted=false;
    	Connection con=DatabaseConnection.initializeDatabase();
        PreparedStatement ps=con.prepareStatement("UPDATE Cabs c INNER JOIN Trips t ON c.id=t.cabid set c.Status=?,c.wallet=?,c.locid=?,t.Status=? WHERE t.id=?;"); 
        ps.setString(1,Constants.cabStatus1);
        ps.setBigDecimal(2,fare);
        ps.setInt(3, locid);
        ps.setString(4, Constants.tripStatus2);
        ps.setInt(5,id);
        ps.executeUpdate();
        con.close();
        isTripStarted=true;
        return isTripStarted;
   }
    
    protected static boolean cancelTrip(BigDecimal fare,int locid,int id) throws SQLException,ClassNotFoundException,NullPointerException{
    	 boolean isTripcancelled=false;
    	 Timestamp ts=new Timestamp(System.currentTimeMillis());
    	 Connection con=DatabaseConnection.initializeDatabase();
         PreparedStatement ps=con.prepareStatement("UPDATE Cabs c INNER JOIN Trips t ON c.id=t.cabid set c.Status=?,c.wallet=?,c.locid=?,t.Status=?,t.time_ended=? WHERE t.id=?;"); 
         ps.setString(1,Constants.cabStatus1);
         ps.setBigDecimal(2,fare);
         ps.setInt(3, locid);
         ps.setString(4, Constants.tripStatus3);
         ps.setTimestamp(5, ts);
         ps.setInt(6,id);
         ps.executeUpdate();
         con.close();
         isTripcancelled=true;
         return isTripcancelled;
     
    }
    
    protected static boolean cancelUnderwayTrip(BigDecimal fare,int id) throws SQLException,ClassNotFoundException,NullPointerException{
   	 boolean isTripcancelled=false;
   	 Timestamp ts=new Timestamp(System.currentTimeMillis());
   	 Connection con=DatabaseConnection.initializeDatabase();
        PreparedStatement ps=con.prepareStatement("UPDATE Cabs c INNER JOIN Trips t ON c.id=t.cabid set c.wallet=?,t.Status=?,t.time_ended=? WHERE t.id=?;"); 
        ps.setBigDecimal(1,fare);
        ps.setString(2, Constants.tripStatus3);
        ps.setTimestamp(3, ts);
        ps.setInt(4,id);
        ps.executeUpdate();
        con.close();
        isTripcancelled=true;
        return isTripcancelled;
    
   }
    
    protected static boolean deleteTripsByCab(String query) throws ClassNotFoundException, SQLException {
 	   boolean cabsDeleted=false;
 	   Connection con=DatabaseConnection.initializeDatabase();
        PreparedStatement ps=con.prepareStatement(query);
        System.out.println(ps.toString()+"649");
        ps.executeUpdate();
        cabsDeleted=true;
 	   return cabsDeleted;
    }
    
   protected static boolean deleteCabs(String query,String tripsQuery) throws ClassNotFoundException, SQLException {
	   boolean cabsDeleted=false;
	   boolean tripsDeleted=deleteTripsByCab(tripsQuery);
	   System.out.println(tripsDeleted+"658");
	   System.out.println(query+"659");
	   if(tripsDeleted) {
		   Connection con=DatabaseConnection.initializeDatabase();
	       PreparedStatement ps=con.prepareStatement(query);
	       System.out.println(ps.toString());
	       ps.executeUpdate();
	       cabsDeleted=true;
	   }
	   return cabsDeleted;
   } 
    
    
}
