'use strict';
var calledFunction = 1;
$(document).ready(function () {
    getClasses();

    $("#course").change(function () {
        getClasses();
    });

    $("#classroom").change(function () {
        getSubjects();
    });

    $("#search").click(function () {
        searchStudents();
    });

    $("#searchname").click(function () {
        searchByName();
    });

    $(".action").click(function () {
        activateOrDeacticate($(this));
    });

});


var getClasses = function () {
    var course = $("#course").val();
    var classRoom = $("#classroom");
    var template = '<option value = "{{id}}">{{name}}</option>';
    $.ajax({
        url: "/OAS/ajax/getclass",
        dataType: "json",
        data: {
            course: course
        },
        method: "post",
        success: function (data) {
            classRoom.empty();
            $.each(data, function (i, classdata) {
                classRoom.append(Mustache.render(template, classdata));
            });
            getSubjects();

        }
    });
}

var getSubjects = function () {
    var classRoom = $("#classroom").val();
    var template = '<option value = "{{id}}">{{name}}</option>';
    var subject = $("#subject");

    if (classRoom !== null) {
        $.ajax({
            url: "/OAS/ajax/getsubjectsofclass",
            dataType: "json",
            data: {
                classroom: classRoom
            },
            method: "post",
            success: function (data) {
                subject.empty();
                subject.append('<option value = "all">All</option>');
                $.each(data, function (i, classdata) {
                    subject.append(Mustache.render(template, classdata));
                });


            }
        });
    } else {
        subject.empty();
    }
}

var searchStudents = function () {
    var error = $("#error");
    var success = $("#success");
    var filter = $('input[name=filter]:checked').val();
    var classRoom = $("#classroom").val();
    var subject = $("#subject").val();
    var templateVerified = '<tr><td>{{id}}</td><td><a href="/OAS/admin/students/detailstudent?studentId={{id}}">{{name}}</a></td><td>{{number}}</td><td>{{email}}</td><td>{{classroom}}</td><td>{{rollnumber}}</td><td>{{subjects}}</td><td>{{verified}}</td><td><button class="btn btn-danger mr-xs mb-sm action" value="{{id}}">deverify</button></td></tr>';
    var templateNotVerified = '<tr><td>{{id}}</td><td><a href="/OAS/admin/students/detailstudent?studentId={{id}}">{{name}}</a></td><td>{{number}}</td><td>{{email}}</td><td>{{classroom}}</td><td>{{rollnumber}}</td><td>{{subjects}}</td><td>{{verified}}</td><td><button class="btn btn-success mr-xs mb-sm action" value="{{id}}">verify</button></td></tr>';
    var body = $("#tablebody");
    error.hide();
    success.hide();
    $.ajax({
        url: "/OAS/admin/ajax/searchstudent",
        dataType: "json",
        data: {
            classroom: classRoom,
            subject: subject,
            filter: filter
        },
        method: "post",
        success: function (data) {
            body.empty();
            calledFunction = 1;
            $.each(data, function (i, studentdata) {
                if (studentdata.verified) {
                    body.append(Mustache.render(templateVerified, studentdata));
                } else {
                    body.append(Mustache.render(templateNotVerified, studentdata));
                }
            });
            $(".action").click(function () {
                activateOrDeacticate($(this));
            });
            success.show();
        }
    });

}

var searchByName = function () {
    console.log("called")
    var name = $("#studentname").val();
    var error = $("#error");
    var success = $("#success");
    var body = $("#tablebody");
    var templateVerified = '<tr><td>{{id}}</td><td><a href="/OAS/admin/students/detailstudent?studentId={{id}}">{{name}}</a></td><td>{{number}}</td><td>{{email}}</td><td>{{classroom}}</td><td>{{rollnumber}}</td><td>{{subjects}}</td><td>{{verified}}</td><td><button class="btn btn-danger mr-xs mb-sm action" value="{{id}}">deverify</button></td></tr>';
    var templateNotVerified = '<tr><td>{{id}}</td><td><a href="/OAS/admin/students/detailstudent?studentId={{id}}">{{name}}</a></td><td>{{number}}</td><td>{{email}}</td><td>{{classroom}}</td><td>{{rollnumber}}</td><td>{{subjects}}</td><td>{{verified}}</td><td><button class="btn btn-success mr-xs mb-sm action" value="{{id}}">verify</button></td></tr>';
    error.hide();
    success.hide();
    if (name.length >= 1) {
        $.ajax({
            url: "/OAS/admin/ajax/students/searchstudentbyname",
            dataType: "json",
            data: {
                name: name
            },
            method: "post",
            success: function (data) {
                body.empty();
                calledFunction = 2;
                $.each(data, function (i, studentdata) {
                    if (studentdata.verified) {
                        body.append(Mustache.render(templateVerified, studentdata));
                    } else {
                        body.append(Mustache.render(templateNotVerified, studentdata));
                    }
                });
                $(".action").click(function () {
                    activateOrDeacticate($(this));
                });
                success.show();
            }
        });
    }

}

var activateOrDeacticate = function (button) {
    var action = button.text();
    ;
    var studentId = button.val();
    if (action === "verify" || action === "deverify") {
        $.ajax({
            url: "/OAS/admin/ajax/students/activateordeactivatestudent",
            data: {
                studentId: studentId,
                action: action
            },
            method: "post",
            success: function (data) {
                console.log("called");
                if (data === "true") {
                    if (calledFunction === 1) {
                        searchStudents();
                    } else if (calledFunction == 2) {
                        searchByName();
                    }
                }
            }
        });
    }

}