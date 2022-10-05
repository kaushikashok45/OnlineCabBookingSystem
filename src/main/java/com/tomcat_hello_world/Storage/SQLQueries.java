package com.tomcat_hello_world.Storage;


import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;


import com.tomcat_hello_world.Entity.Cab;
import com.tomcat_hello_world.Entity.Trip;
import com.tomcat_hello_world.Entity.User;
import com.tomcat_hello_world.Operations.Booking.CabOperations;
import com.tomcat_hello_world.Operations.Booking.TripOperations;
import com.tomcat_hello_world.Utility.*;
import java.util.Date;
import java.util.HashMap;

public class SQLQueries{

    public static boolean checkEmailExists(String email) throws SQLException,ClassNotFoundException,NullPointerException{
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
    
    public static boolean checkLocExist(String loc) throws SQLException,ClassNotFoundException,NullPointerException{
        boolean locExists=false;
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Select Count(*) from Location where Point=?");
            ps.setString(1,loc);
            ResultSet rs=ps.executeQuery();
            int count=0;
            if(rs.next()){
                count=rs.getInt(1);
            }
            if(count>0){
                locExists=true;
            }
            con.close();
  
        return locExists;
    } 

    public static boolean checkPassword(String email,String password) throws SQLException,ClassNotFoundException,NullPointerException,NoSuchAlgorithmException{
        boolean isEqual=false;
        
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Select * from Users  where Email=?");
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

    public static boolean insertUser(String name,String email,String pwd,String role) throws SQLException,ClassNotFoundException,NullPointerException, NoSuchAlgorithmException{
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
    
    public static boolean addLoc(String point) throws SQLException,ClassNotFoundException,NullPointerException{
        boolean locInserted=false;
     
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("insert into Location(Point) VALUES(?)");
            ps.setString(1,point);
            ps.executeUpdate();
            ps.close();
            con.close();
            locInserted=true;
            
        
        return locInserted;
    }

    
    public static boolean insertDistance(int src,int dest,float distance) throws SQLException,ClassNotFoundException,NullPointerException{
        boolean distanceInserted=false;
                   Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("insert into Distance(src,dest,distance) VALUES(?,?,?)");
            BigDecimal d = new BigDecimal(distance);
            ps.setInt(1, src);
            ps.setInt(2, dest);
            ps.setBigDecimal(3, d);
            ps.executeUpdate();
            ps.close();
            con.close();
            distanceInserted=true;
        
        return distanceInserted;
    }

    public static String getUserName(String email) throws SQLException,ClassNotFoundException,NullPointerException{
        String name=null;
         
          Connection con=DatabaseConnection.initializeDatabase();
          PreparedStatement ps=con.prepareStatement("Select * from Users  where Email=?");
          ps.setString(1,email);
          ResultSet rs=ps.executeQuery();
          if(rs.next()){
            name=rs.getString(Constants.name);
          }
          con.close();
     
       return name;
    }
    
    public static User getUser(String email) throws SQLException,ClassNotFoundException,NullPointerException{
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
    
    public static String getUserNameById(int id) throws SQLException,ClassNotFoundException,NullPointerException{
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

    public static String getUserId(String email) throws SQLException,ClassNotFoundException,NullPointerException {
        String id=null;
        
          Connection con=DatabaseConnection.initializeDatabase();
          PreparedStatement ps=con.prepareStatement("Select * from Users  where Email=?");
          ps.setString(1,email);
          ResultSet rs=ps.executeQuery();
          if(rs.next()){
            id=Integer.toString(rs.getInt(Constants.id));
          }
          con.close();
       
       return id;
    } 
    
    public static String getUserType(String email) throws SQLException,ClassNotFoundException,NullPointerException{
    	String userType=null;
    	  
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Select * from Users  where Email=?");
            ps.setString(1,email);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
              userType=rs.getString(Constants.role);
            }
            con.close();
         
    	return userType;
    }
    
    public static void changeName(String name,String email) throws SQLException,ClassNotFoundException,NullPointerException {
    	  
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Update Users set Name=? where Email=?");
            ps.setString(1,name);
            ps.setString(2,email);
            ps.executeUpdate();
            con.close();
   
        
    }
    
    public static void changeEmail(String email,String oldEmail) throws SQLException,ClassNotFoundException,NullPointerException{
    	 
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Update Users set Email=? where Email=?");
            ps.setString(1,email);
            ps.setString(2,oldEmail);
            ps.executeUpdate();
            con.close();
   
         
    }
    
    public static void changePassword(String pwd,String email) throws SQLException,ClassNotFoundException,NullPointerException{
    	
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Update Users set Password=? where Email=?");
            ps.setString(1,pwd);
            ps.setString(2,email);
            ps.executeUpdate();
            con.close();
   
       
    }
    
    public static void changeCabStatus(int cabid,String status) throws SQLException,ClassNotFoundException,NullPointerException {
    	
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Update Cabs set Status=? where id=?");
            ps.setString(1,status);
            ps.setInt(2,cabid);
            ps.executeUpdate();
            con.close();
   
       
    }
    
    public static void changeCabType(String cabid,String type) throws SQLException,ClassNotFoundException,NullPointerException{
    	  
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Update Cabs set Type=? where id=?");
            ps.setString(1,type);
            ps.setString(2,cabid);
            ps.executeUpdate();
            
            con.close();
       
    }
    
    public static void changeCabLoc(int cabid,int locid) throws SQLException,ClassNotFoundException,NullPointerException{
      
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Update Cabs set locid=? where id=?");
            ps.setInt(1,locid);
            ps.setInt(2,cabid);
            ps.executeUpdate();
            
        
    }
    
    public static void changeCabWallet(int cabid,BigDecimal newWallet) throws SQLException,ClassNotFoundException,NullPointerException{
    	
    	  
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Update Cabs set wallet=? where id=?");
            ps.setBigDecimal(1,newWallet);
            ps.setInt(2,cabid);
            ps.executeUpdate();
            
            con.close();
        
    }
    
public static void changeTripTimeEnded(int cabid) throws SQLException,ClassNotFoundException,NullPointerException{
    	Timestamp ts=new Timestamp(System.currentTimeMillis());
    	   
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Update Trips set time_ended=? where id=?");
            ps.setTimestamp(1,ts);
            ps.setInt(2,cabid);
            ps.executeUpdate();
            
            con.close();
        
    }
    
    public static int getLocId(String loc) throws SQLException,ClassNotFoundException,NullPointerException{
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
    
    public static ArrayList<String> getUserDetails(int id) throws SQLException,ClassNotFoundException,NullPointerException{
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
    
    public static BigDecimal getCabWallet(int cabid) throws SQLException,ClassNotFoundException,NullPointerException{
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
    	
    	public static String getLocName(int id) throws SQLException,ClassNotFoundException,NullPointerException{
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
    
    public static ArrayList<Cab> getCabs() throws SQLException,ClassNotFoundException,NullPointerException{
    	ArrayList<Cab> cabs=new ArrayList<Cab>();
    	  
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Select * from Cabs;");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
            	int id=rs.getInt(Constants.id);
            	int uid=rs.getInt(Constants.uid);
            	String type=rs.getString(Constants.type);
            	String loc= getLocName(rs.getInt(Constants.location));
            	BigDecimal wallet=rs.getBigDecimal(Constants.wallet);
            	String status=rs.getString(Constants.status);
            	Cab c=new Cab(id,uid,type,loc,wallet,status,getUserNameById(uid));
            	cabs.add(c);
            }
            con.close();
   
        
    	return cabs;
    }
    
    public static ArrayList<Cab> getFreeCabs(String cloc,String carType) throws SQLException,ClassNotFoundException,NullPointerException{
    	ArrayList<Cab> cabs=new ArrayList<Cab>();
    	int locid=getLocId(cloc);
    	   
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Select * from Cabs WHERE locid=? AND Type=? AND Status=?;");
            ps.setInt(1,locid);
            ps.setString(2,carType);
            ps.setString(3,"Available");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
            	int id=rs.getInt(Constants.id);
            	int uid=rs.getInt(Constants.uid);
            	String type=rs.getString(Constants.type);
            	String loc= getLocName(rs.getInt(Constants.locid));
            	BigDecimal wallet=rs.getBigDecimal(Constants.wallet);
            	String status=rs.getString(Constants.status);
            	Cab c=new Cab(id,uid,type,loc,wallet,status,getUserNameById(uid));
            	cabs.add(c);
            }
            con.close();
   
    	return cabs;
    }
    
    public static ArrayList<Integer> getNearByLocs(int locid,int range) throws SQLException,ClassNotFoundException,NullPointerException{
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
    
    public static Cab getCabById(int cabid) throws SQLException, ClassNotFoundException {
    	Cab c=null;
    	Connection con=DatabaseConnection.initializeDatabase();
    	PreparedStatement ps=con.prepareStatement("Select Cabs.uid,Cabs.Type,Cabs.locid,Cabs.wallet,Cabs.Status,Users.Name FROM Cabs,Users WHERE Cabs.id=? AND Cabs.uid=Users.id");
    	ps.setInt(1, cabid);
    	ResultSet rs=ps.executeQuery();
    	if(rs.next()) {
    		int uid=rs.getInt("uid");
    		String type=rs.getString("Type");
    		int locid=rs.getInt("locid");
    		BigDecimal wallet=rs.getBigDecimal("wallet");
    		String status=rs.getString("Status");
    		String name=rs.getString("Name");
    		c=new Cab(cabid,uid,type,getLocName(locid),wallet,status,name);
    	}
    	return c;
    }
    
    public static ArrayList<Cab> getFreeCabs(String cloc,String carType,int range) throws SQLException,ClassNotFoundException,NullPointerException{
    	ArrayList<Cab> cabs=new ArrayList<Cab>();
    	int locid=getLocId(cloc);
    	ArrayList<Integer> locs=getNearByLocs(locid,range);
    	for(int loc:locs) {
    		ArrayList<Cab> temp=getFreeCabs(getLocName(loc),carType);
    		cabs.addAll(temp);
    	}
    	return cabs;
    }
    
    public static ArrayList<String> getLocations() throws SQLException,ClassNotFoundException,NullPointerException{
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
    
    public static BigDecimal getDistance(String src,String dest) throws SQLException,ClassNotFoundException,NullPointerException{
    	BigDecimal dist=null;
    	  
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Select Distance from Distance where (src=? AND dest=?) OR (src=? AND dest=?);");
            ps.setInt(1,getLocId(src));
            ps.setInt(2,getLocId(dest));
            ps.setInt(3,getLocId(dest));
            ps.setInt(4,getLocId(src));
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
            	dist=rs.getBigDecimal(Constants.distance);
            	
            }
            con.close();
   
      
    	return dist;
    }
    
    public static boolean insertTrip(int uid,int cabid,int src,int dest,String otp,String status,int time) throws SQLException,ClassNotFoundException,NullPointerException{
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
    
    public static int getPointsId(String src,String dest) throws SQLException,ClassNotFoundException,NullPointerException{
    	int pid=0;
    	
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Select id from Distance where (src=? AND dest=?) OR (src=? AND dest=?);");
            ps.setInt(1,getLocId(src));
            ps.setInt(2,getLocId(dest));
            ps.setInt(3,getLocId(dest));
            ps.setInt(4,getLocId(src));
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
            	pid=rs.getInt("id");
            	
            }
            con.close();
   
      
    	return pid;
    }
    
    public static int getLastTripId(int uid) throws SQLException,ClassNotFoundException,NullPointerException{
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
    
    public static Trip getTrip(int id) throws SQLException,ClassNotFoundException,NullPointerException{
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
    
    public static ArrayList<TripOperations> getTrips(int uid) throws SQLException,ClassNotFoundException,NullPointerException{
    	ArrayList<TripOperations> trips=new ArrayList<TripOperations>();
       
          Connection con=DatabaseConnection.initializeDatabase();
          PreparedStatement ps=con.prepareStatement("SELECT t.id,t.uid,t.cabid,c.type,l.Point AS src,l2.Point AS dest,t.otp,t.Status,t.time_started,t.time_ended,d.distance FROM Trips t INNER JOIN Cabs c  ON t.cabid=c.id INNER JOIN Distance d ON ((t.src=d.src AND t.dest=d.dest) OR (t.src=d.dest AND t.dest=d.src)) INNER JOIN Location l on t.src=l.id INNER JOIN location l2 ON t.dest=l2.id WHERE t.uid=? ORDER BY t.id desc;");
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
            Trip  trip=new Trip(id,uid,cabid,otp,status,timeCreated,timeEnded,src,dest,cabType,distance,getUserNameById(uid));
            CabOperations cab=new CabOperations(getCabById(cabid));
            TripOperations temp=new TripOperations();
            temp.setTrip(trip);
            temp.setCab(cab);
            trips.add(temp);
          }
      
       return trips;
    }
    
    public static void changeTripStatus(int tid,String status) throws SQLException,ClassNotFoundException,NullPointerException{
    	    
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Update Trips set Status=? where id=?");
            ps.setString(1,status);
            ps.setInt(2,tid);
            ps.executeUpdate();
            
            con.close();
        
    }
    
    public static boolean startTrip(BigDecimal fare,int locid,int id) throws SQLException,ClassNotFoundException,NullPointerException{
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
    
    public static boolean cancelTrip(BigDecimal fare,int locid,int id) throws SQLException,ClassNotFoundException,NullPointerException{
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
    
    public static boolean updateProfile(String query,String param,int id) throws SQLException,ClassNotFoundException,NullPointerException{
    	boolean isUpdated=false;
    	Connection con=DatabaseConnection.initializeDatabase();
        PreparedStatement ps=con.prepareStatement(query);
        ps.setString(1, param);
        ps.setInt(2, id);
        ps.executeUpdate();
        isUpdated=true;
    	return isUpdated;
    }
    
    public static boolean updateProfile(String query,String param1,String param2,int id) throws SQLException,ClassNotFoundException,NullPointerException{
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
    
    public static HashMap<String,HashMap<String,Integer>> getCabsStats()  throws SQLException,ClassNotFoundException,NullPointerException{
    	HashMap<String,HashMap<String,Integer>> cabStats=new HashMap<String,HashMap<String,Integer>>();
    	Connection con=DatabaseConnection.initializeDatabase();
        PreparedStatement ps=con.prepareStatement("SELECT Count(*) AS count,Type as type,locid FROM Cabs GROUP BY Type,locid;");
        ResultSet rs=ps.executeQuery();
        int count;
        String loc,type;
        HashMap<String,Integer> typeCount=new HashMap<String,Integer>();
        if(rs.next()) {
        	count=rs.getInt("count");
        	loc=getLocName(rs.getInt("locid"));
        	type=rs.getString("Type");
        	typeCount.put(type,count);
        	if(cabStats.containsKey(loc)) {
        		(cabStats.get(loc)).put(type,Integer.valueOf(count));
        	}
        	else {
        		HashMap<String,Integer> temp=new HashMap<String,Integer>();
        		temp.put(type,count);
        		cabStats.put(loc,temp);
        	}
        }
        
    	return cabStats;
    }
    
    public static HashMap<String,HashMap<String,Integer>> getAdminDashboardDetails() throws SQLException,ClassNotFoundException,NullPointerException{
    	HashMap<String,HashMap<String,Integer>> dashboardDetails=new HashMap<String,HashMap<String,Integer>>();
    	Connection con=DatabaseConnection.initializeDatabase();
        PreparedStatement ps=con.prepareStatement("SELECT COUNT(*) AS total,sum(case when Trips.Status='Completed' then 1 else 0 end) AS completedTrip,sum(case when Trips.Status='Cancelled' then 1 else 0 end) AS cancelledTrip FROM Trips;");
        ResultSet rs=ps.executeQuery();
        HashMap<String,Integer> totalTrips=new HashMap<String,Integer>();
        HashMap<String,Integer> completedTrips=new HashMap<String,Integer>();
        HashMap<String,Integer> cancelledTrips=new HashMap<String,Integer>();
        if(rs.next()) {
        	totalTrips.put("totalTrips", rs.getInt("total"));
        	completedTrips.put("completedTrips",rs.getInt("completedTrip"));
        	cancelledTrips.put("cancelledTrips",rs.getInt("cancelledTrip"));
        	
        }
        dashboardDetails.put("totalTrips",totalTrips);
        dashboardDetails.put("completedTrips",completedTrips);
        dashboardDetails.put("cancelledTrips",cancelledTrips);
        HashMap<String,HashMap<String,Integer>> cabDetails=getCabsStats();
        dashboardDetails.putAll(cabDetails);
    	return dashboardDetails;
    }
}

