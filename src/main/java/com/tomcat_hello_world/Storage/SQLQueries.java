package com.tomcat_hello_world.Storage;


import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;

import com.tomcat_hello_world.Security.*;
import com.tomcat_hello_world.User.Driver.*;
import com.tomcat_hello_world.User.Trip;

import java.util.Date;
import java.util.HashMap;

public class SQLQueries{

    public static boolean checkEmailExists(String email) throws SQLException,ClassNotFoundException,NullPointerException{
        boolean emailExists=false;
        Connection con=DatabaseConnection.initializeDatabase();
        PreparedStatement ps=con.prepareStatement(Constants.getUserCountByEmail);
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
            PreparedStatement ps=con.prepareStatement(Constants.getLocFromPoint);
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
            PreparedStatement ps=con.prepareStatement(Constants.getPwdFromUserEmail);
            ps.setString(1,email);
            ResultSet rs=ps.executeQuery();
            String pwd=null;
            if(rs.next()){
                pwd=rs.getString(Constants.password);
            }
            if(pwd.equals(Encryptor.encrypt(password))){
                isEqual=true;
            }
      
        return isEqual;
    }

    public static boolean insertUser(String name,String email,String pwd,String role) throws SQLException,ClassNotFoundException,NullPointerException, NoSuchAlgorithmException{
        boolean userInserted=false;
        
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement(Constants.insertUser);
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
            PreparedStatement ps=con.prepareStatement(Constants.insertLoc);
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
            PreparedStatement ps=con.prepareStatement(Constants.insertDistance);
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
          PreparedStatement ps=con.prepareStatement(Constants.getUserByEmail);
          ps.setString(1,email);
          ResultSet rs=ps.executeQuery();
          if(rs.next()){
            name=rs.getString(Constants.name);
          }
          con.close();
     
       return name;
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
          PreparedStatement ps=con.prepareStatement(Constants.getUserByEmail);
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
            PreparedStatement ps=con.prepareStatement(Constants.getUserByEmail);
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
            PreparedStatement ps=con.prepareStatement(Constants.updateUsersName);
            ps.setString(1,name);
            ps.setString(2,email);
            ps.executeUpdate();
            con.close();
   
        
    }
    
    public static void changeEmail(String email,String oldEmail) throws SQLException,ClassNotFoundException,NullPointerException{
    	 
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement(Constants.updateUsersEmail);
            ps.setString(1,email);
            ps.setString(2,oldEmail);
            ps.executeUpdate();
            con.close();
   
         
    }
    
    public static void changePassword(String pwd,String email) throws SQLException,ClassNotFoundException,NullPointerException{
    	
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement(Constants.updateUsersPassword);
            ps.setString(1,pwd);
            ps.setString(2,email);
            ps.executeUpdate();
            con.close();
   
       
    }
    
    public static void changeCabStatus(int cabid,String status) throws SQLException,ClassNotFoundException,NullPointerException {
    	
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement(Constants.updateCabsStatus);
            ps.setString(1,status);
            ps.setInt(2,cabid);
            ps.executeUpdate();
            con.close();
   
       
    }
    
    public static void changeCabType(String cabid,String type) throws SQLException,ClassNotFoundException,NullPointerException{
    	  
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement(Constants.updateCabsType);
            ps.setString(1,type);
            ps.setString(2,cabid);
            ps.executeUpdate();
            
            con.close();
       
    }
    
    public static void changeCabLoc(int cabid,int locid) throws SQLException,ClassNotFoundException,NullPointerException{
      
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement(Constants.updateCabsLoc);
            ps.setInt(1,locid);
            ps.setInt(2,cabid);
            ps.executeUpdate();
            
        
    }
    
    public static void changeCabWallet(int cabid,BigDecimal newWallet) throws SQLException,ClassNotFoundException,NullPointerException{
    	
    	  
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement(Constants.updateCabsWallet);
            ps.setBigDecimal(1,newWallet);
            ps.setInt(2,cabid);
            ps.executeUpdate();
            
            con.close();
        
    }
    
public static void changeTripTimeEnded(int cabid) throws SQLException,ClassNotFoundException,NullPointerException{
    	Timestamp ts=new Timestamp(System.currentTimeMillis());
    	   
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement(Constants.updateTripsTimeEnded);
            ps.setTimestamp(1,ts);
            ps.setInt(2,cabid);
            ps.executeUpdate();
            
            con.close();
        
    }
    
