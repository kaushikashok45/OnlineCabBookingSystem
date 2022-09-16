window.onload = function() {
    document.getElementById("name").focus();
  };

function validateEmail(field,query){
    if(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(query)){
        
        document.getElementById("signup").disabled=false;
        $.get('EmailExistCheck',{
            email:query
        },function(responseText){
            if(responseText!=null){
                $('#emailExistCheck').text(responseText);
            }
        });
    }
    else{
        document.getElementById(field).innerHTML="<p style=\"color:red;font-family:sans-serif;\">Invalid email-id!</p>";
        document.getElementById("signup").disabled=true;
    }
}   

function validatePassword(field,query){
    var passw=   /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,15}$/;
    if(query.match(passw)){
        document.getElementById(field).innerHTML="<p style=\"font-family:sans-serif;\">Password Strength:<span style=\"color:green;font-family:sans-serif;\">Strong</span></p>";
        document.getElementById("signup").disabled=false;
    }
    else{
        document.getElementById(field).innerHTML="<p style=\"font-family:sans-serif;\">Password Strength:<span style=\"color:red;font-family:sans-serif;\">Weak</span></p>";
        document.getElementById("signup").disabled=true;
    }
}