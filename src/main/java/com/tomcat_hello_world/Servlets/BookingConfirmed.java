package com.tomcat_hello_world.Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.tomcat_hello_world.Exceptions.CabAlreadyBookedException;
import com.tomcat_hello_world.Operations.Authentication.UserOperations;
import com.tomcat_hello_world.Operations.Booking.TripOperations;
import java.sql.SQLException;


public class BookingConfirmed extends HttpServlet{
	private static final long serialVersionUID = 1L;
    
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException,NullPointerException{
		HttpSession session=request.getSession(false);      
		JSONParser parser = new JSONParser();
		      JSONObject jsonparam=null;
		      TripOperations bookingDetails=null;
		      boolean insertedTrip=false;
		      JSONObject json=new JSONObject();
		      int uid=((UserOperations)session.getAttribute("User")).getUser().getId();
			try {
				jsonparam = (JSONObject)parser.parse(request.getParameter("bookingDetails"));
				bookingDetails = TripOperations.objectFromBookingJson(jsonparam,uid);
				insertedTrip = bookingDetails.confirmBooking();
				json=TripOperations.getMinimalJSONObject(bookingDetails);
			} catch (ClassNotFoundException | NullPointerException | SQLException  | ParseException |CabAlreadyBookedException e) {
				e.printStackTrace();
				if(e.getMessage().equals("Cab already booked")) {
					response.setStatus(404);
				}
				
			}
		    if(insertedTrip) {
		     	 PrintWriter out = response.getWriter();
		     	 response.setContentType("application/json");
		     	 response.setCharacterEncoding("UTF-8");
		     	 out.print(json);
		     	 out.flush();
		     }
		     
	}
}
