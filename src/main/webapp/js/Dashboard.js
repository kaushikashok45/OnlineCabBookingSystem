

class StatsDOM{
	getDash(){
		var element=document.getElementById("dash");
		return element;
	}
	
	getTotalTripsMade(){
		var element=document.getElementById("totalTripsMade");
		return element;
	}
	
	getTotalTripsCompleted(){
		var element=document.getElementById("totalTripsCompleted");
		return element;
	}
	
	getTotalTripsCancelled(){
		var element=document.getElementById("totalTripsCancelled");
		return element;
	}
	
	getTotalTripsUnderway(){
		var element=document.getElementById("totalTripsUnderway");
		return element;
	}
	
	getTotalCabs(){
		var element=document.getElementById("totalCabs");
		return element;
	}
	
	getAvailableCabs(){
		var element=document.getElementById("availableCabs");
		return element;
	}
	
	getBookedCabs(){
		var element=document.getElementById("bookedCabs");
		return element;
	}
	
	getCustomersCount(){
		var element=document.getElementById("customers");
		return element;
	}
	
	getAdminsCount(){
		var element=document.getElementById("admins");
		return element;
	}
	
	getDriversCount(){
		var element=document.getElementById("drivers");
		return element;
	}
	
	getFormBox4(){
		var element=document.getElementById("formbox4");
		return element;
	}
	
	formBox(){
		var element=document.getElementById("formbox5");
		return element;
	}
	
	
	getformBox6(){
		var element=document.getElementById("formbox6");
		return element;
	}
	
	getTripHistoryBox(){
		var element=document.getElementById("tripHistoryBox");
		return element;
	}
	
	getUserDataBox(){
		var element=document.getElementById("userDataBox");
		return element;
	}
}


class Stats{
    
	get self(){
        return this._self;
    }

    set self(context){
        this._self=context;
    }

    constructor(){
	  this.self=this;	
	  this.self.adminStats=this.self.fetchStats();
	  this.self.writeStats();
    }	
	get adminStats() {
        return this._adminStats;
    }
    
    set adminStats(json){
	  this._adminStats=json;
    }
    
    get filteredTrips() {
        return this._filteredTrips;
    }
    
    set filteredTrips(json){
	  this._filteredTrips=json;
    }
    
    get filteredUsers() {
        return this._filteredUsers;
    }
    
    set filteredUsers(json){
	  this._filteredUsers=json;
    }
    
    writeTrips(){
	    var dom=new StatsDOM();
	    var html;
	    if(this.self.filteredTrips.length>0){
	      var tripData=Handlebars.compile("{{#with parsedTrips}}{{#each this}}<div id=\"t{{id}}\" class=\"trip\" onClick=\"renderAdmin.currentRender.expand('trip{{id}}','t{{id}}')\"><h3 class=\"detailans\">Trip from <span class=\"detail\">{{src}}</span> to <span class=\"detail\">{{dest}}</span> on <span class=\"detail\">{{timeCreated}}</span><span id=\"down\">&nbsp;&nbsp;&#9660;</span></h3><div id=\"trip{{id}}\" class=\"expandedTrip hide\"><h5 class=\"detail\">Driver name:<span class=\"detailans\">{{driverName}}</span></h5><h5 class=\"detail\">Distance:<span class=\"detailans\">{{distance}}</span>&nbsp;kms</h5><h5 class=\"detail\">Fare:&nbsp;&#8377;<span class=\"detailans\">{{fare}}</span></h5><h5 class=\"detail\">Trip status:<span class=\"detailans\">{{status}}</span></h5><h5 class=\"detail\">Ending time:<span class=\"detailans\">{{timeEnded}}</span></h5></div></div>{{/each}}{{/with}}");
          html = tripData({parsedTrips:this.self.filteredTrips});
        }
        else{
	       html="<h5 class=\"resultNone\">No trips found!</h5>";
        }  
        console.log()
        dom.getTripHistoryBox().innerHTML=html;
    }
    
    writeUsers(elementString){
	    var dom=new StatsDOM();
	    var html;
	    if(this.self.filteredUsers.length>0){
	      var userData=Handlebars.compile("{{#with parsedUsers}}{{#each this}}<div id=\"t{{id}}\" class=\"trip\"><h5 class=\"detailans\">#<span class=\"detail\">{{id}}</span></h5><h5 class=\"detail\">User name:<span class=\"detailans\">{{name}}</span></h5><h5 class=\"detail\">Email:<span class=\"detailans\">{{email}}</span></h5><h5 class=\"detail\">Role:<span class=\"detailans\">{{role}}</span></h5></div>{{/each}}{{/with}}");
          html = userData({parsedUsers:this.self.filteredUsers});
        }
        else{
	       html="<h5 class=\"resultNone\">No users found!</h5>";
        } 
		var element;
		console.log(elementString);
		element=dom.getUserDataBox();
        element.innerHTML=html;
    }
    
