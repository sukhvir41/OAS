<%-- 
    Document   : home
    Created on : Dec 31, 2016, 1:12:13 AM
    Author     : sukhvir
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>

        <!-- Basic -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">	

        <title>Home - Admin</title>	

        <meta name="keywords" content="HTML5 Template" />
        <meta name="description" content="Porto - Responsive HTML5 Template">
        <meta name="author" content="okler.net">

        <!-- Favicon -->
        <link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon" />
        <link rel="apple-touch-icon" href="img/apple-touch-icon.png">

        <!-- Mobile Metas -->
        <meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">

        <!-- Web Fonts  -->
        

        <!-- Vendor CSS -->
        <link rel="stylesheet" href="vendor/bootstrap/css/bootstrap.css">
        <link rel="stylesheet" href="vendor/font-awesome/css/font-awesome.css">
        <link rel="stylesheet" href="vendor/simple-line-icons/css/simple-line-icons.css">
        <link rel="stylesheet" href="vendor/owl.carousel/assets/owl.carousel.min.css">
        <link rel="stylesheet" href="vendor/owl.carousel/assets/owl.theme.default.min.css">
        <link rel="stylesheet" href="vendor/magnific-popup/magnific-popup.css">

        <!-- Theme CSS -->
        <link rel="stylesheet" href="css/theme.css">
        <link rel="stylesheet" href="css/theme-elements.css">
        <link rel="stylesheet" href="css/theme-blog.css">
        <link rel="stylesheet" href="css/theme-shop.css">
        <link rel="stylesheet" href="css/theme-animate.css">

        <!-- Current Page CSS -->
        <link rel="stylesheet" href="vendor/rs-plugin/css/settings.css" media="screen">
        <link rel="stylesheet" href="vendor/rs-plugin/css/layers.css" media="screen">
        <link rel="stylesheet" href="vendor/rs-plugin/css/navigation.css" media="screen"> 

        <link rel="stylesheet" href="vendor/circle-flip-slideshow/css/component.css" media="screen">

        <!-- Skin CSS -->
        <link rel="stylesheet" href="css/skins/default.css">

        <!-- Theme Custom CSS -->
        <link rel="stylesheet" href="css/custom.css">

        <!-- Head Libs -->
        <script src="vendor/modernizr/modernizr.js"></script>

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
                                        <li>Home</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <h1>Home</h1>
                                </div>
                            </div>
                        </div>
                    </section>

                    <div class="container">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="row">
                                    <div class="col-md-12">
                                        <!--Department-->
                                        <div class="col-md-8">
                                            <h4>Department</h4>
                                        </div>
                                        <div class="col-md-4">
                                            <a class="mb-xs mt-xs mr-xs btn btn-primary pull-right" href="admin/departments">Details</a>
                                        </div>
                                        <table class="table">
                                            <thead>
                                                <tr>
                                                    <th>#</th>
                                                    <th>Name</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="department" items="${requestScope.departments}">
                                                <tr>
                                                    <td>${department.id}</td>
                                                    <td>${department.name}</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>

                                    <hr class="tall">
                                    <!--Course-->
                                    <div class="col-md-8">
                                        <h4>Course</h4>
                                    </div>
                                    <div class="col-md-4">
                                        <a class="mb-xs mt-xs mr-xs btn btn-primary pull-right" href="admin/courses">Details</a>
                                    </div>
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th>#</th>
                                                <th>Name</th>
                                                <th>Started</th>
                                                <th>Start Date</th>
                                                <th>End Date</th>
                                                <th>Department Name</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="course" items="${requestScope.courses}">
                                                <tr>
                                                    <td>${course.id}</td>
                                                    <td>${course.name}</td>
                                                    <td>${course.started}</td>
                                                    <td><c:choose>
                                                            <c:when test="${course.started}">
                                                                ${course.start}
                                                            </c:when>
                                                            <c:otherwise>
                                                                --------
                                                            </c:otherwise>
                                                        </c:choose></td>
                                                    <td><c:choose>
                                                            <c:when test="${course.started} == false && ${course.start} != 'null'">
                                                                ${course.end}
                                                            </c:when>
                                                            <c:otherwise>
                                                                --------
                                                            </c:otherwise>
                                                        </c:choose></td>
                                                    <td>${course.department.name}</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>

                                    <hr class="tall">
                                    <!--class-->
                                    <div class="col-md-8">
                                        <h4>Class Room</h4>
                                    </div>
                                    <div class="col-md-4">
                                        <a class="mb-xs mt-xs mr-xs btn btn-primary pull-right" href="/OAS/admin/classrooms">Details</a>
                                    </div>
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
                                            <c:forEach var="classRoom" items="${requestScope.classRooms}">
                                                <tr>
                                                    <td>${classRoom.id}</td>
                                                    <td>${classRoom.name}</td>
                                                    <td>${classRoom.division}</td>
                                                    <td>${classRoom.semister}</td>
                                                    <td>${classRoom.course.name}</td>
                                                    <td>${classRoom.minimumSubjects}</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>

                                    <hr class="tall">

                                    <!--Subjects-->
                                    <div class="col-md-8">
                                        <h4>Subjects</h4>
                                    </div>
                                    <div class="col-md-4">
                                        <a class="mb-xs mt-xs mr-xs btn btn-primary pull-right" href="/OAS/admin/subjects">Details</a>
                                    </div>

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
                                            <c:forEach var="subject" items="${requestScope.subjects}">
                                                <tr>
                                                    <td>${subject.id}</td>
                                                    <td>${subject.name}</td>
                                                    <td>${subject.course.name}</td>
                                                    <td>${subject.elective}</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                    <hr class="tall">
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
        <script src="vendor/jquery/jquery.js"></script>
        <!--<![endif]-->
        <script src="vendor/jquery.appear/jquery.appear.js"></script>
        <script src="vendor/jquery.easing/jquery.easing.js"></script>
        <script src="vendor/jquery-cookie/jquery-cookie.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.js"></script>
        <script src="vendor/common/common.js"></script>
        <script src="vendor/jquery.validation/jquery.validation.js"></script>
        <script src="vendor/jquery.stellar/jquery.stellar.js"></script>
        <script src="vendor/jquery.easy-pie-chart/jquery.easy-pie-chart.js"></script>
        <script src="vendor/jquery.gmap/jquery.gmap.js"></script>
        <script src="vendor/jquery.lazyload/jquery.lazyload.js"></script>
        <script src="vendor/isotope/jquery.isotope.js"></script>
        <script src="vendor/owl.carousel/owl.carousel.js"></script>
        <script src="vendor/magnific-popup/jquery.magnific-popup.js"></script>
        <script src="vendor/vide/vide.js"></script>

        <!-- Theme Base, Components and Settings -->
        <script src="js/theme.js"></script>

        <!-- Specific Page Vendor and Views -->
        <script src="vendor/rs-plugin/js/jquery.themepunch.tools.min.js"></script>
        <script src="vendor/rs-plugin/js/jquery.themepunch.revolution.min.js"></script>
        <script src="vendor/rs-plugin/js/extensions/revolution.extension.actions.min.js"></script>
        <script src="vendor/rs-plugin/js/extensions/revolution.extension.carousel.min.js"></script>
        <script src="vendor/rs-plugin/js/extensions/revolution.extension.kenburn.min.js"></script>
        <script src="vendor/rs-plugin/js/extensions/revolution.extension.layeranimation.min.js"></script>
        <script src="vendor/rs-plugin/js/extensions/revolution.extension.migration.min.js"></script>
        <script src="vendor/rs-plugin/js/extensions/revolution.extension.navigation.min.js"></script>
        <script src="vendor/rs-plugin/js/extensions/revolution.extension.parallax.min.js"></script>
        <script src="vendor/rs-plugin/js/extensions/revolution.extension.slideanims.min.js"></script>
        <script src="vendor/rs-plugin/js/extensions/revolution.extension.video.min.js"></script>

        <script src="vendor/circle-flip-slideshow/js/jquery.flipshow.js"></script>
        <script src="js/views/view.home.js"></script>

        <!-- Theme Custom -->
        <script src="js/custom.js"></script>

        <!-- Theme Initialization Files -->
        <script src="js/theme.init.js"></script>

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
