$(".del").click(function(event){
  event.stopPropagation();  
});

window.onload=function(){
   if(localStorage.getItem("Users")!=null){
      var display=document.getElementById("accounts");
      var users=JSON.parse(localStorage.getItem("Users"));
      var ans="<h4 style=\"font-weight:bold;font-family:sans-serif;color:black;\">Users</h4><br><ul style=\"list-style:none;display:flex;align-items: center;justify-content: center;flex-wrap:wrap;\">";
      for(var i=0;i<users.length;i++){
         ans=ans+"<div class=\"flex-item\" onClick=\"selector(\'"+users[i]+"\')\"><li><a id=\""+users[i]+"\""+" href=\"login?usr="+users[i]+"\">"+users[i]+"</a>&nbsp;&nbsp&nbsp;<button class=\"del\" style=\"border-color:white;\""+" value=\""+users[i]+"\" onclick=\"removeUser(this.value,event)\">x</button></li></div>";
      }
      ans=ans+"</ul>";
      display.innerHTML=ans;
   }
};

function removeUser(user,event){
    console.log("Yes");
    event.stopPropagation();
    usrs=JSON.parse(localStorage.getItem("Users"));
    const index=usrs.indexOf(user);
    if(index>-1){
        usrs.splice(index,1);
    }
    localStorage.removeItem("Users");
    localStorage.setItem("Users",JSON.stringify(usrs));
    location.reload();
}

function selector(usr){
	document.getElementById(usr).click();
}

