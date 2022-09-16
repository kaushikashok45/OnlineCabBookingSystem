package com.tomcat_hello_world.User;


import java.sql.Timestamp;

public class Trip{
    private int id,uid,cabid,pointsid,src,dest;
    String otp,status;
    Timestamp timeCreated,timeEnded;
    
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
    
    public Trip(int id,int uid,int cabid,int pointsid,String otp,String status,Timestamp timeCreated,Timestamp timeEnded,int src,int dest){
    	setId(id);
    	setCabId(cabid);
    	setUid(uid);
    	setPointsId(pointsid);
    	setOtp(otp);
    	setStatus(status);
    	setTimeCreated(timeCreated);
    	setTimeEnded(timeEnded);
    	setSrc(src);
    	setDest(dest);
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
    
    public void setTimeCreated(Timestamp timeCreated) {
    	this.timeCreated=timeCreated;
    }
    
    public void setTimeEnded(Timestamp timeEnded) {
    	this.timeEnded=timeEnded;
    }
    
    public void setSrc(int src) {
    	this.src=src;
    }
    
    public void setDest(int dest) {
    	this.cabid=dest;
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
    
    public int getSrc() {
    	return this.src;
    }
    
    public int getDest() {
    	return this.dest;
    }
}
