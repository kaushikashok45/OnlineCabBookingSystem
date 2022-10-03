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
		String dest=request.getParameter(Constants.dest);
        String src=request.getParameter(Constants.src);
        String carType=request.getParameter(Constants.carType);
        int uid=Integer.parseInt(request.getParameter("uid"));
        TripOperations bookingDetails=null;
        JSONObject json=null;
		try {
			bookingDetails = new TripOperations(src,dest,carType,uid);
			json=bookingDetails.toJson();
		} catch (Exception e) {
			e.printStackTrace();
			json=null;
		}
	    /*JsonObject cab=new JsonObject();
	    cab.addProperty("cabid",bookingDetails.getCab().getAssignedCab().getId());
	    cab.addProperty("driverName",bookingDetails.getCab().getAssignedCab().getDriverName());
        cab.addProperty("status",bookingDetails.getCab().getAssignedCab().getStatus());
        cab.addProperty("type",bookingDetails.getCab().getAssignedCab().getType() );
        cab.addProperty("loc",bookingDetails.getCab().getAssignedCab().getLoc());
        json.pu("cab",new Gson().toJsonTree(bookingDetails.getCab()));*/
    	PrintWriter out = response.getWriter();
    	response.setContentType("application/json");
    	response.setCharacterEncoding("UTF-8");
    	out.print(json);
    	out.flush();
        
    }
}
