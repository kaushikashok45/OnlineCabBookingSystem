package com.tomcat_hello_world.Operations.Authentication;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.tomcat_hello_world.Entity.Cab;
import com.tomcat_hello_world.Entity.Trip;
import com.tomcat_hello_world.Entity.User;
import com.tomcat_hello_world.Operations.Booking.CabOperations;
import com.tomcat_hello_world.Operations.Booking.TripOperations;
import com.tomcat_hello_world.Storage.DatabaseConnection;
import com.tomcat_hello_world.Utility.Constants;
import com.tomcat_hello_world.Utility.Encryptor;

public class SQLQueries extends DatabaseConnection{
	 protected static boolean checkEmailExists(String email) throws SQLException,ClassNotFoundException,NullPointerException{
	        boolean emailExists=false;
	        Connection con=DatabaseConnection.initializeDatabase();
	        PreparedStatement ps=con.prepareStatement("Select Count(*) from Users  where Email=?");
	        ps.setString(1,email);
	        ResultSet rs=ps.executeQuery();
	        int count=0;
	        if(rs.next()){
	              count=rs.getInt(1);
	        }
	        if(count>0){
	           emailExists=true;
	        }
	        con.close();
	       
	        return emailExists;
	    }
	 
	 protected static boolean checkUserStatus(int id) throws ClassNotFoundException, SQLException {
		 boolean userValid=false;
		 Connection con=DatabaseConnection.initializeDatabase();
	     PreparedStatement ps=con.prepareStatement("SELECT Count(t.id) AS trips FROM Trips t WHERE t.id=? AND t.status=?;");
	     ps.setInt(1, id);
	     ps.setString(2,"Underway");
	     ResultSet rs=ps.executeQuery();
	     if(rs.next()) {
	    	 if(rs.getInt("trips")==0) {
	    		 userValid=true;
	    	 }
	     }
		 return userValid;
	 }
	 
	 protected static boolean deleteAccountTrips(int id) throws ClassNotFoundException, SQLException {
		 boolean tripsDeleted=false;
		 Connection con=DatabaseConnection.initializeDatabase();
	     PreparedStatement ps=con.prepareStatement("DELETE FROM Trips t WHERE t.uid=?");
	     ps.setInt(1, id);
	     ps.executeUpdate();
	     tripsDeleted=true;
		 return tripsDeleted;
	 }
	 
	 protected static boolean deleteAccount(int id) throws ClassNotFoundException, SQLException {
		 boolean usersDeleted=false;
		 Connection con=DatabaseConnection.initializeDatabase();
	     PreparedStatement ps=con.prepareStatement("DELETE FROM Users u WHERE u.id=?");
	     ps.setInt(1, id);
	     ps.executeUpdate();
	     usersDeleted=true;
		 return usersDeleted;
	 }
	 
	     protected static boolean[] checkEmailExists(String email,String role) throws SQLException,ClassNotFoundException,NullPointerException{
	        boolean[] emailExists= {false,false};
	        Connection con=DatabaseConnection.initializeDatabase();
	        PreparedStatement ps=con.prepareStatement("SELECT Email,Role FROM Users WHERE email=?;");
	        ps.setString(1,email);
	        ResultSet rs=ps.executeQuery();
	        String Role=null,Email=null;
	        if(rs.next()){
	              Email=rs.getString("Email");
	              Role=rs.getString("Role");
	        }
	        if(Email!=null && Role!=null) {
	        	if(email.equals(Email)){
	  	           emailExists[0]=true;
	  	        }
	 
	  	        if(role.equals(Role)) {
	  	        	emailExists[1]=true;
	  	        }
	        }
	        con.close();
	       
	        return emailExists;
	    }
	 
	 protected static boolean checkMultipleEmailsExist(String emails) throws ClassNotFoundException, SQLException {
		 boolean emailsExist=true;
		 Connection con=DatabaseConnection.initializeDatabase();
		 String query="SELECT COUNT(id) as emailCount FROM USERS WHERE Email IN "+emails;
		 PreparedStatement ps=con.prepareStatement(query);
		 ResultSet rs=ps.executeQuery();
		 if(rs.next()) {
			 if(rs.getInt("emailCount")==0) {
				 emailsExist=false;
			 }
		 }
		 return emailsExist;
	 }
	 
	 protected static boolean addUsers(String query) throws ClassNotFoundException, SQLException {
		 boolean addedUsers=false;
		 Connection con=DatabaseConnection.initializeDatabase();
		 PreparedStatement ps=con.prepareStatement(query);
		 ps.executeUpdate();
		 addedUsers=true;
		 return addedUsers;
	 }
	 
	 protected static boolean checkPassword(String email,String password) throws SQLException,ClassNotFoundException,NullPointerException,NoSuchAlgorithmException{
	        boolean isEqual=false;
	        
	            Connection con=DatabaseConnection.initializeDatabase();
	            PreparedStatement ps=con.prepareStatement("Select Password from Users  where Email=?");
	            ps.setString(1,email);
	            ResultSet rs=ps.executeQuery();
	            String pwd=null;
	            if(rs.next()){
	                pwd=rs.getString(Constants.password);
	            }
	            if(pwd.equals(password)){
	                isEqual=true;
	            }
	      
	        return isEqual;
	    }
	 
