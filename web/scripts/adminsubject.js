
$(document).ready(function () {
    getClasses();

    $("#course").change(function () {
        getClasses();
    });

});

// var getCourses = function(){
// 	var course = $("#course");
// 	$.ajax({
// 		url: "/OAS/ajax/getcourse",
// 		dataType: "json",
// 		method: "post",
// 		success: function (data) {
// 			$.each(data,function(i,jsoncourse){
// 				course.append($('<option>', {
// 					value: jsoncourse.id,
// 					text: jsoncourse.name
// 				}));
// 			});
// 			getClasses();
// 		}
// 	});

// }

var getClasses = function () {
    var classes = $("#class");
    var template = '<span class="checkbox"><label class="checkbox"><input type="checkbox" name="classes" value="{{id}}">{{name}}</label></span>';
    classes.empty();
    $.ajax({
        url: "/OAS/ajax/getclass",
        dataType: "json",
        data: {
            course: $('#course').val()
        },
        method: "post",
        success: function (data) {
            $.each(data, function (i, jsonclass) {
                classes.append(Mustache.render(template, jsonclass));
            });
        }
    });

}