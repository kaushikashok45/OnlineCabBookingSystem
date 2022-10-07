var q = 1, qMax = 0;
var locs;

window.onload=function(){
	 $.get('fetchLocations',function(responseText){
            if(responseText!=null){
                var src=document.getElementById("src");
                var options="<option id=\"placeholder\" value=\"\" disabled selected>Select your location</option>";
                responseText=responseText.trim();
                locs=responseText.split(",");
                for(var i=0;i<locs.length;i++){
	              options=options+"<option id=\""+"option"+i+"\" class=\"options\" value=\""+locs[i]+"\">"+locs[i]+"</option>"
                }
                src.innerHTML=options;
                
            }
        });
}







