'use strict';
var calledFunction = 1;
$(document).ready(function () {

    $("#searchname").click(function () {
        searchByName();
    });

    $("#search").click(function () {
        search();
    });

    $(".action").click(function () {
        activateOrDeacticate($(this));
    });
});

//function value 2
var searchByName = function () {
    var error = $("#error");
    var success = $("#success");
    var name = $("#teachername").val();
    var body = $("#tablebody");
    var verified = '<tr><td>{{id}}</td><td><a href="/OAS/admin/teachers/detailteacher?teacherId={{id}}">{{name}}</a></td><td>{{number}}</td><td>{{email}}</td><td>{{hodof}}</td><td>{{classteacher}}</td><td>{{departments}}</td><td>{{verified}}</td></td><td><button class="btn btn-danger mr-xs mb-sm action" value="{{id}}">deverify</button></td></tr>';
    var notVerified = '<tr><td>{{id}}</td><td><a href="/OAS/admin/teachers/detailteacher?teacherId={{id}}">{{name}}</a></td><td>{{number}}</td><td>{{email}}</td><td>{{hodof}}</td><td>{{classteacher}}</td><td>{{departments}}</td><td>{{verified}}</td></td><td><button class="btn btn-success mr-xs mb-sm action" value="{{id}}">verify</button></td></tr>';
    error.hide();
    success.hide();
    if (name.length >= 1) {
        $.ajax({
            url: "/OAS/admin/ajax/teachers/searchteacherbyname",
            dataType: "json",
            data: {
                name: name
            },
            method: "post",
            success: function (data) {
                body.empty();
                $.each(data, function (i, teacher) {
                    calledFunction = 2;
                    if (teacher.verified) {
                        body.append(Mustache.render(verified, teacher));
                    } else {
                        body.append(Mustache.render(notVerified, teacher));
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

//function value 1
var search = function () {
    var error = $("#error");
    var success = $("#success");
    var departmentId = $("#department").val();
    var filter = $('input[name=filter]:checked').val();
    var body = $("#tablebody");
    var verified = '<tr><td>{{id}}</td><td><a href="/OAS/admin/teachers/detailteacher?teacherId={{id}}">{{name}}</a></td><td>{{number}}</td><td>{{email}}</td><td>{{hodof}}</td><td>{{classteacher}}</td><td>{{departments}}</td><td>{{verified}}</td></td><td><button class="btn btn-danger mr-xs mb-sm action" value="{{id}}">deverify</button></td></tr>';
    var notVerified = '<tr><td>{{id}}</td><td><a href="/OAS/admin/teachers/detailteacher?teacherId={{id}}">{{name}}</a></td><td>{{number}}</td><td>{{email}}</td><td>{{hodof}}</td><td>{{classteacher}}</td><td>{{departments}}</td><td>{{verified}}</td></td><td><button class="btn btn-success mr-xs mb-sm action" value="{{id}}">verify</button></td></tr>';
    error.hide();
    success.hide();
    $.ajax({
        url: "/OAS/admin/ajax/teachers/searchteacher",
        dataType: "json",
        data: {
            departmentId: departmentId,
            verified: filter
        },
        method: "post",
        success: function (data) {
            calledFunction = 1;
            body.empty();
            $.each(data, function (i, teacher) {
                if (teacher.verified) {
                    body.append(Mustache.render(verified, teacher));
                } else {
                    body.append(Mustache.render(notVerified, teacher));
                }
            });
            $(".action").click(function () {
                activateOrDeacticate($(this));
            });
            success.show();
        }
    });
}

var activateOrDeacticate = function (button) {
    var action = button.text();
    ;
    var teacherId = button.val();
    console.log("called");
    if (action === "verify" || action === "deverify") {
        console.log("called");
        $.ajax({
            url: "/OAS/admin/ajax/teachers/activateordeactivateteacher",
            data: {
                teacherId: teacherId,
                action: action
            },
            method: "post",
            success: function (data) {
                console.log("called");
                if (data === "true") {
                    if (calledFunction === 1) {
                        search();
                    } else if (calledFunction == 2) {
                        searchByName();
                    }
                }
            }
        });
    }
}