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
<%@ page import="com.tomcat_hello_world.Operations.Authentication.UserOperations" %>
<%
	

HttpSession a=request.getSession(false);
UserOperations user=(UserOperations)a.getAttribute("User");
String name=user.getUser().getName();
String email=user.getUser().getEmail();
int uid=user.getUser().getId();
%>
<!DOCTYPE html>
  <html>
    <head>
      <title>Hello <%= name%>!</title>  
      <script type="text/javascript" src="https://code.jquery.com/jquery-1.10.2.js"></script> 
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
      <script type="text/javascript" src="${pageContext.request.contextPath}/js/StoreLogin.js"></script>
      <script type="text/javascript" src="${pageContext.request.contextPath}/js/FillForm.js"></script>
      <script type="text/javascript" src="${pageContext.request.contextPath}/js/FormQns.js"></script>
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Admin.css"> 
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Account.css"> 
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/nav.css"> 
      <script type="text/javascript" src="${pageContext.request.contextPath}/js/nav.js"></script>
    </head>
    <body>
    <div id="accountbox">
    
    <div id="link">
      
    </div>
  </div>
  <div id="navbar">
    <aside class="side-bar-wrap">
  <nav class="side-bar">
    <div class="logo-area">  
      <h1 class="min" id="logo">Ashok Cabs</h1>
     
    </div>
    <ul>
      <li class="active">
        <a href="/com.tomcat_hello_world/admin">
           <span class="icon">
             <img class="filter-white sizer" src="./resources/images/home.svg">
            </span>
          <span class="title">Home</span>
        </a>
      </li>
      <li>
        <a href="#">
           <span class="icon">
             <img class="filter-white sizer" src="./resources/images/car.svg">
            </span>
          <span class="title">Add Cabs</span>
        </a>
      </li>
      <li>
        <a href="#">
          <span class="icon">
             <img class="filter-white sizer" src="./resources/images/location.svg">
            </span>
          <span class="title">Add Locations</span>
        </a>
      </li>
      <li>
        <a href="#">
          <span class="icon">
             <img class="filter-white sizer" src="./resources/images/admin.svg">
            </span>
          <span class="title">Add Admins</span>
        </a>
      </li>
      <li>
        <a href="#">
          <span class="icon">
             <img class="filter-white sizer" src="./resources/images/logout.svg">
            </span>
          <span class="title"><form action="logout" method="POST">
        <div id="email" data-email="<%=email %>"></div>
        <div id="logoutWrapper">
           <button  id="logout2" type="submit">Log out!</button>
        </div>
      </form></span>
        </a>
      </li>
    </ul>
  </nav>
</aside>
  </div>
  <div id="formbox">
        <div class="flexbox" id="dashboardWrapper">
     <div class="flexbox" id="stats">
       <div class="flexbox" id="total">
        <h2 id="totaltripsMade" class="headline"></h2>
        <h3 class="caption">Trips made</h3>
        </div>
     </div>
  </div>

        
      </div>
   </body>
</html>