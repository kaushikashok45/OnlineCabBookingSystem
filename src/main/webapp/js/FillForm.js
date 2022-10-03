var q = 1, qMax = 0;
window.onload=function(){
	if(window.localStorage.getItem("Locations")==null){
	 $.get('fetchLocations',{
            email:"Requesting available destinations.."
        },function(responseText){
            if(responseText!=null){
                var src=document.getElementById("src");
                var options="<option id=\"placeholder\" value=\"\" disabled selected>Select your location</option>";
                responseText=responseText.trim();
                const locs=responseText.split(",");
                window.localStorage.setItem("Locations", JSON.stringify(locs));
                for(var i=0;i<locs.length;i++){
	              options=options+"<option id=\""+"option"+i+"\" class=\"options\" value=\""+locs[i]+"\">"+locs[i]+"</option>"
                }
                src.innerHTML=options;
                
            }
        });
      }
      else{
	       var locs=JSON.parse(window.localStorage.getItem("Locations"));
	        var src=document.getElementById("src");
	        var options="<option id=\"placeholder\" value=\"\" disabled selected>Select your location</option>";
                for(var i=0;i<locs.length;i++){
	              options=options+"<option id=\""+"option"+i+"\" class=\"options\" value=\""+locs[i]+"\">"+locs[i]+"</option>"
                }
                src.innerHTML=options;
                
      }
}

function removeItems(){
	if(window.localStorage.getItem("bookingStatus")=="confirmed"){
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
	window.localStorage.removeItem("active");
	window.localStorage.removeItem("Locations");
	window.localStorage.removeItem("bookingDetails");
	window.localStorage.removeItem("bookingStatus");
}