    public static int getLocId(String loc) throws SQLException,ClassNotFoundException,NullPointerException{
    	int locid=0;
    	
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement(Constants.getLocIdByPoint);
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
            PreparedStatement ps=con.prepareStatement(Constants.getUserDetails);
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
            PreparedStatement ps=con.prepareStatement(Constants.getCabWallet);
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
                PreparedStatement ps=con.prepareStatement(Constants.getLocId);
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
            PreparedStatement ps=con.prepareStatement(Constants.getAllCabs);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
            	int id=rs.getInt(Constants.id);
            	int uid=rs.getInt(Constants.uid);
            	String type=rs.getString(Constants.type);
            	String loc= getLocName(rs.getInt(Constants.location));
            	BigDecimal wallet=rs.getBigDecimal(Constants.wallet);
            	String status=rs.getString(Constants.status);
            	Cab c=new Cab(id,uid,type,loc,wallet,status);
            	cabs.add(c);
            }
            con.close();
   
        
    	return cabs;
    }
    
    public static ArrayList<Cab> getFreeCabs(String cloc,String carType) throws SQLException,ClassNotFoundException,NullPointerException{
    	ArrayList<Cab> cabs=new ArrayList<Cab>();
    	int locid=getLocId(cloc);
    	   
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement(Constants.getCabByLocTypeStatus);
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
            	Cab c=new Cab(id,uid,type,loc,wallet,status);
            	cabs.add(c);
            }
            con.close();
   
    	return cabs;
    }
    
    public static ArrayList<Integer> getNearByLocs(int locid,int range) throws SQLException,ClassNotFoundException,NullPointerException{
    	ArrayList<Integer> locs=new ArrayList<Integer>();
    	 
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement(Constants.getNearbyLocs);
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
            PreparedStatement ps=con.prepareStatement(Constants.getAllLocs);
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
            PreparedStatement ps=con.prepareStatement(Constants.getDistance);
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
            PreparedStatement ps=con.prepareStatement(Constants.insertTrip);
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
            PreparedStatement ps=con.prepareStatement(Constants.getDistanceId);
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
    
    public static int getLastTripId() throws SQLException,ClassNotFoundException,NullPointerException{
    	int id=0;
       
          Connection con=DatabaseConnection.initializeDatabase();
          PreparedStatement ps=con.prepareStatement(Constants.getMaxTripId);
          ResultSet rs=ps.executeQuery();
          if(rs.next()){
            id=rs.getInt(1);
          }
      
       return id;
    }
    
    public static Trip getTrip(int id) throws SQLException,ClassNotFoundException,NullPointerException{
    	Trip trip=null;
       
          Connection con=DatabaseConnection.initializeDatabase();
          PreparedStatement ps=con.prepareStatement(Constants.getTripById);
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
    
    public static ArrayList<Trip> getTrips(int uid) throws SQLException,ClassNotFoundException,NullPointerException{
    	ArrayList<Trip> trip=new ArrayList<Trip>();
       
          Connection con=DatabaseConnection.initializeDatabase();
          PreparedStatement ps=con.prepareStatement(Constants.getTripByUid);
          ps.setInt(1, uid);
          ResultSet rs=ps.executeQuery();
          
          while(rs.next()){
            int id=rs.getInt(Constants.id);
            int cabid=rs.getInt(Constants.cabid);
            String otp=String.valueOf(rs.getInt(Constants.otp));
            String status=rs.getString(Constants.status);
            Timestamp timeCreated=rs.getTimestamp(Constants.timeStarted);
            Timestamp timeEnded=rs.getTimestamp(Constants.timeEnded); 
            String src=SQLQueries.getLocName(rs.getInt(Constants.src));
            String dest=SQLQueries.getLocName(rs.getInt(Constants.dest));
            String cabType=rs.getString("Type");
            BigDecimal distance=rs.getBigDecimal("distance");
            trip.add(new Trip(id,uid,cabid,otp,status,timeCreated,timeEnded,src,dest,cabType,distance));
          }
      
       return trip;
    }
    
    public static void changeTripStatus(int tid,String status) throws SQLException,ClassNotFoundException,NullPointerException{
    	    
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement(Constants.updateTripsStatus);
            ps.setString(1,status);
            ps.setInt(2,tid);
            ps.executeUpdate();
            
            con.close();
        
    }
    
    public static void startTrip(BigDecimal fare,int locid,int id) throws SQLException,ClassNotFoundException,NullPointerException{
   	 Connection con=DatabaseConnection.initializeDatabase();
        PreparedStatement ps=con.prepareStatement(Constants.updateTripStart); 
        ps.setString(1,Constants.cabStatus1);
        ps.setBigDecimal(2,fare);
        ps.setInt(3, locid);
        ps.setString(4, Constants.tripStatus2);
        ps.setInt(5,id);
        ps.executeUpdate();
        con.close();
    
   }
    
    public static void cancelTrip(BigDecimal fare,int locid,int id) throws SQLException,ClassNotFoundException,NullPointerException{
    	 Timestamp ts=new Timestamp(System.currentTimeMillis());
    	 Connection con=DatabaseConnection.initializeDatabase();
         PreparedStatement ps=con.prepareStatement(Constants.updateTrip); 
         ps.setString(1,Constants.cabStatus1);
         ps.setBigDecimal(2,fare);
         ps.setInt(3, locid);
         ps.setString(4, Constants.tripStatus3);
         ps.setTimestamp(5, ts);
         ps.setInt(6,id);
         ps.executeUpdate();
         con.close();
     
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
        PreparedStatement ps=con.prepareStatement(Constants.getCabCounts);
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
        PreparedStatement ps=con.prepareStatement(Constants.getTripCounts);
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

