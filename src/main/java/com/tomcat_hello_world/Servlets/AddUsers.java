package com.tomcat_hello_world.Servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.tomcat_hello_world.Exceptions.DriverAlreadyExistsException;
import com.tomcat_hello_world.Exceptions.InvalidInputException;
import com.tomcat_hello_world.Exceptions.UserAlreadyExistsException;
import com.tomcat_hello_world.Exceptions.UserNotFoundException;
import com.tomcat_hello_world.Exceptions.UserRoleMismatchException;
import com.tomcat_hello_world.Operations.Administration.AdminOperations;


public class AddUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    JSONParser parser = new JSONParser();    
	        boolean addedUsers=false; 
	        JSONObject result=new JSONObject();
	        StringBuffer jb = new StringBuffer();
	        String line = null;
	        try{
	           BufferedReader reader = request.getReader();
	           while ((line = reader.readLine()) != null)
	        	      jb.append(line);	
	           System.out.println(jb);
	           JSONObject json=(JSONObject) parser.parse(jb.toString()); 
	           addedUsers=new AdminOperations().addUsers(json);
	           if(addedUsers){
	            response.setStatus(200);
	            result.put("message","Users added successfully!");
	           }

	        }
	        catch(DriverAlreadyExistsException | SQLException | ClassNotFoundException | NullPointerException | InvalidInputException | ParseException | UserNotFoundException | UserRoleMismatchException | NoSuchAlgorithmException | UserAlreadyExistsException e){
	             System.out.println(e.getMessage());
	             e.printStackTrace();
	             if(e.getMessage().equals("Invalid input details")) {
	            	 response.setStatus(400);
	             }
	             else if(e.getMessage().equals("The given email-id is already a registered user.")){
	            	 response.setStatus(406);
	             }
	             else {
	            	 response.setStatus(500);
	             }
	        }
	        System.out.println(result);
	        PrintWriter out = response.getWriter();
	    	response.setContentType("application/json");
	    	response.setCharacterEncoding("UTF-8");
	    	out.print(result);
	    	out.flush();
	        
	}

}
