var result="false";
$(document).ready(function renderPages(){
	var hash = window.location.hash;
	console.log(hash);
	if(hash=='#pricing')
	{
		renderPricing();
		}
	else if(hash=='#about')
	{
		renderAbout();
	}
	else if(hash=='#profile')
	{
		renderProfile();
		}
	else if(hash=='#home'){
		 result=checkUsertrips();
		 if(result=="false" || result==null){
			console.log("triggered");
			renderHomeOnLoad();
			$("#homeLink").trigger("click");
			
		}
		
	}	
	});
	
	
function activeClassChanger(currentActive){
   console.log("hi");
	var prevElement=document.getElementsByClassName("active")[0];
	var prevMobileElement=document.getElementsByClassName("activeMobileNavItem")[0];
	if(window.innerWidth-15<=600){
		console.log(prevMobileElement);
		prevMobileElement.classList.remove("activeMobileNavItem");
		var mobileSelectedElement=document.getElementById(currentActive+"Mobile");
		mobileSelectedElement.classList.add("activeMobileNavItem")
	}
	else{
        prevElement.classList.remove("active");
	    var selectedElement=document.getElementById(currentActive);
	    selectedElement.classList.add("active");
	}
}

function renderHome(event){
	console.log(event);
	console.log(event.isTrigger);
	if(event.isTrigger==undefined){
	  result=checkUsertrips();
	}  
	if(result=="false"|| result==null){
	  var formbox=document.getElementById("formbox");
	  var name=document.getElementById("name").value;
	  formbox.innerHTML="<form id=\"bookingForm\"><div class=\"group\"><h2 id=\"qn\">What's your current<span id=\"name\"> location</span>?</h2><br><span id=\"input\"> <select name=\"src\" class=\"field\" id=\"src\" onchange=\"report(this.value)\"><option id=\"placeholder\" value=\"\" disabled selected>Select your location</option></select> </span><br><br><br></div><div class=\"group\">  <div id=\"backbtn\"><button class=\"back\">&#8592; Back</button></div><h2 id=\"qn\">Where do you want to go,<span id=\"name\">"+name+"</span>?</h2><br><span id=\"input\"> <select name=\"dest\" class=\"field\" id=\"dest\" ><option id=\"placeholder\" value=\"\" disabled selected>Select your destination</option></select> </span><br><br><br></div><div class=\"group\">  <div id=\"backbtn\"><button class=\"back\">&#8592; Back</button></div><h2 id=\"qn\">What's your preferred vehicle <span id=\"name\">type</span>?</h2><br><div id=\"input\"> <div id=\"carTypes\"><div id=\"car1\" class=\"radioOptn\"  onClick=\"selector(\'hatchback\')\"><input type=\"radio\" id=\"hatchback\" class=\"radiobtns\" name=\"carType\" value=\"hatchback\"><label for=\"hatchback\"><h3>Hatchback</h3><p>Seats <span id=\"qn\">4</span> people</p></label></div><div id=\"car2\" class=\"radioOptn\" onClick=\"selector(\'sedan\')\"><input type=\"radio\" id=\"sedan\" class=\"radiobtns\" name=\"carType\" value=\"sedan\"><label for=\"sedan\"><h3>Sedan</h3><p>Seats <span id=\"qn\">5</span> people</p></label></div><div id=\"car3\" class=\"radioOptn\" onClick=\"selector(\'suv\')\"><input type=\"radio\" id=\"suv\" class=\"radiobtns\" name=\"carType\" value=\"suv\"><label for=\"suv\"><h3>SUV</h3><p>Seats <span id=\"qn\">8</span> people</p></label></div></div></div><br><br><br></div><div><button id=\"next\" >NEXT &#8594;</button></div></form>";
	  activeClassChanger("home");
	}  
	
}

