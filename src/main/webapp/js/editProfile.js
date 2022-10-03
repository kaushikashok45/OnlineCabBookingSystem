$(document).on('click','#editProfileBtn',function(){
	var formbox=document.getElementById('formbox');
	formbox.innerHTML="";
	var html="<div id=\"profileIcon\"><img class=\"image\" src=\"./resources/images/profile.svg\"></div>";
	var update="<div id=\"editProfile\"><h2 id=\"header\">Edit Profile</h2><form id=\"\" method=\"POST\" action=\"updateProfile\"><label for=\"Name\" class=\"label\">Name : </label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input class=\"field\" type=\"text\" id=\"Name\" name=\"Name\" placeholder=\"Type new name here...\"><br><label for=\"password\" class=\"label\">Password : </label><input class=\"field\" type=\"password\" id=\"password\" name=\"password\"  placeholder=\"Type new password here...\"><p id=\"note\">*Note:Leaving field empty will not change the value.</p><button id=\"submit\" type=\"submit\">SUBMIT CHANGES</button></form></div>";
    var updatedhtml=html+update;
    formbox.innerHTML=updatedhtml;
   
});

$(document).on('click','#tripHistory',function(){
	var formbox=document.getElementById("formbox");
	formbox.innerHTML="<div id=\"tripTable\"><div id=\"profileIcon\"><img class=\"image\" src=\"./resources/images/profile.svg\"></div>"+"<div id=\"editProfile\"><h2 id=\"header\">Trip History</h2><div id=\"filter\"><div id=\"today\" class=\"filterOptions  activeFilter\" onClick=\"filterTodayTrips()\"><h6>Today</h6></div><div id=\"yesterday\" class=\"filterOptions\" onClick=\"filterYesterdayTrips()\"><h6>Yesterday</h6></div><div id=\"week\" class=\"filterOptions\" onclick=\"filterThisWeekTrips()\"><h6>This week</h6></div><div id=\"all\" class=\"filterOptions\" onClick=\"allTrips()\"><h6>All trips</h6></div></div><div id=\"tripHistoryBox\"><div id=\"dummyTrip\"><div id=\"dummyText\"></div></div></div></div></div>";
	var parsedTrips;
	var tripData;
	var html;
	var filter="today";
	var user
	$.ajax({
        type: "GET",
        url: "/com.tomcat_hello_world/account/GetTrips",
        data: { 
	       filter:filter
        },
        success: function(result) {
            
            parsedTrips=result;
            console.log(result);
            if(result.length>0){
              tripData=Handlebars.compile("<div id=\"tripTable\"><div id=\"profileIcon\"><img class=\"image\" src=\"./resources/images/profile.svg\"></div>"+"<div id=\"editProfile\"><h2 id=\"header\">Trip History</h2><div id=\"filter\"><div id=\"today\" class=\"filterOptions  activeFilter\" onClick=\"filterTodayTrips()\"><h6>Today</h6></div><div id=\"yesterday\" class=\"filterOptions\" onClick=\"filterYesterdayTrips()\"><h6>Yesterday</h6></div><div id=\"week\" class=\"filterOptions\" onclick=\"filterThisWeekTrips()\"><h6>This week</h6></div><div id=\"all\" class=\"filterOptions\" onClick=\"allTrips()\"><h6>All trips</h6></div></div><div id=\"tripHistoryBox\">{{#each parsedTrips}}<div id=\"t{{id}}\" class=\"trip\" onClick=\"expand('trip{{id}}','t{{id}}')\"><h3 class=\"detailans\">Trip from <span class=\"detail\">{{src}}</span> to <span class=\"detail\">{{dest}}</span> on <span class=\"detail\">{{timeCreated}}</span><span id=\"down\">&nbsp;&nbsp;&#9660;</span></h3><div id=\"trip{{id}}\" class=\"expandedTrip hide\"><h5 class=\"detail\">Driver name:<span class=\"detailans\">{{driverName}}</span></h5><h5 class=\"detail\">Distance:<span class=\"detailans\">{{distance}}</span>&nbsp;kms</h5><h5 class=\"detail\">Fare:&nbsp;&#8377;<span class=\"detailans\">{{fare}}</span></h5><h5 class=\"detail\">Trip status:<span class=\"detailans\">{{status}}</span></h5><h5 class=\"detail\">Ending time:<span class=\"detailans\">{{timeEnded}}</span></h5></div></div>{{/each}}</div></div></div>");
              html = tripData({parsedTrips:parsedTrips});
              formbox.innerHTML=html;
            }
            else{
	           formbox.innerHTML="<div id=\"tripTable\"><div id=\"profileIcon\"><img class=\"image\" src=\"./resources/images/profile.svg\"></div>"+"<div id=\"editProfile\"><h2 id=\"header\">Trip History</h2><div id=\"filter\"><div id=\"today\" class=\"filterOptions  activeFilter\" onClick=\"filterTodayTrips()\"><h6>Today</h6></div><div id=\"yesterday\" class=\"filterOptions\" onClick=\"filterYesterdayTrips()\"><h6>Yesterday</h6></div><div id=\"week\" class=\"filterOptions\" onclick=\"filterThisWeekTrips()\"><h6>This week</h6></div><div id=\"all\" class=\"filterOptions\" onClick=\"allTrips()\"><h6>All trips</h6></div></div><div id=\"tripHistoryBox\"><h4 class=\"noTrips\">No trips made today!</h4></div></div></div>";
             }  
        },
        error: function(result) {
            alert('Error while getting trips!');
        }
    });
    
});

