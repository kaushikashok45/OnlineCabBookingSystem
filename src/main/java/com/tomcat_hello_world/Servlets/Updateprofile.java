package com.tomcat_hello_world.Servlets;
import javax.servlet.*;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.servlet.http.*;
import com.tomcat_hello_world.Utility.Constants;
import com.tomcat_hello_world.Operations.Authentication.UserOperations;

public class Updateprofile extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		HttpSession session=request.getSession(false);
		UserOperations user=(UserOperations)session.getAttribute("User");
		String newName=request.getParameter(Constants.name);
        String newPassword=request.getParameter("password");
        boolean isUserUpdated=false;
        try {
          isUserUpdated=user.updateUser(newName, newPassword);
          }
        catch(SQLException | ClassNotFoundException | NullPointerException | NoSuchAlgorithmException e) {
        	e.printStackTrace();
        }
        if(isUserUpdated) {
        HttpSession session2=request.getSession(true);
        session2.setAttribute("User",user);
        response.setContentType("text/plain");
        PrintWriter out=response.getWriter();
        out.write("Success");
        response.sendRedirect("/com.tomcat_hello_world/account");
        }
	}
        

}
