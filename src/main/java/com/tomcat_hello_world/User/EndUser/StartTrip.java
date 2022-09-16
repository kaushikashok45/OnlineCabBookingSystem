package com.tomcat_hello_world.User.EndUser;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.*;
import javax.servlet.http.*;
import com.tomcat_hello_world.Storage.*;
import com.tomcat_hello_world.User.*;

public class StartTrip extends HttpServlet{

	private static final long serialVersionUID = 1L;
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
    	HttpSession sessionsa = request.getSession(false);
    	BigDecimal fare=(BigDecimal) sessionsa.getAttribute("fare");
    	int id=SQLQueries.getLastTripId();
    	Trip t=SQLQueries.getTrip(id);
    	SQLQueries.changeTripStatus(id,"Completed");
    	SQLQueries.changeCabStatus(t.getCabId(),"Available");
    	BigDecimal newWallet=(SQLQueries.getCabWallet(t.getCabId())).add(fare);
    	SQLQueries.changeCabWallet(t.getCabId(),newWallet);
    	SQLQueries.changeCabLoc(t.getCabId(),SQLQueries.getLocId((String)sessionsa.getAttribute("dest")));
    	sessionsa.setAttribute("trip", t);
    	request.getRequestDispatcher("TripCompleted.jsp").forward(request, response);
    }
}
