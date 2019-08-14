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
                required: "Please enter department name",
                minlength: $.validator.format("Enter at least {0} characters"),
                maxlength: $.validator.format("At max {0} characters")
            }
        }
    });

    var tr = `
        <tr v-for = "(department,index) in data" >
            <td>
            {{ counter + index }}
            </td>
            <td>
                <a v-bind:href="'/OAS/admin/departments/department-details?departmentId=' + department.id" > 
                    {{ department.name }} 
                </a>
            </td>
        </tr>        
    `;

    paginate('#departmentsTable', 'Departments', "/OAS/admin/ajax/get-departments", ['Name'], tr, "<tr> <td>----</td> <td> no departments to show </td> </tr>");

});