	 protected static boolean insertUser(String name,String email,String pwd,String role) throws SQLException,ClassNotFoundException,NullPointerException, NoSuchAlgorithmException{
	        boolean userInserted=false;
	        
	            Connection con=DatabaseConnection.initializeDatabase();
	            PreparedStatement ps=con.prepareStatement("insert into Users(Name,Email,Password,Role) VALUES(?,?,?,?)");
	            String password=Encryptor.encrypt(pwd);
	            ps.setString(1, name);
	            ps.setString(2, email);
	            ps.setString(3, password);
	            ps.setString(4,role);
	            ps.executeUpdate();
	            ps.close();
	            con.close();
	            userInserted=true;
	            
	       
	        return userInserted;
	    }
	 
	 protected static String getUserName(String email) throws SQLException,ClassNotFoundException,NullPointerException{
	        String name=null;
	         
	          Connection con=DatabaseConnection.initializeDatabase();
	          PreparedStatement ps=con.prepareStatement("Select Name from Users  where Email=?");
	          ps.setString(1,email);
	          ResultSet rs=ps.executeQuery();
	          if(rs.next()){
	            name=rs.getString(Constants.name);
	          }
	          con.close();
	     
	       return name;
	    }

      protected static int getIdFromEmail(String email) throws ClassNotFoundException, SQLException{
        int id=0;
        Connection con=DatabaseConnection.initializeDatabase();
        PreparedStatement ps=con.prepareStatement("Select id FROM Users WHERE email=?");
        ps.setString(1, email);
        ResultSet rs=ps.executeQuery();
        if(rs.next()){
          id=rs.getInt("id");
        }
        con.close();
        return id;
      }
	 
	 protected static User getUser(String email) throws SQLException,ClassNotFoundException,NullPointerException{
         User user=null;
        
         Connection con=DatabaseConnection.initializeDatabase();
         PreparedStatement ps=con.prepareStatement("Select * from Users WHERE email=?");
         ps.setString(1,email);
         ResultSet rs=ps.executeQuery();
         if(rs.next()){
           user=new User(rs.getInt("id"),rs.getString(Constants.name),rs.getString(Constants.bigEmail),rs.getString(Constants.password),rs.getString(Constants.role));
         }
         con.close();
    
      return user;
   }
   
   protected static String getUserNameById(int id) throws SQLException,ClassNotFoundException,NullPointerException{
       String name=null;
        
         Connection con=DatabaseConnection.initializeDatabase();
         PreparedStatement ps=con.prepareStatement("Select Name AS name FROM Users WHERE id=?");
         ps.setInt(1,id);
         ResultSet rs=ps.executeQuery();
         if(rs.next()){
           name=rs.getString("name");
         }
         con.close();
    
      return name;
   }

   protected static String getUserId(String email) throws SQLException,ClassNotFoundException,NullPointerException {
       String id=null;
       
         Connection con=DatabaseConnection.initializeDatabase();
         PreparedStatement ps=con.prepareStatement("Select id from Users  where Email=?");
         ps.setString(1,email);
         ResultSet rs=ps.executeQuery();
         if(rs.next()){
           id=Integer.toString(rs.getInt(Constants.id));
         }
         con.close();
      
      return id;
   } 
   
   protected static String getUserType(String email) throws SQLException,ClassNotFoundException,NullPointerException{
   	String userType=null;
   	  
           Connection con=DatabaseConnection.initializeDatabase();
           PreparedStatement ps=con.prepareStatement("Select Role from Users  where Email=?");
           ps.setString(1,email);
           ResultSet rs=ps.executeQuery();
           if(rs.next()){
             userType=rs.getString(Constants.role);
           }
           con.close();
        
   	return userType;
   }
   
   protected static void changeName(String name,String email) throws SQLException,ClassNotFoundException,NullPointerException {
   	  
           Connection con=DatabaseConnection.initializeDatabase();
           PreparedStatement ps=con.prepareStatement("Update Users set Name=? where Email=?");
           ps.setString(1,name);
           ps.setString(2,email);
           ps.executeUpdate();
           con.close();
  
       
   }
   
   protected static void changeEmail(String email,String oldEmail) throws SQLException,ClassNotFoundException,NullPointerException{
   	 
           Connection con=DatabaseConnection.initializeDatabase();
           PreparedStatement ps=con.prepareStatement("Update Users set Email=? where Email=?");
           ps.setString(1,email);
           ps.setString(2,oldEmail);
           ps.executeUpdate();
           con.close();
  
        
   }
   
   protected static void changePassword(String pwd,String email) throws SQLException,ClassNotFoundException,NullPointerException{
   	
           Connection con=DatabaseConnection.initializeDatabase();
           PreparedStatement ps=con.prepareStatement("Update Users set Password=? where Email=?");
           ps.setString(1,pwd);
           ps.setString(2,email);
           ps.executeUpdate();
           con.close();
  
      
   }
   
