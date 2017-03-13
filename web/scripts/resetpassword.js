'use strict';

$(document).ready(function () {

    $("#password").blur(function () {
        checkPassword();
    });
    $("#confirmpassword").blur(function () {
        checkPassword();
    });
});

var submitCheck = function () {
    return checkPassword();
}

var checkPassword = function () {
    var password = $("#password");
    var confirmPassword = $("#confirmpassword");
    var error = $("#passworderror");
    var errorLength = $("#passwordlengtherror");

    if (password.val() !== "") {
        if (password.val().length >= 8 && password.val().length <= 40) {
            if (password.val() === confirmPassword.val()) {
                error.hide();
                errorLength.hide();
                return true;
            } else {
                errorLength.hide();
                error.show();
                return false;
            }
        } else {
            errorLength.show();
            error.hide();
            return false;
        }
    } else {
        errorLength.hide();
        error.show();
        return false;
    }

}
