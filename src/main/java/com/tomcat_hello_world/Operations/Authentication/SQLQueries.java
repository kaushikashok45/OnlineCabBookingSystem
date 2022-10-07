package com.tomcat_hello_world.Operations.Authentication;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.tomcat_hello_world.Entity.User;
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
}
