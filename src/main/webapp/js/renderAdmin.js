class AdminRenderer{
	
	constructor(){
		var hash = window.location.hash;
		if(hash=="#dashboard" || hash==""){
			window.location.hash="#dashboard"
			this.changeCurrentRender("dashboard");
		}
		else if(hash=="#manageCabs"){
			this.changeCurrentRender("manageCabs");
		}
		else if(hash=="#manageLocs"){
			this.changeCurrentRender("manageLocs");
		}
		else if(hash=="#manageUsers"){
			this.changeCurrentRender("manageUsers");
		}
	}
	
	get currentRender(){
		return this._currentRender;
	}
	
	set currentRender(currentRender){
		this._currentRender=currentRender;
	}
	
	changeCurrentRender(element){
		this.activeClassChanger(element);
		if(element=="dashboard"){
			this.currentRender=new Stats();
			this.currentRender.fetchStats(this.currentRender.writeStats.bind(this.currentRender));
		}
		else if(element=="manageCabs"){
			this.currentRender=new ManageCabs();
			this.currentRender.fetchCabStats(this.currentRender.writeCabStats.bind(this.currentRender));
		}
		else if(element=="manageLocs"){
			this.currentRender=new ManageLocations();
			this.currentRender.fetchLocStats(this.currentRender.writeLocStats.bind(this.currentRender));
		}
		else if(element=="manageUsers"){
			this.currentRender=new ManageUsers();
		}
	}
	
	activeClassChanger(currentActive){
	  var prevElement=document.getElementsByClassName("active")[0];
	  prevElement.classList.remove("active");
	  var selectedElement=document.getElementById(currentActive);
	  selectedElement.classList.add("active");
   }
}

var renderAdmin=null
$(document).ready(function(){
  renderAdmin=new AdminRenderer();
});


