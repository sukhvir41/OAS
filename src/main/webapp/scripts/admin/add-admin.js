'use strict';

$(document).ready(function () {

    $('#adminForm').validate({
        rules: {
            username: {
                required: true,
                minlength: 8,
                maxlength: 20,
                remote: {
                    url: "/OAS/ajax/checkusername",
                    type: "post"
                },
            },
            email: {
                required: true,
                email: true,
                remote: {
                    url: "/OAS/ajax/checkemail",
                    type: "post"
                }
            },
            password: {
                required: true,
                minlength: 8,
                maxlength: 40
            },
            repassword: {
                required: true,
                minlength: 8,
                maxlength: 40,
                equalTo: "#password"
            }
        },
        messages: {
            username: {
                required: "Please enter username",
                remote: $.validator.format("Username: {0} is taken"),
                minlength: $.validator.format("Enter at least {0} characters"),
                maxlength: $.validator.format("At max {0} characters")
            },
            email: {
                remote: $.validator.format("Email: {0} is in use")
            }
        }
    });

});


var usernameTakenCheck = function () {
    var errorDialog = $("#usernametakenerror");
    var check;
    if (usernameCheck()) {
        $.ajax({
            url: "/OAS/ajax/checkusername",
            data: {
                username: $('#username').val()
            },
            method: "post",
            success: function (responseText) {
                if (responseText === true) {
                    errorDialog.hide();
                    return true;
                } else {
                    errorDialog.show();
                    return false;
                }
                //presponse(responseText);
            },
            error: function () {
                //perror(false);
                errorDialog.show();
                return false;
            },
            async: false
        });
        /*try{   
            var data = await promise;
            
            if(data ===true){
                errorDialog.hide();
                return true;
            }else{
                errorDialog.show();
                return false;
            }
        }catch(err){
            errorDialog.show();
            return false;
        }
    }else{
        return false;
    }*/
    }
};


var emailTakenCheck = async function () {
    var error = $("#emailtakenerror");
    var check;
    if (emailCheck()) {

        var promise = await new Promise((presponse, perror) => {
            $.ajax({
                url: "/OAS/ajax/checkemail",
                data: {
                    email: $('#email').val()
                },
                method: 'post',
                success: function (responseText) {
                    presponse(responseText)

                },
                error: function () {
                    perror(false);
                }
            });
        });
        try {
            var data = promise;
            if (data === true) {
                error.hide();
                return true;
            } else {
                error.show();
                return false;
            }
        } catch (err) {
            error.show()
            return false;
        }

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
    var promise = emailTakenCheck() && passwordCheck() && usernameTakenCheck();
    return promise === true;

};