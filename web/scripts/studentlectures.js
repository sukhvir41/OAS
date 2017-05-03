'use strict';

$(document).ready(function(){
	
	$("#search").click(function(){
		getLectures();
	});

});


var getLectures = function(){
	var error = $("#error");
	var success = $("#success");
	var subject = $("#subject").val();
	var startdate = $("#startdate").val();
	var enddate = $("#enddate").val();
	var tableBody = $("#tablebody");
	var template = '<tr><td>{{id}}</td><td>{{class}}</td><td>{{subject}}</td><td>{{count}}</td><td>{{date}}</td><td>{{status}}</td></tr>';

	error.hide();
	success.hide();

	$.ajax({
		url: "/OAS/student/ajax/getlectures",
		dataType: "json",
		data: {
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