

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

	getTripById(id){
		return document.getElementById(id);
	} 
}


class Stats{
    
	get self(){
        return this._self;
    }

    set self(context){
        this._self=context;
    }

	get usersLazyLoadCount(){
		return this._usersLazyLoadCount;
	}

	set usersLazyLoadCount(newCount){
		this._usersLazyLoadCount=newCount;
	}

	get tripsLazyLoadCount(){
		return this._tripsLazyLoadCount;
	}

	set tripsLazyLoadCount(newCount){
		this._tripsLazyLoadCount=newCount;
	}

	get usersLazyLoadedRecords(){
		return this._usersLazyLoadedRecords;
	}

	set usersLazyLoadedRecords(newRecords){
		this._usersLazyLoadedRecords=newRecords;
	}

	get lazyLoadedTrips(){
		return this._lazyLoadedTrips;
	}

	set lazyLoadedTrips(newRecords){
		this._lazyLoadedTrips=newRecords;
	}

	get trips(){
		return this._trips;
	}

	set trips(newRecords){
		this._trips=newRecords;
	}

	get filter(){
		return this._filter;
	}

	set filter(newFilter){
        this._filter=newFilter;
	}

	get tripsFilter(){
		return this._tripsFilter;
	}

	set tripsFilter(newFilter){
        this._tripsFilter=newFilter;
	}

    constructor(){
	  this.self=this;	
	  this.self.filter="Customer";
	  this.self.tripsFilter="today";
	  this.usersLazyLoadCount=0;
	  this.tripsLazyLoadCount=0;
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
	 this.self.changeActiveFilter(filter);	
	 this.self.tripsLazyLoadCount=0;
	 this.self.trips=null;	
	 this.self.tripsFilter=filter;
	 console.log('lazy fetching trips');	
	 this.self.lazyFetchTrips(this.self.tripsFilter,0).done((data)=>{
        renderAdmin.currentRender.lazyLoadedTrips=data;
		renderAdmin.currentRender.trips=renderAdmin.currentRender.lazyLoadedTrips;
		var dom=new StatsDOM();
		dom.getTripHistoryBox().scrollTop=0;
		if(filter=="week"){
		   filter="this week";
		}
		else if(filter=="all"){
		   filter="at all";
		}
		if(renderAdmin.currentRender.lazyLoadedTrips.trips.trips.length>0){
		   var tripData=Handlebars.compile("{{#with parsedTrips}}{{#each this}}<div id=\"t{{id}}\" class=\"trip\" onClick=\"renderAdmin.currentRender.expand('trip{{id}}','t{{id}}')\"><h5 class=\"detailans\">Trip from <span class=\"detail\">{{src}}</span> to <span class=\"detail\">{{dest}}</span> on <span class=\"detail\">{{timeCreated}}</span><span id=\"down\">&nbsp;&nbsp;&#9660;</span></h5><div id=\"trip{{id}}\" class=\"expandedTrip hideTrip\"><h5 class=\"detail\">Driver name:<span class=\"detailans\">{{driverName}}</span></h5><h5 class=\"detail\">Distance:<span class=\"detailans\">{{distance}}</span>&nbsp;kms</h5><h5 class=\"detail\">Fare:&nbsp;&#8377;<span class=\"detailans\">{{fare}}</span></h5><h5 class=\"detail\">Trip status:<span class=\"detailans\">{{status}}</span></h5><h5 class=\"detail\">Ending time:<span class=\"detailans\">{{timeEnded}}</span></h5></div></div>{{/each}}{{/with}}");
		   var html = tripData({parsedTrips:renderAdmin.currentRender.lazyLoadedTrips.trips.trips});
		   dom.getTripHistoryBox().innerHTML=html;
		 }
		 else{
		   dom.getTripHistoryBox().innerHTML="<h4 class=\"noTrips\">No trips made+"+filter+"!</h4>";
		 } 
	 });	
	 
	  /*var allTrips=this.self.adminStats.trips.trips;
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
	   }*/
	   
    }

