package com.tomcat_hello_world.User.EndUser;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;
import com.tomcat_hello_world.Storage.*;
import com.tomcat_hello_world.User.Driver.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class BookCab extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	public static String upgradeCarType(String carType) {
		String upgradedCarType=carType;
		if(carType.equals("hatchback")) {
			upgradedCarType="sedan";
			
		}
		else if(carType.equals("sedan")) {
			upgradedCarType="suv";
			
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

	
	public static Cab assignCab(String src,String dest,String carType) {
		ArrayList<Cab> cars=new ArrayList<Cab>();
		ArrayList<Cab> assignedCabs=new ArrayList<Cab>();
		cars=SQLQueries.getFreeCabs(src,carType);
		if(cars.isEmpty()){
			cars=SQLQueries.getFreeCabs(src,carType,10);
		}
		
		
		if((cars.isEmpty())&&(!(carType.equals("suv")))){
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
        String dest=request.getParameter("dest");
        String src=request.getParameter("src");
        String carType=request.getParameter("carType");
        Cab c=assignCab(src,dest,carType);
        HttpSession session=request.getSession(true);
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();
        
        request.setAttribute("cab",c);
        session.setAttribute("cab", c);
        BigDecimal fare=null;
        int eta=0;
        BigDecimal dist=SQLQueries.getDistance(src,dest);
        if(c!=null) {
   	       if(("hatchback").equals(c.getType())){
   		     fare=dist.multiply(new BigDecimal(20));
   		     if(src.equals(c.getLoc())) {
   		    	 eta=5;
   		     }
   		     else {
   		    	eta=5+((SQLQueries.getDistance(src,c.getLoc())).divide(new BigDecimal(Double.toString(0.8)))).intValue();
   		     }
   	       }
   	       else if(("sedan").equals(c.getType())){
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
       
        session.setAttribute("dist",dist);
        session.setAttribute("eta",eta);
        request.setAttribute("src",src);
        request.setAttribute("dest",dest);
        session.setAttribute("fare",fare);
        session.setAttribute("src", src);
        session.setAttribute("dest", dest);
        String email=(String) request.getSession(false).getAttribute("email");
        session.setAttribute("email",email);
        out.print("hi");
        response.sendRedirect("ReqConfirm.jsp");
        
    }
}
