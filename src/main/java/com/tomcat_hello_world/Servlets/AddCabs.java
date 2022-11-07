package com.tomcat_hello_world.Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.tomcat_hello_world.Exceptions.DriverAlreadyExistsException;
import com.tomcat_hello_world.Exceptions.InvalidInputException;
import com.tomcat_hello_world.Exceptions.UserNotFoundException;
import com.tomcat_hello_world.Exceptions.UserRoleMismatchException;
import com.tomcat_hello_world.Operations.Administration.AdminOperations;
import com.tomcat_hello_world.Operations.Authentication.UserOperations;



public class AddCabs extends HttpServlet{
    private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
        JSONParser parser = new JSONParser();    
        boolean addedCabs=false; 
        JSONObject result=new JSONObject();
        try{
           JSONObject json=(JSONObject)parser.parse(request.getParameter("cabsToBeAdded")); 
           addedCabs=new AdminOperations().addCabs(json);
           if(addedCabs){
            response.setStatus(200);
            result.put("message","Cabs added successfully!");
           }

        }
        catch(DriverAlreadyExistsException | SQLException | ClassNotFoundException | NullPointerException | InvalidInputException | ParseException | UserNotFoundException | UserRoleMismatchException e){
             System.out.println(e.getMessage());
             e.printStackTrace();
             if(e.getMessage()=="Invalid input details") {
            	 response.setStatus(400);
             }
             else if(e.getMessage().equals("The given email-id is already a registered driver.")){
            	 response.setStatus(406);
             }
             else if(e.getMessage().equals("User does not exist.")){
            	 response.setStatus(402);
             }
             else if(e.getMessage().equals("User role mismatched.")) {
            	 response.setStatus(417);
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
