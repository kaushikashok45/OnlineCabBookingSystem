package com.tomcat_hello_world.Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.tomcat_hello_world.Exceptions.AccessDeniedException;
import com.tomcat_hello_world.Operations.Administration.AdminOperations;
import com.tomcat_hello_world.Operations.Authentication.UserOperations;


public class userLazyFetchTrips extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserOperations user=null;
		JSONObject json=null;
		HttpSession session=request.getSession(false);
		String filter=request.getParameter("filter");
		int limit=Integer.valueOf(request.getParameter("limit"));
		try {
			user=(UserOperations)session.getAttribute("User");
			json=user.lazyLoadTrips(filter,limit,user.getUser().getId());
		}
		catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		PrintWriter out = response.getWriter();
    	response.setContentType("application/json");
    	response.setCharacterEncoding("UTF-8");
    	out.print(json);
    	out.flush();
	}

}