	writeFirstUsers(){
	  var lazyLoadedData=this.self.adminStats.users; 	
	  var userData=Handlebars.compile("{{#with parsedUsers}}{{#each this}}<div id=\"t{{id}}\" class=\"trip\"><h5 class=\"detailans\">#<span class=\"detail\">{{id}}</span></h5><h5 class=\"detail\">User name:<span class=\"detailans\">{{name}}</span></h5><h5 class=\"detail\">Email:<span class=\"detailans\">{{email}}</span></h5><h5 class=\"detail\">Role:<span class=\"detailans\">{{role}}</span></h5></div>{{/each}}{{/with}}");
	  var html=userData({parsedUsers:lazyLoadedData.users});
	  console.log(lazyLoadedData);
      var dom=new StatsDOM();
	  dom.getUserDataBox().scrollTop=0;
	  dom.getUserDataBox().innerHTML=html;
	}
    
     filterUsers(filter,element){
		this.self.filter=filter;	
		this.self.fetchStats(this.self.filter,0).done(function(data){
			var lazyLoadedData=data;
			renderAdmin.currentRender.adminStats=lazyLoadedData;
			if(element=="usersBox"){
			  renderAdmin.currentRender.userStats.adminStats.users.users=[];
			  renderAdmin.currentRender.lazyLoadCount=0;
			  renderAdmin.currentRender.userStats.adminStats.users.users=renderAdmin.currentRender.userStats.adminStats.users.users.concat(lazyLoadedData.users.users);
			  renderAdmin.currentRender.userStats.changeActiveFilter2(filter);
			}	
			else{
			  renderAdmin.currentRender.usersLazyLoadCount=0;
			  renderAdmin.currentRender.changeActiveFilter2(filter);
			}
			console.log(filter);
			
			var userData=Handlebars.compile("{{#with parsedUsers}}{{#each this}}<div id=\"t{{id}}\" class=\"trip\"><h5 class=\"detailans\">#<span class=\"detail\">{{id}}</span></h5><h5 class=\"detail\">User name:<span class=\"detailans\">{{name}}</span></h5><h5 class=\"detail\">Email:<span class=\"detailans\">{{email}}</span></h5><h5 class=\"detail\">Role:<span class=\"detailans\">{{role}}</span></h5></div>{{/each}}{{/with}}");
			var html=userData({parsedUsers:lazyLoadedData.users.users});
			console.log(lazyLoadedData);
			var dom=new StatsDOM();
			dom.getUserDataBox().scrollTop=0;
			dom.getUserDataBox().innerHTML=html;
		}).error(function(jqXHR){
			console.log("Error while getting user data!");
		});	
    }
    
    
	fetchStats(filter,lazyLoadCount){
	 var result=null;	
	 console.log(1);
	 return $.ajax({
        type: "GET",
        url: "/com.tomcat_hello_world/admin/FetchStats",
		async:false,
		data:{
			filter:filter,
			limit:lazyLoadCount
		},
     });
		}	
	
	lazyFetchTrips(tripsFilter,limit){
		var result=null;	
		console.log(2);
		return $.ajax({
		   type: "GET",
		   url: "/com.tomcat_hello_world/admin/LazyFetchTrips",
		   data:{
			   filter:tripsFilter,
			   limit:limit
		   }
		});
	}	

    minimizeStat(event){
		console.log(event);
		var element=event.currentTarget.getElementsByClassName("showStats")[0];
		element.classList.add("hideStats");
		element.classList.remove("showStats");
		event.currentTarget.setAttribute('onclick','renderAdmin.currentRender.expandStat(event)');
	}

	expandStat(event){
		console.log(event)
		var element=event.currentTarget.getElementsByClassName("hideStats")[0];
		console.log(element);
		element.classList.remove("hideStats");
		element.classList.add("showStats");
		event.currentTarget.setAttribute('onclick','renderAdmin.currentRender.minimizeStat(event)');
	}
	
