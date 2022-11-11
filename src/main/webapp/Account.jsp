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
      <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
      <script type="text/javascript">
          function noBack(){window.history.forward();}
          noBack();
          window.onload=noBack;
          window.onpageshow=function(evt){if(evt.persisted)noBack();}
          window.onunload=function(){void(0);}
      </script>
      <script  defer src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
      <script defer type="text/javascript" src="${pageContext.request.contextPath}/js/StoreLogin.js"></script>
      <script defer type="text/javascript" src="${pageContext.request.contextPath}/js/FillForm.js"></script>
      <script defer type="text/javascript" src="${pageContext.request.contextPath}/js/FormQns.js"></script>
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Account.css"> 
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Profile.css"> 
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/nav.css"> 
      <script defer type="text/javascript" src="${pageContext.request.contextPath}/js/nav.js"></script>
      <script defer type="text/javascript" src="${pageContext.request.contextPath}/js/renderAccount.js"></script>
      <script defer type="text/javascript" src="${pageContext.request.contextPath}/js/editProfile.js"></script>
      <script defer async="true" src="https://cdn.jsdelivr.net/npm/handlebars@latest/dist/handlebars.js"></script>
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
  <nav class="side-bar">
    <div class="logo-area">  
      <h1 class="min" id="logo">Ashok Cabs</h1>
     
    </div>
    <ul>
      <li class="active" id="home">
        <a href="#home" id="homeLink" onClick="renderHome(event)">
           <span class="icon">
             <img class="filter-white sizer" src="./resources/images/home.svg" alt="home icon">
            </span>
          <span class="title">Home</span>
        </a>
      </li>
      <li id="profile">
        <a href="#profile" onClick="renderProfile()">
           <span class="icon">
             <img class="filter-white sizer" src="./resources/images/profile.svg" alt="profile icon">
            </span>
          <span class="title">Profile</span>
        </a>
      </li>
      <li id="about">
        <a href="#about" onClick="renderAbout()">
          <span class="icon">
             <img class="filter-white sizer" src="./resources/images/about.svg" alt="About icon">
            </span>
          <span class="title">About Us</span>
        </a>
      </li>
      <li id="pricing">
        <a href="#pricing" onclick="renderPricing()">
          <span class="icon">
             <img class="filter-white sizer" src="./resources/images/pricing.svg" alt="Pricing icon">
            </span>
          <span class="title">Pricing</span>
        </a>
      </li>
      <li>
        <a href="#">
          <span class="icon">
             <img class="filter-white sizer" src="./resources/images/logout.svg" alt="Logout icon">
            </span>
          <span class="title"><form action="logout" method="POST">
        <div id="email" data-email="<%=email %>"></div>
        <div id="logoutWrapper">
           <button  id="logout2" type="submit" onClick="removeItems()">Log out!</button>
        </div>
      </form></span>
        </a>
      </li>
    </ul>
  </nav>
</aside>
  </div>
  <div id="formbox">
        <form id="bookingForm" >
         <div class="group">
          <h2 id="qn">What's your current<span id="name"> location</span>?</h2>
           <br>
           <span id="input"> 
                          <select name="src" class="field" id="src" onchange="report(this.value)">
                            <option id="placeholder" value="" disabled selected>Select your location</option>
						  </select> 
		   </span>
           <br>
           <br>
           <br>
         </div>
         <div class="group">  
           <div id="backbtn">
             <button class="back">&#8592; Back</button>
           </div>
            <h2 id="qn">Where do you want to go,<span id="name"><%= name%></span>?</h2>
           <br>
           <span id="input"> 
                          <select name="dest" class="field" id="dest" >
                            <option id="placeholder" value="" disabled selected>Select your destination</option>
						  </select> 
		   </span>
           <br>
           <br>
           <br>
         </div>
         <div class="group">  
           <div id="backbtn">
             <button class="back">&#8592; Back</button>
           </div>
           <h2 id="qn">What's your preferred vehicle <span id="name">type</span>?</h2>
           <br>
           <div id="input"> 
                          <div id="carTypes">
                            <div id="car1" class="radioOptn"  onClick="selector('hatchback')">
                              <input type="radio" id="hatchback" class="radiobtns" name="carType" value="hatchback">
                              <label for="hatchback"><h3>Hatchback</h3><p>Seats <span id="qn">4</span> people</p></label>
                            </div> 
                             <div id="car2" class="radioOptn" onClick="selector('sedan')">
                              <input type="radio" id="sedan" class="radiobtns" name="carType" value="sedan">
                              <label for="sedan"><h3>Sedan</h3><p>Seats <span id="qn">5</span> people</p></label>
                            </div>
                             <div id="car3" class="radioOptn" onClick="selector('suv')">
                              <input type="radio" id="suv" class="radiobtns" name="carType" value="suv">
                              <label for="suv"><h3>SUV</h3><p>Seats <span id="qn">8</span> people</p></label>
                            </div>
                          </div>
		   </div>
           <br>
           <br>
           <br>
         </div>
         <div>
           <button id="next" type="submit">NEXT &#8594;</button>
         </div>
        </form>
      </div>
      <div id="mobileNav" class="showHide smallNav">
         <div id="contentWrapper" class="navContent">
            <div id="homeMobile" class="mobileNavItem activeMobileNavItem"><p>Home</p></div>
            <div id="profileMobile" class="mobileNavItem"><p>Profile</p></div>
            <div id="pricingMobile" class="mobileNavItem"><p>Pricing</p></div>
            <div id="aboutMobile" class="mobileNavItem"><p>About</p></div>
            <div id="mobileLogout"  class="mobileNavItem"><form id="logoutForm" action="logout" method="POST"> <div id="email" data-email="<%=email %>"></div><p>Log out</p></form></div>
         </div>
      </div>
   </body>
</html>