package com.tomcat_hello_world.Entity;

public class User{
	private int id;
	private String name,email,password,role;
	
	public User(){}
	
	public User(String name,String email,String password,String role){
		this.setEmail(email);
		this.setName(name);
		this.setPassword(password);
		this.setRole(role);
	}
	
	public User(int id,String name,String email,String password,String role){
		this.setEmail(email);
		this.setName(name);
		this.setPassword(password);
		this.setRole(role);
		this.setId(id);
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

	public String getPassword(){
		return this.password;
	}
	
	public String getRole(){
		return this.role;
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
	
	public void setRole(String role) {
		this.role=role;
	}
	
	public void setId(int id) {
		this.id=id;
	}
}