	writeStats(){
		     this.self.fetchStats("Customer",0).done(function(data){
				renderAdmin.currentRender.adminStats=data;
				var adminStats=data;
				var dashHtml="<div id=\"dashrow1\"><div id=\"formbox1\" onClick=\"renderAdmin.currentRender.expandStat(event)\"><div class=\"flexbox\" id=\"dashboardWrapper2\"><h2 class=\"statTitle\" id=\"tripTitle\">Trips</h2><div id=\"moreTrips\" class=\"accordion\">&#x25BC; </div></div><div class=\"hideStats\" id=\"stats\"><div class=\"flexbox\" id=\"total\"><h2 id=\"totalTripsMade\" class=\"headline\"></h2><h3 class=\"caption\">Trips Booked</h3></div><div class=\"flexbox\" id=\"total\"><h2 id=\"totalTripsCompleted\" class=\"headline\"></h2><h3 class=\"caption\">Trips completed</h3></div><div class=\"flexbox\" id=\"total\"><h2 id=\"totalTripsCancelled\" class=\"headline\"></h2><h3 class=\"caption\">Trips cancelled</h3></div><div class=\"flexbox\" id=\"total\"><h2 id=\"totalTripsUnderway\" class=\"headline\"></h2><h3 class=\"caption\">Trips underway</h3></div></div></div><div id=\"formbox2\" onclick=\"renderAdmin.currentRender.expandStat(event)\"><div class=\"flexbox\" id=\"dashboardWrapper2\"><h2 class=\"statTitle\">Cabs</h2><div id=\"moreTrips\" class=\"accordion\">&#x25BC; </div></div><div class=\"hideStats\" id=\"stats\"><div class=\"flexbox\" id=\"total\"><h2 id=\"totalCabs\" class=\"headline\"></h2><h3 class=\"caption\">Total Cabs</h3></div><div class=\"flexbox\" id=\"total\"><h2 id=\"availableCabs\" class=\"headline\"></h2><h3 class=\"caption\">Available Cabs</h3></div><div class=\"flexbox\" id=\"total\"><h2 id=\"bookedCabs\" class=\"headline\"></h2><h3 class=\"caption\">Booked Cabs</h3></div></div></div><div id=\"formbox3\" onclick=\"renderAdmin.currentRender.expandStat(event)\"><div class=\"flexbox\" id=\"dashboardWrapper2\"><h2 class=\"statTitle\">Users</h2><div id=\"moreTrips\" class=\"accordion\">&#x25BC; </div></div><div class=\"hideStats\" id=\"stats\"><div class=\"flexbox\" id=\"total\"><h2 id=\"customers\" class=\"headline\"></h2><h3 class=\"caption\">Customers</h3></div><div class=\"flexbox\" id=\"total\"><h2 id=\"admins\" class=\"headline\"></h2><h3 class=\"caption\">Admins</h3></div><div class=\"flexbox\" id=\"total\"><h2 id=\"drivers\" class=\"headline\"></h2><h3 class=\"caption\">Drivers</h3></div></div></div><div id=\"formbox4\" onclick=\"renderAdmin.currentRender.expandStat(event)\"></div></div><div id=\"dashrow2\"><div id=\"formbox5\"><div class=\"flexbox\" id=\"dashboardWrapper2\"><h2 class=\"statTitle\">Trips Overview</h2></div></div><div id=\"formbox6\"><div class=\"flexbox\" id=\"dashboardWrapper2\"><h2 class=\"statTitle\">Users Overview</h2></div></div></div>";
		        if(adminStats==undefined){
				  adminStats="waiting for data";
			    }
		        var dom=new StatsDOM();
		        dom.getDash().innerHTML=dashHtml;
                var totalTrips=adminStats.tripsByStatus.Completed+adminStats.tripsByStatus.Cancelled+adminStats.tripsByStatus.Underway;
                dom.getTotalTripsMade().innerHTML=totalTrips;
                dom.getTotalTripsCompleted().innerHTML=adminStats.tripsByStatus.Completed;
                dom.getTotalTripsCancelled().innerHTML=adminStats.tripsByStatus.Cancelled;
                dom.getTotalTripsUnderway().innerHTML=adminStats.tripsByStatus.Underway;
                var totalCabs=adminStats.cabsByStatus.Booked+adminStats.cabsByStatus.Available;
                dom.getTotalCabs().innerHTML=totalCabs;
                dom.getBookedCabs().innerHTML=adminStats.cabsByStatus.Booked;
                dom.getAvailableCabs().innerHTML=adminStats.cabsByStatus.Available;
                dom.getCustomersCount().innerHTML=adminStats.usersByRole.Customer;
                dom.getAdminsCount().innerHTML=adminStats.usersByRole.Admin;
                dom.getDriversCount().innerHTML=adminStats.usersByRole.Driver;
                var data=Handlebars.compile("<div class=\"flexbox\" id=\"dashboardWrapper2\"><h2 class=\"statTitle\">Cabs by location</h2><div id=\"moreTrips\" class=\"accordion\">&#x25BC; </div></div><div class=\"hideStats\" id=\"stats\">{{#with cabsByLoc}}{{#each this}}<div class=\"flexbox blackBottom\" id=\"box{{@key}}\"><h2 id=\"loc{{@key}}\" class=\"headline\">{{this}}</h2><h3 class=\"caption\">Cabs at {{@key}}</h3></div>{{/each}}{{/with}}</div>");
                var html=data({cabsByLoc:adminStats.cabsByLoc});
                dom.getFormBox4().innerHTML=html;
                console.log(adminStats);
			    dom.formBox().innerHTML="<h2 class=\"statTitle\" id=\"tripTitle\">Trips Overview</h2><div id=\"tripTable2\">"+"<div id=\"editProfile\"><div id=\"filter\"><div id=\"today\" class=\"filterOptions  activeFilter\" onClick=\"renderAdmin.currentRender.filterTrips('today')\"><h6>Today</h6></div><div id=\"yesterday\" class=\"filterOptions\" onClick=\"renderAdmin.currentRender.filterTrips('yesterday')\"><h6>Yesterday</h6></div><div id=\"week\" class=\"filterOptions\" onclick=\"renderAdmin.currentRender.filterTrips('week')\"><h6>This week</h6></div><div id=\"all\" class=\"filterOptions\" onClick=\"renderAdmin.currentRender.filterTrips('all')\"><h6>All Trips</h6></div></div><div id=\"tripHistoryBox\" onscroll=\"renderAdmin.currentRender.tripsLazyLoader(event)\"></div></div></div>"; 
			    console.log('lazy fetching trips'); 
			    renderAdmin.currentRender.lazyFetchTrips(renderAdmin.currentRender.tripsFilter,renderAdmin.currentRender.tripsLazyLoadCount).done(function(data){
					renderAdmin.currentRender.lazyLoadedTrips=data;
					renderAdmin.currentRender.trips=renderAdmin.currentRender.lazyLoadedTrips;
					if(renderAdmin.currentRender.lazyLoadedTrips.trips.trips!=undefined){
					 var tripData=Handlebars.compile("{{#with parsedTrips}}{{#each this}}<div id=\"t{{id}}\" class=\"trip\" onClick=\"renderAdmin.currentRender.expand('trip{{id}}','t{{id}}')\"><h5 class=\"detailans\">Trip from <span class=\"detail\">{{src}}</span> to <span class=\"detail\">{{dest}}</span> on <span class=\"detail\">{{timeCreated}}</span><span id=\"down\">&nbsp;&nbsp;&#9660;</span></h5><div id=\"trip{{id}}\" class=\"expandedTrip hideTrip\"><h5 class=\"detail\">Driver name:<span class=\"detailans\">{{driverName}}</span></h5><h5 class=\"detail\">Distance:<span class=\"detailans\">{{distance}}</span>&nbsp;kms</h5><h5 class=\"detail\">Fare:&nbsp;&#8377;<span class=\"detailans\">{{fare}}</span></h5><h5 class=\"detail\">Trip status:<span class=\"detailans\">{{status}}</span></h5><h5 class=\"detail\">Ending time:<span class=\"detailans\">{{timeEnded}}</span></h5></div></div>{{/each}}{{/with}}");
					 html = tripData({parsedTrips:renderAdmin.currentRender.lazyLoadedTrips.trips.trips});
					 dom.getTripHistoryBox().innerHTML=html;
					}
					else{
					 dom.getTripHistoryBox().innerHTML="<h4 class=\"noTrips\">No trips made today!</h4>";
					} 
					renderAdmin.currentRender.lazyLoadedTrips=null;
					/*dom.getformBox6().innerHTML="<h2 class=\"statTitle\" id=\"tripTitle\">Users overview</h2><div id=\"userTable\"><div id=\"filter\"><div id=\"Customers\" class=\"filterOptions  activeFilter2\" onClick=\"adminDash.filterUsers('Customers')\"><h6>Customers</h6></div><div id=\"Drivers\" class=\"filterOptions\" onClick=\"adminDash.filterUsers('Drivers')\"><h6>Drivers</h6></div><div id=\"Admins\" class=\"filterOptions\" onClick=\"adminDash.filterUsers('Admins')\"><h6>Admins</h6></div><div id=\"AllUsers\" class=\"filterOptions\" onClick=\"adminDash.filterUsers('AllUsers')\"><h6>All users</h6></div></div><div id=\"userDataBox\"></div></div>");*/
					dom.getformBox6().innerHTML="<h2 class=\"statTitle\" id=\"tripTitle\">Users overview</h2><div id=\"userTable\"><div id=\"filter\"><div id=\"Customer\" class=\"filterOptions  activeFilter2\" onClick=\"renderAdmin.currentRender.filterUsers('Customer','formbox6')\"><h6>Customers</h6></div><div id=\"Driver\" class=\"filterOptions\" onClick=\"renderAdmin.currentRender.filterUsers('Driver','formbox6')\"><h6>Drivers</h6></div><div id=\"Admin\" class=\"filterOptions\" onClick=\"renderAdmin.currentRender.filterUsers('Admin','formbox6')\"><h6>Admins</h6></div></div><div id=\"userDataBox\" onscroll=\"renderAdmin.currentRender.usersLazyLoader(event)\"></div></div>"           
					renderAdmin.currentRender.writeFirstUsers();
				});
			    
			 });
	}
	
