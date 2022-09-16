package com.tomcat_hello_world.User;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/logout")

public class Logout extends HttpServlet{
    private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
        request.getSession().invalidate();
        response.sendRedirect("/com.tomcat_hello_world/");
    }
}

