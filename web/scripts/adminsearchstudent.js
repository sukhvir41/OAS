'use strict';

$(document).load(function(){
	getClasses();

	$("#course").change(function(){
		getClasses();
	});

	$("#classroom").change(function(){
		getSubjects();
	});

});


var getClasses = function(){
	var course = $("#course").val();
	var classRoom = $("#classroom");
	var template = '<option value = "{{id}}">{{name}}</option>';
	$.ajax({
		url: "/OAS/getclass",
		dataType : "json",
		data: {
			course: course
		},
		method: "post",
		success: function (data) {
			classRoom.empty();
			classRoom.append('<option value="all">All</option>');
			$.each(data,function(i,classdata){
				classRoom.append(Mustache.render(template,classdata));
			});
			getSubjects();

		}
	});
}

var getSubjects = function(){
	var classRoom = $("#classroom").val();
	var template = '<option value = "{{id}}">{{name}}</option>';

}