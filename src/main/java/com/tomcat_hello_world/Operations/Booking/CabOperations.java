package com.tomcat_hello_world.Operations.Booking;

import java.sql.SQLException;
import java.util.ArrayList;

import com.tomcat_hello_world.Entity.Cab;
import com.tomcat_hello_world.Storage.SQLQueries;
import com.tomcat_hello_world.Utility.Constants;

public class CabOperations {
	
	private Cab assignedCab;
	
	public CabOperations() {}
	
	public CabOperations(String src,String dest,String cabType) throws ClassNotFoundException, NullPointerException, SQLException {
		this.setAssignedCab(assignCab(src,dest,cabType));
	}
	
	public CabOperations(Cab c) throws ClassNotFoundException, NullPointerException, SQLException {
		this.setAssignedCab(c);
	}
	
	public Cab getAssignedCab() {
		return this.assignedCab;
	}
	
	public void setAssignedCab(Cab assignedCab) {
		this.assignedCab=assignedCab;
	}
	
	public static String upgradeCarType(String carType) {
		String upgradedCarType=carType;
		if(carType.equals(Constants.car1)) {
			upgradedCarType=Constants.car2;
			
		}
		else if(carType.equals(Constants.car2)) {
			upgradedCarType=Constants.car3;
			
		}
		return upgradedCarType;
	}
	
	public static Cab findLeastWallet(ArrayList<Cab> cars) {
		Cab assignedCab=null;
		for(Cab c:cars) {
			if(assignedCab==null) {
				assignedCab=c;
			}
			else {
				if(((assignedCab.getWallet()).compareTo(c.getWallet()))==1) {
					assignedCab=c;
				}
			}
		}
		return assignedCab;
	}
	
	public Cab getCabById(int cabid) throws ClassNotFoundException, SQLException {
		Cab c=null;
		c=SQLQueries.getCabById(cabid);
		return c;
	}

	
	public  Cab assignCab(String src,String dest,String carType) throws SQLException,ClassNotFoundException,NullPointerException{
		ArrayList<Cab> cars=new ArrayList<Cab>();
		ArrayList<Cab> assignedCabs=new ArrayList<Cab>();
		cars=SQLQueries.getFreeCabs(src,carType);
		if(cars.isEmpty()){
			cars=SQLQueries.getFreeCabs(src,carType,10);
		}
		
		
		if((cars.isEmpty())&&(!(carType.equals(Constants.car3)))){
			Cab d=assignCab(src,dest,upgradeCarType(carType));
			cars.add(d);
		}
		
		if(!(cars.isEmpty())) {
			if(cars.size()>1) {
				assignedCabs.add(findLeastWallet(cars));
			}
			else {
				assignedCabs.addAll(cars);
			}
		}
		Cab c=null;
		if(!(assignedCabs.isEmpty())) {
			c=assignedCabs.get(0);
		}
		return c;
	}
	
	
}
