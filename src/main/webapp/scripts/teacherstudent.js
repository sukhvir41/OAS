'use strict';

$(document).ready(function(){

	$("#search").click(function(){
		getStudents();
	});
});


var getStudents = function(){
	var error = $("#error");
    var success = $("#success");
    var teaching = $("#teaching").val();
    var template = '<tr><td>{{id}}</td><td><a href="/OAS/teacher/students/detailstudent?studentId={{id}}">{{name}}</a></td><td>{{number}}</td><td>{{email}}</td><td>{{classroom}}</td><td>{{rollnumber}}</td><td>{{subjects}}</td><td>{{verified}}</td></tr>';
    
    var body = $("#tablebody");
    error.hide();
    success.hide();

    $.ajax({
        url: "/OAS/teacher/ajax/getstudents",
        dataType: "json",
        data: {
            teachingId : teaching,
        },
        method: "post",
        success: function (data) {
            body.empty();
            $.each(data, function (i, studentdata) {
                    body.append(Mustache.render(template, studentdata));
            });
            success.show();
        },
        error : function(){
        	body.empty();
        	error.show();
        }
    });


}