'use strict';

$(document).ready(function(){

	getClasses();

	$("#course").change(function () {
		getClasses();
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