package com.tomcat_hello_world.User.Administrator;

import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.http.Cookie;

import com.google.gson.Gson;
import com.tomcat_hello_world.Storage.*;


public class AdminDashboardDetails extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		HashMap<String,HashMap<String,Integer>> dashboardDetails=new HashMap<String,HashMap<String,Integer>>();
		try {
			 dashboardDetails=SQLQueries.getAdminDashboardDetails();
		} catch (ClassNotFoundException | NullPointerException | SQLException e) {
			// TODO Auto-generated catch block
			response.sendRedirect("./Error1.jsp");
		}
		String json = new Gson().toJson(dashboardDetails);
		response.setContentType("application/json");
		PrintWriter out=response.getWriter();
		out.write(json);
		out.flush();
	}

}