   protected static ArrayList<String> getUserDetails(int id) throws SQLException,ClassNotFoundException,NullPointerException{
	    ArrayList<String> userDetails=new ArrayList<String>();   	
       Connection con=DatabaseConnection.initializeDatabase();
       PreparedStatement ps=con.prepareStatement("Select Name,Email FROM Users where id=?");
       ps.setInt(1,id);
       ResultSet rs=ps.executeQuery();
       if(rs.next()){
       	userDetails.add(rs.getString("Name"));  	
       	userDetails.add(rs.getString("Email"));
       }
       con.close();

 
	return userDetails;
   }
   
   protected static boolean updateProfile(String query,String param,int id) throws SQLException,ClassNotFoundException,NullPointerException{
   	boolean isUpdated=false;
   	Connection con=DatabaseConnection.initializeDatabase();
       PreparedStatement ps=con.prepareStatement(query);
       ps.setString(1, param);
       ps.setInt(2, id);
       ps.executeUpdate();
       isUpdated=true;
   	return isUpdated;
   }
   
   protected static boolean updateProfile(String query,String param1,String param2,int id) throws SQLException,ClassNotFoundException,NullPointerException{
   	boolean isUpdated=false;
   	Connection con=DatabaseConnection.initializeDatabase();
       PreparedStatement ps=con.prepareStatement(query);
       ps.setString(1, param1);
       ps.setString(2, param2);
       ps.setInt(3, id);
       ps.executeUpdate();
       isUpdated=true;
   	return isUpdated;
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
   
    protected static  ArrayList<TripOperations> tripsLazyLoader(String filter,int startIndex,int userid) throws ClassNotFoundException, SQLException {
	   ArrayList<TripOperations> trips=new ArrayList<TripOperations>();
	   Connection con=DatabaseConnection.initializeDatabase();
	   PreparedStatement ps=null;
	   if(filter.equals("today")) {
		   ps=con.prepareStatement("SELECT t.id,t.uid,t.cabid,c.type,l.Point AS src,l2.Point AS dest,t.otp,t.Status,t.time_started,t.time_ended,d.distance,u.Name FROM Trips t INNER JOIN Cabs c  ON t.cabid=c.id INNER JOIN Users u ON c.uid=u.id  INNER JOIN Distance d ON ((t.src=d.src AND t.dest=d.dest) OR (t.src=d.dest AND t.dest=d.src)) INNER JOIN Location l on t.src=l.id INNER JOIN location l2 ON t.dest=l2.id WHERE t.time_started>=CURRENT_DATE AND t.uid=? ORDER BY t.id desc LIMIT ?,5;");
	   }
	   else if(filter.equals("yesterday")) {
		   ps=con.prepareStatement("SELECT t.id,t.uid,t.cabid,c.type,l.Point AS src,l2.Point AS dest,t.otp,t.Status,t.time_started,t.time_ended,d.distance,u.Name FROM Trips t INNER JOIN Cabs c  ON t.cabid=c.id INNER JOIN Users u ON c.uid=u.id  INNER JOIN Distance d ON ((t.src=d.src\n AND t.dest=d.dest) OR (t.src=d.dest AND t.dest=d.src)) INNER JOIN Location l on t.src=l.id INNER JOIN location l2 ON t.dest=l2.id WHERE t.time_started>=SUBDATE(curdate(),1) AND t.time_started<CURRENT_DATE ORDER AND t.uid=? BY t.id desc LIMIT ?,5;");
	   }
	   else if(filter.equals("week")) {
		   ps=con.prepareStatement("SELECT t.id,t.uid,t.cabid,c.type,l.Point AS src,l2.Point AS dest,t.otp,t.Status,t.time_started,t.time_ended,d.distance,u.Name FROM Trips t INNER JOIN Cabs c  ON t.cabid=c.id INNER JOIN Users u ON c.uid=u.id  INNER JOIN Distance d ON ((t.src=d.src AND t.dest=d.dest) OR (t.src=d.dest AND t.dest=d.src)) INNER JOIN Location l on t.src=l.id INNER JOIN location l2 ON t.dest=l2.id WHERE YEARWEEK(t.time_started, 1) = YEARWEEK(CURDATE(), 1) AND t.uid=? ORDER BY t.id desc LIMIT ?,5");
	   }
	   else {
		   ps=con.prepareStatement("SELECT t.id,t.uid,t.cabid,c.type,l.Point AS src,l2.Point AS dest,t.otp,t.Status,t.time_started,t.time_ended,d.distance,u.Name FROM Trips t INNER JOIN Cabs c  ON t.cabid=c.id INNER JOIN Users u ON c.uid=u.id  INNER JOIN Distance d ON ((t.src=d.src AND t.dest=d.dest) OR (t.src=d.dest AND t.dest=d.src)) INNER JOIN Location l on t.src=l.id INNER JOIN location l2 ON t.dest=l2.id WHERE t.uid=? ORDER BY t.id desc LIMIT ?,5;");
	   }
	   ps.setInt(1,userid);
	   ps.setInt(2, startIndex*5);
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
}
