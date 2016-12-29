'use strict';

//this method checks if the username and password entred is empty or not 
var loginBlankCheck = function(){
	var username = $("#username");
	var password = $("#password");
	var error = $("#loginblank");
	if(username.val()===""||password.val()===""){
		error.show();
		return false;
	}else{
		error.hide();
		return true;
	}
}


//this method is called when the user clicks on the login buttom
var submitCheck = function(){
	return loginBlankCheck();
}