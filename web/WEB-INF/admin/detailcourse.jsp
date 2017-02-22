

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>

        <!-- Basic -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">	

        <title>Course Details - Admin</title>	

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
                                    <li><a href="/OAS/admin/courses">Courses</a></li>
                                    <li>Course Detail</li>
                                </ul>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <h1>Course Detail</h1>
                            </div>
                        </div>
                    </div>
                </section>

                <div class="container">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="row">
                                <div class="col-md-12">
                                    <h2>Course : <c:out value="${requestScope.course.name}"/></h2>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <h4>Deatils</h4>
                                            <dl>
                                                <dt>Name</dt>
                                                <dd>${requestScope.course.name}</dd>
                                                <dt>Started</dt>
                                                <dd>${requestScope.course.started}</dd>
                                                <dt>Start Date</dt>
                                                <dd><c:choose>
                                                        <c:when test="${requestScope.course.started}">
                                                            ${course.start}
                                                        </c:when>
                                                        <c:otherwise>
                                                            --------
                                                        </c:otherwise>
                                                    </c:choose></dd>
                                                <dt>End Date</dt>
                                                <dd><c:choose>
                                                        <c:when test="!${requestScope.course.started} && ${requestScope.course.start} != 'null'">
                                                            ${course.end}
                                                        </c:when>
                                                        <c:otherwise>
                                                            --------
                                                        </c:otherwise>
                                                    </c:choose></dd>
                                                <dt>Department Name</dt>
                                                <dd>${requestScope.course.department.name}</dd>
                                            </dl>
                                            <a class="mb-xs mt-xs mr-xs btn btn-primary" href="/OAS/admin/courses/startcourse?courseId=${requestScope.course.id}">Start</a>
                                            <a class="mb-xs mt-xs mr-xs btn btn-primary" href="/OAS/admin/courses/editcourse?courseId=${requestScope.course.id}">Edit</a>
                                            <button class="btn btn-danger" data-toggle="modal" data-target="#deleteModal">Delete</button>
                                            <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="formModalLabel" aria-hidden="true" style="display: none;">
                                                <div class="modal-dialog">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                                            <h4 class="modal-title" id="formModalLabel">Alert! Are you sure you want to delete</h4>
                                                        </div>
                                                        <form class="form-horizontal mb-lg" action="/OAS/admin/courses/deletecourse" method="post">
                                                            <div class="modal-body">

                                                                <input type="hidden" name="courseId" value="${requestScope.course.id}">
                                                                <label>Deleting the course directly will make the system unpredictable and may cause some problems</label>
                                                                <label><b>Its is advised to delete the course respective data first before deleting it</b></label>
                                                            </div>
                                                            <div class="modal-footer">
                                                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                                <input type="submit" value="Delete" class="btn btn-danger">
                                                            </div>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-12">
                                    <hr class="tall">
                                    <div class="col-md-6">
                                        <form action="/OAS/admin/classrooms/addclassroom" method="Post">
                                            <h4>Add Class Room</h4>
                                            <input type="hidden" name="courseId" value="${requestScope.course.id}">
                                            <input type="hidden" name="from" value="course">
                                            <div class="row">
                                                <div class="form-group">
                                                    <div class="col-md-8">
                                                        <label>Class Room Name</label>
                                                        <input class="form-control input-lg" placeholder="class room name" type="text" name="classroomname" id="classroomname" required="true">

                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group">
                                                    <div class="col-md-8">
                                                        <label>Division</label>
                                                        <input class="form-control input-lg" placeholder="A" type="text" name="division" id="division" required="true">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group">
                                                    <div class="col-md-8">
                                                        <label>Semister</label>
                                                        <input class="form-control input-lg" placeholder="1" type="number" name="semister" id="semister" required="true">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group">
                                                    <div class="col-md-8">
                                                        <label>Minimmum Subjects</label>
                                                        <input class="form-control input-lg" placeholder="1" type="number" name="minimumsubjects" id="minimumsubjects" required="true">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class= "row">
                                                <div class="form-group">
                                                    <div class="col-md-8">
                                                        <input type="submit" value="Add" class="btn btn-primary">
                                                    </div>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                    <div class="col-md-6">
                                        <form action="/OAS/admin/subjects/addsubject" method="post">
                                            <h4>Add Subject</h4>
                                            <input type="hidden" name="course" value="${requestScope.course.id}">
                                            <input type="hidden" name="from" value="course">
                                            <div class="row">
                                                <div class="form-group">
                                                    <div class="col-md-8">
                                                        <label>Subject Name</label>
                                                        <input class="form-control input-lg" placeholder="subject name" type="text" name="subjectname" id="subjectname" required="true">

                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group">
                                                    <div class="col-md-8">
                                                        <label>Elective</label>
                                                        <span class="checkbox">
                                                            <label class="checkbox"><input type="checkbox" name="elective" value="true">Elective</label>
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                    <div class="form-group">
                                                        <div class="col-md-8">
                                                            <label>Class Rooms</label>
                                                            <c:forEach var="classroom" items="${requestScope.course.classRooms}">
                                                                <span class="checkbox">
                                                                    <label class="checkbox"><input type="checkbox" name="classes" value="${classroom.id}">${classroom.name}</label>
                                                                </span>
                                                            </c:forEach>
                                                        </div>
                                                    </div>
                                                </div>
                                            <div class= "row">
                                                <div class="form-group">
                                                    <div class="col-md-8">
                                                        <input type="submit" value="Add" class="btn btn-primary">
                                                    </div>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                            <hr class="tall">
                            <div class="row">
                                <div class="col-md-12">
                                    <h4>Class Rooms</h4>
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th>#</th>
                                                <th>Name</th>
                                                <th>Divison</th>
                                                <th>Semister</th>
                                                <th>Course</th>
                                                <th>MiniSubjects</th>
                                            </tr>
                                        </thead>

                                        <tbody>
                                            <c:forEach var="classRoom" items="${requestScope.course.classRooms}">
                                                <tr>
                                                    <td>${classRoom.id}</td>
                                                    <td><a href="/OAS/admin/classrooms/detailclassroom?classroomId=${classRoom.id}">${classRoom.name}</a></td>
                                                    <td>${classRoom.division}</td>
                                                    <td>${classRoom.semister}</td>
                                                    <td>${classRoom.course.name}</td>
                                                    <td>${classRoom.minimumSubjects}</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                    <hr class="tall">
                                    <h4>Subjects</h4>
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th>#</th>
                                                <th>Name</th>
                                                <th>Course</th>
                                                <th>Elective</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="subject" items="${requestScope.course.subjects}">
                                                <tr>
                                                    <td>${subject.id}</td>
                                                    <td><a href="/OAS/admin/subjects/detailsubject?subjectId=${subject.id}">${subject.name}</a></td>
                                                    <td>${subject.course.name}</td>
                                                    <td>${subject.elective}</td>
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
