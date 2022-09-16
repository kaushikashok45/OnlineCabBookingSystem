<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1" session="false"%>
<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);
%> 
<%@page import="com.tomcat_hello_world.User.Driver.Cab" %>
<%@page import="java.math.BigDecimal" %>
<%@page import="javax.servlet.http.HttpSession" %>
<%
HttpSession sessionsa = request.getSession(false);  
     Cab assignedCab =(Cab)(sessionsa.getAttribute("cab"));
     String email=(String)sessionsa.getAttribute("email");
     BigDecimal dist=(BigDecimal)sessionsa.getAttribute("dist");
     int eta=(int)sessionsa.getAttribute("eta");
     String msg=null;
     double speed=0.4;
     if(assignedCab!=null){
    	 String cabType="SUV";
    	 if(("hatchback").equals(assignedCab.getType())){
    		 cabType="Hatchback";
    		 speed=0.8;
    	 }
    	 else if(("sedan").equals(assignedCab.getType())){
    		 cabType="Sedan";
    		 speed=0.6;
    	 }
    	 msg="<div id=\"cabBox\"><div id=\"header\"><h3>Cab Details</h3></div><div id=\"item1\"<h4><span id=\"qn\"> Assigned Cab- ID: </span>"+assignedCab.getId()+ "</h4></div><div id=\"item2\"><h4> <span id=\"qn\">  Type: </span>"+cabType+"</h4></div><div id=\"item3\"><h4><span id=\"qn\">  Trip Distance: </span>"+dist+" kms</h4></div><div id=\"item4\"><h4> <span id=\"qn\"> ETA: </span>"+sessionsa.getAttribute("eta")+" mins"+"</h4></div><div id=\"item5\"><h4> <span id=\"qn\"> Fare: &#8377;</span>"+((BigDecimal)sessionsa.getAttribute("fare"))+"</h4></div><div id=\"BookBtn\"><form method=\"GET\" action=\"BookingConfirmed\"><button type=\"submit\" id=\"submit\">Confirm Booking &#8594;</button></div></div>";
     }
     else{
    	 msg="<div id=\"cabBox\">Sorry...no cabs found!</div>";
     }
     String src=(String)sessionsa.getAttribute("src");
     String dest=(String)sessionsa.getAttribute("dest");
     HttpSession sessionsb = request.getSession(true); 
     sessionsb.setAttribute("cab",assignedCab);
     sessionsb.setAttribute("dist",dist);
     sessionsb.setAttribute("speed",speed);
     sessionsb.setAttribute("email",email);
     sessionsb.setAttribute("src",src);
     sessionsb.setAttribute("dest",dest);
     
%>

<!DOCTYPE html>
  <html>
    <head>
      <title>Hello ${name}!</title>  
      <script type="text/javascript" src="https://code.jquery.com/jquery-1.10.2.js"></script> 
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Account.css"> 
    </head>
    <body>
    <div id="accountbox">
    <h1>Ashok Cabs</h1>
    <div id="link">
      <form action="logout" method="POST">
        <div id="email" data-email="<%=email%>"></div>
        <button id="logout" type="submit">Log out!</button>
      </form>
    </div>
  </div>
       <%=msg %>
       
   </body>
</html>