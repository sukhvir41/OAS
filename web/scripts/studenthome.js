
$(document).ready(function () {
    $("#mark").click(function () {
        markAttendance();
    });
});


var markAttendance = function () {
    var lectureId = $("#lectureId").val();
    var error = $("#error");
    var success = $("#success");
    success.hide();
    error.hide();
    $("#mark").prop("disabled", true);
    if (lectureId.length == 8) {
        $.ajax({
            url: "/OAS/student/ajax/markattendance",
            data: {
                lectureId: lectureId
            },
            method: "post",
            success: function (data) {
                if (data == "true") {
                    error.hide();
                    success.show();
                } else {
                    error.show();
                    success.hide();
                }
                $("#mark").prop("disabled", false);
            },
            error: function () {
                console.log("error");
                error.show();
                success.hide();
                $("#mark").prop("disabled", false);
            }
        });
    } else {
        success.hide();
        error.show();
        $("#mark").prop("disabled", false);
    }

}