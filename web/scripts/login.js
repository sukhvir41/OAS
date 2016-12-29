'use strict';

//this method checks if the username and password entred is empty or not 
var loginBlankCheck = function(){
	var username = $("#username");
	var password = $("#password");
	var error = $("#loginblank");
	var errorLenght = $("#loginError");
	if(username.val()===""||password.val()===""){
		errorLenght.hide();
		error.show();
		return false;
	}else{
		if (password.val().length >=8 && password.val().length<=40) {
			errorLenght.hide();
			error.hide();
			return true;
		}else{
			errorLenght.show();
			error.hide();
			return false;
		}
	}
}


//this method is called when the user clicks on the login buttom
var submitCheck = function(){
	return loginBlankCheck();
}