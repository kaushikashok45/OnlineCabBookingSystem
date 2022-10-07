package com.tomcat_hello_world.Exceptions;

public class TripTimeOutException extends Exception{
   private static final long serialVersionUID = 1L;

public TripTimeOutException() {
	   super("Trip timed out");
   }
}
