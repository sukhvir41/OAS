'use strict';

$(document).ready(function () {

    $('#upadteCourseForm').validate({
        rules: {
            courseName: {
                required: true,
                minlength: 2,
                maxlength: 40,

            }
        },
        messages: {
            courseName: {
                required: "Please enter department name",
                minlength: $.validator.format("Enter at least {0} characters"),
                maxlength: $.validator.format("At max {0} characters")
            }
        }
    });

});