package com.tomcat_hello_world.User.EndUser;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.tomcat_hello_world.Storage.*;
import java.util.*;



public class FetchLocations extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
       ArrayList<String> locations=SQLQueries.getLocations();
       String locationsString="";
       for(String loc:locations) {
    	   locationsString=locationsString+loc+",";
       }
       locationsString=locationsString.trim();
       response.setContentType("text/plain");
       response.getWriter().write(locationsString);
    }

}