	expand(trip,tripParent){
	  var element=document.getElementById(tripParent);
	  var child=document.getElementById(trip);
	  console.log(trip);
	  element.style.backgroundColor="aliceblue";
	  var min="renderAdmin.currentRender.minimize('"+trip+"','"+tripParent+"')";
	  element.setAttribute('onclick',min);
	  child.classList.remove("hideTrip");
	  child.classList.add("showTrip");
    }

    minimize(trip,tripParent){
	  var element=document.getElementById(tripParent);
	  var child=document.getElementById(trip);
	  element.style.backgroundColor="white";
	  console.log(trip);
	  var min="renderAdmin.currentRender.expand('"+trip+"','"+tripParent+"')";
	  element.setAttribute('onclick',min);
	  child.classList.remove("showTrip");
	  child.classList.add("hideTrip");
    }

	writeUserStats(){
       var dom=new StatsDOM();
       var userData=Handlebars.compile("{{#with parsedUsers}}{{#each this}}<div id=\"t{{id}}\" class=\"trip\"><h5 class=\"detailans\">#<span class=\"detail\">{{id}}</span></h5><h5 class=\"detail\">User name:<span class=\"detailans\">{{name}}</span></h5><h5 class=\"detail\">Email:<span class=\"detailans\">{{email}}</span></h5><h5 class=\"detail\">Role:<span class=\"detailans\">{{role}}</span></h5></div>{{/each}}{{/with}}");
	   var html=userData({parsedUsers:this.self.usersLazyLoadedRecords});
	   dom.getTripById("t"+this.self.adminStats.users.users[((this.self.usersLazyLoadCount-1)*5)+4].id).insertAdjacentHTML('afterend',html);
	   this.self.adminStats.users.users=this.self.adminStats.users.users.concat(this.self.usersLazyLoadedRecords);
	   this.self.usersLazyLoadedRecords=null;
	}