    filterTrips(filter){
	  var allTrips=this.self.adminStats.trips.trips;
	  const filteredTrip=[];
	  if(filter=="first"){
		allTrips.forEach((jsonTrip)=>{
			if(jsonTrip.timeCreated.split(" ")[0]==this.self.getDate()){
				filteredTrip.push(jsonTrip);
			}
		})
		this.self.filteredTrips=filteredTrip;
	  }
	  else if(filter=="today"){
		this.self.changeActiveFilter(filter);
		allTrips.forEach((jsonTrip)=>{
			if(jsonTrip.timeCreated.split(" ")[0]==this.self.getDate()){
				filteredTrip.push(jsonTrip);
			}
		})
		 this.self.filteredTrips=filteredTrip;
		 this.self.writeTrips();
	  }
	  else if(filter=="yesterday"){
		this.self.changeActiveFilter(filter);
		console.log(allTrips);
		allTrips.forEach((jsonTrip)=>{
			if(jsonTrip.timeCreated.split(" ")[0]==this.self.getYesterday()){
				filteredTrip.push(jsonTrip);
			}
		})
		this.self.filteredTrips=filteredTrip;
	    this.self.writeTrips();
	  }
	  else if(filter=="week"){
	    this.self.changeActiveFilter(filter);
		console.log(allTrips);
		allTrips.forEach((jsonTrip)=>{
			if(jsonTrip.timeCreated.split(" ")[0]>=this.self.getWeek().sunday && jsonTrip.timeCreated.split(" ")[0]<=this.self.getWeek().saturday){
				filteredTrip.push(jsonTrip);
			}
		})
		this.self.filteredTrips=filteredTrip;
	    this.self.writeTrips();
	  }
	  else{
		this.self.changeActiveFilter("all");
		this.self.filteredTrips=allTrips;
	    this.self.writeTrips();
	   }
	   
    }
    
     filterUsers(filter,element){
	  console.log(this.self);	
	  var allUsers=this.self.adminStats.users.users;
	  const filteredUser=[];
	  if(filter=="Customers"){
		this.self.changeActiveFilter2(filter);
		allUsers.forEach((jsonUser)=>{
			if(jsonUser.role=="Customer"){
				filteredUser.push(jsonUser);
			}
		})
		this.self.filteredUsers=filteredUser;
		this.self.writeUsers(element);
	  }
	  else if(filter=="first"){
		allUsers.forEach((jsonUser)=>{
			if(jsonUser.role=="Customer"){
				filteredUser.push(jsonUser);
			}
		})
		this.self.filteredUsers=filteredUser;
	  }
	  else if(filter=="Drivers"){
		this.self.changeActiveFilter2(filter);
		allUsers.forEach((jsonUser)=>{
			if(jsonUser.role=="Driver"){
				filteredUser.push(jsonUser);
			}
		})
		 this.self.filteredUsers=filteredUser;
		 this.self.writeUsers(element);
	  }
	  else if(filter=="Admins"){
		this.self.changeActiveFilter2(filter);
		allUsers.forEach((jsonUser)=>{
			if(jsonUser.role=="Admin"){
				filteredUser.push(jsonUser);
			}
		})
		this.self.filteredUsers=filteredUser;
		var dom=new StatsDOM();
		console.log(element);
		if(filter!="first"){
			this.self.writeUsers(element);
		}
	  }
	  else{
		this.self.changeActiveFilter2(filter);
		this.self.filteredUsers=allUsers;
	    this.self.writeUsers(element);
	   }
	   
    }
    
    
	fetchStats(){
	 var result=null;	
	 console.log(1);
	 $.ajax({
        type: "GET",
        url: "/com.tomcat_hello_world/admin/FetchStats",
		async:false
     }).done(
		    function(data, textStatus,jqXHR){
				console.log(data,textStatus,jqXHR);
				result=data;
			}
		).fail(
			function (data, textStatus,jqXHR){
				 alert(jqXHR.status);
			}
		);
		return result;
		}	
	
