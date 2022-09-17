package com.tomcat_hello_world.User;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.tomcat_hello_world.Security.Constants;


public class Account extends HttpServlet{
    private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
        request.getRequestDispatcher(Constants.accountJSP).forward(request, response);
    }
}
