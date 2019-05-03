<%-- 
    Document   : detaildepartment
    Created on : Jan 3, 2017, 11:26:59 PM
    Author     : sukhvir
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>

        <!-- Basic -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">	

        <title>Department Details - Admin</title>	

        <!-- Favicon -->
        <link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon" />
        <link rel="apple-touch-icon" href="img/apple-touch-icon.png">

        <!-- Mobile Metas -->
        <meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">

        <!-- Web Fonts  -->
        

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
                                        <li><a href="/OAS/admin/departments">Departments</a></li>
                                        <li>Department Details</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <h1>Department Details</h1>
                                </div>
                            </div>
                        </div>
                    </section>

                    <div class="container">
                        <div class="row">
                            <div class="col-md-12">

                                <jsp:include page="/WEB-INF/message-box" />

                                <div class="row">
                                    <div class="col-md-12">
                                        <h2>Department : <c:out value="${requestScope.department.name}"/></h2>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <h4>Deatils</h4>
                                            <dl>
                                                <dt>Name</dt>
                                                <dd>${requestScope.department.name}</dd>
                                                <dt>Hod</dt>
                                                <c:choose>
                                                    <c:when test="${requestScope.department.hod == null}" >
                                                        <dd>No Hod</dd>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <dd><c:out value="${requestScope.department.hod}" /></dd>
                                                    </c:otherwise>
                                                </c:choose>
                                            </dl>
                                            <a class="mb-xs mt-xs mr-xs btn btn-primary" href="/OAS/admin/departments/edit-department?departmentId=${requestScope.department.id}">Edit</a>
                                            
                                            <c:if test="${requestScope.canDelete}">
                                                <button class="btn btn-danger" data-toggle="modal" data-target="#deleteModal">Delete</button>
                                            </c:if>
                                            
                                            <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="formModalLabel" aria-hidden="true" style="display: none;">
                                                <div class="modal-dialog">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                                            <h4 class="modal-title" id="formModalLabel">Alert! Are you sure you want to delete</h4>
                                                        </div>
                                                        <form class="form-horizontal mb-lg" action="/OAS/admin/departments/delete-department-post" method="post">
                                                            <div class="modal-body">

                                                                <input type="hidden" name="departmentId" value="${requestScope.department.id}">
                                                                <label>Deleting the department directly will make the system unpredictable and may cause some problems</label>
                                                                <label><b>Its is advised to delete the departments respective data first before deleting the department</b></label>
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
                                    <hr class="tall">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <form action="/OAS/admin/courses/add-course" method="post">
                                                <h4>Add Course</h4>
                                                <input type="hidden" name="departmentId" value="${requestScope.department.id}">
                                                <input type="hidden" name="from" value="department-details">
                                                <div class="row">
                                                    <div class="form-group">
                                                        <div class="col-md-4">
                                                            <label>Course Name</label>
                                                            <input class="form-control input-lg" placeholder="course name" type="text" name="courseName" id="courseName">

                                                        </div>
                                                    </div>
                                                </div>
                                                <div class= "row">
                                                    <div class="form-group">
                                                        <div class="col-md-4">
                                                            <input type="submit" value="Add" class="btn btn-primary">
                                                        </div>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                    <hr class="tall">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <h4>Courses</h4>
                                            <table class="table">
                                                <thead>
                                                    <tr>
                                                        <th>#</th>
                                                        <th>Name</th>
                                                    </tr>
                                                </thead>
                                                <tbody>

                                                    <c:if test="${fn:length(requestScope.courses) eq 0}">
                                                        <tr>
                                                            <td>----</td>
                                                            <td>This departmetnt has no courses</td>
                                                        </tr>        
                                                    </c:if>

                                                    <c:forEach var="course" items="${requestScope.courses}" varStatus="count">
                                                        <tr>
                                                            <td>${count.index + 1}</td>
                                                            <td><a href="/OAS/admin/courses/course-details?courseId=${course.id}"><c:out value="${course.name}"/></a></td>
                                                        </tr>
                                                    </c:forEach>

                                                </tbody>
                                            </table>
                                            <hr class="tall">
                                            <h4>Teachers</h4>
                                            <table class="table">
                                                <thead>
                                                    <tr>
                                                        <th>#</th>
                                                        <th>Name</th>
                                                        <th>Email</th>
                                                        <th>Verified</th>
                                                    </tr>
                                                </thead>
                                                <tbody>

                                                    <c:if test="${fn:length(requestScope.teachers) eq 0}">
                                                        <tr>
                                                            <td>----</td>
                                                            <td>This departmetnt has no teachers</td>
                                                            <td>----</td>
                                                            <td>----</td>
                                                        </tr>        
                                                    </c:if>

                                                    <c:forEach var="teacher" items="${requestScope.teachers}" varStatus="count" >
                                                        <tr>
                                                            <td>${count.index + 1}</td>
                                                            <td><a href="#"><c:out value="${teacher}"/></a></td>
                                                            <td><c:out value="${teacher.email}"/></td>
                                                            <td>${teacher.verified}</td>
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