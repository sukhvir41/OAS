'use strict';
//global variables here
var minimumSubjects;
var departments;
// this will show the details acccoding to the type of account selected 
$(document).ready(function () {
    getCourses();
    getDepartments();
    if ($('input[name=type]:checked', '#register').val() === 'student') {
        $('#student-details').show();
        $('#teacher-details').hide();
    } else {
        $('#student-details').hide();
        $('#teacher-details').show();

    }


    //validation function called on blur
    $("#password1").blur(function () {
        passwordCheck();
    });
    $("#password2").blur(function () {
        passwordCheck();
    });
    $("#firstname").blur(function () {
        firstNameCheck();
    });
    $("#lastname").blur(function () {
        lastNameCheck();
    });
    $("#email").blur(function () {
        emailTakenCheck();
    });
    $("#username").blur(function () {
        usernameTakenCheck();
    });
    $("#number").blur(function () {
        numberCheck();
    });
    $("#rollnumber").blur(function () {
        rollNumberCheck();
    });

    $("#course").change(function () {
        getClasses();
    });
    $("#class").change(function () {
        getSubjects()
    });

});

//what detail to show when the type of the account is changed
$('input[name=type]', '#register').click(function () {

    if ($('input[name=type]:checked', '#register').val() === 'student') {
        $('#student-details').fadeIn(500).show();
        $('#teacher-details').fadeOut(500).hide();

    } else {
        $('#student-details').fadeOut(500).hide();
        $('#teacher-details').fadeIn(500).show();
    }
});


var departmentCheck = function () {
    var error = $("#departmenterror");
    if ($('input[name=department]:checked', '#register').val().length >= 1) {
        error.hide();
        return true;
    } else {
        error.show();
        return false;
    }
}

var subjectCheck = function () {
    var error = $("#subjecterror");
    if ($('input[name=subject]:checked', '#register').val().length >= minimumSubjects) {
        error.hide();
        return true;
    } else {
        error.empty();
        var template = '<strong>Error!</strong> please select ' + minimumSubjects + 'subjects';
        error.append(template);
        error.show();
        return false;
    }
}

var getSubjects = function () {
    var course = $("#course").val();
    var classes = $("#class").val();
    var subjectDiv = $("#subjects");
    var templateElective = '<span class="checkbox"><label class="checkbox"><input type="checkbox" name="subject" value="{{id}}">{{name}}</label></span>';
    var templateNotElective = '<span class="checkbox"><label class="checkbox"><input type="checkbox" name="subject" checked disabled value="{{id}}">{{name}}<input type="hidden" name="subject" value="{{id}}"></label></span>';
    subjectDiv.empty();
    $.ajax({
        url: "ajax/getsubjects",
        dataType: "json",
        data: {
            course: course,
            class: classes
        },
        method: "post",
        success: function (data) {
            minimumSubjects = data.minimumsubjects;
            $.each(data.subjects, function (i, subject) {
                if (subject.elective) {
                    subjectDiv.append(Mustache.render(templateElective, subject));
                } else {
                    subjectDiv.append(Mustache.render(templateNotElective, subject));
                }
            });

        }
    });
}
var getDepartments = function () {
    var departmentDiv = $("#departments");
    var template = '<span class="checkbox"><label class="checkbox"><input type="checkbox" name="department" value="{{id}}">{{name}}</label></span>';
    $.ajax({
        url: "ajax/getdepartment",
        dataType: "json",
        method: "post",
        success: function (data) {
            $.each(data, function (i, department) {
                departmentDiv.append(Mustache.render(template, department));
            });
        }
    });
}

var getClasses = function () {
    var classes = $("#class");
    classes.empty();
    $.ajax({
        url: "ajax/getclass",
        dataType: "json",
        data: {
            course: $('#course').val()
        },
        method: "post",
        success: function (data) {
            $.each(data, function (i, jsonclass) {
                classes.append($('<option>', {
                    value: jsonclass.id,
                    text: jsonclass.name
                }));
            });
            getSubjects();
        }
    });

}
var getCourses = function () {
    var course = $("#course");
    $.ajax({
        url: "ajax/getcourse",
        dataType: "json",
        method: "post",
        success: function (data) {
            $.each(data, function (i, jsoncourse) {
                course.append($('<option>', {
                    value: jsoncourse.id,
                    text: jsoncourse.name
                }));
            });
            getClasses();
        }
    });

}

//validation methods to call whem submit in clicked
var submitCheck = function () {
    if ($('input[name=type]:checked', '#register').val() === 'student') {
        return passwordCheck() && firstNameCheck() && lastNameCheck() && emailTakenCheck() && numberCheck() && subjectCheck() && rollNumberCheck();
    } else {
        return passwordCheck() && firstNameCheck() && lastNameCheck() && emailTakenCheck() && numberCheck() && departmentCheck();
    }
}


//this methods checks if the values entered is a 10 digot number or not
var numberCheck = function () {
    var number = $("#number").val();
    var error = $("#numbererror");
    var regex = /\d{10}/g;
    if (regex.test(number)) {
        error.hide();
        return true;
    } else {
        error.show();
        return false;
    }
}

// this method makes na ajax call to the server to check if the username is taken or not
//calls the usernameCheck method first to check if the username entered is valid not not  
var usernameTakenCheck = function () {
    var error = $("#usernametakenerror");
    var check;
    if (usernameCheck()) {
        $.ajax({
            url: "ajax/checkusername",
            data: {
                username: $('#username').val()
            },
            method: "post",
            success: function (responseText) {
                if (responseText === "false") {
                    error.show();
                    check = false;
                } else {
                    error.hide();
                    check = true;
                }
            }
        });
        return check;
    } else {
        return false;
    }

}

//this method checks if the username entred is valid or not
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

}

//this method checks if the email id is taken or not 
var emailTakenCheck = function () {
    var error = $("#emailtakenerror");
    var check;
    if (emailCheck()) {
        $.ajax({
            url: "ajax/checkemail",
            data: {
                email: $('#email').val()
            },
            method: 'post',
            success: function (responseText) {
                if (responseText === "false") {
                    error.show();
                    check = false;
                } else {
                    error.hide();
                    check = true;
                }
            }
        });
        return check;
    } else {
        return false;
    }

}

//this method check if the email entred is valid not not
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
}

//this method checks if the entred firstname is valid or not
var firstNameCheck = function () {
    var firstName = $("#firstname");
    var regex = /\w[a-z]|[A-z]+/g;
    var error = $("#firstnameerror");
    if (firstName.val() === "" || !regex.test(firstName.val())) {
        error.show();
        return false;
    } else {
        error.hide();
        return true;
    }
}

//this method checks if the entred lastname is valid or not
var lastNameCheck = function () {
    var lastName = $("#lastname");
    var regex = /\w[a-z]|[A-z]+/g;
    var error = $("#lastnameerror");
    if (lastName.val() === "" || !regex.test(lastName.val())) {
        error.show();
        return false;
    } else {
        error.hide();
        return true;
    }
}


//this method checks if the entred password and confirm password field matches and also checks for other validation  
var passwordCheck = function () {

    var password1 = $("#password1");
    var password2 = $("#password2");
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


}

var rollNumberCheck = function () {
    var rollNumber = $("#rollnumber").val();
    var error = $("#rollnumbererror");
    if ($('input[name=type]:checked', '#register').val() === 'student') {
        if (rollNumber >= 1 && rollNumber <= 999) {
            error.hide();
            return true;
        } else {
            error.show();
            return false;
        }
    } else {
        error.hide();
        return true;

    }

}
