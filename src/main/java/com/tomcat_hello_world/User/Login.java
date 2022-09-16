package com.tomcat_hello_world.User;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class Login extends HttpServlet{
    private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}