/**
 * 
 */
 var q = 1, qMax = 0;
var bookingDetails;
 $(document).on('click','#homeLink',function () {
	     q=1;
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
	         renderHome();
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
		    renderHome();
	      }
		}
	     
         qMax = $('#bookingForm div.group').length;
         $('#bookingForm div.group').hide();
         $('#bookingForm div.group:nth-child(1)').show();
         $('#next').on('click', function (event) {
            event.preventDefault();
            handleClick(event);
         });
         $('.back').on('click', function (event) {
            event.preventDefault();
            goBack();
         });
         var locs=JSON.parse(localStorage.getItem("Locations"));
         console.log(locs);
	     var src=document.getElementById("src");
         var options="<option id=\"placeholder\" value=\"\" disabled selected>Select your location</option>";
         for(var i=0;i<locs.length;i++){
	      options=options+"<option id=\""+"option"+i+"\" class=\"options\" value=\""+locs[i]+"\">"+locs[i]+"</option>"
         }
         src.innerHTML=options;
         });
         
$(function () {
         qMax = $('#bookingForm div.group').length;
         $('#bookingForm div.group').hide();
         $('#bookingForm div.group:nth-child(1)').show();
         $('#next').on('click', function (event) {
            event.preventDefault();
            handleClick(event);
         });
         $('.back').on('click', function (event) {
            event.preventDefault();
            goBack();
         });
         });         


function handleClick(event) {
	var qstore,qmaxstore;
    if (q < qMax) {
	   console.log($('#bookingForm div.group:nth-child(' + q + ')').find("select").val());
	   if( $('#bookingForm div.group:nth-child(' + q + ')').find("select").val()!=null){
        $('#bookingForm div.group:nth-child(' + q + ')').hide();
        $('#bookingForm div.group:nth-child(' + (q + 1) + ')').show();
        if (q == (qMax - 1)) {
            $('#next').html('Book now &#8594;');
        }
        q++;
       }
       else{
	     var msg;
	     if(q==1){
		  msg="Please select your location!";
	     }
	     else if(q==2){
		  msg="Please select your destination!";
	     }
	     alert(msg);
        } 
        qstore=q;
        qmaxstore=qMax;
    } else {
	   if( $('#bookingForm div.group:nth-child(' + q + ')').find('input[name=carType]:checked').val()!=null){
	    q=1,qMax=3;
	    var src=document.getElementById("src").value;
	    var dest=document.getElementById("dest").value;
	    var carType=document.getElementsByName("carType")[0].value;
	    var uid=document.getElementById("uid").value;
        $(function() {
	    event.preventDefault();
        $.ajax({
        type: "POST",
        url: "/com.tomcat_hello_world/BookCab",
        data: { 
            src:src,
            dest:dest,
            carType:carType,
            uid:uid
        },
        success: function(result) {
	        console.log(result);
	        if(result!=null){
	          bookingDetails=result;
	          window.localStorage.setItem("bookingStatus","pending");
	          window.localStorage.setItem("bookingDetails",JSON.stringify(bookingDetails));
              var formbox=document.getElementById("formbox");
              var bookingData=Handlebars.compile("<div id=\"cabBox\"><div id=\"header\"><h3>Cab Details</h3></div>{{#with result}}<div id=\"item1\"<h4><span id=\"qn\"> Driver Name: </span>{{cab.name}}</h4></div><div id=\"item2\"><h4> <span id=\"qn\">  Type: </span>{{cab.type}}</h4></div><div id=\"item3\"><h4><span id=\"qn\">  Trip Distance: </span>{{trip.distance}} kms</h4></div><div id=\"item4\"><h4> <span id=\"qn\"> ETA: </span>{{trip.eta}} mins</h4></div><div id=\"item5\"><h4> <span id=\"qn\"> Fare: &#8377;</span>{{trip.fare}}</h4>{{/with}}</div><div id=\"BookBtn\"><button  id=\"submit\" onClick=\"handleSubmit()\">Confirm Booking &#8594;</button></div></div>");
              var html=bookingData({result:result});
              formbox.innerHTML=html;
            }
            else{
	            q=3;
	            alert("Sorry....no cabs found!");
            }  
        },
        error: function(result) {
	        q=3;
            alert('Error while finding cabs...Please try again later!');
        }
    });
}); 
     }
     else{
	   alert("Please select your cab type!");
     }
    }
}

