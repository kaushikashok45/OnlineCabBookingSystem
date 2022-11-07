package com.tomcat_hello_world.Exceptions;

public class UserAlreadyExistsException extends Exception{
  private static final long serialVersionUID = 1L;

  public UserAlreadyExistsException() {
	  super("The given email-id is already a registered user.");
  }
}


