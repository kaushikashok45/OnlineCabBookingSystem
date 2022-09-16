package com.tomcat_hello_world.User.EndUser;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tomcat_hello_world.Storage.SQLQueries;
import com.tomcat_hello_world.User.Trip;
import java.math.BigDecimal;


public class CancelTrip extends HttpServlet{
   private static final long serialVersionUID = 1L;

public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
	HttpSession sessionsa = request.getSession(false);
	BigDecimal fare=new BigDecimal("100");
	int id=SQLQueries.getLastTripId();
	Trip t=SQLQueries.getTrip(id);
	SQLQueries.changeTripStatus(id,"Cancelled");
	SQLQueries.changeCabStatus(t.getCabId(),"Available");
	SQLQueries.changeTripTimeEnded(id);
	BigDecimal newWallet=(SQLQueries.getCabWallet(t.getCabId())).add(fare);
	SQLQueries.changeCabWallet(t.getCabId(),newWallet);
	SQLQueries.changeCabLoc(t.getCabId(),SQLQueries.getLocId((String)sessionsa.getAttribute("src")));
	sessionsa.setAttribute("trip", t);
	sessionsa.setAttribute("fare", fare);
	request.getRequestDispatcher("TripCancelled.jsp").forward(request, response);
}
}
