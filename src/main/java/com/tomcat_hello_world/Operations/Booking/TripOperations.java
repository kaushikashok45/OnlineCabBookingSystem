package com.tomcat_hello_world.Operations.Booking;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Random;
import org.json.simple.JSONObject;
import com.tomcat_hello_world.Entity.Cab;
import com.tomcat_hello_world.Entity.Trip;
import com.tomcat_hello_world.Exceptions.CabAlreadyBookedException;
import com.tomcat_hello_world.Exceptions.CabNotFoundException;
import com.tomcat_hello_world.Exceptions.InvalidInputException;
import com.tomcat_hello_world.Exceptions.NoUnderwayTripsException;
import com.tomcat_hello_world.Exceptions.TripTimeOutException;
import com.tomcat_hello_world.Utility.Constants;

public class TripOperations {
	 private Trip trip;
	 private CabOperations cab;
	   
	 public TripOperations() {}
	 
	 public Trip getTrip() {
		 return this.trip;
	 }
	 
	 public CabOperations getCab() {
		 return this.cab;
	 }
	 
	 public void setTrip(Trip trip) {
		 this.trip=trip;
	 }
	 
	 public void setCab(CabOperations cab) {
		 this.cab=cab;
	 }
	 
	 public void setTripDuration() {
		 int tripDuration=((this.getTrip().getDistance()).divide(new BigDecimal(this.getCab().getAssignedCab().getSpeed()),2, RoundingMode.HALF_UP)).intValue();
		 this.getTrip().setTripDuration(tripDuration);
	 }
	 
	 public void generateOtp() {
		 Random rnd = new Random();
	     int number = rnd.nextInt(999999);
	     String numbers=String.format(Constants.otpFormat, number);
	     this.getTrip().setOtp(numbers);
	 }
	 
	 public boolean confirmBooking() throws ClassNotFoundException, NullPointerException, SQLException, CabAlreadyBookedException {
		 boolean bookingConfirmed=false;
		 this.generateOtp();
		 this.setTripDuration();
		 if(this.getCab().checkCabAvailability()) {
		   bookingConfirmed=SQLQueries.insertTrip(this.getTrip().getUid(),this.getCab().getAssignedCab().getId(),SQLQueries.getLocId(this.getTrip().getSrc()), SQLQueries.getLocId(this.getTrip().getDest()),this.getTrip().getOtp(),this.getTrip().getStatus(),this.getTrip().getTripDuration());
    	   SQLQueries.changeCabStatus(this.getCab().getAssignedCab().getId(),Constants.cabStatus2);
		 } 
		 else {
			 throw new CabAlreadyBookedException();
		 }
		 return bookingConfirmed;
	 }
	 
	 public static TripOperations checkUserTripUnderway(int uid) throws ClassNotFoundException, SQLException, TripTimeOutException, NoUnderwayTripsException {
		 TripOperations tripUnderway=null;
		 Trip trip=getLastTripByUid(uid);
		 tripUnderway=SQLQueries.getTripsByStatus(uid,Constants.tripStatus1,trip.getId(),trip.getCabId());
		 return tripUnderway;
	 }
	 
	 public boolean startTrip(int uid) throws ClassNotFoundException, NullPointerException, SQLException {
		 boolean tripStarted=false;
		 if(this.getCab().checkCabIsStillAvailable(this.getTrip().getId())) {
		  int id=SQLQueries.getLastTripId(uid);
	      Trip t=SQLQueries.getTrip(id);
	      this.getTrip().setDistance(SQLQueries.getDistance(this.getTrip().getSrc(),this.getTrip().getDest()));
	      this.getTrip().setFare(this.getCab().getAssignedCab().getType(),this.getTrip().getDistance());
		  BigDecimal newWallet=(SQLQueries.getCabWallet(t.getCabId())).add(this.getTrip().getFare());
		  tripStarted=SQLQueries.startTrip(newWallet, SQLQueries.getLocId(this.getTrip().getDest()), id);
		 }
		 return tripStarted;
	 }
	 
