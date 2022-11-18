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
		}
		else if(element=="manageCabs"){
			this.currentRender=new ManageCabs();
			this.currentRender.writeCabStats();
		}
		else if(element=="manageLocs"){
			this.currentRender=new ManageLocations();
			this.currentRender.fetchLocStats(this.currentRender.writeLocStats.bind(this.currentRender));
		}
		else if(element=="manageUsers"){
			this.currentRender=new ManageUsers();
			this.currentRender.userStats.fetchStats("Customer",renderAdmin.currentRender.lazyLoadCount).done(function(data){
				renderAdmin.currentRender.userStats.adminStats=data;
				console.log(renderAdmin.currentRender.userStats.adminStats);
				renderAdmin.currentRender.writeUserStats();
			});
		}
	}
	
	activeClassChanger(currentActive){
	  if(currentActive=='logout'){
        document.getElementById("logoutForm").submit();
	  }	
	  else{
	   var prevElement=document.getElementsByClassName("active")[0];
	   prevElement.classList.remove("active");
	   var selectedElement=document.getElementById(currentActive);
	   selectedElement.classList.add("active");
	   var prevElementMobile=document.getElementsByClassName("activeMobileNavItem")[0];
	   prevElementMobile.classList.remove("activeMobileNavItem");
	   var selectedElementMobile=document.getElementById(currentActive+"Mobile");
	   selectedElementMobile.classList.add("activeMobileNavItem");
	  } 
   }
}

var renderAdmin=null
$(document).ready(function(){
  renderAdmin=new AdminRenderer();
  renderAdmin.currentRender.writeStats();
});


