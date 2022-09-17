package com.tomcat_hello_world.Security;


import javax.servlet.*;
import java.io.*;
import javax.servlet.http.*;


public class AuthFilter implements Filter{
    public void init(FilterConfig arg0) throws ServletException{}
    public void doFilter(ServletRequest req,ServletResponse res,FilterChain chain) throws ServletException,IOException{
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        String loginURI = request.getContextPath() + Constants.authURL;

        boolean loggedIn = session != null && session.getAttribute(Constants.smallName) != null;
        boolean loginRequest = request.getRequestURI().equals(loginURI);

        if (loggedIn || loginRequest) {
            chain.doFilter(request, response);
        } else {
            response.sendRedirect(Constants.indexURL);
        }
    }
    public void destroy(){}
}