	writeStats(){
		     var dashHtml="<div id=\"dashrow1\"><div id=\"formbox1\"><div class=\"flexbox\" id=\"dashboardWrapper2\"><h2 class=\"statTitle\" id=\"tripTitle\">Trips</h2><div class=\"flexbox\" id=\"stats\"><div class=\"flexbox\" id=\"total\"><h2 id=\"totalTripsMade\" class=\"headline\"></h2><h3 class=\"caption\">Trips Booked</h3></div><div class=\"flexbox\" id=\"total\"><h2 id=\"totalTripsCompleted\" class=\"headline\"></h2><h3 class=\"caption\">Trips completed</h3></div><div class=\"flexbox\" id=\"total\"><h2 id=\"totalTripsCancelled\" class=\"headline\"></h2><h3 class=\"caption\">Trips cancelled</h3></div><div class=\"flexbox\" id=\"total\"><h2 id=\"totalTripsUnderway\" class=\"headline\"></h2><h3 class=\"caption\">Trips underway</h3></div></div></div></div><div id=\"formbox2\"><div class=\"flexbox\" id=\"dashboardWrapper2\"><h2 class=\"statTitle\">Cabs</h2><div class=\"flexbox\" id=\"stats\"><div class=\"flexbox\" id=\"total\"><h2 id=\"totalCabs\" class=\"headline\"></h2><h3 class=\"caption\">Total Cabs</h3></div><div class=\"flexbox\" id=\"total\"><h2 id=\"availableCabs\" class=\"headline\"></h2><h3 class=\"caption\">Available Cabs</h3></div><div class=\"flexbox\" id=\"total\"><h2 id=\"bookedCabs\" class=\"headline\"></h2><h3 class=\"caption\">Booked Cabs</h3></div></div></div></div><div id=\"formbox3\"><div class=\"flexbox\" id=\"dashboardWrapper2\"><h2 class=\"statTitle\">Users</h2><div class=\"flexbox\" id=\"stats\"><div class=\"flexbox\" id=\"total\"><h2 id=\"customers\" class=\"headline\"></h2><h3 class=\"caption\">Customers</h3></div><div class=\"flexbox\" id=\"total\"><h2 id=\"admins\" class=\"headline\"></h2><h3 class=\"caption\">Admins</h3></div><div class=\"flexbox\" id=\"total\"><h2 id=\"drivers\" class=\"headline\"></h2><h3 class=\"caption\">Drivers</h3></div></div></div></div><div id=\"formbox4\"></div></div><div id=\"dashrow2\"><div id=\"formbox5\"><div class=\"flexbox\" id=\"dashboardWrapper2\"><h2 class=\"statTitle\">Trips Overview</h2></div></div><div id=\"formbox6\"><div class=\"flexbox\" id=\"dashboardWrapper2\"><h2 class=\"statTitle\">Users Overview</h2></div></div></div>";
		     if(this.self.adminStats==undefined){
				this.self.adminStats="waiting for data";
			 }
		      var dom=new StatsDOM();
		      dom.getDash().innerHTML=dashHtml;
              var totalTrips=this.self.adminStats.tripsByStatus.Completed+this.self.adminStats.tripsByStatus.Cancelled+this.self.adminStats.tripsByStatus.Underway;
              dom.getTotalTripsMade().innerHTML=totalTrips;
              dom.getTotalTripsCompleted().innerHTML=this.self.adminStats.tripsByStatus.Completed;
              dom.getTotalTripsCancelled().innerHTML=this.self.adminStats.tripsByStatus.Cancelled;
              dom.getTotalTripsUnderway().innerHTML=this.self.adminStats.tripsByStatus.Underway;
              var totalCabs=this.self.adminStats.cabsByStatus.Booked+this.self.adminStats.cabsByStatus.Available;
              dom.getTotalCabs().innerHTML=totalCabs;
              dom.getBookedCabs().innerHTML=this.self.adminStats.cabsByStatus.Booked;
              dom.getAvailableCabs().innerHTML=this.self.adminStats.cabsByStatus.Available;
              dom.getCustomersCount().innerHTML=this.self.adminStats.usersByRole.Customer;
              dom.getAdminsCount().innerHTML=this.self.adminStats.usersByRole.Admin;
              dom.getDriversCount().innerHTML=this.self.adminStats.usersByRole.Driver;
              var data=Handlebars.compile("<div class=\"flexbox\" id=\"dashboardWrapper2\"><h2 class=\"statTitle\">Cabs by location</h2><div class=\"flexbox\" id=\"stats\">{{#with cabsByLoc}}{{#each this}}<div class=\"flexbox blackBottom\" id=\"box{{@key}}\"><h2 id=\"loc{{@key}}\" class=\"headline\">{{this}}</h2><h3 class=\"caption\">Cabs at {{@key}}</h3></div>{{/each}}{{/with}}</div></div>");
              var html=data({cabsByLoc:this.self.adminStats.cabsByLoc});
              dom.getFormBox4().innerHTML=html;
              this.self.filterTrips("first");
              console.log(this.self.adminStats);
               if(this.self.adminStats.trips.trips.length>0){
                 var tripData=Handlebars.compile("<h2 class=\"statTitle\" id=\"tripTitle\">Trips Overview</h2><div id=\"tripTable\">"+"<div id=\"editProfile\"><div id=\"filter\"><div id=\"today\" class=\"filterOptions  activeFilter\" onClick=\"renderAdmin.currentRender.filterTrips('today')\"><h6>Today</h6></div><div id=\"yesterday\" class=\"filterOptions\" onClick=\"renderAdmin.currentRender.filterTrips('yesterday')\"><h6>Yesterday</h6></div><div id=\"week\" class=\"filterOptions\" onclick=\"renderAdmin.currentRender.filterTrips('week')\"><h6>This week</h6></div><div id=\"all\" class=\"filterOptions\" onClick=\"renderAdmin.currentRender.filterTrips('all')\"><h6>All Trips</h6></div></div><div id=\"tripHistoryBox\">{{#with parsedTrips}}{{#each this}}<div id=\"t{{id}}\" class=\"trip\" onClick=\"renderAdmin.currentRender.expand('trip{{id}}','t{{id}}')\"><h5 class=\"detailans\">Trip from <span class=\"detail\">{{src}}</span> to <span class=\"detail\">{{dest}}</span> on <span class=\"detail\">{{timeCreated}}</span><span id=\"down\">&nbsp;&nbsp;&#9660;</span></h5><div id=\"trip{{id}}\" class=\"expandedTrip hide\"><h5 class=\"detail\">Driver name:<span class=\"detailans\">{{driverName}}</span></h5><h5 class=\"detail\">Distance:<span class=\"detailans\">{{distance}}</span>&nbsp;kms</h5><h5 class=\"detail\">Fare:&nbsp;&#8377;<span class=\"detailans\">{{fare}}</span></h5><h5 class=\"detail\">Trip status:<span class=\"detailans\">{{status}}</span></h5><h5 class=\"detail\">Ending time:<span class=\"detailans\">{{timeEnded}}</span></h5></div></div>{{/each}}{{/with}}</div></div></div>");
                 html = tripData({parsedTrips:this.self.filteredTrips});
                 dom.formBox().innerHTML=html;
               }
               else{
	             dom.formBox().innerHTML="<h2 class=\"statTitle\" id=\"tripTitle\">Trips</h2><div id=\"tripTable\">"+"<div id=\"editProfile\"><div id=\"filter\"><div id=\"today\" class=\"filterOptions  activeFilter\" onClick=\"filterTodayTrips()\"><h6>Today</h6></div><div id=\"yesterday\" class=\"filterOptions\" onClick=\"filterYesterdayTrips()\"><h6>Yesterday</h6></div><div id=\"week\" class=\"filterOptions\" onclick=\"filterThisWeekTrips()\"><h6>This week</h6></div><div id=\"all\" class=\"filterOptions\" onClick=\"allTrips()\"><h6>All Trips</h6></div></div><div id=\"tripHistoryBox\"><h4 class=\"noTrips\">No trips made today!</h4></div></div></div>";
               } 
               /*dom.getformBox6().innerHTML="<h2 class=\"statTitle\" id=\"tripTitle\">Users overview</h2><div id=\"userTable\"><div id=\"filter\"><div id=\"Customers\" class=\"filterOptions  activeFilter2\" onClick=\"adminDash.filterUsers('Customers')\"><h6>Customers</h6></div><div id=\"Drivers\" class=\"filterOptions\" onClick=\"adminDash.filterUsers('Drivers')\"><h6>Drivers</h6></div><div id=\"Admins\" class=\"filterOptions\" onClick=\"adminDash.filterUsers('Admins')\"><h6>Admins</h6></div><div id=\"AllUsers\" class=\"filterOptions\" onClick=\"adminDash.filterUsers('AllUsers')\"><h6>All users</h6></div></div><div id=\"userDataBox\"></div></div>");*/
               this.self.filterUsers("first","formbox6");
               if(this.self.adminStats.users.users.length>0){
                  var userData=Handlebars.compile("<h2 class=\"statTitle\" id=\"tripTitle\">Users overview</h2><div id=\"userTable\"><div id=\"filter\"><div id=\"Customers\" class=\"filterOptions  activeFilter2\" onClick=\"renderAdmin.currentRender.filterUsers('Customers','formbox6')\"><h6>Customers</h6></div><div id=\"Drivers\" class=\"filterOptions\" onClick=\"renderAdmin.currentRender.filterUsers('Drivers','formbox6')\"><h6>Drivers</h6></div><div id=\"Admins\" class=\"filterOptions\" onClick=\"renderAdmin.currentRender.filterUsers('Admins','formbox6')\"><h6>Admins</h6></div><div id=\"AllUsers\" class=\"filterOptions\" onClick=\"renderAdmin.currentRender.filterUsers('AllUsers','formbox6')\"><h6>All users</h6></div></div><div id=\"userDataBox\">{{#with parsedUsers}}{{#each this}}<div id=\"t{{id}}\" class=\"trip\"><h5 class=\"detailans\">#<span class=\"detail\">{{id}}</span></h5><h5 class=\"detail\">User name:<span class=\"detailans\">{{name}}</span></h5><h5 class=\"detail\">Email:<span class=\"detailans\">{{email}}</span></h5><h5 class=\"detail\">Role:<span class=\"detailans\">{{role}}</span></h5></div>{{/each}}{{/with}}</div></div>");
                  var html = userData({parsedUsers:this.self.filteredUsers});
                  console.log()
                  dom.getformBox6().innerHTML=html;
               }  
               else{
	             dom.getformBox6().innerHTML="<h4 class=\"noTrips\">No Users found!</h4>";
               } 
                
	}
	