function handleSubmit(){  
	    bookingDetails=JSON.parse(window.localStorage.getItem("bookingDetails"));
	    var email=document.getElementById("email").value;
	    
        $.ajax({
        type: "GET",
        url: "/com.tomcat_hello_world/BookingConfirmed",
        contentType: "application/json",
        data: { 
	       bookingDetails:JSON.stringify(bookingDetails),
	       email:email
        },
        success: function(result) {
	        bookingDetails=result;
	        window.localStorage.setItem("bookingStatus","confirmed");
	        window.localStorage.setItem("confirmationDetails",JSON.stringify(result));
            var formbox=document.getElementById("formbox");
            console.log(result);
            var bookingData=Handlebars.compile("<div id=\"confirmbox\">{{#with result}}<h2 class=\"confirmation\" id=\"tick\">&#10004;</h2><h2 class=\"confirmation\">Booking Confirmed</h2><h3 class=\"details\">OTP: <span class=\"detailans\"> {{otp}} </span></h3><div id=\"btns\"><div id=\"start\"><form><input type=\"hidden\" name=\"fare\" value=\"{{fare}}\"><input type=\"hidden\" name=\"dest\" value=\"{{dest}}\">{{/with}}<button id=\"startbtn\" onClick=\"startTrip(event)\">START TRIP &#8594;</button></form></div><div id=\"cancel\"><form><button id=\"cancelbtn\" onClick=\"cancelTrip(event)\">&#8592; CANCEL TRIP</button></form></div></div></div>");
            var html=bookingData({result:result});
            formbox.innerHTML=html;
        },
        error: function(result) {
            alert('Error while booking cab...Please try again later!');
        }
    });
}

function startTrip(e){  
	    e.preventDefault();
	    bookingDetails=JSON.parse(window.localStorage.getItem("confirmationDetails"));
	    var email=document.getElementById("email").value;
        $.ajax({
        type: "GET",
        url: "/com.tomcat_hello_world/StartTrip",
        contentType: "application/json",
        data: { 
	       bookingDetails:JSON.stringify(bookingDetails),
	       email:email
        },
        success: function(result) {
	        window.localStorage.removeItem("bookingStatus");
	        window.localStorage.removeItem("bookingDetails");
            window.localStorage.removeItem("confirmationDetails");
            var formbox=document.getElementById("formbox");
            console.log(result);
            var bookingData=Handlebars.compile("<div id=\”confirmbox\”><h2 class=\"confirmation\" id=\"tick\">&#10004;</h2><h2 class=\"confirmation\">Trip Completed!</h2><h3 class=\"details\">Fare:&#8377; <span class=\"detailans\"> {{result.fare}} </span></h3><div id=\"btns\"><div id=\"start\"><a href=\"/com.tomcat_hello_world/account\"><button id=\"startbtn\" onClick=\"renderHome()\">GO HOME &#8594;</button></a></div></div></div>");
            var html=bookingData({result:result});
            formbox.innerHTML=html;
        },
        error: function(result) {
	        console.log(JSON.stringify(result));
            alert('Error while starting trip...Please try again later!');
        }
    });
}

function cancelTrip(e){  
	    e.preventDefault();
	    console.log(bookingDetails);
	    var email=document.getElementById("email").value;
	    bookingDetails=JSON.parse(window.localStorage.getItem("confirmationDetails"));
        $.ajax({
        type: "GET",
        url: "/com.tomcat_hello_world/CancelTrip",
        contentType: "application/json",
        data: { 
	       bookingDetails:JSON.stringify(bookingDetails),
	       email:email
        },
        success: function(result) {
	        window.localStorage.removeItem("bookingStatus");
	        window.localStorage.removeItem("bookingDetails");
	        window.localStorage.removeItem("confirmationDetails");
            var formbox=document.getElementById("formbox");
            console.log(result);
            var bookingData=Handlebars.compile("<div id=\"confirmbox\"><h2 class=\"confirmation\" id=\"cross\">&#10060;</h2><h2 class=\"cancellation\">Trip Cancelled!</h2><h3 class=\"details\">Penalty:&#8377; <span class=\"detailans\"> {{result.fare}} </span></h3><p>Amount will be automatically credited from your account.<p><div id=\"btns\"><div id=\"start\"><a href=\"/com.tomcat_hello_world/account\"><button id=\"startbtn\">GO HOME &#8594;</button></a></div></div></div>");
            var html=bookingData({result:result});
            formbox.innerHTML=html;
        },
        error: function(result) {
            alert('Error while cancelling cab...Please try again later!');
        }
    });
}

function goBack(){
	if(q>1){
		console.log("backed");
		$('#bookingForm div.group:nth-child(' + q + ')').hide();
        $('#bookingForm div.group:nth-child(' + (q-1) + ')').show();
	    $('#next').html('Next &#8594;');
        q--;
	}
}

function report(src){
	        var locs=JSON.parse(localStorage.getItem("Locations"));
            var dest=document.getElementById("dest");
            var options="<option id=\"placeholder\" value=\"\" disabled selected>Select your location</option>";
                for(var i=0;i<locs.length;i++){
	              if(locs[i]!=src){
		              options=options+"<option id=\""+"option"+i+"\" class=\"options\" value=\""+locs[i]+"\">"+locs[i]+"</option>"
	               }
                }
                dest.innerHTML=options;
                
            
      
}

function selector(btn){
	document.getElementById(btn).checked=true;
	var car="car1";
	if(btn=="sedan"){
		car="car2";
		document.getElementById("car1").style.backgroundColor="white";
		document.getElementById("car3").style.backgroundColor="white";
	}
	else if(btn=="suv"){
		car="car3";
		document.getElementById("car1").style.backgroundColor="white";
		document.getElementById("car2").style.backgroundColor="white";
	}
	else{
		document.getElementById("car2").style.backgroundColor="white";
		document.getElementById("car3").style.backgroundColor="white";
	}
	document.getElementById(car).style.backgroundColor="aliceblue";
	
}

  

