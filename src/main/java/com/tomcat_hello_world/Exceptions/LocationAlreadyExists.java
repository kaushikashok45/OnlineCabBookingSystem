package com.tomcat_hello_world.Exceptions;

public class LocationAlreadyExists extends Exception{
	private static final long serialVersionUID = 1L;

	  public LocationAlreadyExists() {
		  super("Location is already  registered");
	  }
}
