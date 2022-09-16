var q = 1, qMax = 0;
window.onload=function(){
	 $.get('fetchLocations',{
            email:"Requesting available destinations.."
        },function(responseText){
            if(responseText!=null){
                var dest=document.getElementById("dest");
                var options="<option id=\"placeholder\" value=\"\" disabled selected>Select your destination</option>";
                const locs=responseText.split(",");
                for(var i=0;i<locs.length;i++){
	              options=options+"<option id=\""+"option"+i+"\" class=\"options\" value=\""+locs[i]+"\">"+locs[i]+"</option>"
                }
                dest.innerHTML=options;
                
            }
        });
      
}



