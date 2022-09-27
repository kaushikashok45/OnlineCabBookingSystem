package com.tomcat_hello_world.User.EndUser;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.tomcat_hello_world.Security.Constants;
import com.tomcat_hello_world.Storage.*;
import java.util.*;



public class FetchLocations extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
       ArrayList<String> locations=new ArrayList<String>();
       try {
    	   locations=SQLQueries.getLocations();
       }
       catch(Exception e){
    	   e.printStackTrace();
       }
       String locationsString=Constants.emptyString;
       String lastLoc=locations.get(locations.size() - 1);
       for(String loc:locations) {
    	   if((loc.equals(lastLoc))) {
    		   locationsString=locationsString+loc;
    	   }
    	   else {
    		   locationsString=locationsString+loc+Constants.comma;
    	   }
    	   
       }
       locationsString=locationsString.trim();
       response.setContentType(Constants.contentPlain);
       response.getWriter().write(locationsString);
    }

}
