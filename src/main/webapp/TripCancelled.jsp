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
HttpSession sessionsc = request.getSession(false);
     Cab assignedCab =(Cab)(request.getAttribute("cab"));
     String email=(String)sessionsc.getAttribute("email");
     BigDecimal dist=(BigDecimal)request.getAttribute("dist");
     String otp=(String) sessionsc.getAttribute("otp");
     BigDecimal fare=(BigDecimal) sessionsc.getAttribute("fare");
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
    	 msg="<div id=\"cabBox\"><div id=\"header\"><h3>Cab Details</h3></div><div id=\"item1\"<h4><span id=\"qn\"> Assigned Cab- ID: </span>"+assignedCab.getId()+ "</h4></div><div id=\"item2\"><h4> <span id=\"qn\">  Type: </span>"+cabType+"</h4></div><div id=\"item3\"><h4><span id=\"qn\">  Trip Distance: </span>"+dist+" kms</h4></div><div id=\"item4\"><h4> <span id=\"qn\"> ETA: </span>"+request.getAttribute("eta")+" mins"+"</h4></div><div id=\"item5\"><h4> <span id=\"qn\"> Fare: &#8377;</span>"+((BigDecimal)request.getAttribute("fare"))+"</h4></div><div id=\"BookBtn\"><form method=\"GET\" action=\"BookingConfirmed\"><button id=\"submit\">Confirm Booking &#8594;</button></div></div>";
     }
     else{
    	 msg="Sorry...no cabs found!";
     }
     HttpSession sessionsa = request.getSession(true);
     sessionsa.setAttribute("cab",assignedCab); 
     sessionsa.setAttribute("fare",((BigDecimal)request.getAttribute("fare")));
     sessionsa.setAttribute("eta",request.getAttribute("eta"));
     sessionsa.setAttribute("dist",dist);
     sessionsa.setAttribute("speed",speed);
     sessionsa.setAttribute("email",email);
     sessionsa.setAttribute("src",(String)(request.getAttribute("src")));
     sessionsa.setAttribute("dest",(String)(request.getAttribute("dest")));
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
       <div id="confirmbox">
        <h2 class="confirmation" id="cross">&#10060;</h2>
        <h2 class="cancellation">Trip Cancelled!</h2>
        <h3 class="details">Penalty:&#8377; <span class="detailans"> <%=fare %> </span></h3>
        <p>Amount will be automatically credited from your account.</p>
        <div id="btns">
           <div id="start">
             <a href="/com.tomcat_hello_world/account"><button id="startbtn">GO HOME &#8594;</button></a>
           </div>
        </div>
       </div>
   </body>
</html>