package com.tomcat_hello_world.Exceptions;

public class DriverAlreadyExistsException extends Exception{
  private static final long serialVersionUID = 1L;

  public DriverAlreadyExistsException(String email) {
	  super("The given email-id is already a registered driver.");
  }
}

