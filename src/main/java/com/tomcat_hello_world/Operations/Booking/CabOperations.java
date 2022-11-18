package com.tomcat_hello_world.Operations.Booking;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.tomcat_hello_world.Entity.Cab;
import com.tomcat_hello_world.Exceptions.CabNotFoundException;
import com.tomcat_hello_world.Utility.Constants;

public class CabOperations {
	
	private Cab assignedCab;
	
	public CabOperations() {}
	
	public CabOperations(String src,String dest,String cabType) throws ClassNotFoundException, NullPointerException, SQLException, CabNotFoundException {
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
    
	public  boolean checkCabIsStillAvailable(int tid) throws ClassNotFoundException, SQLException {
		boolean isCabAvailable=false;
		isCabAvailable=SQLQueries.validateAssignedCab(this.getAssignedCab().getId(),tid);
		return isCabAvailable;
	}
	
	public boolean checkCabAvailability() throws ClassNotFoundException, SQLException {
		boolean isCabAvailable=false;
		if(SQLQueries.validateCabAvailability(this.getAssignedCab().getId())) {
			isCabAvailable=true;
		}
		return isCabAvailable;
	}
	
	public  Cab assignCab(String src,String dest,String carType) throws SQLException,ClassNotFoundException,NullPointerException,CabNotFoundException{
		ArrayList<Cab> cars=new ArrayList<Cab>();
		ArrayList<Cab> assignedCabs=new ArrayList<Cab>();
		cars=SQLQueries.getFreeCabs(src,carType);
		if(cars.isEmpty()){
			cars=SQLQueries.getUnderwayCabs(src, carType);
			if(cars.isEmpty()) {
			  cars=SQLQueries.getFreeCabs(src,carType,10);
			  if(cars.isEmpty()) {
				  cars=SQLQueries.getUnderwayCabs(src, carType,10);
			  }
			}  
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
		else {
			throw new CabNotFoundException();
		}
		return c;
	}

	public static boolean checkDriverAlreadyExists(String email) throws ClassNotFoundException, SQLException{
		boolean doesDriverExist=true;
		doesDriverExist=SQLQueries.checkDriverExists(email);
		return doesDriverExist;
	}

	public static boolean addCabs(JSONArray cabsToBeAdded,HashMap<String,Integer> driverids) throws ClassNotFoundException, SQLException{
		boolean cabsAdded=false;
		String query="Insert Into Cabs(uid,Type,locid,wallet,Status) Values";
		ArrayList<String> queryValues=new ArrayList<String>();
		for(int i=0;i<cabsToBeAdded.size();i++) {
			JSONObject keyvalue = (JSONObject)cabsToBeAdded.get(i);
            queryValues.add("("+driverids.get(keyvalue.get("email"))+",'"+keyvalue.get("cabType")+"',"+keyvalue.get("cabLoc")+",0,'Available'"+"),");
		}
		for(String queryValue:queryValues){
			query=query+queryValue;
		} 
		query= query.replaceAll(",$", "");
		System.out.println(query);
		cabsAdded=SQLQueries.executeStringQuery(query);
		return cabsAdded;
	}
	
	public static boolean deleteCabs(String query,String tripsQuery) throws ClassNotFoundException, SQLException {
		boolean cabsDeleted=false;
		cabsDeleted=SQLQueries.deleteCabs(query,tripsQuery);
		return cabsDeleted;
	}
	
	
}
