class ManageUsersDOM{
	getDash(){
		var element=document.getElementById("dash");
		return element;
	}
	

	getAddUserBox(){
		var element=document.getElementById("addUserBox");
		return element;
	}

	getUsersBox(){
		return document.getElementById("usersBox");
	}

	getUserDataBox(){
		return document.getElementById("userDataBox");
	}

    getUsersFullForm(){
		return document.getElementById("usersFullForm");
	}

	getUsersSubForm(id){
		return document.getElementById("usersSubForm"+id);
	}
    
    getTripById(id){
		return document.getElementById(id);
	} 

	getSubFormField(field,id){
		console.log(field+id);
		return document.getElementById(field+id);
	}
}

class ManageUsers{

    get self(){
        return this._self;
    }

    set self(context){
        this._self=context;
    }

	get lazyLoadedRecords(){
		return this._lazyLoadedRecords;
	}

	set lazyLoadedRecords(newData){
        this._lazyLoadedRecords=newData;
	}

	get lazyLoadCount(){
		return this._lazyLoadCount;
	}

	set lazyLoadCount(newCount){
		this._lazyLoadCount=newCount;
	}

    get dom(){
        return this._dom;
    }

    set dom(newDOM){
        this._dom=newDOM;
    }
    
    get userStats(){
        return this._userStats;
    }

    set userStats(userStats){
        this._userStats=userStats;
    }

    constructor(){
        this.self=this;
        this.self.dom=new ManageUsersDOM();
        this.self.userStats=new Stats();
        this.self.writeUsersBox();
		this.self.lazyLoadCount=0;
    }
	
	writeUsersBox(){
		var html="<div id=\"dashUserRow\"><div id=\"dashUserColumn1\" class=\"dashUserColumn\"><div id=\"usersBox\"></div></div><div id=\"dashUserColumn2\" class=\"dashUserColumn\"><h2 class=\"statTitle\" id=\"tripTitle\">Add Users</h2><div id=\"addUserBox\"></div></div></div>";
		this.self.dom.getDash().innerHTML=html;
		this.self.writeUsersUpdateForm();
	}

    writeUserStats(){
		console.log(this.self.userStats.adminStats);
		if(this.self.lazyLoadCount==0){
		  this.self.dom.getUsersBox().innerHTML="<h2 class=\"statTitle\" id=\"tripTitle\">Users overview</h2><div id=\"userTable\"><div id=\"filter\"><div id=\"Customer\" class=\"filterOptions  activeFilter2\" onClick=\"renderAdmin.currentRender.userStats.filterUsers('Customer','usersBox')\"><h6>Customers</h6></div><div id=\"Driver\" class=\"filterOptions\" onClick=\"renderAdmin.currentRender.userStats.filterUsers('Driver','usersBox')\"><h6>Drivers</h6></div><div id=\"Admin\" class=\"filterOptions\" onClick=\"renderAdmin.currentRender.userStats.filterUsers('Admin','usersBox')\"><h6>Admins</h6></div></div><div id=\"userDataBox\" onscroll=\"renderAdmin.currentRender.lazyLoader(event)\"></div></div>";	
		  this.self.userStats.adminStats.filter="Customer";	
		  this.self.userStats.fetchStats(this.self.userStats.filter,this.self.lazyLoadCount).done(function(data){ 
			renderAdmin.currentRender.lazyLoadedRecords=data.users.users;
			var userData=Handlebars.compile("{{#with parsedUsers}}{{#each this}}<div id=\"t{{id}}\" class=\"trip\"><h5 class=\"detailans\">#<span class=\"detail\">{{id}}</span></h5><h5 class=\"detail\">User name:<span class=\"detailans\">{{name}}</span></h5><h5 class=\"detail\">Email:<span class=\"detailans\">{{email}}</span></h5><h5 class=\"detail\">Role:<span class=\"detailans\">{{role}}</span></h5></div>{{/each}}{{/with}}");
		    var html=userData({parsedUsers:renderAdmin.currentRender.lazyLoadedRecords});
		    renderAdmin.currentRender.dom.getUserDataBox().innerHTML=html;
		  });
		  
		}
		else{
			var userData=Handlebars.compile("{{#with parsedUsers}}{{#each this}}<div id=\"t{{id}}\" class=\"trip\"><h5 class=\"detailans\">#<span class=\"detail\">{{id}}</span></h5><h5 class=\"detail\">User name:<span class=\"detailans\">{{name}}</span></h5><h5 class=\"detail\">Email:<span class=\"detailans\">{{email}}</span></h5><h5 class=\"detail\">Role:<span class=\"detailans\">{{role}}</span></h5></div>{{/each}}{{/with}}");
			var html=userData({parsedUsers:this.self.lazyLoadedRecords.users.users});
			this.self.dom.getTripById("t"+this.self.userStats.adminStats.users.users[((this.self.lazyLoadCount-1)*5)+4].id).insertAdjacentHTML('afterend',html);
			this.self.userStats.adminStats.users.users=this.self.userStats.adminStats.users.users.concat(this.self.lazyLoadedRecords.users.users);
			this.self.lazyLoadedRecords=null;
		}		  
    }

