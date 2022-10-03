package com.tomcat_hello_world.Entity;


import java.math.BigDecimal;


import org.json.simple.JSONObject;

import com.tomcat_hello_world.Utility.Constants;


public class Cab{
	private int id;
	private int uid;
	private String name,type,status,loc;
	private String email,password;
	private BigDecimal wallet;
	private double speed;
	
	public Cab() {}
	
	public Cab(int id,int uid,String type,String loc,BigDecimal wallet,String status,String Name) {
		this.id=id;
		this.uid=uid;
		this.setType(type);
		this.setLoc(loc);
		this.setWallet(wallet);
		this.setStatus(status);
		this.setDriverName(Name);
		this.setSpeed(type);
	}
	
	
	public int getId() {
		return this.id;
	}
	
	
	public int getUid() {
		return this.uid;
	}
	
	public String getDriverName(){
		return this.name;
	}
	
	public String getType(){
		return this.type;
	}
	
	public String getStatus(){
		return this.status;
	}
	
	public String getLoc(){
		return this.loc;
	}

	
	public String getEmail() {
		return this.email;
	}
	
	public BigDecimal getWallet() {
		return this.wallet;
	}
	
	public double getSpeed() {
		return this.speed;
	}
	
	public boolean checkPassword(String pwd){
		boolean isEqual=false;
		if(pwd.equals(this.password)) {
			isEqual=true;
		}
		return isEqual;
	}
	
	public void setDriverName(String name) {
		this.name=name;
	}
	
	public void setEmail(String email) {
		this.email=email;
	}
	
	public void setPassword(String pwd) {
		this.password=pwd;
	}
	
	public void setStatus(String status) {
		this.status=status;
	}
	
	public void setType(String type) {
		this.type=type;
	}
	
	public void setLoc(String loc) {
		this.loc=loc;
	}
	
	public void setWallet(BigDecimal wallet) {
		this.wallet=wallet;
	}
	
	public void setId(int id) {
		this.id=id;
	}
	
	public void setUid(int uid) {
		this.uid=uid;
	}
	
	public void setSpeed(String cabType) {
		if(cabType.equals(Constants.car1)) {
			this.speed=0.8;
		}
		else if(cabType.equals(Constants.car2)) {
			this.speed=0.6;
		}
		else {
			this.speed=0.4;
		}
	}
	
	public JSONObject toJson(int uid) {
		JSONObject cab=new JSONObject();
		cab.put("name",this.getDriverName());
		cab.put("type",this.getType());
		cab.put("status",this.getStatus());
		cab.put("loc",this.getLoc());
		cab.put("speed",this.getSpeed());
		cab.put("id",this.getId());
		if(uid==this.getUid()) {
		  cab.put("uid",this.getUid());
		  cab.put("wallet",this.getWallet());
		}
		return cab;
	}
	
	public static Cab toObject(JSONObject json) {
    	Cab cab=new Cab();
    	cab.setId(Math.toIntExact((Long)json.get("id")));
    	cab.setDriverName((String)json.get("name"));
    	cab.setType((String)json.get("type"));
    	cab.setStatus((String)json.get("status"));
    	cab.setLoc((String)json.get("loc"));
    	cab.setSpeed(cab.getType());
    	if(json.get("uid")!=null) {
    		cab.setUid((int)json.get("uid"));
    	}
    	if(json.get("wallet")!=null) {
    		cab.setWallet((BigDecimal)json.get("wallet"));
    	} 	
    	return cab;
    }
}
