package com.tomcat_hello_world.Operations.Administration;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.tomcat_hello_world.Entity.User;
import com.tomcat_hello_world.Exceptions.AccessDeniedException;
import com.tomcat_hello_world.Exceptions.DriverAlreadyExistsException;
import com.tomcat_hello_world.Exceptions.LocationAlreadyExists;
import com.tomcat_hello_world.Exceptions.UserAlreadyExistsException;
import com.tomcat_hello_world.Exceptions.UserNotFoundException;
import com.tomcat_hello_world.Exceptions.UserRoleMismatchException;
import com.tomcat_hello_world.Exceptions.InvalidInputException;
import com.tomcat_hello_world.Operations.Authentication.UserOperations;
import com.tomcat_hello_world.Operations.Booking.CabOperations;
import com.tomcat_hello_world.Operations.Booking.TripOperations;
import com.tomcat_hello_world.Utility.Constants;

public class AdminOperations {
   private User user;
   
   public AdminOperations() {}
   
   public AdminOperations(User user) throws AccessDeniedException {
	   if(user.getRole().equals("Admin")) {
		   this.setUser(user);
	   }
	   else {
		   throw new AccessDeniedException();
	   }
   }
   
   public User getUser() {
	   return this.user;
   }

   public boolean addCabs(JSONObject json) throws ClassNotFoundException, NullPointerException, SQLException, DriverAlreadyExistsException, InvalidInputException, UserNotFoundException, UserRoleMismatchException{
	   boolean areCabsAdded=false;
	   ArrayList<String> driverEmails=new ArrayList<String>();
	   ArrayList<String> cabTypes=new ArrayList<String>();
	   ArrayList<Integer> locids=new ArrayList<Integer>();
	   JSONArray cabs=(JSONArray)(json.get("cabs"));
	   for(int i=0;i<cabs.size();i++) {
		    JSONObject cab =(JSONObject) cabs.get(i);
		    driverEmails.add((String)cab.get("email"));
			cabTypes.add((String)cab.get("cabType"));
	        locids.add(Integer.valueOf((String)cab.get("cabLoc")));
	   }
	   for(String email:driverEmails){
		boolean emailRoleExist[]=UserOperations.emailExists(email,"Driver");   
		if(emailRoleExist[0]){
			if(!emailRoleExist[1]) {
				throw new UserRoleMismatchException();
			}
			if(CabOperations.checkDriverAlreadyExists(email)){
				throw new DriverAlreadyExistsException(email);
			}
		}
		else {
			throw new UserNotFoundException();
		}
	   }
	   for(String cabType:cabTypes){
		  if(!(cabType.equals("hatchback") || cabType.equals("sedan") || cabType.equals("suv"))){  
			throw new InvalidInputException();
		  }
	   }
	   for(Integer locid:locids){
		 if(!SQLQueries.doesLocExist(locid.intValue())){
			throw new InvalidInputException();
		 }
	   }
	   HashMap<String,Integer> driverids=UserOperations.getIdsFromEmails(driverEmails);
	   areCabsAdded=CabOperations.addCabs(cabs,driverids);
	   return areCabsAdded;
   }
   
