$(document).ready(function(){
	$("#submit").click(function(){
		emailSend();
	});
});


//this method check if the email id  entered is valid or not using regex
var emailCheck = function(){
	var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,6})?$/ ;
	var email = $("#email");
	var error = $("#emailblank");

	if(email.val()===""||!emailReg.test(email.val())){
		error.show();
		return false;
	}else{
		error.hide();
		return true;
	}
}

//this method calls emailCheck first and according to that send ds an ajax call to ther server to send an email 
var emailSend = function(){
	var sent = $("#emailsent");
	var error = $("#emailblank");
	if(emailCheck()){
		$.ajax({
			url: "ajax/forgotpassword",
			data: {
				email: $('#email').val()
			},
			method: "post",
			success: function (data) {
				if (data ==="true") {
					error.hide();
					sent.show();
					setTimeout(function(){
						window.location.replace("login");
					},5000);
				}else{
					error.show();
					sent.hide();
				}
			}
		});
	}
}