package com.tomcat_hello_world.Servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.tomcat_hello_world.Utility.Constants;



public class ShowSignUpPage extends HttpServlet{
    private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
      request.getRequestDispatcher(Constants.signupJSP).forward(request, response);
    }
}