	writeTripStats(){
		var dom=new StatsDOM();
		var tripData=Handlebars.compile("{{#with parsedTrips}}{{#each this}}<div id=\"t{{id}}\" class=\"trip\" onClick=\"renderAdmin.currentRender.expand('trip{{id}}','t{{id}}')\"><h5 class=\"detailans\">Trip from <span class=\"detail\">{{src}}</span> to <span class=\"detail\">{{dest}}</span> on <span class=\"detail\">{{timeCreated}}</span><span id=\"down\">&nbsp;&nbsp;&#9660;</span></h5><div id=\"trip{{id}}\" class=\"expandedTrip hideTrip\"><h5 class=\"detail\">Driver name:<span class=\"detailans\">{{driverName}}</span></h5><h5 class=\"detail\">Distance:<span class=\"detailans\">{{distance}}</span>&nbsp;kms</h5><h5 class=\"detail\">Fare:&nbsp;&#8377;<span class=\"detailans\">{{fare}}</span></h5><h5 class=\"detail\">Trip status:<span class=\"detailans\">{{status}}</span></h5><h5 class=\"detail\">Ending time:<span class=\"detailans\">{{timeEnded}}</span></h5></div></div>{{/each}}{{/with}}");
		var html = tripData({parsedTrips:this.self.lazyLoadedTrips.trips.trips});
		dom.getTripById("t"+this.self.trips.trips.trips[((this.self.tripsLazyLoadCount-1)*5)+4].id).insertAdjacentHTML('afterend',html);
		this.self.trips.trips.trips=this.self.trips.trips.trips.concat(this.self.lazyLoadedTrips.trips.trips);
		this.self.lazyLoadedTrips=null;
	 }

