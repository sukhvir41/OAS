<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>

        <!-- Basic -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">   

        <title>Teacher Details - Admin</title>   

        <!-- Favicon -->
        <link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon" />
        <link rel="apple-touch-icon" href="img/apple-touch-icon.png">

        <!-- Mobile Metas -->
        <meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">

        <!-- Web Fonts  -->
        <link href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700,800%7CShadows+Into+Light" rel="stylesheet" type="text/css">

        <!-- Vendor CSS -->
        <link rel="stylesheet" href="/OAS/vendor/bootstrap/css/bootstrap.css">
        <link rel="stylesheet" href="/OAS/vendor/font-awesome/css/font-awesome.css">
        <link rel="stylesheet" href="/OAS/vendor/simple-line-icons/css/simple-line-icons.css">
        <link rel="stylesheet" href="/OAS/vendor/owl.carousel/assets/owl.carousel.min.css">
        <link rel="stylesheet" href="/OAS/vendor/owl.carousel/assets/owl.theme.default.min.css">
        <link rel="stylesheet" href="/OAS/vendor/magnific-popup/magnific-popup.css">

        <!-- Theme CSS -->
        <link rel="stylesheet" href="/OAS/css/theme.css">
        <link rel="stylesheet" href="/OAS/css/theme-elements.css">
        <link rel="stylesheet" href="/OAS/css/theme-blog.css">
        <link rel="stylesheet" href="/OAS/css/theme-shop.css">
        <link rel="stylesheet" href="/OAS/css/theme-animate.css">

        <!-- Current Page CSS -->
        <link rel="stylesheet" href="/OAS/vendor/rs-plugin/css/settings.css" media="screen">
        <link rel="stylesheet" href="/OAS/vendor/rs-plugin/css/layers.css" media="screen">
        <link rel="stylesheet" href="/OAS/vendor/rs-plugin/css/navigation.css" media="screen"> 

        <link rel="stylesheet" href="/OAS/vendor/circle-flip-slideshow/css/component.css" media="screen">

        <!-- Skin CSS -->
        <link rel="stylesheet" href="/OAS/css/skins/default.css">

        <!-- Theme Custom CSS -->
        <link rel="stylesheet" href="/OAS/css/custom.css">

        <!-- Head Libs -->
        <script src="/OAS/vendor/modernizr/modernizr.js"></script>

        <!--[if IE]>
                <link rel="stylesheet" href="css/ie.css">
                <![endif]-->

        <!--[if lte IE 8]>
                <script src="vendor/respond/respond.js"></script>
                <script src="vendor/excanvas/excanvas.js"></script>
                <![endif]-->

    </head>
    <body>
        <div class="body">
            <jsp:include page="/WEB-INF/admin/adminheader.jsp"></jsp:include>

                <div role="main" class="main">
                    <section class="page-header">
                        <div class="container">
                            <div class="row">
                                <div class="col-md-12">
                                    <ul class="breadcrumb">
                                        <li><a href="/OAS/admin">Home</a></li>
                                        <li><a href="/OAS/admin/teachers">Teachers</a></li>
                                        <li>Teacher Details</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <h1>Teacher Details</h1>
                                </div>
                            </div>
                        </div>
                    </section>

                    <div class="container">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="row">
                                    <div class="col-md-12">
                                        <h2>Name : ${requestScope.teacher.fName} ${requestScope.teacher.lName}</h2>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <dl>
                                        <dt>Contact Number</dt>
                                        <dd>${requestScope.teacher.number}</dd>
                                        <dt>Email</dt>
                                        <dd>${requestScope.teacher.email}</dd>
                                        <dt>Hod of </dt>
                                        <dd>
                                            <c:choose>
                                                <c:when test="${requestScope.teacher.hod}" >
                                                    <c:forEach var="department" items="${requestScope.teacher.hodOf}">
                                                        ${department.name}<br>
                                                    </c:forEach>
                                                </c:when>
                                                <c:otherwise>
                                                    Not Hod
                                                </c:otherwise>
                                            </c:choose>
                                        </dd>
                                        <dt>Departments</dt>
                                        <dd>
                                            <c:forEach var="department" items="${requestScope.teacher.department}">
                                                ${department.name}<br>
                                            </c:forEach>
                                        </dd>

                                        <dt>Class Teacher of</dt>
                                        <c:choose>
                                            <c:when test="${requestScope.teacher.classRoom} == 'null'" >
                                                <dd>Not Class Teacher<dd>
                                                </c:when>
                                                <c:otherwise>
                                                <dd>${requestScope.teacher.classRoom.name}</dd>
                                            </c:otherwise>
                                        </c:choose>
                                        <dt>Verified?</dt>
                                        <dd>${requestScope.teacher.verified}</dd>
                                        <dt>Unaccounted?</dt>
                                        <dd>${requestScope.teacher.unaccounted}</dd>
                                    </dl>
                                    <c:choose>
                                        <c:when test="${requestScope.teacher.verified}">
                                            <button class="btn btn-danger" value="${requestScope.teacher.id}" href="/OAS/admin/teachers/verifyteacher?teacherId=${requestScope.teacher.id}">Deverify</button>
                                        </c:when>
                                        <c:otherwise>
                                            <button class="btn btn-success" value="${requestScope.teacher.id}" href="/OAS/admin/teachers/deverifyteacher?teacherId=${requestScope.teacher.id}">Verify</button>
                                        </c:otherwise>
                                    </c:choose>
                                    <button class="btn btn-danger" data-toggle="modal" data-target="#deleteModal">Delete</button>
                                    <c:choose>
                                        <c:when test="${requestScope.teacher.unaccounted}">
                                        </c:when>
                                        <c:otherwise>
                                            <button class="btn btn-danger" data-toggle="modal" data-target="#unaccountTeacher">Unaccout Teacher</button>
                                        </c:otherwise>
                                    </c:choose>
                                    <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="formModalLabel" aria-hidden="true" style="display: none;">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                                    <h4 class="modal-title" id="formModalLabel">Alert! Are you sure you want to delete a subject</h4>
                                                </div>
                                                <form class="form-horizontal mb-lg" action="/OAS/admin/teachers/deleteteacher" method="post">
                                                    <div class="modal-body">

                                                        <input type="hidden" name="teacherId" value="${requestScope.teacher.id}">
                                                        <label>Deleting the teacher directly will make the system unpredictable and may cause some problems</label>
                                                        <label><b>It is advised to unaccount teacher first before deleting the teacher.</b></label>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                        <input type="submit" value="Delete" class="btn btn-danger">
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal fade" id="unaccountTeacher" tabindex="-1" role="dialog" aria-labelledby="formModalLabel" aria-hidden="true" style="display: none;">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                                    <h4 class="modal-title" id="formModalLabel">Alert! Are you sure you want to unaccount the Teacher</h4>
                                                </div>
                                                <form class="form-horizontal mb-lg" action="###" method="post">
                                                    <div class="modal-body">
                                                        <input type="hidden" name="teacherId" value="${requestScope.teacher.id}">
                                                        <label>Unaccounting teacher will remove all subjects and all classes from respective teacher.</label>
                                                        <label><b>Click on unaccount button to unaccount teacher.</b></label>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                        <input type="submit" value="Unaccout Teacher" class="btn btn-danger">
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>

                                    <!--Button to unaccount the teacher and edit teacher details.###-->
                                </div>
                            </div>
                            <hr class="tall">
                            <div class="row">
                                <div class="col-md-12">
                                    <h4>Teacher teaching following subjects in respective class</h4>
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th>Subject</th>
                                                <th>Class Name</th>
                                                <th>Course</th>
                                                <th>Department</th>
                                            </tr>
                                        </thead>
                                        <tbody id="tablebody">
                                            <c:forEach var="teaching" items="${requestScope.teacher.teaches}">
                                                <tr>
                                                    <td><a href="/OAS/admin/subjects/detailsubject?subjectId=${teaching.subject.id}">${teaching.subject.name}</a></td>
                                                    <td><a href="/OAS/admin/classrooms/detailclassroom?classroomId=${teaching.classRoom.id}">${teaching.classRoom.name}</a></td>
                                                    <td><a href="/OAS/admin/courses/detailcourse?courseId=${teaching.classRoom.course.id}">${teaching.classRoom.course.name}</a></td>
                                                    <td><a href="/OAS/admin/departments/detaildepartment?departmentId=${teaching.classRoom.course.department.id}">${teaching.classRoom.course.department.name}</a></td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Vendor -->
