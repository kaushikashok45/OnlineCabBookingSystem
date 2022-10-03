$(document).ready(function(){
	var active='renderHome';
	if(window.localStorage.getItem("active")!=null){
		active=window.localStorage.getItem("active");
	}
	if(active=='renderPricing')
	{
		renderPricing();
		}
	else if(active=='renderAbout')
	{
		renderAbout();
		}
	else if(active=='renderProfile')
	{
		renderProfile();
		}
	else if(active=='renderHome')
	{
		if(window.localStorage.getItem("bookingStatus")=="pending"){
		  let text="You have an unfinished booking.Do you wish to discard it?";
		  result=confirmOperation(text);
		  if(result=="false"){	
		   var formbox=document.getElementById("formbox");
		   var result=JSON.parse(window.localStorage.getItem("bookingDetails"));
		   var bookingData=Handlebars.compile("<div id=\"cabBox\"><div id=\"header\"><h3>Cab Details</h3></div>{{#with result}}<div id=\"item1\"<h4><span id=\"qn\"> Driver Name: </span>{{cab.name}}</h4></div><div id=\"item2\"><h4> <span id=\"qn\">  Type: </span>{{cab.type}}</h4></div><div id=\"item3\"><h4><span id=\"qn\">  Trip Distance: </span>{{trip.distance}} kms</h4></div><div id=\"item4\"><h4> <span id=\"qn\"> ETA: </span>{{trip.eta}} mins</h4></div><div id=\"item5\"><h4> <span id=\"qn\"> Fare: &#8377;</span>{{trip.fare}}</h4>{{/with}}</div><div id=\"BookBtn\"><button  id=\"submit\" onClick=\"handleSubmit()\">Confirm Booking &#8594;</button></div></div>");
           var html=bookingData({result:result});
           formbox.innerHTML=html;
          }
          else{
	         window.localStorage.removeItem("bookingStatus");
	         window.localStorage.removeItem("bookingDetails");
           }   
	     }
	     else if(window.localStorage.getItem("bookingStatus")=="confirmed"){
		   let text="You have confirmed booking and will be charged a penalty for discarding it.Do you wish to discard it?";
		   var promptResult=confirmOperation(text);
		   if(promptResult=="false"){	
		     var result=JSON.parse(window.localStorage.getItem("confirmationDetails"));
		     var formbox=document.getElementById("formbox");
             console.log(result);
             var bookingData=Handlebars.compile("<div id=\"confirmbox\">{{#with result}}<h2 class=\"confirmation\" id=\"tick\">&#10004;</h2><h2 class=\"confirmation\">Booking Confirmed</h2><h3 class=\"details\">OTP: <span class=\"detailans\"> {{otp}} </span></h3><div id=\"btns\"><div id=\"start\"><form><input type=\"hidden\" name=\"fare\" value=\"{{fare}}\"><input type=\"hidden\" name=\"dest\" value=\"{{dest}}\">{{/with}}<button id=\"startbtn\" onClick=\"startTrip(event)\">START TRIP &#8594;</button></form></div><div id=\"cancel\"><form><button id=\"cancelbtn\" onClick=\"cancelTrip(event)\">&#8592; CANCEL TRIP</button></form></div></div></div>");
             var html=bookingData({result:result});
            formbox.innerHTML=html;
	      }
	      else{
		    cancelBooking();
	      }
		}			
	}});
	
function activeClassChanger(currentActive){
	var prevElement=document.getElementsByClassName("active")[0];
	prevElement.classList.remove("active");
	var selectedElement=document.getElementById(currentActive);
	selectedElement.classList.add("active");
}

function renderHome(){
	var result="true";
	if(window.localStorage.getItem("bookingStatus")=="pending"){
		result="false";
	}
	else if(window.localStorage.getItem("bookingStatus")=="confirmed"){
		result="false";
	}
	if(result=="true"){
	 var formbox=document.getElementById("formbox");
	 var name=document.getElementById("name").value;
	 formbox.innerHTML="<form id=\"bookingForm\"><div class=\"group\"><h2 id=\"qn\">What's your current<span id=\"name\"> location</span>?</h2><br><span id=\"input\"> <select name=\"src\" class=\"field\" id=\"src\" onchange=\"report(this.value)\"><option id=\"placeholder\" value=\"\" disabled selected>Select your location</option></select> </span><br><br><br></div><div class=\"group\">  <div id=\"backbtn\"><button class=\"back\">&#8592; Back</button></div><h2 id=\"qn\">Where do you want to go,<span id=\"name\">"+name+"</span>?</h2><br><span id=\"input\"> <select name=\"dest\" class=\"field\" id=\"dest\" ><option id=\"placeholder\" value=\"\" disabled selected>Select your destination</option></select> </span><br><br><br></div><div class=\"group\">  <div id=\"backbtn\"><button class=\"back\">&#8592; Back</button></div><h2 id=\"qn\">What's your preferred vehicle <span id=\"name\">type</span>?</h2><br><div id=\"input\"> <div id=\"carTypes\"><div id=\"car1\" class=\"radioOptn\"  onClick=\"selector(\'hatchback\')\"><input type=\"radio\" id=\"hatchback\" class=\"radiobtns\" name=\"carType\" value=\"hatchback\"><label for=\"hatchback\"><h3>Hatchback</h3><p>Seats <span id=\"qn\">4</span> people</p></label></div><div id=\"car2\" class=\"radioOptn\" onClick=\"selector(\'sedan\')\"><input type=\"radio\" id=\"sedan\" class=\"radiobtns\" name=\"carType\" value=\"sedan\"><label for=\"sedan\"><h3>Sedan</h3><p>Seats <span id=\"qn\">5</span> people</p></label></div><div id=\"car3\" class=\"radioOptn\" onClick=\"selector(\'suv\')\"><input type=\"radio\" id=\"suv\" class=\"radiobtns\" name=\"carType\" value=\"suv\"><label for=\"suv\"><h3>SUV</h3><p>Seats <span id=\"qn\">8</span> people</p></label></div></div></div><br><br><br></div><div><button id=\"next\" >NEXT &#8594;</button></div></form>";
	 window.localStorage.setItem('active','renderHome');
	 activeClassChanger("home");
	} 
}

