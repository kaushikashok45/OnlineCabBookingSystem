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
<%@page import="com.tomcat_hello_world.Entity.Cab" %>
<%@page import="java.math.BigDecimal" %>
<%@page import="javax.servlet.http.HttpSession" %>
<%
HttpSession sessionsc = request.getSession(true);
%>

<!DOCTYPE html>
  <html lang="en">
    <head>
      <title>Internal Error</title>  
      <meta name="keywords" charset="UTF-8" content="Meta Tags, Metadata" />
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
        <h2 class="cancellation">Internal Error!</h2>
        <h3 class="details">Sorry,you session has timed out.</h3>
        <p id="subs">Please try signing in again.</p>
        <div id="btns">
           <div id="start">
             <a href="/com.tomcat_hello_world/login"><button id="startbtn" onClick="<% request.getSession().invalidate(); %>">SIGN IN &#8594;</button></a>
           </div>
        </div>
       </div>
   </body>
</html>