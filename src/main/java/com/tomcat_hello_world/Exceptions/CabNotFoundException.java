package com.tomcat_hello_world.Exceptions;

public class CabNotFoundException extends Exception{
   private static final long serialVersionUID = 1L;

public CabNotFoundException() {
	   super("No cabs found");
   }
}
