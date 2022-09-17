package com.tomcat_hello_world.User;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.util.regex.*;

import com.tomcat_hello_world.Security.Constants;
import com.tomcat_hello_world.Storage.*;


public class Process extends HttpServlet{
    
    private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
        String name=request.getParameter(Constants.smallName);
        String email=request.getParameter(Constants.email);
        String pwd=request.getParameter(Constants.pass);
        boolean isPasswordValid=true;
        String regex = Constants.passwordRegex;
        if (pwd == null) {
            isPasswordValid=false;
        }
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(pwd);
        boolean pwdmatch=m.matches();
        boolean doesEmailExist=false;
        try {
        	doesEmailExist=(SQLQueries.checkEmailExists(email));
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
        if((!doesEmailExist)&&(pwdmatch)&&(isPasswordValid)&&(name!=null)&&(!(name.equals(Constants.emptyString)))&&(!name.equals(Constants.emptySpaceString))){
          boolean insertedUser=false;
          try {
        	  insertedUser=SQLQueries.insertUser(name,email,pwd,Constants.bigCustomer);
          }
          catch(Exception e) {
        	  e.printStackTrace();
          }
          if(insertedUser){
            response.sendRedirect(Constants.indexSuccessURL);
          }
          else{
            response.sendRedirect(Constants.indexRegErrorURL);
          }
       
        }
        else{
            response.sendRedirect(Constants.indexRegErrorURL);
        }
    }    
}
