<%-- 
    Document   : teacherhome
    Created on : 11 Mar, 2017, 11:30:29 PM
    Author     : icr
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>

        <!-- Basic -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">   

        <title>Home - Teacher</title>   

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
            <jsp:include page="/WEB-INF/teacher/teacherheader.jsp"></jsp:include>

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
                                        <h4>Create Lecture</h4>
                                    </div>
                                </div>
                                <form class="form-horizontal mb-lg" action="/OAS/teacher/createlecture" method="post">
                                    <div class="row">
                                        <div class="col-md-4 col-sm-12">
                                            <div class="form-group">
                                                <div class="col-md-12">
                                                    <label>Select Subject</label>
                                                    <select class="form-control input-lg mb-md"  name="teaching" id="teaching" required="true">
                                                    <c:forEach var="teaching" items="${requestScope.teaching}">
                                                        <option value="${teaching.id}">${teaching}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-md-4 col-sm-12">
                                        <div class="form-group">
                                            <div class="col-md-12">
                                                <label>Number of Lectures</label>
                                                <select class="form-control input-lg mb-md"  name="count" id="count" required="true">
                                                    <option value="1">1</option>
                                                    <option value="2">2</option>
                                                    <option value="3">3</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <div class="col-md-12">
                                                <input type="submit" value="Create Lecture" class="btn btn-primary">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                            <hr>
                            <div class="row">
                                <div class="col-md-12">
                                    <label>Recent Lectures</label>
                                    <div class="form-group">
                                        <div class="col-md-12">
                                            <c:forEach var="lecture" items="${requestScope.recent}">
                                                <a class="mb-xs mt-xs mr-xs btn btn-primary" href="/OAS/teacher?lectureId=${lecture.id}">${lecture.id}-${lecture.teaching}</a>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-md-12">
                                    <label>Active Lecture</label><br>
                                    <p>${requestScope.active.id}-${requestScope.active.teaching}</p>
                                    <input type="hidden" name="lectureId" id="lectureId" value="${requestScope.active.id}">
                                    <c:choose>
                                        <c:when test="${requestScope.active.ended==false}" >
                                            <a class="mb-xs mt-xs mr-xs btn btn-danger" href="/OAS/teacher/endlecture?lectureId=${requestScope.active.id}">End Lecture</a>
                                        </c:when>
                                        <c:otherwise>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <br>
                            <div class="row">
                                <div class="col-md-6">
                                    <h4>Present Students</h4>
                                    <table class="table table-hover" id="present">
                                        <thead>
                                            <tr>
                                                <th>Roll No.</th>
                                                <th>Name</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="student" items="${requestScope.present}">
                                                <tr id="${student.id}">
                                                    <td>${student.rollNumber}</td>
                                                    <td>${student}</td>
                                                    <td>
                                                        <button class="btn btn-danger action" id="${student.id}" value="${student.id}">Absent</button>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="col-md-6">
                                    <h4>Absent Students</h4>
                                    <table class="table table-hover" id="absent">
                                        <thead>
                                            <tr>
                                                <th>Roll No.</th>
                                                <th>Name</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="student" items="${requestScope.absent}">
                                                <tr id="${student.id}">
                                                    <td>${student.rollNumber}</td>
                                                    <td>${student}</td>
                                                    <td>
                                                        <button class="btn btn-success action" id="${student.id}" value="${student.id}">Present</button>
                                                    </td>
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
