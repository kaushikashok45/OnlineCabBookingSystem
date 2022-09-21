package com.tomcat_hello_world.User.EndUser;

import javax.servlet.*;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.*;

import com.tomcat_hello_world.Security.Constants;
import com.tomcat_hello_world.Security.Encryptor;
import com.tomcat_hello_world.Storage.SQLQueries;

public class Updateprofile extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		HttpSession session=request.getSession(false);
		int userid=(int)session.getAttribute(Constants.id);
		String oldName=(String)session.getAttribute(Constants.smallName);
		String newName=request.getParameter(Constants.name);
        String newPassword=request.getParameter("password");
        String regex = Constants.passwordRegex;
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(newPassword);
        boolean pwdmatch=m.matches();
        boolean isUpdated=false;
        String query="Update Users SET Name=?,Password=? WHERE id=?";
        try {
        if((newName==null)||(newName.trim().isEmpty())||(newName.equals(oldName))) {
        	if((newPassword==null)||(pwdmatch)) {
        		query=null;
        	}
        	else {
        		query="Update Users SET Password=? WHERE id=?";
        		isUpdated=SQLQueries.updateProfile(query,Encryptor.encrypt(newPassword),userid);
        	}
        } 
        else if((newPassword==null)||(!pwdmatch)) {
        	query="Update Users SET Name=? WHERE id=?";
        	isUpdated=SQLQueries.updateProfile(query,newName,userid);
        }
        else {
        	isUpdated=SQLQueries.updateProfile(query,newName,Encryptor.encrypt(newPassword),userid);
        }
        }
        catch(SQLException | ClassNotFoundException | NullPointerException | NoSuchAlgorithmException e) {
        	response.sendRedirect("./Error1.jsp");
        }
        ArrayList<String> userDetails=new ArrayList<String>();
        try {
			userDetails=SQLQueries.getUserDetails(userid);
		} catch (ClassNotFoundException | NullPointerException | SQLException e) {
			response.sendRedirect("./Error1.jsp");
		}
        HttpSession session2=request.getSession(true);
        session2.setAttribute(Constants.smallName,userDetails.get(0));
        session2.setAttribute(Constants.email,userDetails.get(1));
        response.setContentType("text/plain");
        PrintWriter out=response.getWriter();
        out.write("Success");
        response.sendRedirect("Profile.jsp");
	}
        

}
