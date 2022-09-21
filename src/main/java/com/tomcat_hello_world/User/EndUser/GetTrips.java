package com.tomcat_hello_world.User.EndUser;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.http.*;
import com.google.gson.*;

import com.tomcat_hello_world.Security.Constants;
import com.tomcat_hello_world.Storage.*;
import com.tomcat_hello_world.User.Trip;


public class GetTrips extends HttpServlet{
   private static final long serialVersionUID = 1L;
   
   public static void sort(ArrayList<Trip> list) {
	   
       list.sort((o1, o2)
                 -> o1.getTimeCreated().compareTo(
                     o2.getTimeCreated()));
   }

public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
	HttpSession sessionr=request.getSession(false);
	int uid=(int)sessionr.getAttribute(Constants.id);
	String email=(String)sessionr.getAttribute(Constants.email);
	String name=(String)sessionr.getAttribute("name");
	ArrayList<Trip> trips=new ArrayList<Trip>();
	try {
		trips=SQLQueries.getTrips(uid);
	}catch(SQLException | ClassNotFoundException | NullPointerException e) {
		response.sendRedirect("./Error1.jsp");
	}
	sort(trips);
	HttpSession sessionw=request.getSession(true);
	sessionw.setAttribute(Constants.id,uid);
	sessionw.setAttribute(Constants.email,email);
	sessionw.setAttribute("name",name);
	String json = new Gson().toJson(trips);
	PrintWriter out = response.getWriter();
	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	out.print(json);
	out.flush();
}
}
