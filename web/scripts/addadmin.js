'use strict';

$(document).ready(function () {
    $("#email").blur(function () {
    	console.log(emailTakenCheck());
        
    });
    $("#username").blur(function () {
    	console.log(usernameTakenCheck());
        
    });
    $("#password").blur(function () {
    	console.log(passwordCheck());
        
    });
    $("#repassword").blur(function () {
    	console.log(passwordCheck());
        
    });
    $("#adminform").submit(function () {
        submitCheck();
    });
});


var usernameTakenCheck = function () {
    var error = $("#usernametakenerror");
    if (usernameCheck()) {
        $.ajax({
            url: "/OAS/ajax/checkusername",
            data: {
                username: $('#username').val()
            },
            method: "post",
            success: function (responseText) {
                if (responseText === "false") {
                    error.show();
                    return false;
                } else {
                    error.hide();
                    return true;
                }
            }
        });
    }
};

var emailTakenCheck = function () {
    var error = $("#emailtakenerror");
    if (emailCheck()) {
        $.ajax({
            url: "/OAS/ajax/checkemail",
            data: {
                email: $('#email').val()
            },
            method: 'post',
            success: function (responseText) {
                if (responseText === "false") {
                    error.show();
                    return false;
                } else {
                    error.hide();
                    return true;
                }
            }
        });
    }
};

var emailCheck = function () {
    var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,6})?$/;
    var email = $("#email").val();
    var error = $("#emailerror");
    if (email.length === 0) {
        error.show();
        return false;
    } else if (emailReg.test(email)) {
        error.hide();
        return true;
    } else {
        error.show();
        return false;
    }
};

var passwordCheck = function () {

    var password1 = $("#password");
    var password2 = $("#repassword");
    var passwordError = $("#passworderror");
    var passwordEmpty = $("#passwordempty");
    var passwordShort = $("#passwordshort");

    if (password1.val() === "" && password2.val() === "") {
        passwordEmpty.show();
        passwordError.hide();
        passwordShort.hide();
        return false;
    } else if (password1.val() !== password2.val() || password1.val() === "" || password2.val() === "") {
        passwordError.show();
        passwordEmpty.hide();
        passwordShort.hide();
        return false;
    } else {
        if (password1.val().length > 7 && password1.val().length < 41) {
            passwordError.hide();
            passwordEmpty.hide();
            passwordShort.hide();
            return true;
        } else {
            passwordError.hide();
            passwordEmpty.hide();
            passwordShort.show();
            return false;
        }
    }
};

var usernameCheck = function () {
    var username = $("#username").val();
    var regex = /([a-z]|[A-Z])\w+/;
    var errorLength = $("#usernamelengtherror");
    var error = $("#usernameerror");
    if (username === "" || username.length < 8 || username.length > 20) {
        errorLength.show();
        error.hide();
        return false;
    } else if (regex.test(username) === false) {
        errorLength.hide();
        error.show();
        return false;
    } else {
        error.hide();
        errorLength.hide();
        return true;
    }

};

var submitCheck = function () {
    return emailTakenCheck() && passwordCheck() && usernameTakenCheck();
};