function expand(trip,tripParent){
	var element=document.getElementById(tripParent);
	var child=document.getElementById(trip);
	console.log(trip);
	element.style.backgroundColor="aliceblue";
	var min="minimize('"+trip+"','"+tripParent+"')";
	element.setAttribute('onclick',min);
	child.classList.remove("hide");
	child.classList.add("show");
}

function minimize(trip,tripParent){
	var element=document.getElementById(tripParent);
	var child=document.getElementById(trip);
	element.style.backgroundColor="white";
	console.log(trip);
	var min="expand('"+trip+"','"+tripParent+"')";
	element.setAttribute('onclick',min);
	child.classList.remove("show");
	child.classList.add("hide");
}



function filterTodayTrips(){
	changeActiveFilter("today");
	var formbox=document.getElementById("tripHistoryBox");
	formbox.innerHTML="<div id=\"dummyTrip\"><div id=\"dummyText\"></div></div>";
	 $.ajax({
        type: "GET",
        url: "/com.tomcat_hello_world/account/GetTrips",
        data: { 
	       filter:"today"
        },
        success: function(result) {
            var formbox=document.getElementById("tripHistoryBox");
            var parsedTrips=result;
            console.log(result);
            if(result.length>0){
              tripData=Handlebars.compile("{{#each parsedTrips}}<div id=\"t{{id}}\" class=\"trip\" onClick=\"expand('trip{{id}}','t{{id}}')\"><h3 class=\"detailans\">Trip from <span class=\"detail\">{{src}}</span> to <span class=\"detail\">{{dest}}</span> on <span class=\"detail\">{{timeCreated}}</span><span id=\"down\">&nbsp;&nbsp;&#9660;</span></h3><div id=\"trip{{id}}\" class=\"expandedTrip hide\"><h5 class=\"detail\">Driver name:<span class=\"detailans\">{{driverName}}</span></h5><h5 class=\"detail\">Distance:<span class=\"detailans\">{{distance}}</span>&nbsp;kms</h5><h5 class=\"detail\">Fare:&nbsp;&#8377;<span class=\"detailans\">{{fare}}</span></h5><h5 class=\"detail\">Trip status:<span class=\"detailans\">{{status}}</span></h5><h5 class=\"detail\">Ending time:<span class=\"detailans\">{{timeEnded}}</span></h5></div></div>{{/each}}");
              html = tripData({parsedTrips:parsedTrips});
              formbox.innerHTML=html;
            }
            else{
	           formbox.innerHTML="<h4 class=\"noTrips\">No trips made today!</h4>";
             } 
        },
        error: function(result) {
            alert('No trips made today!');
        }
    });
}

function filterYesterdayTrips(){
	changeActiveFilter("yesterday");
	var formbox=document.getElementById("tripHistoryBox");
	formbox.innerHTML="<div id=\"dummyTrip\"><div id=\"dummyText\"></div></div>";
	 $.ajax({
        type: "GET",
        url: "/com.tomcat_hello_world/account/GetTrips",
        data: { 
	       filter:"yesterday"
        },
        success: function(result) {
            var formbox=document.getElementById("tripHistoryBox");
            var parsedTrips=result;
            console.log(result);
            if(result.length>0){
             tripData=Handlebars.compile("{{#each parsedTrips}}<div id=\"t{{id}}\" class=\"trip\" onClick=\"expand('trip{{id}}','t{{id}}')\"><h3 class=\"detailans\">Trip from <span class=\"detail\">{{src}}</span> to <span class=\"detail\">{{dest}}</span> on <span class=\"detail\">{{timeCreated}}</span><span id=\"down\">&nbsp;&nbsp;&#9660;</span></h3><div id=\"trip{{id}}\" class=\"expandedTrip hide\"><h5 class=\"detail\">Driver name:<span class=\"detailans\">{{driverName}}</span></h5><h5 class=\"detail\">Distance:<span class=\"detailans\">{{distance}}</span>&nbsp;kms</h5><h5 class=\"detail\">Fare:&nbsp;&#8377;<span class=\"detailans\">{{fare}}</span></h5><h5 class=\"detail\">Trip status:<span class=\"detailans\">{{status}}</span></h5><h5 class=\"detail\">Ending time:<span class=\"detailans\">{{timeEnded}}</span></h5></div></div>{{/each}}");
             html = tripData({parsedTrips:parsedTrips});
             formbox.innerHTML=html;
            }
            else{
	          formbox.innerHTML="<h4 class=\"noTrips\">No trips made yesterday!</h4>";
            } 
        },
        error: function(result) {
            alert('No trips made yesterday!');
        }
    });
}

