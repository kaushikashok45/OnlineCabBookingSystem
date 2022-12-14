package com.tomcat_hello_world.Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;
import org.json.simple.JSONObject;
import com.tomcat_hello_world.Utility.Constants;
import com.tomcat_hello_world.Operations.Authentication.UserOperations;
import com.tomcat_hello_world.Operations.Booking.TripOperations;
     

public class BookCab extends HttpServlet{
	
	
	
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		HttpSession session=request.getSession(false);
		String dest=request.getParameter(Constants.dest);
        String src=request.getParameter(Constants.src);
        String carType=request.getParameter(Constants.carType);
        int uid=((UserOperations)session.getAttribute("User")).getUser().getId();
        TripOperations bookingDetails=null;
        JSONObject json=null;
        try {
			if(TripOperations.validateBookingInput(src, dest, carType)) {   
			  bookingDetails = new TripOperations(src,dest,carType,uid);
			  json=bookingDetails.toBookingJson();
			}
		 } catch (Exception e) {
			e.printStackTrace();
			if(e.getMessage().equals("No cabs found")) {
				response.setStatus(406);
			}
			else if(e.getMessage().equals("Invalid input details")) {
				response.setStatus(400);
			}
			
		 }
    	PrintWriter out = response.getWriter();
    	response.setContentType("application/json");
    	response.setCharacterEncoding("UTF-8");
    	out.print(json);
    	out.flush();
        
    }
}
