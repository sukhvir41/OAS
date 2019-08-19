'use strict';

$(document).ready(function () {

    $('#addCourseForm').validate({
        rules: {
            courseName: {
                required: true,
                minlength: 2,
                maxlength: 40,

            },
            departmentId: {
                required: true
            }
        },
        messages: {
            courseName: {
                required: "Please enter course name",
                minlength: $.validator.format("Enter at least {0} characters"),
                maxlength: $.validator.format("At max {0} characters")
            },
            departmentId: {
                required: "Please select a department"
            }
        }
    });

    var tr = `
        <tr v-for = "(course,index) in data" >
            <td>
            {{ counter + index }}
            </td>
            <td>
                <a v-bind:href="'/OAS/admin/courses/course-details?courseId=' + course.id">
                    {{course.name}}
                </a>
            </td>
            <td>
                <a v-bind:href="'/OAS/admin/departments/department-details?departmentId=' + course.departmentId" > 
                    {{ course.departmentName }} 
                </a>
            </td>
        </tr>        
    `;

    paginate('#coursesTable', 'Courses', "/OAS/admin/ajax/get-courses", ['Name', 'Department'], tr, "<tr> <td>----</td> <td colspan='2'> no courses to show </td> </tr>");

});