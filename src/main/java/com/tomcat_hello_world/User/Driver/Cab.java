package com.tomcat_hello_world.User.Driver;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;


public class Cab extends HttpServlet{
	private static final long serialVersionUID = 102831973239L;
	private int id,uid;
	private String name,email,password,type,status,loc;
	private BigDecimal wallet;
	
	public Cab() {}
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
	      request.getRequestDispatcher("Cab.jsp").forward(request, response);
	}
	
	public Cab(int id,int uid,String type,String loc,BigDecimal wallet,String status) {
		this.id=id;
		this.uid=uid;
		this.setType(type);
		this.setLoc(loc);
		this.setWallet(wallet);
		this.setStatus(status);
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getUid() {
		return this.uid;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getType(){
		return this.type;
	}
	
	public String getStatus(){
		return this.status;
	}
	
	public String getLoc(){
		return this.loc;
	}

	
	public String getEmail() {
		return this.email;
	}
	
	public BigDecimal getWallet() {
		return this.wallet;
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
	
	public void setStatus(String status) {
		this.status=status;
	}
	
	public void setType(String type) {
		this.type=type;
	}
	
	public void setLoc(String loc) {
		this.loc=loc;
	}
	
	public void setWallet(BigDecimal wallet) {
		this.wallet=wallet;
	}
}
