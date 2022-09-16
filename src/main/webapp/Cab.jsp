<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);
%> 

<!DOCTYPE html>
  <html>
    <head>
      <title>Partner</title>  
      <script type="text/javascript" src="${pageContext.request.contextPath}/js/StoreLogin.js"></script>
      <script src="https://code.jquery.com/jquery-1.10.2.js" type="text/javascript"></script> 
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Account.css"> 
    </head>
    <div id="accountbox">
    <h1>Hello Partner,<span id="name">${name}</span>!</h1>
    <div id="link">
      <form action="logout" method="POST">
        <div id="email" data-email="${email}"></div>
        <button id="logout" type="submit">Log out!</button>
      </form>

      &nbsp;
      &nbsp;
      &nbsp;
      &nbsp;
      &nbsp;
      &nbsp;
      &nbsp;
      &nbsp;
      &nbsp;
      &nbsp;
     
    </div>
  </div>
   <body>
</html>