	 public boolean cancelTrip(int uid) throws ClassNotFoundException, NullPointerException, SQLException {
		 boolean tripCancelled=false;
		 this.getTrip().setStatus(Constants.tripStatus3);
		 this.getTrip().setFare();
		 int id=SQLQueries.getLastTripId(uid);
	     Trip t=SQLQueries.getTrip(id);
		 BigDecimal newWallet=(SQLQueries.getCabWallet(t.getCabId())).add(this.getTrip().getFare());
		 tripCancelled=SQLQueries.cancelTrip(newWallet, SQLQueries.getLocId(this.getTrip().getSrc()), id);
		 return tripCancelled;
	 }
	 
	 
	 public int calculateEta() throws ClassNotFoundException, NullPointerException, SQLException {
		 int eta=0;
		 BigDecimal distance=SQLQueries.getDistance(this.getTrip().getSrc(),this.getCab().getAssignedCab().getLoc());
		 if((Constants.car1).equals(this.getCab().getAssignedCab().getType())) {
	   		     if((this.getTrip().getSrc()).equals(this.getCab().getAssignedCab().getLoc())) {
	   		    	 eta=5;
	   		     }
	   		     else {
	   		    	 eta=5+((distance).divide(new BigDecimal(Double.toString(0.8)))).intValue();
	   		    	 
	   		     }
	   	  }
	   	  else if((Constants.car2).equals(this.getCab().getAssignedCab().getType())){
	   		       if((this.getTrip().getSrc()).equals(this.getCab().getAssignedCab().getLoc())) {
	  		    	 trip.setEta(7);;
	  		       }
	  		       else {
	  		    	eta=7+((distance).divide(new BigDecimal(Double.toString(0.6)),2, RoundingMode.HALF_UP)).intValue();
	  		       }
	      }
	   	  else{
	   	          	
	   		      if((this.getTrip().getSrc()).equals(this.getCab().getAssignedCab().getLoc())) {
	 		    	 eta=10;
	 		       }
	 		       else {
	 		    	eta=10+((distance).divide(new BigDecimal(Double.toString(0.4)))).intValue();
	 		       }
	   	  }
	        
		 return eta;
	 }
	 
	 public static void sortTrips(ArrayList<TripOperations> list) {
		   
	       list.sort((o1, o2)
	                 -> o2.getTrip().getTimeCreated().compareTo(
	                     o1.getTrip().getTimeCreated()));
	      
	   }
	 
	 public static boolean validateBookingInput(String src,String dest,String cabType) throws ClassNotFoundException, NullPointerException, SQLException, InvalidInputException {
		 boolean isValidInput=false;
		 if(SQLQueries.checkLocPairExists(src, dest)) {
			 if((cabType.equals("hatchback"))||(cabType.equals("sedan"))||(cabType.equals("suv"))) {
				 isValidInput=true;
			 }
		 }
		 if(!isValidInput) {
			 throw new InvalidInputException();
		 }
		 return isValidInput;
	 }
	 
	 public static Trip getLastTripByUid(int uid) throws ClassNotFoundException, NullPointerException, SQLException {
		 Trip trip=null;
		 int tripid=SQLQueries.getLastTripId(uid);
		 trip=SQLQueries.getTrip(tripid);
		 return trip;
	 }
	 
