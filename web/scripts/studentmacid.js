$(document).ready(function(){
	$("#getmacid").click(function(){
		getMacId();
	});
});


var getMacId = function(){
	var error = $("#error");
	error.hide();
	$.ajax({
		url: "/OAS/student/ajax/getmacid",
		method: "post",
		success: function (data) {
			$("#macid").val(data);
		},
		error : function(){
			error.show();
		}
	});
} 

