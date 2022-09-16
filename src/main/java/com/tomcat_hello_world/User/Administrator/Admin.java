package com.tomcat_hello_world.User.Administrator;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.tomcat_hello_world.Storage.*;


public class Admin extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private int id;
	private String name,email,password;
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
	      request.getRequestDispatcher("Admin.jsp").forward(request, response);
	}
	
	public Admin addAdmin(String name,String email,String password) throws SQLException,ClassNotFoundException,NullPointerException{
		Admin a=new Admin();
		a.setEmail(email);
		a.setName(name);
		a.setPassword(password);
		SQLQueries.insertUser(name,email,password,"Admin");
		return a;
	}
	
	
	
	public void addLocation(String loc) throws SQLException,ClassNotFoundException,NullPointerException{
		if(!(SQLQueries.checkLocExist(loc))) {
			SQLQueries.addLoc(loc);
		}
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public boolean checkPassword(String pwd){
		boolean isEqual=false;
		if(pwd.equals(this.password)) {
			isEqual=true;
		}
		return isEqual;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	public void setEmail(String email) {
		this.email=email;
	}
	
	public void setPassword(String pwd) {
		this.password=pwd;
	}
}