function renderHomeOnLoad(){
	  var formbox=document.getElementById("formbox");
	  var name=document.getElementById("name").value;
	  formbox.innerHTML="<form id=\"bookingForm\"><div class=\"group\"><h2 id=\"qn\">What's your current<span id=\"name\"> location</span>?</h2><br><span id=\"input\"> <select name=\"src\" class=\"field\" id=\"src\" onchange=\"report(this.value)\"><option id=\"placeholder\" value=\"\" disabled selected>Select your location</option></select> </span><br><br><br></div><div class=\"group\">  <div id=\"backbtn\"><button class=\"back\">&#8592; Back</button></div><h2 id=\"qn\">Where do you want to go,<span id=\"name\">"+name+"</span>?</h2><br><span id=\"input\"> <select name=\"dest\" class=\"field\" id=\"dest\" ><option id=\"placeholder\" value=\"\" disabled selected>Select your destination</option></select> </span><br><br><br></div><div class=\"group\">  <div id=\"backbtn\"><button class=\"back\">&#8592; Back</button></div><h2 id=\"qn\">What's your preferred vehicle <span id=\"name\">type</span>?</h2><br><div id=\"input\"> <div id=\"carTypes\"><div id=\"car1\" class=\"radioOptn\"  onClick=\"selector(\'hatchback\')\"><input type=\"radio\" id=\"hatchback\" class=\"radiobtns\" name=\"carType\" value=\"hatchback\"><label for=\"hatchback\"><h3>Hatchback</h3><p>Seats <span id=\"qn\">4</span> people</p></label></div><div id=\"car2\" class=\"radioOptn\" onClick=\"selector(\'sedan\')\"><input type=\"radio\" id=\"sedan\" class=\"radiobtns\" name=\"carType\" value=\"sedan\"><label for=\"sedan\"><h3>Sedan</h3><p>Seats <span id=\"qn\">5</span> people</p></label></div><div id=\"car3\" class=\"radioOptn\" onClick=\"selector(\'suv\')\"><input type=\"radio\" id=\"suv\" class=\"radiobtns\" name=\"carType\" value=\"suv\"><label for=\"suv\"><h3>SUV</h3><p>Seats <span id=\"qn\">8</span> people</p></label></div></div></div><br><br><br></div><div><button id=\"next\" >NEXT &#8594;</button></div></form>";
	  activeClassChanger("home");

}

function renderProfile(){
		
	 var formbox=document.getElementById("formbox");
	 var name=document.getElementById("name").value;
	 var email=document.getElementById("email").value;
	 formbox.innerHTML="<div id=\"profileIcon\"><img class=\"image\" src=\"./resources/images/profile.svg\" alt=\"User image icon\"></div><h2 id=\"header\">Profile Details</h2><div class=\"flexbox\"><div class=\"flex-item\"><h3>Name: <span class=\"dets\">"+name+"</span></h3></div><div class=\"flex-item\"><h3>Email: <span class=\"dets\">"+email+"</span></h3></div></div><div class=\"flexbox\"><div class=\"flex-item\"><button id=\"editProfileBtn\">EDIT PROFILE</button></div><div class=\"flex-item\"><button id=\"tripHistory\">VIEW TRIP HISTORY</button></div><div class=\"flex-item\"><button id=\"deleteAccount\" onclick=\"confirmDelete(event)\"><form id=\"logoutForm\" action=\"logout\" method=\"POST\"> <div id=\"email\" data-email=\"<%=email%>\"></div></form>DELETE ACCOUNT</button></div></div>";
	 activeClassChanger("profile");

}

function renderPricing(){
	
	 var formbox=document.getElementById("formbox");
	 formbox.innerHTML=" <div id=\"profileIcon\"><img class=\"image\" src=\"./resources/images/pricing.svg\" alt=\"Pricing image icon\"></div><h2 id=\"header\">Pricing</h2><div class=\"flexbox2\"><div class=\"flex-item2\"><h3>Hatchback:&#8377; <span class=\"dets\">20/km</span></h3></div><div class=\"flex-item2\"><h3>Sedan:&#8377; <span class=\"dets\">30/km</span></h3></div><div class=\"flex-item2\"><h3>SUV:&#8377; <span class=\"dets\">50/km</span></h3></div></div>";
	 activeClassChanger("pricing");
	
}

