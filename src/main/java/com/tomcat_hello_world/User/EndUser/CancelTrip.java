package com.tomcat_hello_world.User.EndUser;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.tomcat_hello_world.Security.Constants;
import com.tomcat_hello_world.Storage.SQLQueries;
import com.tomcat_hello_world.User.Trip;
import java.math.BigDecimal;


public class CancelTrip extends HttpServlet{
   private static final long serialVersionUID = 1L;

public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
	HttpSession sessionsa = request.getSession(false);
	String jsonparam=request.getParameter("bookingDetails");
	Gson gson=new Gson();
	BookCab bookingDetails = gson.fromJson(jsonparam, BookCab.class);
	BigDecimal fare=new BigDecimal(Constants.penalty);
	int id=0;
	Trip t=null;
	try {
	id=SQLQueries.getLastTripId();
	t=SQLQueries.getTrip(id);
	BigDecimal newWallet=(SQLQueries.getCabWallet(t.getCabId())).add(fare);
	SQLQueries.cancelTrip(newWallet,SQLQueries.getLocId(bookingDetails.getSrc()),id);
	sessionsa.setAttribute(Constants.trip, t);
	sessionsa.setAttribute(Constants.fare, fare);
	String json = new Gson().toJson(bookingDetails);
	PrintWriter out = response.getWriter();
	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	out.print(json);
	out.flush();
	}
	catch(Exception e) {
		e.printStackTrace();
	}
}
}
