package com.tomcat_hello_world.User.EndUser;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.*;
import javax.servlet.http.*;

import com.tomcat_hello_world.Security.Constants;
import com.tomcat_hello_world.Storage.*;
import com.tomcat_hello_world.User.*;

public class StartTrip extends HttpServlet{

	private static final long serialVersionUID = 1L;
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
    	HttpSession sessionsa = request.getSession(false);
    	BigDecimal fare=(BigDecimal) sessionsa.getAttribute(Constants.fare);
    	int id=0;
    	Trip t=null;
    	try {
    	id=SQLQueries.getLastTripId();
    	t=SQLQueries.getTrip(id);
    	SQLQueries.changeTripStatus(id,Constants.tripStatus2);
    	SQLQueries.changeCabStatus(t.getCabId(),Constants.cabStatus1);
    	BigDecimal newWallet=(SQLQueries.getCabWallet(t.getCabId())).add(fare);
    	SQLQueries.changeCabWallet(t.getCabId(),newWallet);
    	SQLQueries.changeCabLoc(t.getCabId(),SQLQueries.getLocId((String)sessionsa.getAttribute(Constants.dest)));
    	sessionsa.setAttribute(Constants.trip, t);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	request.getRequestDispatcher(Constants.tripCompleted).forward(request, response);
    }
}