	lazyLoader(event){
		var element = event.target;
		if (element.scrollHeight - element.scrollTop === element.clientHeight)
		{
		   console.log('scrolled');
           this.self.lazyLoadCount=this.lazyLoadCount+1;
		   this.self.userStats.fetchStats(this.self.userStats.filter,this.self.lazyLoadCount).done(function(data){
			renderAdmin.currentRender.lazyLoadedRecords=data;
			renderAdmin.currentRender.writeUserStats(); 
		   });
		  
		}
	  
	}

	writeUsersUpdateForm(){
       var usersUpdateFormHtml="<form><div id=\"usersFullForm\" class=\"fullForm\"><div id=\"addUserBtn\" class=\"addBtn\" onClick=\"renderAdmin.currentRender.writeUserSubForm()\" title=\"Add another User\">+</div><button onclick=\"return renderAdmin.currentRender.addNewUsers()\" id=\"submitAddUsers\" class=\"greenBtn\">Add Users</button></div></form>";
       this.self.dom.getAddUserBox().innerHTML=usersUpdateFormHtml;
	   this.self.writeUserSubForm();
	}

	writeUserSubForm(){
		var div=document.createElement("div");
		var index=this.self.dom.getUsersFullForm().childElementCount-1;
		this.self.dom.getUsersFullForm().insertBefore(div,this.self.dom.getUsersFullForm().children[index-1]);
		var userSubForm="<div id=\"form"+index+"\"><label for=\"userids\">User name:</label><input type=\"text\" id=\"nameid"+index+"\" class=\"field\" name=\"nameid"+index+"\" required></input><br><br><label for=\"userids\">User email-id:</label><input type=\"text\" id=\"emailid"+index+"\" class=\"field\" name=\"emailid"+index+"\" required></input><br><br><label for=\"password\">Password:</label> <input type=\"password\" name=\"password\" id=\"password"+index+"\" class=\"field\"/><label for=\"userRole\">User role:</label> <select name=\"userRole\" id=\"userRoleid"+index+"\" class=\"select\"><option selected=\"true\" disabled=\"disabled\">Select user role</option><option value=\"Customer\">Customer</option><option value=\"Driver\">Driver</option><option value=\"Admin\">Admin</option></select></div><div id=\"remove"+index+"\" class=\"removeBtn\" onclick=\"renderAdmin.currentRender.deleteUserSubForm("+index+")\">-</div>";
		this.self.dom.getUsersFullForm().childNodes[index-1].innerHTML=userSubForm;
		this.self.dom.getUsersFullForm().childNodes[index-1].setAttribute("id","usersSubForm"+index);
		this.self.dom.getUsersFullForm().childNodes[index-1].setAttribute("class","usersSubForm");
	}

	deleteUserSubForm(id){
		this.self.dom.getUsersFullForm().removeChild(this.self.dom.getUsersSubForm(id));
	}

	addNewUsers(){
        console.log("adding Users");
		var usersToBeAdded={
			users:[]
		};
		var len=this.self.dom.getUsersFullForm().childElementCount;
		for(var i=0;i<len-2;i++){
			usersToBeAdded.users.push({
				"name":this.self.dom.getSubFormField("nameid",i+1).value,
				"email":this.self.dom.getSubFormField("emailid",i+1).value,
				"password":this.self.dom.getSubFormField("password",i+1).value,
				"role":this.self.dom.getSubFormField("userRoleid",i+1).value
			});
		}
		$.ajax({
			url: "/com.tomcat_hello_world/admin/AddUsers",
			type: "POST",
			async:false,
			contentType:"application/json; charset=utf-8",
			dataType:"json",
			data: JSON.stringify(usersToBeAdded)
		}).then((data,xhr)=>{console.log(xhr.status); return {data,xhr}}).done(function(response){
			console.log(response.data);
			alert("Users added successfully");
		}).fail(function(response){
			console.log(response);
			if(response.status==406){
				alert("Sorry....one or more of the users are already registered!");
			 }
			 else if(response.status==400){
				alert("Invalid input details"); 
			 }
			 else{
			   alert('Error while adding users...Please try again later!');
			 }  
		});
		this.self.userStats.fetchStats().done(function(data){
			renderAdmin.currentRender.userStats.adminStats=data;
			console.log(renderAdmin.currentRender.userStats.adminStats);
		    renderAdmin.currentRender.writeUserStats();
		    renderAdmin.currentRender.writeUsersUpdateForm();
		});
		return false;
		
	}
}