	expand(trip,tripParent){
	  var element=document.getElementById(tripParent);
	  var child=document.getElementById(trip);
	  console.log(trip);
	  element.style.backgroundColor="aliceblue";
	  var min="renderAdmin.currentRender.minimize('"+trip+"','"+tripParent+"')";
	  element.setAttribute('onclick',min);
	  child.classList.remove("hide");
	  child.classList.add("show");
    }

    minimize(trip,tripParent){
	  var element=document.getElementById(tripParent);
	  var child=document.getElementById(trip);
	  element.style.backgroundColor="white";
	  console.log(trip);
	  var min="renderAdmin.currentRender.expand('"+trip+"','"+tripParent+"')";
	  element.setAttribute('onclick',min);
	  child.classList.remove("show");
	  child.classList.add("hide");
    }
    
    changeActiveFilter(element){
	console.log("changing");
	  var currentActive=document.getElementsByClassName("activeFilter")[0];
	  currentActive.classList.remove("activeFilter");
	  document.getElementById(element).classList.add("activeFilter");
    }
    
     changeActiveFilter2(element){
	  console.log("changing 2");
	  var currentActive=document.getElementsByClassName("activeFilter2")[0];
	  currentActive.classList.remove("activeFilter2");
	  document.getElementById(element).classList.add("activeFilter2");
    }
    
