/**
 * 
 */
 var q = 1, qMax = 0;
 var userInput={src:null,dest:null,carType:null,uid:0};
var bookingDetails;
 $(document).on('click','#homeLink',function () {
	     console.log("here");
	     q=1;
	     if(result=="false" || result==null){
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
          console.log(locs);
	      var src=document.getElementById("src");
          var options="<option id=\"placeholder\" value=\"\" disabled selected>Select your location</option>";
          for(var i=0;i<locs.length;i++){
	       options=options+"<option id=\""+"option"+i+"\" class=\"options\" value=\""+locs[i]+"\">"+locs[i]+"</option>"
          }
          console.log(src);
          src.innerHTML=options;
         } 
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
	    var src,dest,carType,uid;
	    if(document.getElementById('src').value!=null){
	     src=document.getElementById("src").value;
	     userInput.src=src;
	     dest=document.getElementById("dest").value;
	     userInput.dest=dest;
	     carType=document.querySelector('input[name="carType"]:checked').value;
	     userInput.carType=carType;
	     uid=document.getElementById("uid").value;
	     userInput.uid=uid;
	    }
	    else{
		  src=userInput.src;
		  dest=userInput.dest;
		  carType=userInput.carType;
		  uid=userInput.uid;
	    } 
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
              var formbox=document.getElementById("formbox");
              var bookingData=Handlebars.compile("<div id=\"cabBox\"><div id=\"header\"><h3>Booking Details</h3></div>{{#with result}}<div id=\"item1\"<h4><span id=\"qn\"> Driver Name: </span>{{driverName}}</h4></div><div id=\"item2\"><h4> <span id=\"qn\">  Type: </span>{{cabType}}</h4></div><div id=\"item3\"><h4><span id=\"qn\">  Trip Distance: </span>{{distance}} kms</h4></div><div id=\"item4\"><h4> <span id=\"qn\"> ETA: </span>{{eta}} mins</h4></div><div id=\"item5\"><h4> <span id=\"qn\"> Fare: &#8377;</span>{{fare}}</h4>{{/with}}</div><div id=\"BookBtn\"><button  id=\"submit\" onClick=\"handleSubmit()\">Confirm Booking &#8594;</button></div></div>");
              var html=bookingData({result:result});
              formbox.innerHTML=html;
            }
            else{
	            q=3;
	            alert("Sorry....no cabs found!");
            }  
        },
        error: function(xhr) {
	        q=3;
	        if(xhr.status==406){
		       alert("Sorry....no cabs found!");
	        }
	        else if(xhr.status==400){
		       alert("Invalid input details"); 
	        }
	        else{
              alert('Error while finding cabs...Please try again later!');
            }  
        }
    });
}); 
     }
     else{
	   alert("Please select your cab type!");
     }
    }
}

function handleReassign(){
	$(function() {
	   
        $.ajax({
        type: "POST",
        url: "/com.tomcat_hello_world/BookCab",
        data: { 
            src:userInput.src,
            dest:userInput.dest,
            carType:userInput.carType,
            uid:userInput.uid
        },
        success: function(result) {
	        console.log(result);
	        if(result!=null){
	          bookingDetails=result;
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
        error: function(xhr) {
	        q=3;
	        if(xhr.status==406){
		       alert("Sorry....no cabs found!");
	        }
	        else if(xhr.status==400){
		       alert("Invalid input details"); 
	        }
	        else{
              alert('Error while finding cabs...Please try again later!');
            }  
        }
    });
}); 
}

function handleSubmit(){  
	    
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
            var formbox=document.getElementById("formbox");
            console.log(result);
            var bookingData=Handlebars.compile("<div id=\"confirmbox\">{{#with result}}<h2 class=\"confirmation\" id=\"tick\">&#10004;</h2><h2 class=\"confirmation\">Booking Confirmed</h2><h3 class=\"details\">OTP: <span class=\"detailans\"> {{otp}} </span></h3><div id=\"btns\"><div id=\"start\"><form><input type=\"hidden\" name=\"fare\" value=\"{{fare}}\"><input type=\"hidden\" name=\"dest\" value=\"{{dest}}\">{{/with}}<button id=\"startbtn\" onClick=\"startTrip(event)\">START TRIP &#8594;</button></form></div><div id=\"cancel\"><form><button id=\"cancelbtn\" onClick=\"cancelTrip(event)\">&#8592; CANCEL TRIP</button></form></div></div></div>");
            var html=bookingData({result:result});
            formbox.innerHTML=html;
        },
        error: function(xhr) {
	        if(xhr.status==404){
		      alert("Sorry,your assigned cab is no longer available.You will be assigned a new cab shortly.");
		      handleReassign();
	        }
	        else{
              alert('Error while booking cab...Please try again later!');
            }  
        }
    });
}

function startTrip(e){  
	    e.preventDefault();
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
        $.ajax({
        type: "GET",
        url: "/com.tomcat_hello_world/CancelTrip",
        contentType: "application/json",
        data: { 
	       bookingDetails:JSON.stringify(bookingDetails),
	       email:email
        },
        success: function(result) {
            var formbox=document.getElementById("formbox");
            console.log(result);
            var bookingData=Handlebars.compile("<div id=\"confirmbox\"><h2 class=\"confirmation\" id=\"cross\">&#10060;</h2><h2 class=\"cancellation\">Trip Cancelled!</h2><h3 class=\"details\">Penalty:&#8377; <span class=\"detailans\"> {{result.fare}} </span></h3><p>Amount will be automatically credited from your account.<p><div id=\"btns\"><div id=\"start\"><a href=\"/com.tomcat_hello_world/account\"><button id=\"startbtn\">GO HOME &#8594;</button></a></div></div></div>");
            var html=bookingData({result:result});
            formbox.innerHTML=html;
        },
        error: function(result) {
	        console.log(result);
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


  

