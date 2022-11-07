package com.tomcat_hello_world.Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.tomcat_hello_world.Operations.Administration.AdminOperations;

public class GetAdminTrips extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		JSONObject json=null;
		try {
			json=AdminOperations.getAllTrips();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		PrintWriter out = response.getWriter();
    	response.setContentType("application/json");
    	response.setCharacterEncoding("UTF-8");
    	out.print(json);
    	out.flush();
	}

}
