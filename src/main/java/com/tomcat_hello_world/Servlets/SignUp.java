package com.tomcat_hello_world.Servlets;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


import com.tomcat_hello_world.Utility.Constants;
import com.tomcat_hello_world.Operations.Authentication.UserOperations;


public class SignUp extends HttpServlet{
    
    private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
        String name=request.getParameter(Constants.smallName);
        String email=request.getParameter(Constants.email);
        String pwd=request.getParameter(Constants.pass);
        UserOperations userAdder=new UserOperations();
        boolean insertedUser=userAdder.addUser(name, email,pwd,Constants.bigCustomer);
        if(insertedUser){
            response.sendRedirect(Constants.indexSuccessURL);
        }
        else{
            response.sendRedirect(Constants.indexRegErrorURL);
        }
       
       
    }    
}
