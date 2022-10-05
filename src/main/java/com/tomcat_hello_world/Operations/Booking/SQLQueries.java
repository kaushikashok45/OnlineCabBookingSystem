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
import com.tomcat_hello_world.Storage.DatabaseConnection;
import com.tomcat_hello_world.Utility.Constants;

public class SQLQueries extends DatabaseConnection{
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
            PreparedStatement ps=con.prepareStatement("Select c.id,c.uid,c.Type,c.locid,c.wallet,c.Status,u.Name from Cabs c INNER JOIN Users u ON c.uid=u.id WHERE locid=? AND Type=? AND Status=?;");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
            	int id=rs.getInt(Constants.id);
            	int uid=rs.getInt(Constants.uid);
            	String type=rs.getString(Constants.type);
            	String loc= getLocName(rs.getInt(Constants.location));
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
            PreparedStatement ps=con.prepareStatement("Select c.id,c.uid,c.Type,c.locid,c.wallet,c.Status,u.Name from Cabs c INNER JOIN Users u ON c.uid=u.id WHERE locid=? AND Type=? AND Status=?;");
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
            	String driverName=rs.getString("Name");
            	Cab c=new Cab(id,uid,type,loc,wallet,status,driverName);
            	cabs.add(c);
            }
            con.close();
   
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
    
    protected static ArrayList<Cab> getFreeCabs(String cloc,String carType,int range) throws SQLException,ClassNotFoundException,NullPointerException{
    	ArrayList<Cab> cabs=new ArrayList<Cab>();
    	int locid=getLocId(cloc);
    	ArrayList<Integer> locs=getNearByLocs(locid,range);
    	for(int loc:locs) {
    		ArrayList<Cab> temp=getFreeCabs(getLocName(loc),carType);
    		cabs.addAll(temp);
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
    
    protected static void changeTripStatus(int tid,String status) throws SQLException,ClassNotFoundException,NullPointerException{
    	    
            Connection con=DatabaseConnection.initializeDatabase();
            PreparedStatement ps=con.prepareStatement("Update Trips set Status=? where id=?");
            ps.setString(1,status);
            ps.setInt(2,tid);
            ps.executeUpdate();
            
            con.close();
        
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
}
