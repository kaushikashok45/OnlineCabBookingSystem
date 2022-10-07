package com.tomcat_hello_world.Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.http.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.tomcat_hello_world.Operations.Booking.TripOperations;



public class StartTrip extends HttpServlet{

	private static final long serialVersionUID = 1L;
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
    	JSONParser parser = new JSONParser();
	    JSONObject jsonparam=null;
	    TripOperations bookingDetails=null;
	    boolean isTripStarted=false;
		try {
			jsonparam = (JSONObject)parser.parse(request.getParameter("bookingDetails"));
			bookingDetails = TripOperations.objectFromMinimalJson(jsonparam);
			isTripStarted = bookingDetails.startTrip(bookingDetails.getTrip().getUid());
		} catch (ClassNotFoundException | NullPointerException | SQLException  | ParseException e) {
			e.printStackTrace();
		}
    	if(isTripStarted) {
    	 PrintWriter out = response.getWriter();
    	 response.setContentType("application/json");
    	 response.setCharacterEncoding("UTF-8");
    	 out.print(jsonparam);
    	 out.flush();
    	} 
    }
}
