package com.tomcat_hello_world.Entity;


import java.math.BigDecimal;
import java.sql.Timestamp;

import org.json.simple.JSONObject;

import com.tomcat_hello_world.Utility.Constants;

public class Trip{
    private int id,uid,cabid;
    private String otp=null,status,src,dest;
    private Timestamp timeCreated,timeEnded;
    private BigDecimal fare,distance;
    private int eta,tripDuration;
    
    public Trip(){}
    
    public Trip(int id,int uid,int cabid,String otp,String status,Timestamp timeCreated,Timestamp timeEnded,String src,String dest){
    	this.setId(id);
    	this.setCabId(cabid);
    	this.setUid(uid);
    	this.setOtp(otp);
    	this.setStatus(status);
    	this.setTimeCreated(timeCreated);
    	this.setTimeEnded(timeEnded);
        this.setSrc(src);
        this.setDest(dest);
    }
    
    public Trip(int uid,int cabid,String src,String dest,BigDecimal distance){
    	this.setCabId(cabid);
    	this.setUid(uid);
    	this.setSrc(src);
    	this.setDest(dest);
    	this.setDistance(distance);
    }
    
    public Trip(int uid,int cabid,String status,Timestamp timeEnded,String src,String dest,BigDecimal distance){
    	this.setCabId(cabid);
    	this.setUid(uid);
    	this.setStatus(status);
    	this.setTimeCreated(timeCreated);
    	this.setTimeEnded(timeEnded);
    	this.setSrc(src);
    	this.setDest(dest);
    	this.setDistance(distance);
    }
    
    public Trip(int id,int uid,int cabid,String otp,String status,Timestamp timeCreated,Timestamp timeEnded,String src,String dest,String cabType,BigDecimal distance,String driverName){
    	this.setId(id);
    	this.setCabId(cabid);
    	this.setUid(uid);
    	this.setOtp(otp);
    	this.setStatus(status);
    	this.setTimeCreated(timeCreated);
    	this.setTimeEnded(timeEnded);
    	this.setSrc(src);
    	this.setDest(dest);
    	this.setDistance(distance);
    	this.setFare(cabType, distance);
    	
    }
    
    public void setId(int id) {
    	this.id=id;
    }
    
    public void setEta(int eta) {
    	this.eta=eta;
    }
    
    public void setCabId(int cabId) {
    	this.cabid=cabId;
    }
    
    public void setUid(int uid) {
    	this.uid=uid;
    }
    
    public void setStatus(String status) {
    	this.status=status;
    }
    
    public void setOtp(String otp) {
    	this.otp=otp;
    }
    
   public void setTripDuration(int time) {
	   this.tripDuration=time;
   }
    
    public void setDistance(BigDecimal dist) {
    	this.distance=dist;
    }
    
 
    
    public void setFare(String cabType,BigDecimal dist){
    	BigDecimal fare=null;
    	if(cabType.equals(Constants.car1)) {
    		fare=dist.multiply(new BigDecimal(20));
    	}
    	else if(cabType.equals(Constants.car2)) {
    		fare=dist.multiply(new BigDecimal(30));
    	}
    	else if(cabType.equals(Constants.car3)) {
    		fare=dist.multiply(new BigDecimal(50));
    	}
    	if(status.equals("Cancelled")) {
    		fare=new BigDecimal("100");
    	}
    	this.fare=fare;
    }
    
    public void setFare(){
    	BigDecimal fare=null;
    	if(status.equals(Constants.tripStatus3)) {
    		fare=new BigDecimal("100");
    	}
    	this.fare=fare;
    }
    
    public void setFare(BigDecimal fare) {
    	this.fare=fare;
    }
    
    public void setTimeCreated(Timestamp timeCreated) {
    	this.timeCreated=timeCreated;
    }
    
    public void setTimeEnded(Timestamp timeEnded) {
    	this.timeEnded=timeEnded;
    }
    
    public void setSrc(String src) {
    	this.src=src;
    }
    
    public void setDest(String dest) {
    	this.dest=dest;
    }
    
    public int getId() {
    	return this.id;
    }
    
    public int getCabId() {
    	return this.cabid;
    }
    
    public int getUid() {
    	return this.uid;
    }
    
    public int getEta() {
    	return this.eta;
    }
    

    
    public String getStatus() {
    	return this.status;
    }
    
    public String getOtp() {
    	return this.otp;
    }
    
    public int getTripDuration() {
    	return this.tripDuration;
    }

    
    public Timestamp getTimeCreated() {
    	return this.timeCreated;
    }
    
    public Timestamp getTimeEnded() {
    	return this.timeEnded;
    }
    
    public String getSrc() {
    	return this.src;
    }
    
    public String getDest() {
    	return this.dest;
    }

    
    public BigDecimal getDistance() {
    	return this.distance;
    }
    
    public BigDecimal getFare() {
    	return this.fare;
    }
    
    public JSONObject toJson() {
    	 JSONObject trip=new JSONObject();
    	 if(this.getId()!=0) {
    		 trip.put("id",this.getId());
    	 }
    	 if(this.getUid()!=0) {
    		 trip.put("uid",this.getUid());	 
    	 }
    	 if(this.getOtp()!=null) {
    		 trip.put("otp",this.getOtp());
    	 }
    	 if(tripDuration!=0) {
    		 trip.put("time",this.getTripDuration());
    	 }
    	 if(timeCreated!=null) {
    		 trip.put("timeCreated",this.getTimeCreated());
    	 }
    	 if(timeEnded!=null) {
    		 trip.put("timeEnded",this.getTimeEnded());
    	 }
		 trip.put("src",this.getSrc());
		 trip.put("dest",this.getDest());
		 trip.put("fare",this.getFare());
		 trip.put("distance",this.getDistance());
		 trip.put("eta",this.getEta());
		 trip.put("status",this.getStatus());
		 return trip;
    }
    
    public static Trip toObject(JSONObject json) {
    	Trip trip=new Trip();
    	trip.setUid(Math.toIntExact((Long)json.get("uid")));
    	trip.setSrc((String)json.get("src"));
    	trip.setDest((String)json.get("dest"));
    	trip.setStatus((String)json.get("status"));
    	trip.setFare(new BigDecimal((Long)json.get("fare")));
    	trip.setDistance(new BigDecimal((Long)json.get("distance")));
    	trip.setEta(Math.toIntExact((Long)json.get("eta")));
    	if(json.get("otp")!=null) {
    		trip.setOtp((String)json.get("otp"));
    	}
    	if(json.get("time")!=null) {
    		trip.setTripDuration((int)json.get("time"));
    	}
    	if(json.get("timeCreated")!=null) {
    		trip.setTimeCreated((Timestamp)json.get("timeCreated"));
    	}
    	if(json.get("timeEnded")!=null) {
    		trip.setTimeEnded((Timestamp)json.get("timeEnded"));
    	}
    	
    	return trip;
    }
}
