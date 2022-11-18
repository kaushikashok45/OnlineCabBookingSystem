<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
   if(session.getAttribute("name")!=null)
      response.sendRedirect("/com.tomcat_hello_world/account");
%>
<!DOCTYPE html>
  <html lang="en">
   <head>
     <title>Login </title>
     <meta name="keywords" charset="UTF-8" content="Meta Tags, Metadata" />
     <meta name="viewport" content="width=device-width, initial-scale=1.0">
     <script defer type="text/javascript" src="${pageContext.request.contextPath}/js/AutoFill.js"></script> 
     <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css"> 
   </head>
   <body>
    <div id="loginbox">
     <h1>Sign In</h1>
     <form action="auth" method="POST">
     <input type="text" id="email" name="email" class="field" placeholder="Email" required>
     <br>
     <br>
     <br>
     <input type="password" id="pass" name="pass" class="field" placeholder="Password" required>
     <br>
     <br>
     <br>
     <button id="login" type="submit">Sign In</button>
     </form>
     </div>
<body>
</html>