'use strict';

$(document).ready(function () {

    $('#addSubjectForm').validate({
        rules: {
            subjectName: {
                required: true,
                minlength: 2,
                maxlength: 40,

            }
        },
        messages: {
            departmentName: {
                required: "Please enter subject name",
                minlength: $.validator.format("Enter at least {0} characters"),
                maxlength: $.validator.format("At max {0} characters")
            }
        }
    });

    $('#addClassRoomForm').validate({
        rules: {
            classRoomName: {
                required: true,
                minlength: 2,
                maxlength: 40
            },
            division: {
                required: true,
                minlength: 1,
                maxlength: 2
            },
            semester: {
                required: true,
                number: true,
                minlength: 1,
                maxlength: 2
            },
            minimumSubjects: {
                required: true,
                number: true,
                minlength: 1,
                maxlength: 2
            }
        },
        messages: {
            departmentName: {
                required: "Please enter class room name",
                minlength: $.validator.format("Enter at least {0} characters"),
                maxlength: $.validator.format("At max {0} characters")
            }
        }
    });

});