package com.tomcat_hello_world.Storage;


import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.math.BigDecimal;
import com.tomcat_hello_world.Security.*;
import com.tomcat_hello_world.User.Driver.*;
import com.tomcat_hello_world.User.Trip;

import java.util.Date;

public class SQLQueries extends Encryptor{

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

    public static boolean checkPassword(String email,String password) throws SQLException,ClassNotFoundException,NullPointerException{
        boolean isEqual=false;
        
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Select * from Users  where Email=?");
            ps.setString(1,email);
            ResultSet rs=ps.executeQuery();
            String pwd=null;
            if(rs.next()){
                pwd=rs.getString("Password");
            }
            if(pwd.equals(Encryptor.encrypt(password))){
                isEqual=true;
            }
      
        return isEqual;
    }

    public static boolean insertUser(String name,String email,String pwd,String role) throws SQLException,ClassNotFoundException,NullPointerException{
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
            name=rs.getString("Name");
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
            id=Integer.toString(rs.getInt("id"));
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
              userType=rs.getString("Role");
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
    
    public static BigDecimal getCabWallet(int cabid) throws SQLException,ClassNotFoundException,NullPointerException{
    	BigDecimal wallet=new BigDecimal("1000");
    	  
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Select wallet from Cabs where id=?;");
            ps.setInt(1,cabid);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
            	  	wallet=rs.getBigDecimal("wallet");
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
                	loc=rs.getString("Point");  	
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
            	int id=rs.getInt("id");
            	int uid=rs.getInt("uid");
            	String type=rs.getString("Type");
            	String loc= getLocName(rs.getInt("Location"));
            	BigDecimal wallet=rs.getBigDecimal("Wallet");
            	String status=rs.getString("Status");
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
            PreparedStatement ps=con.prepareStatement("Select * from Cabs WHERE locid=? AND Type=? AND Status=?;");
            ps.setInt(1,locid);
            ps.setString(2,carType);
            ps.setString(3,"Available");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
            	int id=rs.getInt("id");
            	int uid=rs.getInt("uid");
            	String type=rs.getString("Type");
            	String loc= getLocName(rs.getInt("locid"));
            	BigDecimal wallet=rs.getBigDecimal("wallet");
            	String status=rs.getString("Status");
            	Cab c=new Cab(id,uid,type,loc,wallet,status);
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
            	int src=rs.getInt("src");
            	int dest=rs.getInt("dest");
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
            PreparedStatement ps=con.prepareStatement("Select * from Location;");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
            	String loc=rs.getString("Point");
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
            	dist=rs.getBigDecimal("Distance");
            	
            }
            con.close();
   
      
    	return dist;
    }
    
    public static boolean insertTrip(int uid,int cabid,int pointsid,String otp,String status,int time) throws SQLException,ClassNotFoundException,NullPointerException{
    	boolean insertedTrip=false;
    	
    		int OTP=Integer.parseInt(otp);
    		Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("insert into Trips(uid,cabid,pointsid,otp,Status,time_ended) VALUES(?,?,?,?,?,?)");
            ps.setInt(1,uid);
            ps.setInt(2, cabid);
            ps.setInt(3, pointsid);
            ps.setInt(4,OTP);
            ps.setString(5,status);
            Date date = new Date();
            Timestamp timestamp2 = new Timestamp(date.getTime());
            ps.setTimestamp(6,new Timestamp(timestamp2.getTime()+(TimeUnit.MINUTES.toMillis(time))));
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
    
    public static int getLastTripId() throws SQLException,ClassNotFoundException,NullPointerException{
    	int id=0;
       
          Connection con=DatabaseConnection.initializeDatabase();
          PreparedStatement ps=con.prepareStatement("Select MAX(id)  FROM Trips;");
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
            int uid=rs.getInt("uid");
            int cabid=rs.getInt("cabid");
            int pointsid=rs.getInt("pointsid");
            String otp=String.valueOf(rs.getInt("otp"));
            String status=rs.getString("Status");
            Timestamp timeCreated=rs.getTimestamp("time_started");
            Timestamp timeEnded=rs.getTimestamp("time_ended"); 
            trip=new Trip(id,uid,cabid,pointsid,otp,status,timeCreated,timeEnded);
          }
      
       return trip;
    }
    
    public static void changeTripStatus(int tid,String status) throws SQLException,ClassNotFoundException,NullPointerException{
    	    
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Update Trips set Status=? where id=?");
            ps.setString(1,status);
            ps.setInt(2,tid);
            ps.executeUpdate();
            
            con.close();
        
    }
}

