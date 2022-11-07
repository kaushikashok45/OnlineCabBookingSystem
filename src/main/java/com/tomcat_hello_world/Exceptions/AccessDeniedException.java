package com.tomcat_hello_world.Exceptions;

public class AccessDeniedException extends Exception{
  private static final long serialVersionUID = 1L;

  public AccessDeniedException() {
	  super("User does not have this role privelege");
  }
}
