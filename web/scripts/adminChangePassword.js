'use strict';


$(document).ready(function(){



});



var checkPasswordLength = function(password, error){
	if (password.length >= 8 && password.length <=40) {
		error.hide();
		return true;
	}else{
		error.show();
		return false;
	}
}

var checkPasswordsMatch = function(password1,password2, error){

	if (password1 === password2) {
		error.hide();
		return true;
	}else{
		error.show();
		return false;
	}

}

var isInputValid = function(){
	var oldPassword = $("#oldpassword");
	var newPassword = $("#newpassword");
	var renewPassword = $("#renewpassword");
	var passwordLengthError = $("#passwordLengthError");
	var newPasswordLengthError = $("#newPasswordLengthError");
	var passwordMatchError = $("#passwordMatchError");

	return (checkPasswordLength(oldPassword.val(),passwordLengthError) && 
	checkPasswordLength(newPassword.val(),newPasswordLengthError) && 
	checkPasswordsMatch(newPassword.val(),renewPassword.val(),passwordMatchError));
}