
$(document).ready(function(){
	$("#mark").click(function(){
		markAttendance();
	});
});


var markAttendance = function(){
	var lectureId = $("#lectureId").val();
	var error = $("#error");
	var success = $("#success");
	success.hide();
	if(lectureId.length===6){
		$.ajax({
			url: "/OAS/student/ajax/markattendance",
			data : {
				lectureId: lectureId
			},
			method : "post",
			success: function(data){
				if(data =="true"){
					error.hide();
					success.show();
				}else{
					error.show();
					success.hide();
				}
			},
			error: function(){
				error.show();
				success.hide();
			}
		});
	}else{
		success.hide();
		error.show();
	}

}