   public boolean deleteCabs(JSONObject cabs) throws Exception {
	   boolean cabsDeleted=false;
	   String checkCabsQuery="SELECT COUNT(c.id) AS cabsCount FROM Cabs c WHERE c.id IN (";
	   String deleteCabsQuery="DELETE FROM Cabs WHERE id IN(";
	   String deleteTripsQuery="DELETE FROM Trips WHERE cabid IN(";
	   ArrayList<Integer> cabids=new ArrayList<Integer>();
	   JSONArray cabsArray=(JSONArray)(cabs.get("cabs"));
	   for(int i=0;i<cabsArray.size();i++) { 
	        cabids.add(Integer.valueOf((String)cabsArray.get(i)));
	   }
	   for(int cabid:cabids) {
		   checkCabsQuery=checkCabsQuery+cabid+",";
		   deleteCabsQuery=deleteCabsQuery+cabid+",";
		   deleteTripsQuery=deleteTripsQuery+cabid+",";
	   }
	   checkCabsQuery =checkCabsQuery.replaceAll(",$", "");
	   deleteCabsQuery=deleteCabsQuery.replaceAll(",$", "");
	   deleteTripsQuery=deleteTripsQuery.replaceAll(",$", "");
	   checkCabsQuery =checkCabsQuery+")";
	   deleteCabsQuery=deleteCabsQuery+")";
	   deleteTripsQuery=deleteTripsQuery+")";
	   System.out.println(checkCabsQuery);
	   System.out.println(SQLQueries.checkCabsExist(checkCabsQuery,cabsArray.size())+"108");
	   if(SQLQueries.checkCabsExist(checkCabsQuery,cabsArray.size())) {
		   cabsDeleted=CabOperations.deleteCabs(deleteCabsQuery,deleteTripsQuery);
	   }
	   else {
		   throw new Exception();
	   }
	   return cabsDeleted;
   }
   
   public boolean addUsers(JSONObject json) throws ClassNotFoundException, NullPointerException, SQLException, DriverAlreadyExistsException, InvalidInputException, UserNotFoundException, UserRoleMismatchException, NoSuchAlgorithmException, UserAlreadyExistsException{
	   boolean areUsersAdded=false;
	   areUsersAdded=UserOperations.addUsers(json);
	   return areUsersAdded;
   }
   
   public boolean addLocs(String locNames) throws Exception {
	   boolean addedLocs=false;
	   String[] locations=locNames.split(",");
	   int locCount=0;
	   String updateDistanceQuery="Insert INTO Distance(src,dest,distance) VALUES";
	   if(!SQLQueries.checkLocsExist(locations)) {
		   String query="INSERT INTO Location(Point) VALUES ";
		   for(String location:locations) {
			   query=query+"('"+location+"'),";
			   locCount+=1;
		   }
		   query= query.replaceAll(",$", "");
		   System.out.println(query);
		   addedLocs=SQLQueries.executeStringQuery(query);
	   }
	   else {
		   throw new LocationAlreadyExists();
	   }
	   int lastLoc=SQLQueries.getLastLocId()-locCount+1;
	   int lastid=lastLoc;
	   for(int i=0;i<locCount;i++) {
		   for(int j=lastid-1;j>0;j--) {
				   int distance=(lastLoc-j)*10;
				   if(i==0 && j==lastLoc-1) {
					   updateDistanceQuery+=" ("+lastid+","+j+","+distance+")";
				   }
				   else {
					   updateDistanceQuery+=",("+lastid+","+j+","+distance+")";
				   }
		   }
		   lastid+=1;
	   }
	   if(!SQLQueries.insertDistances(updateDistanceQuery)) {
		   throw new Exception();
	   }
	   return addedLocs;
   }
   
   public void setUser(User user) {
	   this.user=user;
   }
   
   public static JSONObject getAllTrips(String filter,int limit) throws ClassNotFoundException, SQLException {
	   JSONObject json=new JSONObject();
	   JSONArray  array=new JSONArray();
	   try {
	   ArrayList<TripOperations> trips=SQLQueries.tripsLazyLoader(filter,limit);
	   for(TripOperations tripOp:trips) {
		   array.add(TripOperations.getAdminTripHistoryJSONObject(tripOp));
	   }
	   json.put("trips",array);
	   }
	   catch(Exception e) {
		   e.printStackTrace();
	   }
	   
	   return json;
   }
   
   public static String getAllLocs() throws ClassNotFoundException, SQLException {
	   HashMap<Integer,String> locs=SQLQueries.getAdminLocations();
	   String json=null;
	   json=new Gson().toJson(locs);
	   return json;
	   
   }
   
