package com.tomcat_hello_world.Servlets;

import java.io.*;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.http.*;
import com.tomcat_hello_world.Utility.Constants;
import com.tomcat_hello_world.Operations.Authentication.UserOperations;



public class Auth extends HttpServlet{
    private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
          String userEmail=request.getParameter(Constants.email);
          String userPassword=request.getParameter(Constants.pass);
          UserOperations userAuthorizer=new UserOperations();
          boolean isUserAuthorized=false;
          try {
			isUserAuthorized=userAuthorizer.authenticateUser(userEmail, userPassword);
		} catch (ClassNotFoundException | NullPointerException | NoSuchAlgorithmException | SQLException e) {
			e.printStackTrace();
		}
          if(isUserAuthorized){
                HttpSession sessionsa=request.getSession(true);
                sessionsa.setAttribute("User",userAuthorizer);
                String userRole=userAuthorizer.getUser().getRole();
                if((Constants.bigCustomer).equals(userRole)){
                	response.sendRedirect(Constants.accountURL);
                }
                else if((Constants.bigAdmin).equals(userRole)){
                	response.sendRedirect(Constants.adminURL);
                }
                else if((Constants.bigDriver).equals(userRole)){
                	response.sendRedirect(Constants.driverURL);
                }
          }
          else{
        	  System.out.println("logging out");
            response.sendRedirect(Constants.indexErrorURL);
          }
         
    }
}

