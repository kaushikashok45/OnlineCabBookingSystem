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
import com.tomcat_hello_world.User.Driver.Cab;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class BookingConfirmed extends HttpServlet{
	private static final long serialVersionUID = 1L;
    private String otp;
    private BookCab bookingDetails;
    
    public String getOtp() {
    	return this.otp;
    }
    
    public BookCab getBookingDetails() {
    	return this.bookingDetails;
    }
    
    
    public void setOtp(String otp) {
    	this.otp=otp;
    }
    
    public void setBookingDetails(BookCab bookingDetails) {
    	this.bookingDetails=bookingDetails;
    }
    
    
    public BookingConfirmed() {}
    
    public BookingConfirmed(String otp,BookCab bookingDetails) {
    	this.setOtp(otp);
    	this.setBookingDetails(bookingDetails);
    }
    
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException,NullPointerException{
		      String jsonparam=request.getParameter("bookingDetails");
		      Gson gson=new Gson();
		      BookCab bookingDetails = gson.fromJson(jsonparam, BookCab.class);
		      String email=request.getParameter("email");
		      HttpSession sessionsa = request.getSession(true);
		     Cab c=bookingDetails.getAssignedCab();
		     BigDecimal fare=bookingDetails.getFare();
		     int eta=bookingDetails.getEta();
		     BigDecimal dist=bookingDetails.getDistance();
		     double speed=0;
		     if((c.getType()).equals("hatchback")){
		    	 speed=0.8;
		     }
		     else if((c.getType()).equals("sedan")){
		    	 speed=0.6;
		     }
		     else if((c.getType()).equals("suv")){
		    	 speed=0.4;
		     }	 
		     int time=(dist.divide(new BigDecimal(speed),2, RoundingMode.HALF_UP)).intValue();
		     
		     String uid=null;
		     try {
		     uid=SQLQueries.getUserId(email);
		     }
		     catch(Exception e) {
		    	 e.printStackTrace();
		     }
		     String src=bookingDetails.getSrc();
		     String dest=bookingDetails.getDest();
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
		     else {
		    	 throw new NullPointerException();
		     }
		     if(insertedTrip) {
		    	 BookingConfirmed details=new BookingConfirmed(numbers,bookingDetails);
		    	 String json = new Gson().toJson(details);
		     	 PrintWriter out = response.getWriter();
		     	 response.setContentType("application/json");
		     	 response.setCharacterEncoding("UTF-8");
		     	 out.print(json);
		     	 out.flush();
		     }
		     else {
		    	 throw new NullPointerException();
		     }
	}
}