	 public  static ArrayList<JSONObject> getAllTrips(int uid,String filter) throws ClassNotFoundException, NullPointerException, SQLException{
		 ArrayList<JSONObject> temp=new ArrayList<JSONObject>();
		 ArrayList<TripOperations> trips=new ArrayList<TripOperations>();
		 ArrayList<TripOperations> filteredTrips=new ArrayList<TripOperations>();
		 trips=SQLQueries.getTrips(uid);
		 if(filter.equals("today")){
			 LocalDate today = LocalDate.now( ZoneOffset.UTC );
			 for(TripOperations tripOp:trips) {
				 java.sql.Date tripDate=new java.sql.Date(tripOp.getTrip().getTimeCreated().getTime());
				 System.out.println(tripDate);
				 
				 if(today.isEqual(tripDate.toLocalDate())) {
					 filteredTrips.add(tripOp);
				 }
			 }
			 trips.clear();
			 trips.addAll(filteredTrips);
		 }
		 if(filter.equals("yesterday")){
			 LocalDate today = LocalDate.now( ZoneOffset.UTC );
			 LocalDate yesterday=today.minusDays(1);
			 for(TripOperations tripOp:trips) {
				 java.sql.Date tripDate=new java.sql.Date(tripOp.getTrip().getTimeCreated().getTime());
				 System.out.println(tripDate);
				 
				 if(yesterday.isEqual(tripDate.toLocalDate())) {
					 filteredTrips.add(tripOp);
				 }
			 }
			 trips.clear();
			 trips.addAll(filteredTrips);
		 }
		 if(filter.equals("week")){
			 LocalDate today = LocalDate.now( ZoneOffset.UTC );
			 LocalDate weekStart=today.minusDays(7);
			 for(TripOperations tripOp:trips) {
				 java.sql.Date tripDate=new java.sql.Date(tripOp.getTrip().getTimeCreated().getTime());
				 System.out.println(tripDate);
				 
				 if(((today.isAfter(tripDate.toLocalDate()))&&(weekStart.isBefore(tripDate.toLocalDate())))||(today.isEqual(tripDate.toLocalDate()))||(weekStart.isEqual(tripDate.toLocalDate()))) {
					 filteredTrips.add(tripOp);
				 }
			 }
			 trips.clear();
			 trips.addAll(filteredTrips);
		 }
		 for(TripOperations tripObj:trips) {
			 temp.add(TripOperations.getTripHistoryJSONObject(tripObj));
		 }
		 return temp;
	 }
	 
	 public static ArrayList<String> getLocations() throws ClassNotFoundException, NullPointerException, SQLException{
		 ArrayList<String> locs=SQLQueries.getLocations();
		 return locs;
	 }
	 
	 public JSONObject toJson() {
		 JSONObject json =new JSONObject();
		 JSONObject trip=this.getTrip().toJson();
		 JSONObject cab=this.getCab().getAssignedCab().toJson(this.getTrip().getUid());
		 json.put("trip",trip);
		 json.put("cab", cab);
		 return json;
	 }
	 
	 public JSONObject toBookingJson() {
		 JSONObject json=new JSONObject();
		 json.put("cabid",this.getCab().getAssignedCab().getId());
		 json.put("src",this.getTrip().getSrc());
		 json.put("dest", this.getTrip().getDest());
		 json.put("driverName",this.getCab().getAssignedCab().getDriverName());
		 json.put("cabType",this.getCab().getAssignedCab().getType());
		 json.put("distance",this.getTrip().getDistance());
		 json.put("eta",this.getTrip().getEta());
		 json.put("fare",this.getTrip().getFare());
		 return json;
	 }
	 
	 public static TripOperations objectFromBookingJson(JSONObject json,int uid) throws ClassNotFoundException, NullPointerException, SQLException {
		 TripOperations tripOp=new TripOperations();
		 tripOp.setCab(new CabOperations(SQLQueries.getCabById(Math.toIntExact((Long)json.get("cabid")))));
		 Trip trip=new Trip();
		 trip.setUid(uid);
		 trip.setSrc((String) json.get("src"));
		 trip.setDest((String)json.get("dest"));
		 trip.setStatus(Constants.tripStatus1);
		 trip.setFare(new BigDecimal((Long)json.get("fare")));
		 trip.setDistance(new BigDecimal((Long)json.get("distance")));
		 trip.setEta(Math.toIntExact((Long)json.get("eta")));
		 tripOp.setTrip(trip);
		 return tripOp;
	 }
	 