<!--[if lt IE 9]>
<script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
<![endif]-->
<!--[if gte IE 9]><!-->
<script src="/OAS/vendor/jquery/jquery.js"></script>
<!--<![endif]-->
<script src="/OAS/vendor/jquery.appear/jquery.appear.js"></script>
<script src="/OAS/vendor/jquery.easing/jquery.easing.js"></script>
<script src="/OAS/vendor/jquery-cookie/jquery-cookie.js"></script>
<script src="/OAS/vendor/bootstrap/js/bootstrap.js"></script>
<script src="/OAS/vendor/common/common.js"></script>
<script src="/OAS/vendor/jquery.validation/jquery.validation.js"></script>
<script src="/OAS/vendor/jquery.stellar/jquery.stellar.js"></script>
<script src="/OAS/vendor/jquery.easy-pie-chart/jquery.easy-pie-chart.js"></script>
<script src="/OAS/vendor/jquery.gmap/jquery.gmap.js"></script>
<script src="/OAS/vendor/jquery.lazyload/jquery.lazyload.js"></script>
<script src="/OAS/vendor/isotope/jquery.isotope.js"></script>
<script src="/OAS/vendor/owl.carousel/owl.carousel.js"></script>
<script src="/OAS/vendor/magnific-popup/jquery.magnific-popup.js"></script>
<script src="/OAS/vendor/vide/vide.js"></script>

