package com.tomcat_hello_world.Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import com.tomcat_hello_world.Exceptions.AccessDeniedException;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.simple.JSONObject;
import com.tomcat_hello_world.Operations.Administration.AdminOperations;
import com.tomcat_hello_world.Operations.Authentication.UserOperations;

     

public class FetchStats extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		JSONObject json=null;
		HttpSession session=request.getSession(false);
		AdminOperations admin=null;
		try {
			admin=new AdminOperations(((UserOperations)session.getAttribute("User")).getUser());
			json=admin.getDashboardDetails();
		}
		catch(AccessDeniedException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		PrintWriter out = response.getWriter();
    	response.setContentType("application/json");
    	response.setCharacterEncoding("UTF-8");
    	out.print(json);
    	out.flush();
	}

}
