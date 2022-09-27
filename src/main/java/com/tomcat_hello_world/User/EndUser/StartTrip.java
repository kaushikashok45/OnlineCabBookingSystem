package com.tomcat_hello_world.User.EndUser;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

import javax.servlet.*;
import javax.servlet.http.*;

import com.google.gson.Gson;
import com.tomcat_hello_world.Security.Constants;
import com.tomcat_hello_world.Storage.*;
import com.tomcat_hello_world.User.*;

public class StartTrip extends HttpServlet{

	private static final long serialVersionUID = 1L;
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
    	String jsonparam=request.getParameter("bookingDetails");
	    Gson gson=new Gson();
	    BookCab bookingDetails = gson.fromJson(jsonparam, BookCab.class);
    	HttpSession sessionsa = request.getSession(false);
    	BigDecimal fare=bookingDetails.getFare();
    	int id=0;
    	Trip t=null;
    	try {
    	id=SQLQueries.getLastTripId();
    	t=SQLQueries.getTrip(id);
    	BigDecimal newWallet=(SQLQueries.getCabWallet(t.getCabId())).add(fare);
    	SQLQueries.startTrip(newWallet, SQLQueries.getLocId(bookingDetails.getDest()), id);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	 String json = new Gson().toJson(bookingDetails);
    	 PrintWriter out = response.getWriter();
    	 response.setContentType("application/json");
    	 response.setCharacterEncoding("UTF-8");
    	 out.print(json);
    	 out.flush();
    }
}