<!-- Theme Base, Components and Settings -->
<script src="/OAS/js/theme.js"></script>

<!-- Specific Page Vendor and Views -->
<script src="/OAS/vendor/rs-plugin/js/jquery.themepunch.tools.min.js"></script>
<script src="/OAS/vendor/rs-plugin/js/jquery.themepunch.revolution.min.js"></script>
<script src="/OAS/vendor/rs-plugin/js/extensions/revolution.extension.actions.min.js"></script>
<script src="/OAS/vendor/rs-plugin/js/extensions/revolution.extension.carousel.min.js"></script>
<script src="/OAS/vendor/rs-plugin/js/extensions/revolution.extension.kenburn.min.js"></script>
<script src="/OAS/vendor/rs-plugin/js/extensions/revolution.extension.layeranimation.min.js"></script>
<script src="/OAS/vendor/rs-plugin/js/extensions/revolution.extension.migration.min.js"></script>
<script src="/OAS/vendor/rs-plugin/js/extensions/revolution.extension.navigation.min.js"></script>
<script src="/OAS/vendor/rs-plugin/js/extensions/revolution.extension.parallax.min.js"></script>
<script src="/OAS/vendor/rs-plugin/js/extensions/revolution.extension.slideanims.min.js"></script>
<script src="/OAS/vendor/rs-plugin/js/extensions/revolution.extension.video.min.js"></script>

<script src="/OAS/vendor/circle-flip-slideshow/js/jquery.flipshow.js"></script>
<script src="/OAS/js/views/view.home.js"></script>

<!-- Theme Custom -->
<script src="/OAS/js/custom.js"></script>

<!-- Theme Initialization Files -->
<script src="/OAS/js/theme.init.js"></script>

<!-- Google Analytics: Change UA-XXXXX-X to be your site's ID. Go to http://www.google.com/analytics/ for more information.
<script type="text/javascript">

        var _gaq = _gaq || [];
        _gaq.push(['_setAccount', 'UA-12345678-1']);
        _gaq.push(['_trackPageview']);

        (function() {
        var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
        ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
        var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
        })();

</script>
-->

</body>
</html>
