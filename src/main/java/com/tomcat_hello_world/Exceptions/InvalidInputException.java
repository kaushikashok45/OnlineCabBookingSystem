package com.tomcat_hello_world.Exceptions;

public class InvalidInputException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public InvalidInputException() {
		super("Invalid input details");
	}
}
