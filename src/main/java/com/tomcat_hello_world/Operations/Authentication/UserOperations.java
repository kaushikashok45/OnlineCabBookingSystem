package com.tomcat_hello_world.Operations.Authentication;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tomcat_hello_world.Entity.User;
import com.tomcat_hello_world.Utility.Encryptor;
import com.tomcat_hello_world.Utility.Constants;

public class UserOperations {
    private User user;
    
    public User getUser() {
    	return this.user;
    }
    
    public void setUser(User user) {
    	this.user=user;
    }
    
    public UserOperations() {}
    
    public boolean authenticateUser(String email,String userPassword) throws ClassNotFoundException, NullPointerException, NoSuchAlgorithmException, SQLException {
    	boolean userAuthenticated=false; 
    	String password=Encryptor.encrypt(userPassword);
    	if((SQLQueries.checkEmailExists(email))){
            if(SQLQueries.checkPassword(email,password)){
            	this.setUser(SQLQueries.getUser(email));
            	if(this.getUser()!=null) {
            		userAuthenticated=true;
            	}
            }
         }
    	return userAuthenticated;
    }
    
    public UserOperations(User user) {
    	this.setUser(user);
    }
    
    public boolean addUser(String name,String email,String password,String role) {
    	boolean addedUser=false;
    	boolean doesEmailExist=true,isPasswordValid=true;
    	String regex = Constants.passwordRegex;
        if (password == null) {
            isPasswordValid=false;
        }
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        boolean pwdmatch=m.matches();
    	try {
        	doesEmailExist=(SQLQueries.checkEmailExists(email));
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
    	if((!doesEmailExist)&&(pwdmatch)&&(isPasswordValid)&&(name!=null)&&(!(name.equals(Constants.emptyString)))&&(!name.equals(Constants.emptySpaceString))){
             try {
           	  addedUser=SQLQueries.insertUser(name,email,password,Constants.bigCustomer);
             }
             catch(Exception e) {
           	  e.printStackTrace();
              }
    	}     
    	return addedUser;
    }
    
    public boolean updateUser(String newName,String newPassword) throws ClassNotFoundException, NullPointerException, NoSuchAlgorithmException, SQLException {
    	boolean isUserUpdated=false;
    	String regex = Constants.passwordRegex;
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(newPassword);
        boolean pwdmatch=m.matches();
        String query="Update Users SET Name=?,Password=? WHERE id=?";
        if((newName==null)||(newName.trim().isEmpty())||(newName.equals(this.getUser().getName()))) {
        	if((newPassword==null)||(!pwdmatch)) {
        		query=null;
        	}
        	else {
        		query="Update Users SET Password=? WHERE id=?";
        		isUserUpdated=SQLQueries.updateProfile(query,Encryptor.encrypt(newPassword),this.getUser().getId());
        	}
        } 
        else if((newPassword==null)||(!pwdmatch)) {
        	query="Update Users SET Name=? WHERE id=?";
        	this.getUser().setName(newName);
        	isUserUpdated=SQLQueries.updateProfile(query,newName,this.getUser().getId());
        }
        else {
        	this.getUser().setName(newName);
        	isUserUpdated=SQLQueries.updateProfile(query,newName,Encryptor.encrypt(newPassword),this.getUser().getId());
        }
    	return isUserUpdated;
    }
}
