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

    public static boolean checkEmailExists(String email){
        boolean emailExists=false;
        try{
           
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
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        return emailExists;
    } 
    
    public static boolean checkLocExist(String loc){
        boolean locExists=false;
        try{
           
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
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        return locExists;
    } 

    public static boolean checkPassword(String email,String password){
        boolean isEqual=false;
        try{
           
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
            con.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        return isEqual;
    }

    public static boolean insertUser(String name,String email,String pwd,String role){
        boolean userInserted=false;
        try{
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
            
         }
         catch(Exception e){
             e.printStackTrace();
         }
        return userInserted;
    }
    
    public static boolean addLoc(String point){
        boolean locInserted=false;
        try{
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("insert into Location(Point) VALUES(?)");
            ps.setString(1,point);
            ps.executeUpdate();
            ps.close();
            con.close();
            locInserted=true;
            
         }
         catch(Exception e){
             e.printStackTrace();
         }
        return locInserted;
    }

    
    public static boolean insertDistance(int src,int dest,float distance){
        boolean distanceInserted=false;
        try{
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
            
         }
         catch(Exception e){
             e.printStackTrace();
         }
        return distanceInserted;
    }

    public static String getUserName(String email){
        String name=null;
        try{    
          Connection con=DatabaseConnection.initializeDatabase();
          PreparedStatement ps=con.prepareStatement("Select * from Users  where Email=?");
          ps.setString(1,email);
          ResultSet rs=ps.executeQuery();
          if(rs.next()){
            name=rs.getString("Name");
          }
          con.close();
       }
       catch(SQLException e){
         e.printStackTrace();
       }
       catch(ClassNotFoundException e){
         e.printStackTrace();
       }
       return name;
    }

    public static String getUserId(String email){
        String id=null;
        try{    
          Connection con=DatabaseConnection.initializeDatabase();
          PreparedStatement ps=con.prepareStatement("Select * from Users  where Email=?");
          ps.setString(1,email);
          ResultSet rs=ps.executeQuery();
          if(rs.next()){
            id=Integer.toString(rs.getInt("id"));
          }
          con.close();
       }
       catch(SQLException e){
         e.printStackTrace();
       }
       catch(ClassNotFoundException e){
         e.printStackTrace();
       }
       return id;
    } 
    
    public static String getUserType(String email) {
    	String userType=null;
    	try{    
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Select * from Users  where Email=?");
            ps.setString(1,email);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
              userType=rs.getString("Role");
            }
            con.close();
         }
         catch(SQLException e){
           e.printStackTrace();
         }
         catch(ClassNotFoundException e){
           e.printStackTrace();
         }
    	return userType;
    }
    
    public static void changeName(String name,String email) {
    	try{    
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Update Users set Name=? where Email=?");
            ps.setString(1,name);
            ps.setString(2,email);
            ps.executeUpdate();
            con.close();
   
         }
         catch(SQLException e){
           e.printStackTrace();
         }
         catch(ClassNotFoundException e){
           e.printStackTrace();
         }
    }
    
    public static void changeEmail(String email,String oldEmail) {
    	try{    
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Update Users set Email=? where Email=?");
            ps.setString(1,email);
            ps.setString(2,oldEmail);
            ps.executeUpdate();
            con.close();
   
         }
         catch(SQLException e){
           e.printStackTrace();
         }
         catch(ClassNotFoundException e){
           e.printStackTrace();
         }
    }
    
    public static void changePassword(String pwd,String email) {
    	try{    
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Update Users set Password=? where Email=?");
            ps.setString(1,pwd);
            ps.setString(2,email);
            ps.executeUpdate();
            con.close();
   
         }
         catch(SQLException e){
           e.printStackTrace();
         }
         catch(ClassNotFoundException e){
           e.printStackTrace();
         }
    }
    
    public static void changeCabStatus(int cabid,String status) {
    	try{    
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Update Cabs set Status=? where id=?");
            ps.setString(1,status);
            ps.setInt(2,cabid);
            ps.executeUpdate();
            con.close();
   
         }
         catch(SQLException e){
           e.printStackTrace();
         }
         catch(ClassNotFoundException e){
           e.printStackTrace();
         }
    }
    
    public static void changeCabType(String cabid,String type) {
    	try{    
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Update Cabs set Type=? where id=?");
            ps.setString(1,type);
            ps.setString(2,cabid);
            ps.executeUpdate();
            
            con.close();
         }
         catch(SQLException e){
           e.printStackTrace();
         }
         catch(ClassNotFoundException e){
           e.printStackTrace();
         }
    }
    
    public static void changeCabLoc(int cabid,int locid) {
    	try{    
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Update Cabs set locid=? where id=?");
            ps.setInt(1,locid);
            ps.setInt(2,cabid);
            ps.executeUpdate();
            
            con.close();
         }
         catch(SQLException e){
           e.printStackTrace();
         }
         catch(ClassNotFoundException e){
           e.printStackTrace();
         }
    }
    
    public static void changeCabWallet(int cabid,BigDecimal newWallet) {
    	
    	try{    
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Update Cabs set wallet=? where id=?");
            ps.setBigDecimal(1,newWallet);
            ps.setInt(2,cabid);
            ps.executeUpdate();
            
            con.close();
         }
         catch(SQLException e){
           e.printStackTrace();
         }
         catch(ClassNotFoundException e){
           e.printStackTrace();
         }
    }
    
public static void changeTripTimeEnded(int cabid) {
    	Timestamp ts=new Timestamp(System.currentTimeMillis());
    	try{    
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Update Trips set time_ended=? where id=?");
            ps.setTimestamp(1,ts);
            ps.setInt(2,cabid);
            ps.executeUpdate();
            
            con.close();
         }
         catch(SQLException e){
           e.printStackTrace();
         }
         catch(ClassNotFoundException e){
           e.printStackTrace();
         }
    }
    
    public static int getLocId(String loc){
    	int locid=0;
    	try{    
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Select * from Location where Point=?;");
            ps.setString(1,loc);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
            	locid=rs.getInt("id");  	
            }
            con.close();
   
         }
         catch(SQLException e){
           e.printStackTrace();
         }
         catch(ClassNotFoundException e){
           e.printStackTrace();
         }
    	return locid;
    }
    
    public static BigDecimal getCabWallet(int cabid){
    	BigDecimal wallet=new BigDecimal("1000");
    	try{    
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Select wallet from Cabs where id=?;");
            ps.setInt(1,cabid);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
            	  	wallet=rs.getBigDecimal("wallet");
            }
            con.close();
   
         }
         catch(SQLException e){
           e.printStackTrace();
         }
         catch(ClassNotFoundException e){
           e.printStackTrace();
         }
    	return wallet;
    }
    	
    	public static String getLocName(int id){
        	String loc=null;
        	try{    
                Connection con=DatabaseConnection.initializeDatabase();
                PreparedStatement ps=con.prepareStatement("Select * from Location where id=?;");
                ps.setInt(1,id);
                ResultSet rs=ps.executeQuery();
                if(rs.next()){
                	loc=rs.getString("Point");  	
                }
                
                con.close();
             }
             catch(SQLException e){
               e.printStackTrace();
             }
             catch(ClassNotFoundException e){
               e.printStackTrace();
             }
        	return loc;
        } 	
    
    public static ArrayList<Cab> getCabs(){
    	ArrayList<Cab> cabs=new ArrayList<Cab>();
    	try{    
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
   
         }
         catch(SQLException e){
           e.printStackTrace();
         }
         catch(ClassNotFoundException e){
           e.printStackTrace();
         }
    	return cabs;
    }
    
    public static ArrayList<Cab> getFreeCabs(String cloc,String carType){
    	ArrayList<Cab> cabs=new ArrayList<Cab>();
    	int locid=getLocId(cloc);
    	try{    
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
   
         }
         catch(SQLException e){
           e.printStackTrace();
         }
         catch(ClassNotFoundException e){
           e.printStackTrace();
         }
    	return cabs;
    }
    
    public static ArrayList<Integer> getNearByLocs(int locid,int range){
    	ArrayList<Integer> locs=new ArrayList<Integer>();
    	try{    
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
   
         }
         catch(SQLException e){
           e.printStackTrace();
         }
         catch(ClassNotFoundException e){
           e.printStackTrace();
         }
    	return locs;
    }
    
    public static ArrayList<Cab> getFreeCabs(String cloc,String carType,int range){
    	ArrayList<Cab> cabs=new ArrayList<Cab>();
    	int locid=getLocId(cloc);
    	ArrayList<Integer> locs=getNearByLocs(locid,range);
    	for(int loc:locs) {
    		ArrayList<Cab> temp=getFreeCabs(getLocName(loc),carType);
    		cabs.addAll(temp);
    	}
    	return cabs;
    }
    
    public static ArrayList<String> getLocations(){
    	ArrayList<String> locs=new ArrayList<String>();
    	try{    
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Select * from Location;");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
            	String loc=rs.getString("Point");
            	locs.add(loc);
            }
            con.close();
   
         }
         catch(SQLException e){
           e.printStackTrace();
         }
         catch(ClassNotFoundException e){
           e.printStackTrace();
         }
    	return locs;
    }
    
    public static BigDecimal getDistance(String src,String dest) {
    	BigDecimal dist=null;
    	try{    
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
   
         }
         catch(SQLException e){
           e.printStackTrace();
         }
         catch(ClassNotFoundException e){
           e.printStackTrace();
         }
    	return dist;
    }
    
    public static boolean insertTrip(int uid,int cabid,int pointsid,String otp,String status,int time) {
    	boolean insertedTrip=false;
    	try{    
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
         }
         catch(SQLException e){
           e.printStackTrace();
         }
         catch(ClassNotFoundException e){
           e.printStackTrace();
         }
    	return insertedTrip;
    } 
    
    public static int getPointsId(String src,String dest) {
    	int pid=0;
    	try{    
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
   
         }
         catch(SQLException e){
           e.printStackTrace();
         }
         catch(ClassNotFoundException e){
           e.printStackTrace();
         }
    	return pid;
    }
    
    public static int getLastTripId() {
    	int id=0;
        try{    
          Connection con=DatabaseConnection.initializeDatabase();
          PreparedStatement ps=con.prepareStatement("Select MAX(id)  FROM Trips;");
          ResultSet rs=ps.executeQuery();
          if(rs.next()){
            id=rs.getInt(1);
          }
       }
       catch(SQLException e){
         e.printStackTrace();
       }
       catch(ClassNotFoundException e){
         e.printStackTrace();
       }
       return id;
    }
    
    public static Trip getTrip(int id) {
    	Trip trip=null;
        try{    
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
       }
       catch(SQLException e){
         e.printStackTrace();
       }
       catch(ClassNotFoundException e){
         e.printStackTrace();
       }
       return trip;
    }
    
    public static void changeTripStatus(int tid,String status) {
    	try{    
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Update Trips set Status=? where id=?");
            ps.setString(1,status);
            ps.setInt(2,tid);
            ps.executeUpdate();
            
            con.close();
         }
         catch(SQLException e){
           e.printStackTrace();
         }
         catch(ClassNotFoundException e){
           e.printStackTrace();
         }
    }
}

