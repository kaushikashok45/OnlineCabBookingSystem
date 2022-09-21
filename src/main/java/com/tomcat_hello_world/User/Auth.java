package com.tomcat_hello_world.User;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.http.Cookie;

import com.tomcat_hello_world.Security.Constants;
import com.tomcat_hello_world.Storage.*;


public class Auth extends HttpServlet{
    private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
          String userEmail=request.getParameter(Constants.email);
          String userPassword=request.getParameter(Constants.pass);
          try {
          if((SQLQueries.checkEmailExists(userEmail))){
            if(SQLQueries.checkPassword(userEmail, userPassword)){

                HttpSession sessionsa=request.getSession(true);
                int userId=Integer.parseInt(SQLQueries.getUserId(userEmail));
                String userName=SQLQueries.getUserName(userEmail);
                sessionsa.setAttribute(Constants.id,userId);
                sessionsa.setAttribute(Constants.smallName,userName);
                sessionsa.setAttribute(Constants.email,userEmail);
                Cookie cid=new Cookie(Constants.id,String.valueOf(userId));
                Cookie cname=new Cookie(Constants.smallName,userName);
                Cookie cemail=new Cookie(Constants.email,userEmail);
                response.addCookie(cid);
                response.addCookie(cname);
                response.addCookie(cemail);
                String userRole=SQLQueries.getUserType(userEmail);
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
            response.sendRedirect(Constants.indexErrorURL);
           }
          }
          else{
            response.sendRedirect(Constants.indexErrorURL);
          }
          }
          catch(Exception e) {
        	  e.printStackTrace();
          }
    }
}