	usersLazyLoader(event){
		var element = event.target;
		if ((element.scrollHeight - element.scrollTop === element.clientHeight-0.5)||(element.scrollHeight - element.scrollTop === element.clientHeight+0.5)||(element.scrollHeight - element.scrollTop === element.clientHeight))
		{
		   console.log('scrolled');
           this.self.usersLazyLoadCount=this.self.usersLazyLoadCount+1;
		   this.self.fetchStats(this.self.filter,this.self.usersLazyLoadCount).done(function(data){
			renderAdmin.currentRender.usersLazyLoadedRecords=data.users.users;
			renderAdmin.currentRender.writeUserStats();
		   });
		}
	}

	tripsLazyLoader(event){
		var element = event.target;
		console.log("scrolling");
		if ((element.scrollHeight - element.scrollTop === element.clientHeight+0.5)||(element.scrollHeight - element.scrollTop === element.clientHeight))
		{
		   console.log('lazy fetching trips');
           this.self.tripsLazyLoadCount=this.self.tripsLazyLoadCount+1;
		   this.self.lazyFetchTrips(this.self.tripsFilter,this.self.tripsLazyLoadCount).done((data)=>{
			this.self.lazyLoadedTrips=data;
			renderAdmin.currentRender.writeTripStats();
		   });
		}
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






