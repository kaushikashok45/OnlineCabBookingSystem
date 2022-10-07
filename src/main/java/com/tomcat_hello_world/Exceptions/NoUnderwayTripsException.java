package com.tomcat_hello_world.Exceptions;

public class NoUnderwayTripsException extends Exception{
    private static final long serialVersionUID = 1L;

	public NoUnderwayTripsException() {
    	super("User has no underway trip");
    }
}
