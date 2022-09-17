package com.tomcat_hello_world.User.EndUser;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tomcat_hello_world.Security.Constants;
import com.tomcat_hello_world.Storage.SQLQueries;
import com.tomcat_hello_world.User.Trip;
import java.math.BigDecimal;


public class CancelTrip extends HttpServlet{
   private static final long serialVersionUID = 1L;

public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
	HttpSession sessionsa = request.getSession(false);
	BigDecimal fare=new BigDecimal(Constants.penalty);
	int id=0;
	Trip t=null;
	try {
	id=SQLQueries.getLastTripId();
	t=SQLQueries.getTrip(id);
	BigDecimal newWallet=(SQLQueries.getCabWallet(t.getCabId())).add(fare);
	SQLQueries.cancelTrip(newWallet,SQLQueries.getLocId((String)sessionsa.getAttribute(Constants.src)),id);
	sessionsa.setAttribute(Constants.trip, t);
	sessionsa.setAttribute(Constants.fare, fare);
	request.getRequestDispatcher(Constants.tripCancelled).forward(request, response);
	}
	catch(Exception e) {
		e.printStackTrace();
	}
}
}
