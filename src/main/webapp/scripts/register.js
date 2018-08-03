'use strict';

var minimumSubjects;

$(document).ready(function(){

    getCourses();
    getDepartments();

    if ($('input[name=type]:checked', '#register').val() === 'student') {
        $('#student-details').show();
        $('#teacher-details').hide();
    } else {
        $('#student-details').hide();
        $('#teacher-details').show();
    }

    $("#course").change(function () {
        getClasses();
    });
    $("#class").change(function () {
        getSubjects();
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

    $.validator.addMethod("subjectCheck",function(value,elemecnt){
        return this.optional(element) || subjectCheck();
    },"Please select the subjects");


    $.validator.addMethod("departmentCheck",function(value,element){
        return this.optional(element) || departmentCheck();
    },"Please select at least one department");



    $("#register").validate({
        rules:{
            firstname: {
                required: true,
            },
            lastname: {
                required: true,
            },
            email:{
                required: true,
                email: true,
                remote: {
                    url: "ajax/checkemail",
                    type: "post"
                }
            },
            username: {
                required: true,
                minlength: 8,
                maxlength: 20,
                remote:{
                    url: "ajax/checkusername",
                    type: "post"
                },    
            },
            password:{
                required: true,
                minlength: 8,
                maxlength: 40,   
            },
            repassword:{
                required: true,
                minlength: 8,
                maxlength: 40,
                equalTo: '#password',
            },
            number:{
                minlength:10,
                maxlength:10,
                required: true,
                number: true
            },
            rollnumber: {
                minlength: 1,
                maxlength: 3,
                number: true,
                required: function(element){
                    return ($('input[name=type]:checked', '#register').val() === 'student');
                }
            },
            course: {
                required: function(element){
                    return $('input[name=type]:checked', '#register').val() === 'student';
                }
            },
            class: {
                required: function(element){
                    return $('input[name=type]:checked', '#register').val() === 'student';
                }
            },
            subjects: {
                subjectCheck: true,
                required:  function(element){
                    return $('input[name=type]:checked', '#register').val() === 'student';
                }
            },
            department: {
                departmentCheck: true,
                required:  function(element){
                    return $('input[name=type]:checked', '#register').val() === 'teacher';
                }
            } 
        },
        messages: {
            firstname:{
                required: 'Please enter your first name'
            },
            lastname:{
                required: 'Please enter your last name'
            },
            email:{
                required:'Please enter your email address',
                email: 'Enter a vaild email address',
                remote: $.validator.format('Email: {0} is in use')
            },
            username:{
                required: "Please enter your username",
                remote: $.validator.format("Username: {0} is taken"),
                minlength: $.validator.format("Enter at least {0} characters"),
                maxlength: $.validator.format("At max {0} characters") 
            },
            rollnumber:{
                required: 'Please enter your valid roll number',
            },
            course:{
                required: 'Please select a course',
            },
            class:{
                required: 'Please select a class',
            },
            subjects:{
                required: 'Please slecct the minimum subjects: '+ minimumSubjects,
            },
            department:{
                required: 'Please select at least one department'
            }

        }
    });

});


var departmentCheck = function () {
    var error = $("#departmenterror");
    if ($('input[name=department]:checked', '#register').length >= 1) {
        error.hide();
        return true;
    } else {
        error.show();
        return false;
    }
}

var subjectCheck = function () {
    var error = $("#subjecterror");
    if ($('input[name=subject]:checked', '#register').length >= minimumSubjects) {
        error.hide();
        return true;
    } else {
        error.empty();
        var template = '<strong>Error!</strong> please select ' + minimumSubjects + ' subjects';
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