function renderProfile(){
	var result="true";
	if(window.localStorage.getItem("bookingStatus")=="pending"){
		let text="You have an unfinished booking.Do you wish to discard it?";
		result=confirmOperation(text);
	}
	else if(window.localStorage.getItem("bookingStatus")=="confirmed"){
		let text="You have confirmed booking and will be charged a penalty for discarding it.Do you wish to discard it?";
		result=confirmOperation(text);
		if(result=="true"){
			cancelBooking();
		}
	}
	if(result=="true"){
     window.localStorage.removeItem("bookingStatus");		
	 var formbox=document.getElementById("formbox");
	 var name=document.getElementById("name").value;
	 var email=document.getElementById("email").value;
	 formbox.innerHTML="<div id=\"profileIcon\"><img class=\"image\" src=\"./resources/images/profile.svg\"></div><h2 id=\"header\">Profile Details</h2><div class=\"flexbox\"><div class=\"flex-item\"><h3>Name: <span class=\"dets\">"+name+"</span></h3></div><div class=\"flex-item\"><h3>Email: <span class=\"dets\">"+email+"</span></h3></div></div><div class=\"flexbox\"><div class=\"flex-item\"><button id=\"editProfileBtn\">EDIT PROFILE</button></div><div class=\"flex-item\"><button id=\"tripHistory\">VIEW TRIP HISTORY</button></div> </div>";
	 window.localStorage.setItem('active','renderProfile');
	 activeClassChanger("profile");
	} 
}

function renderPricing(){
	var result="true";
	if(window.localStorage.getItem("bookingStatus")=="pending"){
		let text="You have an unfinished booking.Do you wish to discard it?";
		result=confirmOperation(text);
	}
	else if(window.localStorage.getItem("bookingStatus")=="confirmed"){
		let text="You have confirmed booking and will be charged a penalty for discarding it.Do you wish to discard it?";
		result=confirmOperation(text);
		if(result=="true"){
			cancelBooking();
		}
	}
	if(result=="true"){
	 var formbox=document.getElementById("formbox");
	 formbox.innerHTML=" <div id=\"profileIcon\"><img class=\"image\" src=\"./resources/images/pricing.svg\"></div><h2 id=\"header\">Pricing</h2><div class=\"flexbox2\"><div class=\"flex-item2\"><h3>Hatchback:&#8377; <span class=\"dets\">20/km</span></h3></div><div class=\"flex-item2\"><h3>Sedan:&#8377; <span class=\"dets\">30/km</span></h3></div><div class=\"flex-item2\"><h3>SUV:&#8377; <span class=\"dets\">50/km</span></h3></div></div>";
	 activeClassChanger("pricing");
	 window.localStorage.setItem('active','renderPricing');
	} 
}

function renderAbout(){
	var result="true";
	if(window.localStorage.getItem("bookingStatus")=="pending"){
		let text="You have an unfinished booking.Do you wish to discard it?";
		result=confirmOperation(text);
	}
	else if(window.localStorage.getItem("bookingStatus")=="confirmed"){
		let text="You have confirmed booking and will be charged a penalty for discarding it.Do you wish to discard it?";
		result=confirmOperation(text);
		if(result=="true"){
			cancelBooking();
		}
	}
	if(result=="true"){
	 var formbox=document.getElementById("formbox");
	 formbox.innerHTML=" <div id=\"profileIcon\"><img class=\"image\" src=\"./resources/images/car.svg\"></div><h2 id=\"header\">About Us</h2><div id=\"about\" class=\"flexbox\"><div class=\"flex-item\"><h3><span class=\"dets\">We aim to provide high quality cab services online at affordable prices.</span></h3></div></div>";
	 activeClassChanger("about");
	 window.localStorage.setItem('active','renderAbout');
	} 
}

function renderLogout(){
	window.localStorage.removeItem("Locations");
}

function confirmOperation(words) {
  let text = words;
  if (confirm(text) == true) {
    text = "true";
    window.localStorage.removeItem("bookingStatus");
    window.localStorage.removeItem("bookingDetails");
  } else {
    text = "false";
  }
  return text;
}

function cancelBooking(){
	var bookingDetails=JSON.parse(window.localStorage.getItem("confirmationDetails"));
    $.ajax({
          type: "GET",
          url: "/com.tomcat_hello_world/CancelTrip",
          contentType: "application/json",
          data: { 
	          bookingDetails:JSON.stringify(bookingDetails),
          },
          success: function(result) {
              alert("Your booking was cancelled and a penalty of Rs.100 was charged to your account");
          },
          error: function(result) {
              alert('Error while cancelling cab!');
          }
     });
      window.localStorage.removeItem("bookingStatus");
	  window.localStorage.removeItem("bookingDetails");
      window.localStorage.removeItem("confirmationDetails");
}