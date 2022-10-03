package com.tomcat_hello_world.Utility;

public class Constants {
    public static final String tripStatus1="Underway";
    public static final String tripStatus2="Completed";
    public static final String tripStatus3="Cancelled";
    public static final String cabStatus1="Available";
    public static final String cabStatus2="Booked";
    public static final String src="src";
    public static final String dest="dest";
    public static final String trip="trip";
    public static final String fare="fare";
    public static final String dbDriver="com.mysql.jdbc.Driver";
    public static final String dbURL="jdbc:mysql://localhost:3306/";
    public static final String dbName="tomcat";
    public static final String dbUsername = "root";
    public static final String dbPassword="Ak2001@rox";
    public static final String id="id";
    public static final String uid="uid";
    public static final String type="Type";
    public static final String location="Location";
    public static final String locid="locid";
    public static final String role="Role";
    public static final String name="Name";
    public static final String smallName="name";
    public static final String wallet="wallet";
    public static final String status="Status";
    public static final String password="Password";
    public static final String pass="pass";
    public static final String Point="Point";
    public static final String distance="Distance";
    public static final String cabid="cabid";
    public static final String pointsid="pointsid";
    public static final String otp="otp";
    public static final String timeStarted="time_started";
    public static final String timeEnded="time_ended";
    public static final String car1="hatchback";
    public static final String car2="sedan";
    public static final String car3="suv";
    public static final String carType="carType";
    public static final String contentHtml="text/html";
    public static final String cab="cab";
    public static final String email="email";
    public static final String bigEmail="Email";
    public static final String eta="eta";
    public static final String dist="dist";
    public static final String speed="speed";
    public static final String time="time";
    public static final String account="/account";
    public static final String accountJSP="/Account.jsp";
    public static final String otpFormat="%06d";
    public static final String penalty="100";
    public static final String tripCancelled="TripCancelled.jsp";
    public static final String emptyString="";
    public static final String emptySpaceString=" ";
    public static final String comma=",";
    public static final String bigCustomer="Customer";
    public static final String md5="MD5";
    public static final String accountURL="/com.tomcat_hello_world/account";
    public static final String adminURL="/com.tomcat_hello_world/admin";
    public static final String indexURL="/com.tomcat_hello_world/";
    public static final String logoutURL="/logout";
    public static final String passwordRegex="^(?=.*[0-9])"+ "(?=.*[a-z])(?=.*[A-Z])"+ "(?=\\S+$).{8,15}$";
    public static final String bigAdmin="Admin";
    public static final String bigDriver="Driver";
    public static final String indexErrorURL="/com.tomcat_hello_world/?error=1";
    public static final String indexSuccessURL="/com.tomcat_hello_world/?success=1";
    public static final String indexRegErrorURL="/com.tomcat_hello_world/?regError=1";
    public static final String authURL="/auth";
    public static final String updateProfileURL="/updateProfile";
    public static final String emailAlreadyRegistered="Email already registered!";
    public static final String driverURL="/com.tomcat_hello_world/partner";
    public static final String contentPlain="text/plain";
    public static final String getUserCountByEmail="Select Count(*) from Users  where Email=?";
    public static final String getLocFromPoint="Select Count(*) from Location where Point=?";
    public static final String getPwdFromUserEmail="Select * from Users  where Email=?";
    public static final String getUserByEmail="Select * from Users  where Email=?";
    public static final String getLocId="Select * from Location where id=?;";
    public static final String getLocIdByPoint="Select * from Location where Point=?;";
    public static String getCabWallet="Select wallet from Cabs where id=?;";
    public static final String getAllCabs="Select * from Cabs;";
    public static final String getCabByLocTypeStatus="Select * from Cabs WHERE locid=? AND Type=? AND Status=?;";
    public static final String getNearbyLocs="Select * from Distance WHERE (src=?  OR dest=?) AND Distance=?;";
    public static final String getAllLocs="Select * from Location;";
    public static final String getDistance="Select Distance from Distance where (src=? AND dest=?) OR (src=? AND dest=?);";
    public static final String getDistanceId="Select id from Distance where (src=? AND dest=?) OR (src=? AND dest=?);";
    public static final String getMaxTripId="Select MAX(id)  FROM Trips;";
    public static final String getTripById="Select * from Trips where id=?";
    public static final String getTripByUid="SELECT Trips.id,Trips.uid,Trips.cabid,Cabs.Type,Trips.src,Trips.dest,Trips.otp,Trips.Status,Trips.time_started,Trips.time_ended,Distance.distance FROM Trips,Distance,Cabs WHERE (((Trips.src=Distance.src AND  Trips.dest=Distance.dest) OR (Trips.dest=Distance.src AND Trips.src=Distance.dest)) AND Trips.uid=?) AND (Trips.cabid=Cabs.id);";
    public static final String getUserDetails="Select Name,Email FROM Users where id=?";
    public static final String getTripCounts="SELECT COUNT(*) AS total,sum(case when Trips.Status='Completed' then 1 else 0 end) AS completedTrip,sum(case when Trips.Status='Cancelled' then 1 else 0 end) AS cancelledTrip FROM Trips;";
    public static final String getCabCounts="SELECT Count(*) AS count,Type as type,locid FROM Cabs GROUP BY Type,locid;";
    public static final String insertUser="insert into Users(Name,Email,Password,Role) VALUES(?,?,?,?)";
    public static final String insertLoc="insert into Location(Point) VALUES(?)";
    public static final String insertTrip="insert into Trips(uid,cabid,src,dest,otp,Status,time_ended) VALUES(?,?,?,?,?,?,?)";
    public static final String insertDistance="insert into Distance(src,dest,distance) VALUES(?,?,?)";
    public static final String updateUsersName="Update Users set Name=? where Email=?";
    public static final String updateUsersEmail="Update Users set Email=? where Email=?";
    public static final String updateUsersPassword="Update Users set Password=? where Email=?";
    public static final String updateCabsStatus="Update Cabs set Status=? where id=?";
    public static final String updateCabsType="Update Cabs set Type=? where id=?";
    public static final String updateCabsLoc="Update Cabs set locid=? where id=?";
    public static final String updateCabsWallet="Update Cabs set wallet=? where id=?";
    public static final String updateTripsTimeEnded="Update Trips set time_ended=? where id=?";
    public static final String updateTripsStatus="Update Trips set Status=? where id=?";
    public static final String tripCompleted="TripCompleted.jsp";
    public static final String reqConfirm="ReqConfirm.jsp";
    public static final String bookingConfirm="BookingConfirm.jsp";
    public static final String loginJSP="login.jsp";
    public static final String signupJSP="signup.jsp";
    public static final String cabJSP="Cab.jsp";
    public static final String adminJSP="Admin.jsp";
}
