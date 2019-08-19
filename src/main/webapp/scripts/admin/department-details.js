'use strict';

$(document).ready(function () {
    $('#addCourse').validate({
        rules: {
            courseName: {
                required: true,
                minlength: 2,
                maxlength: 40,

            }
        },
        messages: {
            courseName: {
                required: "Please enter course name",
                minlength: $.validator.format("Enter at least {0} characters"),
                maxlength: $.validator.format("At max {0} characters")
            }
        }
    });


    var courseTr = `
        <tr v-for="(course,index) in data">
            <td> 
                {{ counter + index }} 
            </td> 
            <td >
                <a v-bind:href="'/OAS/admin/courses/course-details?courseId=' + course.id"> 
                    {{ course.name }} 
                </a> 
            </td> 
        </tr>    
    `
    var additionalData = {
        departmentId: $('#departmentId').val()
    }

    paginate('#coursesTable', 'Courses', "/OAS/admin/ajax/get-courses", ['Name'], courseTr, "<tr> <td>----</td> <td> No courses in department </td> </tr>", true, JSON.stringify(additionalData));

    var teacherTr = `
        <tr v-for="(teacher,index) in data">
            <td> 
                {{ counter + index }} 
            </td> 
            <td>
                <a v-bind:href="'/OAS/admin/teacher/teacher-details?teacherId=' + teacher.id"> 
                    {{ teacher.name }} 
                </a> 
            </td> 
            <td>
                {{teacher.email}}
            </td>
            <td>
                {{teacher.status}}
            </td>
        </tr>    
    `
    additionalData = {
        departmentId: $('#departmentId').val()
    }

    paginate('#teachersTable', 'Teachers', "/OAS/admin/ajax/get-teachers", ['Name', 'Email', 'Status'], teacherTr, "<tr> <td>----</td> <td col-span='3'> No Teachers in department </td> </tr>", true, JSON.stringify(additionalData));

});