'use strict';

$(document).ready(function () {

    getCourses()
        .then(function () {
            return getClasses();
        })
        .then(function () {
            getSubjects();
        });
    getDepartments();

    if ($('input[name=type]:checked', '#register').val() === 'student') {
        $('#student-details').show();
        $('#teacher-details').hide();
    } else {
        $('#student-details').hide();
        $('#teacher-details').show();
    }

    $("#course").change(function () {
        getClasses()
            .then(function () {
                getSubjects();
            });
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

    $.validator.addMethod("subjectCheck", function (value, elemecnt) {
        return this.optional(element) || subjectCheck();
    }, "Please select the subjects");


    $.validator.addMethod("departmentCheck", function (value, element) {
        return departmentCheck();
    }, "Please select a department");



    $("#register").validate({
        rules: {
            firstname: {
                required: true,
            },
            lastname: {
                required: true,
            },
            email: {
                required: true,
                email: true,
                remote: {
                    url: "ajax/check-email",
                    type: "post",
                    data: {
                        email: function () {
                            return $('#email').val();
                        },
                        jqueryValidator: true
                    }
                }
            },
            username: {
                required: true,
                minlength: 8,
                maxlength: 20,
                remote: {
                    url: "ajax/check-username",
                    type: "post",
                    data: {
                        username: function () {
                            return $('#username').val();
                        },
                        jqueryValidator: true
                    }
                },
            },
            password: {
                required: true,
                minlength: 8,
                maxlength: 40,
            },
            repassword: {
                required: true,
                minlength: 8,
                maxlength: 100,
                equalTo: '#password',
            },
            number: {
                minlength: 10,
                maxlength: 10,
                required: true,
                number: true
            },
            rollnumber: {
                minlength: 1,
                maxlength: 3,
                number: true,
                required: function (element) {
                    return ($('input[name=type]:checked', '#register').val() === 'student');
                }
            },
            course: {
                required: function (element) {
                    return $('input[name=type]:checked', '#register').val() === 'student';
                }
            },
            classroom: {
                required: function (element) {
                    return $('input[name=type]:checked', '#register').val() === 'student';
                }
            },
            subjects: {
                subjectCheck: true,
                required: function (element) {
                    return $('input[name=type]:checked', '#register').val() === 'student';
                }
            },
            department: {
                departmentCheck: true
            }
        },
        messages: {
            firstname: {
                required: 'Please enter your first name'
            },
            lastname: {
                required: 'Please enter your last name'
            },
            email: {
                required: 'Please enter your email address',
                email: 'Enter a vaild email address',
                remote: $.validator.format('Email: {0} is in use')
            },
            username: {
                required: "Please enter your username",
                remote: $.validator.format("Username: {0} is taken"),
                minlength: $.validator.format("Enter at least {0} characters"),
                maxlength: $.validator.format("At max {0} characters")
            },
            rollnumber: {
                required: 'Please enter your valid roll number',
            },
            course: {
                required: 'Please select a course',
            },
            classroom: {
                required: 'Please select a class',
            },
        }
    });

});


var departmentCheck = function () {

    if ($('input[name=type]:checked', '#register').val() !== 'teacher') {
        return true;
    }

    if ($('input[name=department]:checked', '#register').length >= 1) {
        $('input[name=department]').closest('.col-md-12').css({
            "border": "unset"
        });

        return true;
    } else {
        $('input[name=department]').closest('.col-md-12').css({
            "border": "1px #a94442 solid"
        });

        $('#department-error').remove();

        $($('input[name=department]').closest('.row'))
            .append('<label id="department-error" class="error" for="department" style="display: inline-block;"></label>')

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
        //var template = '<strong>Error!</strong> please select ' + minimumSubjects + ' subjects';
        //error.append(template);
        //error.show();
        return false;
    }
}

var getSubjects = function () {
    var classes = $("#classroom").val();
    var subjectDiv = $("#subjects"); // will have subjecets that are not elective
    var electiveSubjectsDiv = $('#electiveSubjects');
    subjectDiv.empty();
    electiveSubjectsDiv.empty();

    var subjectTemplate = "<label>{{name}} {{#show}}<span>,</span>{{/show}} <input type='hidden' name='subjects' value={{id}}/></label>"

    $.ajax({
        url: "ajax/get-subjects",
        dataType: "json",
        data: {
            classroom: classes
        },
        method: "post",
        success: function (data) {
            if (data.status === "success") {
                var electiveSubjects = [];
                var subjects = [];
                $.each(data.data, function (i, subject) {
                    if (subject.elective) {
                        electiveSubjects.push(subject);
                    } else {
                        subjects.push(subject);
                    }
                });

                $.each(subjects, function (i, subject) {
                    subject.show = (i < subjects.length - 1)
                    subjectDiv.append(Mustache.render(subjectTemplate, subject));
                });

                multiSelect('#electiveSubjects', 'subjects', '', electiveSubjects, 200);
            }
        }
    });
}

var getDepartments = function () {
    $.ajax({
        url: "ajax/get-all-departments",
        dataType: "json",
        method: "post",
        success: function (result) {
            if (result.status === "success") {
                multiSelect('#departments', 'department', 'Departments', result.data, 200);
            }
        }
    });
}

var getClasses = function () {
    var classroom = $("#classroom");
    var selectTemplate = '<option value="{{id}}" data-minimum-subjects="{{minimum_subjects}}">{{name}}</option>';
    classroom.empty();
    return $.ajax({
        url: "ajax/get-classrooms",
        dataType: "json",
        data: {
            course: $('#course').val()
        },
        method: "post",
        success: function (data) {
            if (data.status === "success") {
                $.each(data.data, function (i, jsonclass) {
                    classroom.append(Mustache.render(selectTemplate, jsonclass));
                });
            }
        }
    });

}
var getCourses = function () {
    var course = $("#course");
    var selectTemplate = '<option value="{{id}}">{{name}}</option>';
    return $.ajax({
        url: "ajax/get-all-courses",
        dataType: "json",
        method: "post",
        success: function (data) {
            if (data.status === "success") {
                $.each(data.data, function (i, jsoncourse) {
                    course.append(Mustache.render(selectTemplate, jsoncourse));
                });
            }
        }
    });

}