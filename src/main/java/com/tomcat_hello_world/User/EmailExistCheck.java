package com.tomcat_hello_world.User;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.tomcat_hello_world.Security.Constants;
import com.tomcat_hello_world.Storage.*;

@WebServlet("/EmailExistCheck")

public class EmailExistCheck extends HttpServlet{
   private static final long serialVersionUID = 1L;

public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
        String email=request.getParameter(Constants.email).trim();
        response.setContentType(Constants.contentPlain);
        boolean doesEmailExist=false;
        try {
        	doesEmailExist=(SQLQueries.checkEmailExists(email));
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
        if(doesEmailExist){
            response.getWriter().write(Constants.emailAlreadyRegistered);
        }
    }
}

