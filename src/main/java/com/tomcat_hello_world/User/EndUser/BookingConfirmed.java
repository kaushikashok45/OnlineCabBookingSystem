package com.tomcat_hello_world.User.EndUser;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tomcat_hello_world.Security.Constants;
import com.tomcat_hello_world.Storage.SQLQueries;
import com.tomcat_hello_world.User.Driver.Cab;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class BookingConfirmed extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		      HttpSession sessionsa = request.getSession(true);
		     Cab c=(Cab) sessionsa.getAttribute(Constants.cab); 
		     BigDecimal fare=(BigDecimal) sessionsa.getAttribute(Constants.fare);
		     int eta=(int) sessionsa.getAttribute(Constants.eta);
		     BigDecimal dist=(BigDecimal) sessionsa.getAttribute(Constants.dist);
		     double speed=(double) sessionsa.getAttribute(Constants.speed);
		     int time=(dist.divide(new BigDecimal(speed),2, RoundingMode.HALF_UP)).intValue();
		     String email=(String) sessionsa.getAttribute(Constants.email);
		     String uid=null;
		     try {
		     uid=SQLQueries.getUserId(email);
		     }
		     catch(Exception e) {
		    	 e.printStackTrace();
		     }
		     String src=(String) sessionsa.getAttribute(Constants.src);
		     String dest=(String) sessionsa.getAttribute(Constants.dest);
		     Random rnd = new Random();
		     int number = rnd.nextInt(999999);
		     String numbers=String.format(Constants.otpFormat, number);
		     sessionsa.setAttribute(Constants.cab,c);
		     sessionsa.setAttribute(Constants.src,src);
		     sessionsa.setAttribute(Constants.dest,dest);
		     sessionsa.setAttribute(Constants.fare,fare);
		     sessionsa.setAttribute(Constants.eta,eta);
		     sessionsa.setAttribute(Constants.dist,dist);
		     sessionsa.setAttribute(Constants.speed,speed);
		     sessionsa.setAttribute(Constants.uid,uid);
		     sessionsa.setAttribute(Constants.otp,numbers);
		     sessionsa.setAttribute(Constants.email,email);
		     boolean insertedTrip=false;
		     if(email!=null) {
		    	 try {
		    	  insertedTrip=SQLQueries.insertTrip(Integer.parseInt(uid),c.getId(),SQLQueries.getLocId(src), SQLQueries.getLocId(dest),numbers,Constants.tripStatus1,time);
		    	  SQLQueries.changeCabStatus(c.getId(),Constants.cabStatus2);
		    	 }
		    	 catch(Exception e) {
		    		 e.printStackTrace();
		    	 }
		     }
		     if(insertedTrip) {
		    	 request.getRequestDispatcher(Constants.bookingConfirm).forward(request, response);
		     }
		     else {
		    	 request.getRequestDispatcher(Constants.account).forward(request, response);
		     }
	}
}
