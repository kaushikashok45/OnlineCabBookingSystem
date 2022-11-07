package com.tomcat_hello_world.Exceptions;

public class UserNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;
    
	public UserNotFoundException(){
		super("User does not exist.");
	}
}
