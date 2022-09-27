package com.tomcat_hello_world.User.EndUser;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import com.tomcat_hello_world.Security.Constants;
import com.tomcat_hello_world.Storage.*;
import com.tomcat_hello_world.User.Driver.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import com.google.gson.*;   

public class BookCab extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private String src,dest,cabType,driverName;
	private BigDecimal fare,distance;
	private int eta;
	private Cab assignedCab;
	
	public BookCab() {}
	
	public String getSrc() {
		return this.src;
	}
	
	public String getDest() {
		return this.dest;
	}
	
	public String getCabType() {
		return this.cabType;
	}
	
	public String getDriverName() {
		return this.driverName;
	}
	
	public BigDecimal getFare() {
		return this.fare;
	}
	
	public BigDecimal getDistance() {
		return this.distance;
	}
	
	public int getEta() {
		return this.eta;
	}
	
	public Cab getAssignedCab() {
		return this.assignedCab;
	}
	
	public void setSrc(String src) {
		this.src=src;
	}
	
	public void setDest(String dest) {
		this.dest=dest;
	}
	
	public void setCabType(String cabType) {
		this.cabType=cabType;
	}
	
	public void setDriverName(String driverName) {
		this.driverName=driverName;
	}
	
	public void setFare(BigDecimal fare) {
		this.fare=fare;
	}
	
	public void setDistance(BigDecimal distance) {
		this.distance=distance;
	}
	
	public void setEta(int eta) {
		this.eta=eta;
	}
	
	public void setAssignedCab(Cab assignedCab) {
		this.assignedCab=assignedCab;
	}
	
	public BookCab(String src,String dest,String cabtype,String driverName,BigDecimal fare,BigDecimal distance,int eta,Cab assignedcab) {
		this.setSrc(src);
		this.setDest(dest);
		this.setCabType(cabtype);
		this.setDriverName(driverName);
		this.setFare(fare);
		this.setDistance(distance);
		this.setEta(eta);
		this.setAssignedCab(assignedcab);
	}
	public static String upgradeCarType(String carType) {
		String upgradedCarType=carType;
		if(carType.equals(Constants.car1)) {
			upgradedCarType=Constants.car2;
			
		}
		else if(carType.equals(Constants.car2)) {
			upgradedCarType=Constants.car3;
			
		}
		return upgradedCarType;
	}
	
	public static Cab findLeastWallet(ArrayList<Cab> cars) {
		Cab assignedCab=null;
		for(Cab c:cars) {
			if(assignedCab==null) {
				assignedCab=c;
			}
			else {
				if(((assignedCab.getWallet()).compareTo(c.getWallet()))==1) {
					assignedCab=c;
				}
			}
		}
		return assignedCab;
	}

	
	public static Cab assignCab(String src,String dest,String carType) throws SQLException,ClassNotFoundException,NullPointerException{
		ArrayList<Cab> cars=new ArrayList<Cab>();
		ArrayList<Cab> assignedCabs=new ArrayList<Cab>();
		cars=SQLQueries.getFreeCabs(src,carType);
		if(cars.isEmpty()){
			cars=SQLQueries.getFreeCabs(src,carType,10);
		}
		
		
		if((cars.isEmpty())&&(!(carType.equals(Constants.car3)))){
			Cab d=assignCab(src,dest,upgradeCarType(carType));
			cars.add(d);
		}
		
		if(!(cars.isEmpty())) {
			if(cars.size()>1) {
				assignedCabs.add(findLeastWallet(cars));
			}
			else {
				assignedCabs.addAll(cars);
			}
		}
		Cab c=null;
		if(!(assignedCabs.isEmpty())) {
			c=assignedCabs.get(0);
		}
		return c;
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
        String dest=request.getParameter(Constants.dest);
        String src=request.getParameter(Constants.src);
        String carType=request.getParameter(Constants.carType);
        Cab c=null;
        try {
        c=assignCab(src,dest,carType);
        }
        catch(Exception  e) {
        	e.printStackTrace();
        }
        HttpSession session=request.getSession(true);
        response.setContentType(Constants.contentHtml);
       
        
        request.setAttribute(Constants.cab,c);
        session.setAttribute(Constants.cab, c);
        BigDecimal fare=null,dist=null;
        int eta=0;
        try {
        dist=SQLQueries.getDistance(src,dest);
        if(c!=null) {
   	       if((Constants.car1).equals(c.getType())){
   		     fare=dist.multiply(new BigDecimal(20));
   		     if(src.equals(c.getLoc())) {
   		    	 eta=5;
   		     }
   		     else {
   		    	eta=5+((SQLQueries.getDistance(src,c.getLoc())).divide(new BigDecimal(Double.toString(0.8)))).intValue();
   		     }
   	       }
   	       else if((Constants.car2).equals(c.getType())){
   		       fare=dist.multiply(new BigDecimal(30));
   		       if(src.equals(c.getLoc())) {
  		    	 eta=7;
  		       }
  		       else {
  		    	eta=7+((SQLQueries.getDistance(src,c.getLoc())).divide(new BigDecimal(Double.toString(0.6)),2, RoundingMode.HALF_UP)).intValue();
  		       }
     	    }
   	        else{
   		      fare=dist.multiply(new BigDecimal(50));
   		      if(src.equals(c.getLoc())) {
 		    	 eta=10;
 		       }
 		       else {
 		    	eta=10+((SQLQueries.getDistance(src,c.getLoc())).divide(new BigDecimal(Double.toString(0.4)))).intValue();
 		       }
   	       }
        }
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
        String driverName=null;
		try {
			driverName = SQLQueries.getUserNameById(c.getUid());
		} catch (ClassNotFoundException | NullPointerException | SQLException e) {
			// TODO Auto-generated catch block
			response.sendRedirect("./Error1.jsp");
		}
        session.setAttribute(Constants.dist,dist);
        session.setAttribute(Constants.eta,eta);
        request.setAttribute(Constants.src,src);
        request.setAttribute(Constants.dest,dest);
        session.setAttribute(Constants.fare,fare);
        session.setAttribute(Constants.src, src);
        session.setAttribute(Constants.dest, dest);
        String email=(String) request.getSession(false).getAttribute(Constants.email);
        session.setAttribute(Constants.email,email);
        BookCab bookingDetails=new BookCab(src,dest,c.getType(),driverName,fare,dist,eta,c);
        String json = new Gson().toJson(bookingDetails);
    	PrintWriter out = response.getWriter();
    	response.setContentType("application/json");
    	response.setCharacterEncoding("UTF-8");
    	out.print(json);
    	out.flush();
        
    }
}