function filterThisWeekTrips(){
	changeActiveFilter("week");
	var formbox=document.getElementById("tripHistoryBox");
	formbox.innerHTML="<div id=\"dummyTrip\"><div id=\"dummyText\"></div></div>";
	$.ajax({
        type: "GET",
        url: "/com.tomcat_hello_world/account/GetTrips",
        data: { 
	       filter:"week"
        },
        success: function(result) {
            var formbox=document.getElementById("tripHistoryBox");
            var parsedTrips=result;
            console.log(result);
            if(result.length>0){
              tripData=Handlebars.compile("{{#each parsedTrips}}<div id=\"t{{id}}\" class=\"trip\" onClick=\"expand('trip{{id}}','t{{id}}')\"><h3 class=\"detailans\">Trip from <span class=\"detail\">{{src}}</span> to <span class=\"detail\">{{dest}}</span> on <span class=\"detail\">{{timeCreated}}</span><span id=\"down\">&nbsp;&nbsp;&#9660;</span></h3><div id=\"trip{{id}}\" class=\"expandedTrip hide\"><h5 class=\"detail\">Driver name:<span class=\"detailans\">{{driverName}}</span></h5><h5 class=\"detail\">Distance:<span class=\"detailans\">{{distance}}</span>&nbsp;kms</h5><h5 class=\"detail\">Fare:&nbsp;&#8377;<span class=\"detailans\">{{fare}}</span></h5><h5 class=\"detail\">Trip status:<span class=\"detailans\">{{status}}</span></h5><h5 class=\"detail\">Ending time:<span class=\"detailans\">{{timeEnded}}</span></h5></div></div>{{/each}}");
              html = tripData({parsedTrips:parsedTrips});
              formbox.innerHTML=html;
            }
            else{
	           formbox.innerHTML="<h4 class=\"noTrips\">No trips made this week!</h4>";
             }  
        },
        error: function(result) {
            alert('No trips made this week!');
        }
    });
}

function allTrips(){
	changeActiveFilter("all");
	var formbox=document.getElementById("tripHistoryBox");
	formbox.innerHTML="<div id=\"dummyTrip\"><div id=\"dummyText\"></div></div>";
	$.ajax({
        type: "GET",
        url: "/com.tomcat_hello_world/account/GetTrips",
        data: { 
	       filter:"all"
        },
        success: function(result) {
            var formbox=document.getElementById("tripHistoryBox");
            var parsedTrips=result;
            console.log(result);
            if(result.length>0){
              tripData=Handlebars.compile("{{#each parsedTrips}}<div id=\"t{{id}}\" class=\"trip\" onClick=\"expand('trip{{id}}','t{{id}}')\"><h3 class=\"detailans\">Trip from <span class=\"detail\">{{src}}</span> to <span class=\"detail\">{{dest}}</span> on <span class=\"detail\">{{timeCreated}}</span><span id=\"down\">&nbsp;&nbsp;&#9660;</span></h3><div id=\"trip{{id}}\" class=\"expandedTrip hide\"><h5 class=\"detail\">Driver name:<span class=\"detailans\">{{driverName}}</span></h5><h5 class=\"detail\">Distance:<span class=\"detailans\">{{distance}}</span>&nbsp;kms</h5><h5 class=\"detail\">Fare:&nbsp;&#8377;<span class=\"detailans\">{{fare}}</span></h5><h5 class=\"detail\">Trip status:<span class=\"detailans\">{{status}}</span></h5><h5 class=\"detail\">Ending time:<span class=\"detailans\">{{timeEnded}}</span></h5></div></div>{{/each}}");
              html = tripData({parsedTrips:parsedTrips});
              formbox.innerHTML=html;
            }
            else{
	           formbox.innerHTML="<h4 class=\"noTrips\">No trips made yet!</h4>";
             }  
        },
        error: function(result) {
            alert('No trips made this week!');
        }
    });
}

function changeActiveFilter(element){
	var currentActive=document.getElementsByClassName("activeFilter")[0];
	currentActive.classList.remove("activeFilter");
	document.getElementById(element).classList.add("activeFilter");
}





