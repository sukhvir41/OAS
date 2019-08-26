<%-- 
    Document   : editdepartment
    Created on : Jan 5, 2017, 9:04:59 PM
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

    <title>Edit Department - Admin</title>

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
                                <li>Edit Department</li>
                            </ul>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <h1>Edit Department</h1>
                        </div>
                    </div>
                </div>
            </section>

            <div class="container">
                <div class="row">
                    <div class="col-md-8">
                        <div class="row">
                            <div class="col-md-12">
                                <form action="/OAS/admin/departments/update-department" method="Post">
                                    <input type="hidden" name="departmentId" value="${requestScope.department.id}">
                                    <input type="hidden" name="from" value="${param.from}">
                                    <!-- might not need this anymore-->

                                    <jsp:include page="/WEB-INF/message-box" />
                                    <h4>Edit Deparmtent</h4>
                                    <div class="row">
                                        <div class="form-group">
                                            <div class="col-md-8">
                                                <label>Department Name</label>
                                                <input class="form-control input-lg" type="text" name="departmentName"
                                                    id="departmentName" required="true"
                                                    value="${requestScope.department.name}">
                                            </div>
                                        </div>
                                    </div>
                                    <c:if test="${fn:length(requestScope.teachers) gt 0}">
                                        <div class="row">
                                            <div class="form-group">
                                                <div class="col-md-8">
                                                    <label>Select Hod</label>
                                                    <select class="form-control input-lg mb-md" name="teacherId"
                                                        id="departmentHod">
                                                        <option value="no-hod">No Hod</option>
                                                        <c:forEach var="teacher" items="${requestScope.teachers}">
                                                            <option value="${teacher.id}">
                                                                <c:out value="${teacher.FName} ${teacher.LName}" />
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>
                                    <div class="row">
                                        <div class="form-group">
                                            <div class="col-md-8">
                                                <input type="submit" value="Update" class="btn btn-primary">
                                            </div>
                                        </div>
                                    </div>
                                </form>
                                <hr class="tall" />
                                <form action="/OAS/admin/departments/transfer-department" method="post">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <h4>Transfer Department</h4>
                                            <label>Transfers the courses and teachers from the department
                                                <c:out value="${department.name}" /> to the selected
                                                department.
                                            </label>

                                        </div>
                                        <div class="col-md-6">
                                            <label class="bold">From :</label>
                                            <br />
                                            <label>
                                                <c:out value="${department.name}" />
                                            </label>
                                            <br />
                                            <label class="bold"> To :</label>
                                            <br />


                                            <input name="from" value="${department.id}" type="hidden" />

                                            <select class="form-control input mb-md" name="to" id="departmentList">

                                                <c:forEach var="department" items="${requestScope.departments}">
                                                    <option value="${department.id}">
                                                        <c:out value="${department.name}" />
                                                    </option>
                                                </c:forEach>

                                            </select>
                                            <input type="submit" value="Transfer" class="btn btn-primary">
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <hr class="tall" />
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

</body>

</html>