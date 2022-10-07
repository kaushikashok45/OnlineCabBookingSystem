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
import com.tomcat_hello_world.Operations.Booking.TripOperations;




public class CancelTrip extends HttpServlet{
   private static final long serialVersionUID = 1L;

public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
	JSONParser parser = new JSONParser();
    JSONObject jsonparam=null;
    TripOperations bookingDetails=null;
    boolean isTripCancelled=false;
	try {
		jsonparam = (JSONObject)parser.parse(request.getParameter("bookingDetails"));
		bookingDetails = TripOperations.objectFromMinimalJson(jsonparam);
		isTripCancelled = bookingDetails.cancelTrip(bookingDetails.getTrip().getUid());
		jsonparam=TripOperations.getMinimalJSONObject(bookingDetails);
	} catch (ClassNotFoundException | NullPointerException | SQLException  | ParseException e) {
		e.printStackTrace();
	}
	if(isTripCancelled) {
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(jsonparam);
		out.flush();
	}
}
}
