package com.tomcat_hello_world.User;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.util.regex.*;
import com.tomcat_hello_world.Storage.*;


public class Process extends HttpServlet{
    
    private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
        String name=request.getParameter("name");
        String email=request.getParameter("email");
        String pwd=request.getParameter("pass");
        boolean isPasswordValid=true;
        String regex = "^(?=.*[0-9])"
        + "(?=.*[a-z])(?=.*[A-Z])"
        + "(?=\\S+$).{8,15}$";
        if (pwd == null) {
            isPasswordValid=false;
        }
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(pwd);
        boolean pwdmatch=m.matches();
        if((!(SQLQueries.checkEmailExists(email)))&&(pwdmatch)&&(isPasswordValid)&&(name!=null)&&(!(name.equals("")))&&(!name.equals(" "))){
          if(SQLQueries.insertUser(name,email,pwd,"Customer")){
            response.sendRedirect("/com.tomcat_hello_world/?success=1");
          }
          else{
            response.sendRedirect("/com.tomcat_hello_world/?regError=1");
          }
       
        }
        else{
            response.sendRedirect("/com.tomcat_hello_world/?regError=1");
        }
    }    
}
