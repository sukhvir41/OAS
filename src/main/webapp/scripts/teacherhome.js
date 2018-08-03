'use strict';

$(document).ready(function () {
    $(".action").click(function () {
        putAttendance($(this))
    });

    $("#refresh").click(function () {
        getAttendance();
    });
});


var putAttendance = function (data) {
    var lectureId = $("#lectureId").val();
    var studentId = data.val();
    var error = $("#error");
    if (lectureId.length > 0) {
        if (data.text() == "Absent") {

            $.ajax({
                url: "/OAS/teacher/ajax/putattendance",
                data: {
                    lectureId: lectureId,
                    studentId: studentId,
                    mark: false
                },
                method: "post",
                success: function (data) {
                    if (data == "true") {
                        getAttendance();
                        error.hide();
                    } else if (data == "false") {
                        error.show();
                    }
                },
                error: function () {
                    error.show();
                }
            });
        } else if (data.text() == "Present") {

            $.ajax({
                url: "/OAS/teacher/ajax/putattendance",
                data: {
                    lectureId: lectureId,
                    studentId: studentId,
                    mark: true
                },
                method: "post",
                success: function (data) {
                    if (data == "true") {
                        getAttendance();
                        error.hide();
                    } else if (data == "false") {
                        error.show();
                    }
                },
                error: function () {
                    error.show();
                }
            });
        }
    }
}

var getAttendance = function () {
    var presentTable = $("#present");
    var absentTable = $("#absent");
    var lectureId = $("#lectureId").val();
    var error = $("#error");
    var present = '<tr id="{{id}}"><td>{{rollNumber}}</td><td>{{name}}</td><td><button class="btn btn-danger action" id="{{id}}" value="{{id}}">Absent</button></td></tr>';
    var absent = '<tr id="{{id}}"><td>{{rollNumber}}</td><td>{{name}}</td><td><button class="btn btn-success action" id="{{id}}" value="{{id}}">Present</button></td></tr>';
    if (lectureId.length > 0) {
       $(".action").prop("disabled", true);
        $.ajax({
            url: "/OAS/teacher/getattendance",
            dataType: "json",
            data: {
                lectureId: lectureId
            },
            method: "post",
            success: function (data) {
                presentTable.empty();
                absentTable.empty();
                console.log(data.headcount);
                $("#headcount").text(''+data.headcount);
                $.each(data.present, function (i, student) {
                    presentTable.append((Mustache.render(present, student)));
                });

                $.each(data.absent, function (i, student) {
                    absentTable.append((Mustache.render(absent, student)));
                });
                error.hide();
                $(".action").click(function () {
                    putAttendance($(this))
                });
                $(".action").prop("disabled", false);
            },
            error: function () {
                error.show();
                $(".action").prop("disabled", false);
            }
        });
    }

}