class ManageLocationsDOM{
	getDash(){
		var element=document.getElementById("dash");
		return element;
	}
	
    getLocsBox(){
        return document.getElementById("locsBox");
    }

    getLocNames(){
        return document.getElementById("locNames");
    }
    
    getLocUpdateBox(){
        return document.getElementById("locUpdateBox");
    }


}

class ManageLocations{
    
	

	get locStats(){
		return this._locStats;
	}
	
	set locStats(newLocStats){
		this._locStats=newLocStats;
	}
	
	/*get filteredCabs(){
		return this._filteredCabs;
	}
	
	set filteredCabs(newCabStats){
		this._filteredCabs=newCabStats;
	}
	
	writeLocs(){
	     var html;
		 var dom=new ManageCabsDOM();
	    if(this.filteredCabs.length>0){
	       var userData=Handlebars.compile("{{#with parsedCabs}}{{#each this}}<div id=\"c{{id}}\" class=\"cab\"><h5 class=\"detailans\">#<span class=\"detail\">{{id}}</span></h5><h5 class=\"detail\">Driver name:<span class=\"detailans\">{{driverName}}</span></h5><h5 class=\"detail\">Wallet:<span class=\"detailans\">{{wallet}}</span></h5><h5 class=\"detail\">Cab status:<span class=\"detailans\">{{status}}</span></h5><h5 class=\"detail\">Cab type:<span class=\"detailans\">{{cabType}}</span></h5><h5 class=\"detail\">Cab location:<span class=\"detailans\">{{loc}}</span></h5><h5 class=\"detail\">Driver uid:<span class=\"detailans\">{{uid}}</span></h5></div>{{/each}}{{/with}}");
           html = userData({parsedCabs:this.filteredCabs});
        }
        else{
	      html="<h5 class=\"resultNone\">No cabs found!</h5>";
        }    
        console.log()
        dom.getCabsBox().innerHTML=html;
    }
	
	filterCabStats(filter){
		var allCabs=this.cabStats.cabs;
		const filteredCab=[];
		 if(filter=="Booked"){
		this.changeActiveFilter(filter);
		allCabs.forEach((jsonCab)=>{
			if(jsonCab.status=="Booked"){
				filteredCab.push(jsonCab);
			}
		})
		this.filteredCabs=filteredCab;
		this.writeCabs();
	  }
	  else if(filter=="Available"){
		this.changeActiveFilter(filter);
		allCabs.forEach((jsonCab)=>{
			if(jsonCab.status=="Available"){
				filteredCab.push(jsonCab);
			}
		})
		 this.filteredCabs=filteredCab;
		 this.writeCabs();
	  }
	  else{
		this.changeActiveFilter(filter);
		this.filteredCabs=allCabs;
	    this.writeCabs();
	   }
	}
	
	changeActiveFilter(element){
	console.log("changing");
	  var currentActive=document.getElementsByClassName("activeFilterCabs")[0];
	  currentActive.classList.remove("activeFilterCabs");
	  document.getElementById(element).classList.add("activeFilterCabs");
    }*/
	
	fetchLocStats(callback){
	 $.ajax({
        type: "GET",
        url: "/com.tomcat_hello_world/admin/GetAdminLocs",
        success:callback,
        error:function(xhr,error){
	       console.log(error);
	       return error;
        }
    });
	}

    writeLocsOverview(locs){
        console.log(locs);
        var dom=new ManageLocationsDOM();
        var locData=Handlebars.compile("{{#with parsedLocs}}{{#each this}}<div id=\"loc{{id}}\" class=\"loc\"><h5 class=\"detail\">#{{this.key}}:<span class=\"detailans\">{{this.value}}</span></h5></div>{{/each}}{{/with}}");
        var html = locData({parsedLocs:locs});
        dom.getLocsBox().innerHTML=html;
    }
	
	writeLocStats(result){
		var dom=new ManageLocationsDOM();
		self.locStats=result;
		var html="<div id=\"dashLocRow\"><div id=\"dashLocColumn1\" class=\"dashLocColumn\"><h2 class=\"statTitle\" id=\"tripTitle\">Locations Overviews</h2><div id=\"locsBox\"></div></div><div id=\"dashLocColumn2\" class=\"dashLocColumn\"><div id=\"locUpdateBox\"></div></div></div>";
        dom.getDash().innerHTML=html;
        var locData=Handlebars.compile("{{#with parsedLocs}}{{#each this}}<div id=\"loc{{id}}\" class=\"loc\"><h5 class=\"detail\">#{{@key}}:<span class=\"detailans\">{{this}}</span></h5></div>{{/each}}{{/with}}");
        var html = locData({parsedLocs:self.locStats});
        dom.getLocsBox().innerHTML=html;
        this.writeLocsUpdateForm();
	}

	writeLocsUpdateForm(){
		var dom=new ManageLocationsDOM();
       var locsUpdateFormHtml="<h2 class=\"statTitle\" id=\"tripTitle\">Add Locations</h2><form><label for=\"locNames\">Location Name(s):</label><input type=\"text\" id=\"locNames\" class=\"field\" name=\"locNames\" placeholder=\"Multiple locations should be seperated by comma\" required></input><button onclick=\"return renderAdmin.currentRender.addNewLocs()\" id=\"submitAddLocs\" class=\"greenBtn\">Add locations</button></form>";
	   dom.getLocUpdateBox().innerHTML=locsUpdateFormHtml;
	}

	addNewLocs(){
        console.log("adding locations");
		var dom=new ManageLocationsDOM();
		var locNames=dom.getLocNames().value;
		$.ajax({
			type: "GET",
			url: "/com.tomcat_hello_world/admin/AddLocs",
			data: { 
				locNames:locNames
			},
			success: function() {
				console.log("success");
				$(document).ready(alert("Location(s) added successfully"));
			},
			error: function(xhr) {
				if(xhr.status==406){
				   alert("Sorry....one or more of the locations are already registered!");
				}
				else if(xhr.status==400){
				   alert("Invalid input details"); 
				}
				else{
				  alert('Error while adding location(s)!');
				}  
			}
		});
		dom.getLocNames().value="";
		return false;
		
	}
}

