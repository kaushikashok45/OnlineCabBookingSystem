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
	

HttpSession a=request.getSession(false);
String email=(String)a.getAttribute("email");
String name=(String)(request.getSession().getAttribute("name"));
int id=(int)(a.getAttribute("id"));
HttpSession b=request.getSession(true);
b.setAttribute("name",name);
b.setAttribute("email",email);
b.setAttribute("id",id);
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
        <a href="/com.tomcat_hello_world/account">
           <span class="icon">
             <img class="filter-white sizer" src="./resources/images/home.svg">
            </span>
          <span class="title">Home</span>
        </a>
      </li>
      <li>
        <a href="./Profile.jsp">
           <span class="icon">
             <img class="filter-white sizer" src="./resources/images/profile.svg">
            </span>
          <span class="title">Profile</span>
        </a>
      </li>
      <li>
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
        <form id="bookingForm" action="BookCab" method="POST">
         <div class="group">
           <h2 id="qn">Where do you want to go,<span id="name"><%= name%></span>?</h2>
           <br>
           <span id="input"> 
                          <select name="dest" class="field" id="dest" onchange="report(this.value)">
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
           <h2 id="qn">What's your current<span id="name"> location</span>?</h2>
           <br>
           <span id="input"> 
                          <select name="src" class="field" id="src">
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
   </body>
</html>