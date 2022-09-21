package com.tomcat_hello_world.User;


import java.math.BigDecimal;
import java.sql.Timestamp;

import com.tomcat_hello_world.Security.Constants;

public class Trip{
    private int id,uid,cabid,pointsid;
    String otp,status,src,dest,cabType;
    Timestamp timeCreated,timeEnded;
    BigDecimal fare,distance;
    
    public Trip(){}
    
    public Trip(int id,int uid,int cabid,int pointsid,String otp,String status,Timestamp timeCreated,Timestamp timeEnded){
    	this.setId(id);
    	this.setCabId(cabid);
    	this.setUid(uid);
    	this.setPointsId(pointsid);
    	this.setOtp(otp);
    	this.setStatus(status);
    	this.setTimeCreated(timeCreated);
    	this.setTimeEnded(timeEnded);

    }
    
    public Trip(int id,int uid,int cabid,int pointsid,String otp,String status,Timestamp timeCreated,Timestamp timeEnded,String src,String dest,String cabType,BigDecimal distance){
    	this.setId(id);
    	this.setCabId(cabid);
    	this.setUid(uid);
    	this.setPointsId(pointsid);
    	this.setOtp(otp);
    	this.setStatus(status);
    	this.setTimeCreated(timeCreated);
    	this.setTimeEnded(timeEnded);
    	this.setSrc(src);
    	this.setDest(dest);
    	this.setDistance(distance);
    	this.setCabType(cabType);
    	this.setFare(cabType, distance);
    }
    
    public void setId(int id) {
    	this.id=id;
    }
    
    public void setCabId(int cabId) {
    	this.cabid=cabId;
    }
    
    public void setUid(int uid) {
    	this.uid=uid;
    }
    
    public void setPointsId(int pointsId) {
    	this.pointsid=pointsId;
    }
    
    public void setStatus(String status) {
    	this.status=status;
    }
    
    public void setOtp(String otp) {
    	this.otp=otp;
    }
    
    public void setCabType(String cabType) {
    	this.cabType=cabType;
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
    
    public int getPointsId() {
    	return this.pointsid;
    }
    
    public String getStatus() {
    	return this.status;
    }
    
    public String getOtp() {
    	return this.otp;
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
    
    public String getCabType() {
    	return this.cabType;
    }
    
    public BigDecimal getDistance() {
    	return this.distance;
    }
    
    public BigDecimal getFare() {
    	return this.fare;
    }
}