	 public static TripOperations toObject(JSONObject json) throws ClassNotFoundException, NullPointerException, SQLException {
		 TripOperations tripOp=new TripOperations();
		 tripOp.setCab(new CabOperations(Cab.toObject((JSONObject)json.get("cab"))));
		 tripOp.setTrip(Trip.toObject((JSONObject)json.get("trip")));
		 return tripOp;
	 }
	 
	 public static JSONObject getMinimalJSONObject(TripOperations tripOp) {
		 JSONObject json=new JSONObject();
		 json.put("uid",tripOp.getTrip().getUid());
		 json.put("cabid",tripOp.getCab().getAssignedCab().getId());
		 if(tripOp.getTrip().getOtp()!=null) {
			 json.put("otp",tripOp.getTrip().getOtp());
		 }
		 json.put("dest",tripOp.getTrip().getDest());
		 json.put("fare",tripOp.getTrip().getFare());
		 return json;
	 }
	 
	 public static JSONObject getTripHistoryJSONObject(TripOperations tripOp) {
		 JSONObject json=new JSONObject();
		 json.put("id",tripOp.getTrip().getId());
		 json.put("src",tripOp.getTrip().getSrc());
		 json.put("dest",tripOp.getTrip().getDest());
		 json.put("timeCreated",tripOp.getTrip().getTimeCreated());
		 json.put("timeEnded",tripOp.getTrip().getTimeEnded());
		 json.put("driverName",tripOp.getCab().getAssignedCab().getDriverName());
		 json.put("distance",tripOp.getTrip().getDistance());
		 json.put("status",tripOp.getTrip().getStatus());
		 json.put("fare",tripOp.getTrip().getFare());
		 return json;
	 }
	 
	 public static JSONObject getAdminTripHistoryJSONObject(TripOperations tripOp) {
		 JSONObject json=new JSONObject();
		 json.put("id",tripOp.getTrip().getId());
		 json.put("src",tripOp.getTrip().getSrc());
		 json.put("dest",tripOp.getTrip().getDest());
		 json.put("timeCreated",new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(tripOp.getTrip().getTimeCreated()));
		 json.put("timeEnded",new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(tripOp.getTrip().getTimeEnded()));
		 json.put("driverName",tripOp.getCab().getAssignedCab().getDriverName());
		 json.put("distance",tripOp.getTrip().getDistance());
		 json.put("status",tripOp.getTrip().getStatus());
		 json.put("fare",tripOp.getTrip().getFare());
		 return json;
	 }
	 
	 public static TripOperations objectFromMinimalJson(JSONObject json) throws ClassNotFoundException, SQLException {
		 TripOperations tripOp=new TripOperations();
		 CabOperations c=new CabOperations();
		 c.setAssignedCab(c.getCabById(Math.toIntExact((Long)json.get("cabid"))));
		 tripOp.setCab(c);
		 tripOp.setTrip(TripOperations.getLastTripByUid(Math.toIntExact((Long)json.get("uid"))));
		 return tripOp;
	 }
	 
	 public TripOperations(String src,String dest,String cabType,int uid) throws ClassNotFoundException, NullPointerException, SQLException, CabNotFoundException {
		   this.setCab(new CabOperations(src,dest,cabType));
		   BigDecimal distance=SQLQueries.getDistance(src, dest);
		   if(this.getCab().getAssignedCab()==null) {
			   throw new NullPointerException();
		   }
		   this.setTrip(new Trip(uid,this.getCab().getAssignedCab().getId(),src,dest,distance));
		   this.getTrip().setStatus(Constants.tripStatus1);
		   this.getTrip().setFare(this.getCab().getAssignedCab().getType(),distance);
		   this.getTrip().setEta(this.calculateEta());
	   }
}