   public static JSONObject getAllCabs(String filter,int limit) throws ClassNotFoundException, SQLException {
	   JSONObject json=new JSONObject();
	   JSONArray  array=new JSONArray();
	   try {
	   ArrayList<CabOperations> cabs=SQLQueries.cabsLazyLoader(filter,limit);
	   for(CabOperations cab:cabs) {
		   JSONObject jsonCab=new JSONObject();
		   jsonCab.put("id",cab.getAssignedCab().getId());
		   jsonCab.put("uid",cab.getAssignedCab().getUid());
		   jsonCab.put("cabType",cab.getAssignedCab().getType());
		   jsonCab.put("wallet",cab.getAssignedCab().getWallet());
		   jsonCab.put("status",cab.getAssignedCab().getStatus());
		   jsonCab.put("driverName",cab.getAssignedCab().getDriverName());
		   jsonCab.put("loc",cab.getAssignedCab().getLoc());
		   array.add(jsonCab);
	   }
	   json.put("cabs",array);
	   }
	   catch(Exception e) {
		   e.printStackTrace();
	   }
	   
	   return json;
   }
   
   public static JSONObject getAllUsers(String filter,int limit) throws ClassNotFoundException, SQLException {
	   JSONObject json=new JSONObject();
	   JSONArray  array=new JSONArray();
	   try {
	   ArrayList<UserOperations> users=SQLQueries.getAllUsers(filter,limit);
	   for(UserOperations user:users) {
		   JSONObject jsonUser=new JSONObject();
		   jsonUser.put("id",user.getUser().getId());
		   jsonUser.put("name",user.getUser().getName());
		   jsonUser.put("email",user.getUser().getEmail());
		   jsonUser.put("role", user.getUser().getRole());
		   array.add(jsonUser);
	   }
	   json.put("users",array);
	   }
	   catch(Exception e) {
		   e.printStackTrace();
	   }
	   
	   return json;
   }
   
   public JSONObject getDashboardDetails(String filter,int limit) throws ClassNotFoundException, SQLException {
	   JSONObject json=SQLQueries.getDashboardDetails();
	   JSONObject tripsByStatus=(JSONObject) json.get("tripsByStatus");
	   JSONObject cabsByStatus=(JSONObject) json.get("cabsByStatus");
	   JSONObject cabsByLoc=(JSONObject) json.get("cabsByLoc");
	   JSONObject usersByRole=(JSONObject) json.get("usersByRole");
	   if(tripsByStatus.get("Completed")==null) {
		   tripsByStatus.put("Completed",0);
	   }
	   if(tripsByStatus.get("Cancelled")==null) {
		   tripsByStatus.put("Cancelled",0);
	   }
	   if(tripsByStatus.get("Underway")==null) {
		   tripsByStatus.put("Underway",0);
	   }
	   if(cabsByStatus.get("Available")==null) {
		   cabsByStatus.put("Available",0);
	   }
	   if(cabsByStatus.get("Booked")==null) {
		   cabsByStatus.put("Booked",0);
	   }
	   if(usersByRole.get("Customer")==null) {
		   usersByRole.put("Customer",0);
	   }
	   if(usersByRole.get("Admin")==null) {
		   usersByRole.put("Admin",0);
	   }
	   if(usersByRole.get("Driver")==null) {
		   json.put("Driver",0);
	   }
	   json.put("tripsByStatus",tripsByStatus);
	   json.put("cabsByStatus",cabsByStatus);
	   json.put("cabsByLoc",cabsByLoc);
	   json.put("usersByRole",usersByRole);
	   /*JSONObject trips=getAllTrips();
	   json.put("trips",trips);*/
	   json.put("users", getAllUsers(filter,limit));
	   return json;
   }
   
   public JSONObject lazyLoadTrips(String filter,int limit) throws ClassNotFoundException, SQLException {
	   JSONObject trips=new JSONObject();
	   JSONObject tripData=getAllTrips(filter,limit);
	   trips.put("trips",tripData);
	   return trips;
   }
   
   public JSONObject lazyLoadCabs(String filter,int limit) throws ClassNotFoundException, SQLException {
	   JSONObject cabs=new JSONObject();
	   JSONObject cabData=getAllCabs(filter,limit);
	   cabs.put("cabs",cabData);
	   return cabs;
   }
}
