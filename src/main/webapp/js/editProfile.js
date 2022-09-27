$(document).on('click','#editProfileBtn',function(){
	var formbox=document.getElementById('formbox');
	formbox.innerHTML="";
	var html="<div id=\"profileIcon\"><img class=\"image\" src=\"./resources/images/profile.svg\"></div>";
	var update="<div id=\"editProfile\"><h2 id=\"header\">Edit Profile</h2><form id=\"\" method=\"POST\" action=\"updateProfile\"><label for=\"Name\" class=\"label\">Name : </label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input class=\"field\" type=\"text\" id=\"Name\" name=\"Name\" placeholder=\"Type new name here...\"><br><label for=\"password\" class=\"label\">Password : </label><input class=\"field\" type=\"password\" id=\"password\" name=\"password\"  placeholder=\"Type new password here...\"><p id=\"note\">*Note:Leaving field empty will not change the value.</p><button id=\"submit\" type=\"submit\">SUBMIT CHANGES</button></form></div>";
    var updatedhtml=html+update;
    formbox.innerHTML=updatedhtml;
   
});

$(document).on('click','#tripHistory',function(){
	var formbox=document.getElementById('formbox');
	formbox.innerHTML="";
	var parsedTrips;
	var tripData;
	var html;
	 $.get('/com.tomcat_hello_world/GetTrips',function(responseText){
            if(responseText!=null){
                parsedTrips=responseText;
                tripData=Handlebars.compile("<div id=\"tripTable\"><div id=\"profileIcon\"><img class=\"image\" src=\"./resources/images/profile.svg\"></div>"+"<div id=\"editProfile\"><h2 id=\"header\">Trip History</h2><table id=\"tripList\" frame=\"box\"><tr><th>Trip id</th><th>Cab id</th><th>Cab Type</th><th>From</th><th>To</th><th>Trip Distance</th><th>Trip Fare</th><th>Trip Status</th><th>Trip Starting time</th><th>Trip Ending time</th></tr>"+"{{#each parsedTrips}}<tr> <td>{{id}}</td> <td>{{cabid}}</td> <td>{{cabType}}</td> <td>{{src}}</td> <td>{{dest}}</td> <td>{{distance}}km</td> <td>&#8377;{{fare}}</td> <td>{{status}}</td> <td>{{timeCreated}}</td> <td>{{timeEnded}}</td></tr> {{/each}}"+"</table></div></div>");
                html = tripData({parsedTrips:parsedTrips});
                formbox.innerHTML=html;
            }
        });
    
});







