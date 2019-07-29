'use strict';


$(document).ready(function () {

	$('#loginForm').validate({
		rules: {
			username: {
				required: true,
				minlength: 8,
				maxlength: 40
			},
			password: {
				required: true,
				minlength: 8,
				maxlength: 40,
			}
		},
		messages: {
			username: {
				required: 'Please entred your username or email'
			},
			password: {
				required: 'Please enter your password'
			}
		}
	});

});