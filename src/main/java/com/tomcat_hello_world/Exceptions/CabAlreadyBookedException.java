package com.tomcat_hello_world.Exceptions;

public class CabAlreadyBookedException extends Exception{
   private static final long serialVersionUID = 1L;

public CabAlreadyBookedException() {
	   super("Cab already booked");
   }
}
