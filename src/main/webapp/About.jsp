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
<%
	

String email=null;
HttpSession a=request.getSession(false);
email=(String)a.getAttribute("email");
String name=(String)(request.getSession().getAttribute("name"));

if ( email== null) {  
   response.sendRedirect("/com.tomcat_hello_world/");
}

%>
<!DOCTYPE html>
  <html>
    <head>
      <title>Hello <%= name%>!</title>  
      <script type="text/javascript" src="https://code.jquery.com/jquery-1.10.2.js"></script> 
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Account.css"> 
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Profile.css"> 
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
      <li>
        <a href="/com.tomcat_hello_world/account">
           <span class="icon">
             <img class="filter-white sizer" src="./resources/images/home.svg">
            </span>
          <span class="title">Home</span>
        </a>
      </li>
      <li>
        <a href="Profile.jsp">
           <span class="icon">
             <img class="filter-white sizer" src="./resources/images/profile.svg">
            </span>
          <span class="title">Profile</span>
        </a>
      </li>
      <li class="active">
        <a href="About.jsp">
          <span class="icon">
             <img class="filter-white sizer" src="./resources/images/about.svg">
            </span>
          <span class="title">About Us</span>
        </a>
      </li>
      <li>
        <a href="Pricing.jsp">
          <span class="icon">
             <img class="filter-white sizer" src="./resources/images/pricing.svg">
            </span>
          <span class="title">Pricing</span>
        </a>
      </li>
      <li>
        <a href="#">
          <span class="icon">
             <img class="filter-white sizer" src="./resources/images/logout.svg">
            </span>
          <span class="title"><form action="logout" method="POST">
        <div id="email" data-email="<%=email %>"></div>
        <button  id="logout2" type="submit">Log out!</button>
      </form></span>
        </a>
      </li>
    </ul>
  </nav>
</aside>
  </div>
  <div id="formbox">
        <div id="profileIcon">
          <img class="image" src="./resources/images/car.svg">
        </div>
        <h2 id="header">About Us</h2>
        <div class="flexbox">
          <div class="flex-item"><h3><span class="dets">We aim to provide high quality cab services online at affordable prices.</span></h3></div>
      </div>
   </body>
</html>