    getDate(){
       const newDate = new Date();
       const year = newDate.getFullYear();
       const month = newDate.getMonth() + 1;
       const d = newDate.getDate();
       return `${d.toString().padStart(2, '0')}/${month.toString().padStart(2, '0')}/${year}`;
    }
    
    getYesterday(){
	   const newDate = new Date();
       const year = newDate.getFullYear();
       const month = newDate.getMonth() + 1;
       const d = newDate.getDate()-1;
       return `${d.toString().padStart(2, '0')}/${month.toString().padStart(2, '0')}/${year}`;
    }
    
    getWeek(){
	   var curr = new Date; // get current date
       var first = curr.getDate() - curr.getDay(); // First day is the day of the month - the day of the week
       var last = first + 6; // last day is the first day + 6
       var firstday = new Date(curr.setDate(first));
       var lastday = new Date(curr.setDate(last));
       const year1 = firstday.getFullYear();
       const month1 = firstday.getMonth() + 1;
       const d1 = firstday.getDate();
       var sunday=`${d1.toString().padStart(2, '0')}/${month1.toString().padStart(2, '0')}/${year1}`;
       const year2 = lastday.getFullYear();
       const month2 = lastday.getMonth() + 1;
       const d2 = lastday.getDate();
       var saturday=`${d2.toString().padStart(2, '0')}/${month2.toString().padStart(2, '0')}/${year2}`;
       var week={"sunday":sunday,"saturday":saturday};
       return week;
    }
    
}




/*var adminDash=new Stats();
 


 
$(document).ready(function(){
  adminDash.fetchStats(adminDash.writeStats);
});*/






