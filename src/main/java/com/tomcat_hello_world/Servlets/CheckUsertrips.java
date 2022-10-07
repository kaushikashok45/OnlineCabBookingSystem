package com.tomcat_hello_world.Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.tomcat_hello_world.Exceptions.NoUnderwayTripsException;
import com.tomcat_hello_world.Exceptions.TripTimeOutException;
import com.tomcat_hello_world.Operations.Authentication.UserOperations;
import com.tomcat_hello_world.Operations.Booking.TripOperations;
import java.sql.SQLException;


public class CheckUsertrips extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		HttpSession session=request.getSession(false);
		int uid=((UserOperations)session.getAttribute("User")).getUser().getId();
		TripOperations tripDetails=null;
		JSONObject json=null;
		try {
			tripDetails=TripOperations.checkUserTripUnderway(uid);
			json=TripOperations.getMinimalJSONObject(tripDetails);
		}
		catch(ClassNotFoundException | NullPointerException | SQLException  |  TripTimeOutException | NoUnderwayTripsException e ) {
			e.printStackTrace();
			if(e.getMessage().equals("Trip timed out")) {
				response.setStatus(408);
			}
			else {
				response.setStatus(200);
				
			}	
		}
		 PrintWriter out = response.getWriter();
    	 response.setContentType("application/json");
    	 response.setCharacterEncoding("UTF-8");
    	 out.print(json);
    	 out.flush();
	}
   
}
