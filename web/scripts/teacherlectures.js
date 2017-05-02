'use script';

$(document).ready(function(){

	$("#search").click(function(){

		getLectures();
	});
});


var getLectures = function(){
	var error = $("#error");
	var success = $("#success");
	var teaching = $("#teaching").val();
	var startdate = $("#stardate").val();
	var enddate = $("#enddate").val();
	var tableBody = $("#tablebody");
	var template = '<tr><td><a href="/OAS/teacher/lectures/detaillecture?lectureId={{id}}">{{id}}</a></td><td>{{class}}</td><td>{{subject}}</td><td>{{count}}</td><td>{{date}}</td><td>{{present}}</td><td>{{absent}}</td></tr>';
	error.hide();
	success.hide();

	$.ajax({
		url: "/OAS/teacher/ajax/getlectures",
		dataType: "json",
		data: {
			teachingId : teaching,
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