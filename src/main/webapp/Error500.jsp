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
HttpSession sessionsc = request.getSession(true);
%>

<!DOCTYPE html>
  <html>
    <head>
      <title>Error 500!</title>  
      <script type="text/javascript" src="https://code.jquery.com/jquery-1.10.2.js"></script> 
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Account.css"> 
    </head>
    <body>
    <div id="accountbox">
    <h1>Ashok Cabs</h1>
  </div>
       <div id="confirmbox">
        <h2 class="confirmation" id="cross">&#10060;</h2>
        <h2 class="cancellation">Error 500!</h2>
        <h3 class="details">Sorry,something is not right at our end.</h3>
        <p id="subs">We'll be back soon.Please try again later.</p>
        <div id="btns">
           <div id="start">
             <a href="/com.tomcat_hello_world/account"><button id="startbtn">GO HOME &#8594;</button></a>
           </div>
        </div>
       </div>
   </body>
</html>