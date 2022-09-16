<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
   if(session.getAttribute("name")!=null)
      response.sendRedirect("/com.tomcat_hello_world/account");
%>
<!DOCTYPE html>
  <html>
   <head>
     <title>Sign up!</title>
     <script type="text/javascript" src="${pageContext.request.contextPath}/js/ValidateSignUp.js"></script>
     <script src="https://code.jquery.com/jquery-1.10.2.js" type="text/javascript"></script>
     <link rel="stylesheet" href="${pageContext.request.contextPath}/css/signup.css"> 
    </head>
   <body>
    <div id="signupbox">
     <h1>Sign up!</h1>
     <form action="process" method="POST">
     <input type="text" id="name" name="name" class="field" placeholder="Name" required>
     <br>
     <br>
     <br>
     <input type="text" name="email" class="field" placeholder="Email" onblur="validateEmail('emailCheck',this.value)" required>
     <br>
     <div id="emailCheck"></div>
     <div id="emailExistCheck"></div>
     <br>
     <br>
     <input type="password" name="pass" class="field" placeholder="Password" onblur="validatePassword('passwordCheck',this.value)" title="Must contain at least one number,one special character and one uppercase and lowercase letter, and must be 8-15 characters in length" required>
     <br>
     <div id="passwordCheck"></div>
     <br>
     <br>
     <br>
     <div id="btn">
      <button id="signup" type="submit" style="font-weight:bold;">Submit</button>
     </div>
     </form>
    </div> 
<body>
</html>