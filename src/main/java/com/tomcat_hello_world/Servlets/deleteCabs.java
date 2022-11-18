package com.tomcat_hello_world.Servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.tomcat_hello_world.Exceptions.DriverAlreadyExistsException;
import com.tomcat_hello_world.Exceptions.InvalidInputException;
import com.tomcat_hello_world.Exceptions.UserNotFoundException;
import com.tomcat_hello_world.Exceptions.UserRoleMismatchException;
import com.tomcat_hello_world.Operations.Administration.AdminOperations;


public class deleteCabs extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 JSONParser parser = new JSONParser();    
	        boolean deletedCabs=false; 
	        JSONObject result=new JSONObject();
	        StringBuffer jb = new StringBuffer();
	        String line = null;
	        try{
	           System.out.println("Deleting cabs ,msg from deleteCabs");	
	           BufferedReader reader = request.getReader();
	           while ((line = reader.readLine()) != null)
	        	      jb.append(line);	
	           System.out.println(jb);
	           JSONObject json=(JSONObject) parser.parse(jb.toString()); 
	           System.out.println(json);
	           deletedCabs=new AdminOperations().deleteCabs(json);
	           if(deletedCabs){
	        	   response.setStatus(200);
	               result.put("message","Cabs deleted successfully!");
	           }

	        }
	        catch(Exception e){
	             System.out.println(e.getMessage());
	             e.printStackTrace();
	            
	        }
	        System.out.println(response.getStatus()+" 45");
	        PrintWriter out = response.getWriter();
	    	response.setContentType("application/json");
	    	response.setCharacterEncoding("UTF-8");
	    	out.print(result);
	    	out.flush();
	        
	}
}
