/**
 * 
 */
 var q = 1, qMax = 0;
 $(function () {
         qMax = $('#bookingForm div.group').length;
         $('#bookingForm div.group').hide();
         $('#bookingForm div.group:nth-child(1)').show();
         $('#next').on('click', function (event) {
            event.preventDefault();
            handleClick();
         });
         $('.back').on('click', function (event) {
            event.preventDefault();
            goBack();
         });
         });


function handleClick() {
    if (q < qMax) {
        $('#bookingForm div.group:nth-child(' + q + ')').hide();
        $('#bookingForm div.group:nth-child(' + (q + 1) + ')').show();
        if (q == (qMax - 1)) {
            $('#next').html('Book now &#8594;');
        }
        q++;
    } else {
        document.getElementById("bookingForm").submit(); // Add code to submit your form
    }
}

function goBack(){
	if(q>1){
		$('#bookingForm div.group:nth-child(' + q + ')').hide();
        $('#bookingForm div.group:nth-child(' + (q-1) + ')').show();
	    $('#next').html('Next &#8594;');
        q--;
	}
}

function report(src){
	 $.get('fetchLocations',{
            email:"Requesting available destinations.."
        },function(responseText){
            if(responseText!=null){
                var dest=document.getElementById("dest");
                var options="<option id=\"placeholder\" value=\"\" disabled selected>Select your location</option>";
                const locs=responseText.split(",");
                for(var i=0;i<locs.length;i++){
	              if(locs[i]!=src){
		              options=options+"<option id=\""+"option"+i+"\" class=\"options\" value=\""+locs[i]+"\">"+locs[i]+"</option>"
	               }
                }
                dest.innerHTML=options;
                
            }
        });
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