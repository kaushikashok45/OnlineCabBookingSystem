$( document ).ready(function(){
	console.log("hi");
 var rawUser=document.getElementById("email"),usr;
 usr=rawUser.value;
 console.log(usr);
 if(localStorage.getItem("Users")!=null){
    if(!(checkIfUserExists(usr))){
        let usrs=JSON.parse(localStorage.getItem("Users"));
        usrs.push(usr);
        localStorage.removeItem("Users");
        localStorage.setItem("Users",JSON.stringify(usrs));
    }
 }
 else{
    var items=[];
    items.push(usr);
    localStorage.setItem("Users",JSON.stringify(items));
 }
});

function checkIfUserExists(usr){
    const items=localStorage.getItem("Users");
    let userExists=false;
    if(items){
        const itemsData=JSON.parse(items);
        userExists=itemsData.find(item=>item===usr);
    }
    return userExists;
}