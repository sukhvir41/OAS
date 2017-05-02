'use script';
var searched = false;

$(document).ready(function(){

	$("#search").click(function(){

		getStudents();
	});

	$("#searchname").click(function(){
		searchByName();
	});


	$(".action").click(function () {
		activateOrDeacticate($(this));
	});

});


var searchByName = function(){
	var error = $("#error");
	var success = $("#success");
	var name = $("#studentname").val();

	var body = $("#tablebody");
	var templateVerified = '<tr><td>{{id}}</td><td><a href="/OAS/teacher/classteacher/students/detailstudent?studentId={{id}}">{{name}}</a></td><td>{{number}}</td><td>{{email}}</td><td>{{classroom}}</td><td>{{rollnumber}}</td><td>{{subjects}}</td><td>{{verified}}</td><td><button class="btn btn-danger mr-xs mb-sm action" value="{{id}}">deverify</button></td></tr>';
	var templateNotVerified = '<tr><td>{{id}}</td><td><a href="/OAS/teacher/classteacher/students/detailstudent?studentId={{id}}">{{name}}</a></td><td>{{number}}</td><td>{{email}}</td><td>{{classroom}}</td><td>{{rollnumber}}</td><td>{{subjects}}</td><td>{{verified}}</td><td><button class="btn btn-success mr-xs mb-sm action" value="{{id}}">verify</button></td></tr>';
	error.hide();
	success.hide();
	if (name.length >= 1) {
		$.ajax({
			url: "/OAS/teacher/classteacher/ajax/searchstudentbyname",
			dataType: "json",
			data: {
				name: name
			},
			method: "post",
			success: function (data) {
				body.empty();
				searched = true;
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
			},
			error: function (){
				body.empty();
				error.show();
			}
		});
	}

}

var getStudents = function (){
	var error = $("#error");
	var success = $("#success");

	var filter = $('input[name=filter]:checked').val();
	var templateVerified = '<tr><td>{{id}}</td><td><a href="/OAS/teacher/classteacher/students/detailstudent?studentId={{id}}">{{name}}</a></td><td>{{number}}</td><td>{{email}}</td><td>{{classroom}}</td><td>{{rollnumber}}</td><td>{{subjects}}</td><td>{{verified}}</td><td><button class="btn btn-danger mr-xs mb-sm action" value="{{id}}">deverify</button></td></tr>';
	var templateNotVerified = '<tr><td>{{id}}</td><td><a href="/OAS/teacher/classteacher/students/detailstudent?studentId={{id}}">{{name}}</a></td><td>{{number}}</td><td>{{email}}</td><td>{{classroom}}</td><td>{{rollnumber}}</td><td>{{subjects}}</td><td>{{verified}}</td><td><button class="btn btn-success mr-xs mb-sm action" value="{{id}}">verify</button></td></tr>';
	var body = $("#tablebody");
	error.hide();
	success.hide();
	
	$.ajax({
		url: "/OAS/teacher/classteacher/ajax/getstudents",
		dataType: "json",
		data: {
			filter: filter
		},
		method: "post",
		success: function (data) {
			body.empty();
			searched = false;
			if (data=="error") {
				error.show();
			}else{
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
		},
		error : function(){
			console.log("error was callled")
			body.empty();
			error.show();
		}
	});

}

var activateOrDeacticate = function (button) {
	var action = button.text();
	var studentId = button.val();
	if (action === "verify" || action === "deverify") {
		$.ajax({
			url: "/OAS/teacher/classteacher/ajax/activatedeactivateteacher",
			data: {
				studentId: studentId,
				action: action
			},
			method: "post",
			success: function (data) {
				console.log("called");
				if (data === "true") {
					if(searched){
						searchByName();
					}else{
						getStudents();
					}
				}
			}
		});
	}

}