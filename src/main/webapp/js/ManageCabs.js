class ManageCabsDOM{
	getDash(){
		var element=document.getElementById("dash");
		return element;
	}
	
	getCabsBox(){
		var element=document.getElementById("cabsBox");
		return element;
	}

	getCabUpdateBox(){
		var element=document.getElementById("cabUpdateBox");
		return element;
	}

	getEmails(){
		return document.getElementById("cabids");
	}

	getCabType(){
		return document.getElementById("cabType");
	}

	getCabLoc(){
		return document.getElementById("cabloc");
	}

	getCabsFullForm(){
		return document.getElementById("cabsFullForm");
	}

	getCabsSubForm(id){
		return document.getElementById("cabsSubForm"+id);
	}

	getSubFormField(field,id){
		console.log(field+id);
		return document.getElementById(field+id);
	}
}

class ManageCabs{
	get cabStats(){
		return this._cabStats;
	}
	
	set cabStats(newCabStats){
		this._cabStats=newCabStats;
	}
	
	get filteredCabs(){
		return this._filteredCabs;
	}
	
	set filteredCabs(newCabStats){
		this._filteredCabs=newCabStats;
	}
	
	writeCabs(){
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
    }
	
	fetchCabStats(callback){
	console.log(this.filterTrips===undefined);	
	 $.ajax({
        type: "GET",
        url: "/com.tomcat_hello_world/admin/GetAdminCabs",
        success:callback,
        error:function(xhr,error){
	       console.log(error);
	       return error;
        }
    });
	}
	
	writeCabStats(result){
		var dom=new ManageCabsDOM();
		this.cabStats=result;
		console.log("here");
		var html="<div id=\"dashCabRow\"><div id=\"dashCabColumn1\" class=\"dashCabColumn\"><h2 class=\"statTitle\" id=\"tripTitle\">Cabs Overviews</h2><div id=\"filter\"><div id=\"Booked\" class=\"filterOptions  activeFilterCabs\" onClick=\"renderAdmin.currentRender.filterCabStats('Booked')\"><h6>Booked</h6></div><div id=\"Available\" class=\"filterOptions\" onClick=\"renderAdmin.currentRender.filterCabStats('Available')\"><h6>Available</h6></div><div id=\"AllCabs\" class=\"filterOptions\" onClick=\"renderAdmin.currentRender.filterCabStats('AllCabs')\"><h6>All Cabs</h6></div></div><div id=\"cabsBox\"></div></div><div id=\"dashCabColumn2\" class=\"dashCabColumn\"><div id=\"cabUpdateBox\"></div></div></div>";
		dom.getDash().innerHTML=html;
		this.filterCabStats("Booked");
		this.writeCabsUpdateForm();
	}

	writeCabsUpdateForm(){
		var dom=new ManageCabsDOM();
       var cabsUpdateFormHtml="<h2 class=\"statTitle\" id=\"tripTitle\">Add Cabs</h2><form><div id=\"cabsFullForm\" class=\"fullForm\"><div id=\"addCabBtn\" class=\"addBtn\" onClick=\"renderAdmin.currentRender.writeCabSubForm()\" title=\"Add another Cab\">+</div><button onclick=\"return renderAdmin.currentRender.addNewCabs()\" id=\"submitAddCabs\" class=\"greenBtn\">Add cabs</button></div></form>";
	   dom.getCabUpdateBox().innerHTML=cabsUpdateFormHtml;
	   this.writeCabSubForm();
	}

	writeCabSubForm(){
		var dom=new ManageCabsDOM();
		var div=document.createElement("div");
		var index=dom.getCabsFullForm().childElementCount-1;
		dom.getCabsFullForm().insertBefore(div,dom.getCabsFullForm().children[index-1]);
		var cabSubForm="<div id=\"form"+index+"\"><label for=\"cabids\">Driver email-id:</label><input type=\"text\" id=\"emailid"+index+"\" class=\"field\" name=\"emailid"+index+"\" required></input><br><br><label for=\"cabType\">Cab type:</label> <select name=\"cabType\" id=\"cabTypeid"+index+"\" class=\"select\"><option selected=\"true\" disabled=\"disabled\">Select cab type</option><option value=\"hatchback\">Hatchback</option><option value=\"sedan\">Sedan</option><option value=\"suv\">SUV</option></select><br></br><br></br><label for=\"cablocid"+index+"\">Cabs(s) location id:</label><input type=\"number\" id=\"cablocid"+index+"\" class=\"field\" name=\"cablocid"+index+"\" placeholder=\"Enter Cabs(s) location id..\" required></input></div><div id=\"remove"+index+"\" class=\"removeBtn\" onclick=\"renderAdmin.currentRender.deleteCabSubForm("+index+")\">-</div>";
		dom.getCabsFullForm().childNodes[index-1].innerHTML=cabSubForm;
		dom.getCabsFullForm().childNodes[index-1].setAttribute("id","cabsSubForm"+index);
		dom.getCabsFullForm().childNodes[index-1].setAttribute("class","cabsSubForm");
	}

	deleteCabSubForm(id){
		var dom=new ManageCabsDOM();
		dom.getCabsFullForm().removeChild(dom.getCabsSubForm(id));
	}

	addNewCabs(){
        console.log("adding");
		var cabsToBeAdded={
			cabs:[]
		};
		var dom=new ManageCabsDOM();
		var len=dom.getCabsFullForm().childElementCount;
		for(var i=0;i<len-2;i++){
			cabsToBeAdded.cabs.push({
				"email":dom.getSubFormField("emailid",i+1).value,
				"cabType":dom.getSubFormField("cabTypeid",i+1).value,
				"cabLoc":dom.getSubFormField("cablocid",i+1).value
			});
		}
		const cabs=JSON.stringify(cabsToBeAdded);
		console.log(cabs);
		$.ajax({
			url: "/com.tomcat_hello_world/admin/AddCabs",
			type: "GET",
			async:false,
			contentType:"application/json",
			data: { 
				cabsToBeAdded:cabs
			}
		  }
		).then((data,xhr)=>{console.log(xhr.status); return {data,xhr}}).done(function(response){
			console.log(response.data);
			alert("Cabs added successfully");
		}).fail(function(response){
			console.log(response);
			if(response.status==406){
				alert("Sorry....one or more of the drivers are already registered!");
			 }
			 else if(response.status==400){
				alert("Invalid input details"); 
			 }
			 else if(response.status==402){
				alert("Sorry....one or more drivers is not registered as a user."); 
			 }
			 else if(response.status==417){
				alert("Sorry....one or more email-ids is not registered as a driver."); 
			 }
			 else{
			   alert('Error while adding cabs...Please try again later!');
			 }  
		});
		this.fetchCabStats(this.writeCabStats.bind(this));
		return false;
		
	}
}

/*"<form action=\"\" method=\"GET\">
	<label for=\"cabids\">Driver email-id(s):</label>
	<input type=\"number\" id=\"cabids\" name=\"cabids\" required></input>
	<br>
	<br>
    <label for=\"cabType\">Cab type:</label> 
	<select name=\"cabType\" id=\"cabType\">
	 <option selected=\"true\" disabled=\"disabled\">Select cab type</option>
	 <option value=\"hatchback\">Hatchback</option>
	 <option value=\"sedan\">Sedan</option>
	 <option value=\"suv\">SUV</option>
    </select>
	<button type=\"submit\" id=\"submitAddCabs\">Submit</button>
</form>"*/

