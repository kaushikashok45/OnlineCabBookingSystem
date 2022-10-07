package com.tomcat_hello_world.Servlets;

import java.io.*;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.http.*;
import com.google.gson.*;
import com.tomcat_hello_world.Operations.Authentication.UserOperations;
import com.tomcat_hello_world.Operations.Booking.TripOperations;


public class GetTrips extends HttpServlet{
   private static final long serialVersionUID = 1L;
   
  

public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
	HttpSession sessionr=request.getSession(false);
	UserOperations user=(UserOperations)sessionr.getAttribute("User");
	String filter=request.getParameter("filter");
	String json=null;
	try {
		json = new Gson().toJson(TripOperations.getAllTrips(user.getUser().getId(),filter));
	} catch (ClassNotFoundException | NullPointerException | SQLException e) {
		e.printStackTrace();
	}
	PrintWriter out = response.getWriter();
	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	out.print(json);
	out.flush();
}
}
