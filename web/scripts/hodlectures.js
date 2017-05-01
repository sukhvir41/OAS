'use strict';

$(document).ready(function(){
	getClasses();

	$("#course").change(function(){
		getClasses();
	});

	$("#classroom").change(function(){
		getSubjects();
	});
	$("#search").click(function(){
		getLectures();
	});

});


var getClasses = function(){
	var course = $("#course").val();
	var classRoom = $("#classroom");
	var error = $("#error");
	var success = $("#success");
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
		},
		error: function(){
			//show error do it in future if needed
		}
	});

}


var getSubjects = function(){
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

var getLectures = function(){
	var error = $("#error");
	var success = $("#success");
	var course = $("#course").val();
	var classRoom = $("#classroom").val();
	var subject = $("#subject").val();
	var startdate = $("#stardate").val();
	var enddate = $("#enddate").val();
	var tableBody = $("#tablebody");
	var template = '<tr><td><a href="/OAS/teacher/hod/lectures/detaillecture?lectureId={{id}}">{{id}}</a></td><td>{{class}}</td><td>{{subject}}</td><td>{{count}}</td><td>{{date}}</td><td>{{present}}</td><td>{{absent}}</td></tr>';
	error.hide();
	success.hide();

	$.ajax({
		url: "/OAS/teacher/hod/ajax/searchlectures",
		dataType: "json",
		data: {
			classroomId: classRoom,
			subjectId: subject,
			startdate: startdate,
			enddate: enddate
		},
		method: "post",
		success: function (data) {
			tableBody.empty();
			if (data =="error") {
				error.show();
			}else{
				$.each(data, function (i, lecturedata) { 
					tableBody.append(Mustache.render(template, lecturedata));
				});
				success.show();
			}
		},
		error : function(){
			tableBody.empty();
			error.show();
		}
	});
}