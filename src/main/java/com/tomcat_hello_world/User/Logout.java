package com.tomcat_hello_world.User;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.tomcat_hello_world.Security.Constants;

import javax.servlet.annotation.*;

@WebServlet(Constants.logoutURL)

public class Logout extends HttpServlet{
    private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
        request.getSession().invalidate();
        response.sendRedirect(Constants.indexURL);
    }
}

