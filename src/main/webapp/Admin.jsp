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
  <html lang="en">
    <head>
      <title>Hello <%= name%>!</title>
      <meta name="keywords" charset="UTF-8" content="Meta Tags, Metadata" />  
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <script defer src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
      <script defer type="text/javascript" src="${pageContext.request.contextPath}/js/StoreLogin.js"></script>
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Account.css"> 
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Admin.css">
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/nav.css"> 
      <script defer src="https://cdn.jsdelivr.net/npm/handlebars@latest/dist/handlebars.js"></script>
      <script defer type="text/javascript" src="${pageContext.request.contextPath}/js/nav.js"></script>
      <script defer type="text/javascript" src="${pageContext.request.contextPath}/js/Dashboard.js"></script>
      <script defer type="text/javascript" src="${pageContext.request.contextPath}/js/ManageCabs.js"></script>
      <script defer type="text/javascript" src="${pageContext.request.contextPath}/js/ManageLocations.js"></script>
      <script defer type="text/javascript" src="${pageContext.request.contextPath}/js/ManageUsers.js"></script>
      <script defer type="text/javascript" src="${pageContext.request.contextPath}/js/showAccounts.js"></script>
      <script defer type="text/javascript" src="${pageContext.request.contextPath}/js/renderAdmin.js"></script>
    </head>
    <body>
    <div id="accountbox">
        <input type="hidden" id="name" value="<%=name %>"></input> 
      <input type="hidden" id="email" value="<%=email %>"></input>
      <input type="hidden" id="uid" value="<%=uid %>"></input>
    <div id="link">
      
    </div>
  </div>
  <div id="navbar">
     <div class="mobileHeader">  
      <h1 class="mobileTitle">Ashok Cabs</h1> 
    </div>
    <aside class="side-bar-wrap hide">
  <nav class="side-bar raised">
    <div class="logo-area">  
      <h1 class="min" id="logo">Ashok Cabs</h1>
     
    </div>
    <ul>
      <li id="dashboard" class="active" >
        <a href="/com.tomcat_hello_world/admin#dashboard" onClick="renderAdmin.changeCurrentRender('dashboard')">
           <span class="icon">
             <img class="filter-white sizer" src="./resources/images/home.svg">
            </span>
          <span class="title">Dashboard</span>
        </a>
      </li>
      <li id="manageCabs">
        <a href="#manageCabs" onClick="renderAdmin.changeCurrentRender('manageCabs')">
           <span class="icon">
             <img class="filter-white sizer" src="./resources/images/car.svg">
            </span>
          <span class="title">Manage Cabs</span>
        </a>
      </li>
      <li id="manageLocs">
        <a href="#manageLocs" onClick="renderAdmin.changeCurrentRender('manageLocs')">
          <span class="icon">
             <img class="filter-white sizer" src="./resources/images/location.svg">
            </span>
          <span class="title">Manage Locations</span>
        </a>
      </li>
      <li id="manageUsers">
        <a href="#manageUsers" onClick="renderAdmin.changeCurrentRender('manageUsers')">
          <span class="icon">
             <img class="filter-white sizer" src="./resources/images/admin.svg">
            </span>
          <span class="title">Manage Users</span>
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
  <div id="dash">
    
  </div> 
   <div id="mobileNav" class="showHide smallNav">
         <div id="contentWrapper" class="navContent">
            <div id="dashboardMobile" class="mobileNavItem activeMobileNavItem" onclick="renderAdmin.changeCurrentRender('dashboard')"><p>Home</p></div>
            <div id="manageCabsMobile" class="mobileNavItem" onclick="renderAdmin.changeCurrentRender('manageCabs')"><p>Cabs</p></div>
            <div id="manageLocsMobile" class="mobileNavItem" onclick="renderAdmin.changeCurrentRender('manageLocs')"><p>Locations</p></div>
            <div id="manageUsersMobile" class="mobileNavItem" onclick="renderAdmin.changeCurrentRender('manageUsers')"><p>Users</p></div>
            <div id="mobileLogout"  class="mobileNavItem" onclick="renderAdmin.changeCurrentRender('logout')"><form id="logoutForm" action="logout" method="POST"> <div id="email" data-email="<%=email %>"></div><p>Log out</p></form></div>
         </div>
      </div>   
   </body>
</html>