'use strict';

$(document).ready(function () {

    $('#addDepartmentForm').validate({
        rules: {
            departmentName: {
                required: true,
                minlength: 2,
                maxlength: 40,

            }
        },
        messages: {
            departmentName: {
                required: "Please enter department anme",
                minlength: $.validator.format("Enter at least {0} characters"),
                maxlength: $.validator.format("At max {0} characters")
            }
        }
    });

});