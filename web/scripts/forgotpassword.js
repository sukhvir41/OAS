$(document).ready(function(){
	

	$('#forgotForm').validate({
		rules:{
			email:{
				required:true,
				email: true,
			},
		},
		messages:{
			email:{
				required: 'Please enter your email address'
			}
		},
		submitHandler: function(form){
			var sent = $("#emailsent");
			var error = $("#emailblank");
			$.ajax({
				url: "ajax/forgotpassword",
				data: {
					email: $('#email').val()
				},
				method: "post",
				success: function (data) {
					console.log(data)
					if (data === true) {
						console.log('true called')
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
	});

});
