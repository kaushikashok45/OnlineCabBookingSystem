package com.tomcat_hello_world.User.EndUser;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tomcat_hello_world.Storage.SQLQueries;
import com.tomcat_hello_world.User.Driver.Cab;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class BookingConfirmed extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		      HttpSession sessionsa = request.getSession(true);
		     Cab c=(Cab) sessionsa.getAttribute("cab"); 
		     BigDecimal fare=(BigDecimal) sessionsa.getAttribute("fare");
		     int eta=(int) sessionsa.getAttribute("eta");
		     BigDecimal dist=(BigDecimal) sessionsa.getAttribute("dist");
		     double speed=(double) sessionsa.getAttribute("speed");
		     int time=(dist.divide(new BigDecimal(speed),2, RoundingMode.HALF_UP)).intValue();
		     String email=(String) sessionsa.getAttribute("email");
		     String uid=SQLQueries.getUserId(email);
		     String src=(String) sessionsa.getAttribute("src");
		     String dest=(String) sessionsa.getAttribute("dest");
		     Random rnd = new Random();
		     int number = rnd.nextInt(999999);
		     String numbers=String.format("%06d", number);
		     sessionsa.setAttribute("cab",c);
		     sessionsa.setAttribute("src",src);
		     sessionsa.setAttribute("dest",dest);
		     sessionsa.setAttribute("fare",fare);
		     sessionsa.setAttribute("eta",eta);
		     sessionsa.setAttribute("dist",dist);
		     sessionsa.setAttribute("speed",speed);
		     sessionsa.setAttribute("time",uid);
		     sessionsa.setAttribute("otp",numbers);
		     sessionsa.setAttribute("email",email);
		     boolean insertedTrip=false;
		     if(email!=null) {
		    	  insertedTrip=SQLQueries.insertTrip(Integer.parseInt(uid),c.getId(),SQLQueries.getPointsId(src, dest),numbers,"Underway",time);
		    	  SQLQueries.changeCabStatus(c.getId(),"Booked");
		     }
		     if(insertedTrip) {
		    	 request.getRequestDispatcher("BookingConfirm.jsp").forward(request, response);
		     }
		     else {
		    	 request.getRequestDispatcher("/account").forward(request, response);
		     }
	}
}