function renderAbout(){
	
	 var formbox=document.getElementById("formbox");
	 formbox.innerHTML=" <div id=\"profileIcon\"><img class=\"image\" src=\"./resources/images/car.svg\" alt=\"Car image icon\"></div><h2 id=\"header\">About Us</h2><div id=\"about\" class=\"flexbox\"><div class=\"flex-item\"><h3><span class=\"dets\">We aim to provide high quality cab services online at affordable prices.</span></h3></div></div>";
	 activeClassChanger("about");
	
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

function checkUsertrips(){
	var result="false";
	$.ajax({
        type: "GET",
        url: "/com.tomcat_hello_world/account/CheckUsertrips",
        success: function(result) {
	       if(result==null){
	           userTrip=null;
             }
            else{ 
	         userTrip="true";
	         bookingDetails=result;
	         window.localStorage.setItem("bookingStatus","confirmed");
	         window.localStorage.setItem("confirmationDetails",JSON.stringify(result));
             var formbox=document.getElementById("formbox");
             console.log(result);
             var bookingData=Handlebars.compile("<div id=\"confirmbox\">{{#with result}}<h2 class=\"confirmation\" id=\"tick\">&#10004;</h2><h2 class=\"confirmation\">Booking Confirmed</h2><h3 class=\"details\">OTP: <span class=\"detailans\"> {{otp}} </span></h3><div id=\"btns\"><div id=\"start\"><form><input type=\"hidden\" name=\"fare\" value=\"{{fare}}\"><input type=\"hidden\" name=\"dest\" value=\"{{dest}}\">{{/with}}<button id=\"startbtn\" onClick=\"startTrip(event)\">START TRIP &#8594;</button></form></div><div id=\"cancel\"><form><button id=\"cancelbtn\" onClick=\"cancelTrip(event)\">&#8592; CANCEL TRIP</button></form></div></div></div>");
             var html=bookingData({result:result});
             formbox.innerHTML=html;
             }
        },
        error: function(xhr,result) {
	       if(xhr.status==408){
		       alert("Your previous booking was cancelled due to inactivity and your account was charged a penalty of Rs.100");
  	         }
  	         else if(result==null){
	           renderHome();
             }
  	         else{
	           alert("Error while fetching current trip.")
            }
            result="true";
        }
    });
    return result;
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
}

const buttons = document.getElementsByClassName("mobileNavItem");
for (const button of buttons) {
  button.addEventListener("click", createRipple);
}

function createRipple(event){
	console.log("ripple");
	const button=event.currentTarget;
	const circle = document.createElement("span");
    const diameter = Math.max(button.clientWidth, button.clientHeight);
    const radius = diameter / 2;
	circle.style.width = circle.style.height = `${diameter}px`;
    circle.style.left = `${event.clientX - (button.offsetLeft + radius)}px`;
    circle.style.top = `${event.clientY - (button.offsetTop + radius)}px`;
    circle.classList.add("ripple"); 
	const ripple = button.getElementsByClassName("ripple")[0];
    if (ripple) {
      ripple.remove();
    }
	button.appendChild(circle);
	if(event.currentTarget.id=="homeMobile"){
		renderHome(event);
	}
	else if(event.currentTarget.id=="profileMobile"){
		renderProfile();
	}
	else if(event.currentTarget.id=="pricingMobile"){
		renderPricing();
	}
	else if(event.currentTarget.id=="aboutMobile"){
		renderAbout();
	}
	else if(event.currentTarget.id=="mobileLogout"){
		logout();
	}
}

function logout(){
	document.getElementById("logoutForm").submit(); 
}

function asyncCall(servletName){
	return $.ajax({
		type: "GET",
		url: servletName,
	});
}

function confirmDelete(event){
	let deleteResponse=confirm("Do you want to delete your account?");
	let userEmail=document.getElementById("email").value;
    if(deleteResponse){
		asyncCall("/com.tomcat_hello_world/CheckUserStatus")
		.done(function(){
			alert("Your account was successfully deleted!");
			logout();
			removeUser(userEmail,event);
		})
		.fail(function(){
			alert("Sorry..you have an underway trip.Please wait for the trip to complete before deleting your account!");})
	    .always(function(jqXHR){
			console.log("AJAX call status : "+jqXHR.status);
		})
	}
}