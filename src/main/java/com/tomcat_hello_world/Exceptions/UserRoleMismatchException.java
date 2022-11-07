package com.tomcat_hello_world.Exceptions;

public class UserRoleMismatchException extends Exception{
   private static final long serialVersionUID = 1L;

public UserRoleMismatchException() {
	   super("User role mismatched.");
   }
}
