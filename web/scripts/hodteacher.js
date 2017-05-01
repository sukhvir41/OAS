'use strict';

var searched = false;
$(document).ready(function(){
	$(".action").click(function () {
        activateOrDeacticate($(this));
    });

    $("#searchname").click(function () {
        searchTeacherByName();
    });

    $("#showall").click(function () {
        showAll();
    });

});


var showAll = function(){
	var error = $("#error");
	var success = $("#success");
	var verified = '<tr><td>{{id}}</td><td><a href="/OAS/teacher/hod/teachers/detailteacher?teacherId={{id}}">{{name}}</a></td><td>{{number}}</td><td>{{email}}</td><td>{{hodof}}</td><td>{{classteacher}}</td><td>{{departments}}</td><td>{{verified}}</td></td><td><button class="btn btn-danger mr-xs mb-sm action" value="{{id}}">deverify</button></td></tr>';
	var notVerified = '<tr><td>{{id}}</td><td><a href="/OAS/teacher/hod/teachers/detailteacher?teacherId={{id}}">{{name}}</a></td><td>{{number}}</td><td>{{email}}</td><td>{{hodof}}</td><td>{{classteacher}}</td><td>{{departments}}</td><td>{{verified}}</td></td><td><button class="btn btn-success mr-xs mb-sm action" value="{{id}}">verify</button></td></tr>';
	var tableBody = $("#tablebody");
	error.hide();
	success.hide();

	$.ajax({
		url: "/OAS/teacher/hod/ajax/getteachers",
		dataType: "json",
		method: "post",
		success: function (data) {
			tableBody.empty();
			searched = false;
			$.each(data, function (i, teacher) {
				if (teacher.verified) {
					tableBody.append(Mustache.render(verified, teacher));
				} else {
					tableBody.append(Mustache.render(notVerified, teacher));
				}
			});
			$(".action").click(function () {
				activateOrDeacticate($(this));
			});
			success.show();
		},
		error: function(){
			tableBody.empty();
			error.show();
		}
	});
}

var activateOrDeacticate = function (button) {
	var action = button.text();
	var teacherId = button.val();
	var error = $("#error");
	var success = $("#success");
	error.hide();
	success.hide();
	console.log("called");
	if (action === "verify" || action === "deverify") {
		console.log("called");
		$.ajax({
			url: "/OAS/teacher/hod/ajax/activateordeactivateteacher",
			data: {
				teacherId: teacherId,
				action: action
			},
			method: "post",
			success: function (data) {
				console.log("called");
				if (data === "true") {
					if (searched) {
						searchTeacherByName();
					}else{
						showAll();
					}
				}else{
					error.show();
				}
			},
			error : function(){
				error.show();
			}
		});
	}
}

var searchTeacherByName = function(){
	var name = $("#teachername").val();
	var tableBody = $("#tablebody");
	var error = $("#error");
	var success = $("#success");
	var verified = '<tr><td>{{id}}</td><td><a href="/OAS/teacher/hod/teachers/detailteacher?teacherId={{id}}">{{name}}</a></td><td>{{number}}</td><td>{{email}}</td><td>{{hodof}}</td><td>{{classteacher}}</td><td>{{departments}}</td><td>{{verified}}</td></td><td><button class="btn btn-danger mr-xs mb-sm action" value="{{id}}">deverify</button></td></tr>';
	var notVerified = '<tr><td>{{id}}</td><td><a href="/OAS/teacher/hod/teachers/detailteacher?teacherId={{id}}">{{name}}</a></td><td>{{number}}</td><td>{{email}}</td><td>{{hodof}}</td><td>{{classteacher}}</td><td>{{departments}}</td><td>{{verified}}</td></td><td><button class="btn btn-success mr-xs mb-sm action" value="{{id}}">verify</button></td></tr>';
	error.hide();
	success.hide();
	if (name.length>=1) {
		$.ajax({
			url: "/OAS/teacher/hod/ajax/searchteacherbyname",
			dataType: "json",
			data: {
				name: name
			},
			method: "post",
			success: function (data) {
				tableBody.empty();
				searched = true;
				$.each(data, function (i, teacher) {

					if (teacher.verified) {
						tableBody.append(Mustache.render(verified, teacher));
					} else {
						tableBody.append(Mustache.render(notVerified, teacher));
					}

				});
				$(".action").click(function () {
					activateOrDeacticate($(this));
				});
				success.show();
			},
			error : function(){
				tableBody.empty();
				error.show();
			}
		});
	}

}
