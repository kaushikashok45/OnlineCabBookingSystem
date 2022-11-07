window.onload=function(){
    var email = new URLSearchParams(window.location.search).get("usr");
    var HTMLelement=document.getElementById("email");
    HTMLelement.value=email;
    if(email!=null){
        document.getElementById("pass").focus();
    }
    else{
        HTMLelement.focus();
    }
    document.getElementById()
}