package com.tomcat_hello_world.Servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tomcat_hello_world.Exceptions.UserRoleMismatchException;
import com.tomcat_hello_world.Operations.Authentication.UserOperations;


public class CheckUserStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession(false);
		boolean accountDeleted=false;
		 UserOperations userOp=((UserOperations)session.getAttribute("User"));
		 try {
			if(!userOp.checkUserTripUnderway()) {
				throw new UserRoleMismatchException();
			 }
			 accountDeleted=userOp.deleteAccount();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
