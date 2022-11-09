
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
   if(session.getAttribute("name")!=null)
      response.sendRedirect("/com.tomcat_hello_world/account");
%>
<!DOCTYPE html>
  <html lang="en">
    <head>
      <title>Ashok Cabs</title>
      <meta name="keywords" charset="UTF-8" content="Meta Tags, Metadata" />
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <meta name="Ashok Cabs home" content="Home page with options to perform sign-in and register new users.">
      <script async="true" defer src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
      <script async="true" defer type="text/javascript" src="${pageContext.request.contextPath}/js/ShowAccounts.js"></script>
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"> 
    </head>
    <body>
    <h1>Ashok Cabs</h1>
    <div id="link">
      <a href="signup"><button id="signup" >SIGN UP <span id="arrow">&#8594;</span></button></a>
      &nbsp;
      &nbsp;
      &nbsp;
      &nbsp;
      &nbsp;
      &nbsp;
      &nbsp;
      &nbsp;
      <a href="login"><button id="login">LOG IN &#8594;</button></a>
    </div>
    <br>
    <br>
    <br>
    <div id="accounts" style="text-align:center;"></div>
    <c:if test="${param.success eq 1}">
     <h3 id="success"> User registration successful! </h3>
    </c:if>
    <c:if test="${param.regError eq 1}">
      <h3 id="error"> User registration failed.....Please try again! </h3>
     </c:if>
    <c:if test="${param.error eq 1}">
     <h3 id="error"> Invalid Login details! </h3>
    </c:if>

   </body>
</html>