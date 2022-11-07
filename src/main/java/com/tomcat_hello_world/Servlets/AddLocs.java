package com.tomcat_hello_world.Servlets;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.tomcat_hello_world.Exceptions.DriverAlreadyExistsException;
import com.tomcat_hello_world.Exceptions.InvalidInputException;
import com.tomcat_hello_world.Operations.Administration.AdminOperations;
import com.tomcat_hello_world.Operations.Authentication.UserOperations;



public class AddLocs extends HttpServlet{
    private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		String locNames=request.getParameter("locNames");
		boolean addedLocs=false;
		try {
			addedLocs=new AdminOperations().addLocs(locNames);
		}
		catch(Exception e) {
			if(e.getMessage().equals("Location is already  registered")) {
				response.setStatus(406);
			}
			else if(e.getMessage().equals("Invalid input details")) {
				response.setStatus(400);
			}		
		}
	}
}