
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
   if(session.getAttribute("name")!=null)
      response.sendRedirect("/com.tomcat_hello_world/account");
%>
<!DOCTYPE html>
  <html>
    <head>
      <title>Ashok Cabs</title>
      <script type="text/javascript" src="https://code.jquery.com/jquery-1.10.2.js"></script>
      <script type="text/javascript" src="${pageContext.request.contextPath}/js/ShowAccounts.js"></script>
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