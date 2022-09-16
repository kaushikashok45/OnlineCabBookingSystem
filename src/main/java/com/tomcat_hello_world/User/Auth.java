package com.tomcat_hello_world.User;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.http.Cookie;
import com.tomcat_hello_world.Storage.*;


public class Auth extends HttpServlet{
    private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
          String userEmail=request.getParameter("email");
          String userPassword=request.getParameter("pass");
          if((SQLQueries.checkEmailExists(userEmail))){
            if(SQLQueries.checkPassword(userEmail, userPassword)){

                HttpSession sessionsa=request.getSession(true);
                String userId=SQLQueries.getUserId(userEmail);
                String userName=SQLQueries.getUserName(userEmail);
                sessionsa.setAttribute("id",userId);
                sessionsa.setAttribute("name",userName);
                sessionsa.setAttribute("email",userEmail);
                Cookie cid=new Cookie("id",userId);
                Cookie cname=new Cookie("name",userName);
                Cookie cemail=new Cookie("email",userEmail);
                response.addCookie(cid);
                response.addCookie(cname);
                response.addCookie(cemail);
                String userRole=SQLQueries.getUserType(userEmail);
                if(("Customer").equals(userRole)){
                	response.sendRedirect("/com.tomcat_hello_world/account");
                }
                else if(("Admin").equals(userRole)){
                	response.sendRedirect("/com.tomcat_hello_world/admin");
                }
                else if(("Driver").equals(userRole)){
                	response.sendRedirect("/com.tomcat_hello_world/partner");
                }
           }
           else{
            response.sendRedirect("/com.tomcat_hello_world/?error=1");
           }
          }
          else{
            response.sendRedirect("/com.tomcat_hello_world/?error=1");
          }
    }
}

