<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>

        <!-- Basic -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">	

        <title>Home - Admin</title>	

       
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
                                    <li>Reports</li>
                                </ul>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <h1>Reports</h1>
                            </div>
                        </div>
                    </div>
                </section>

                <div class="container">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="row">
                                <div class="col-md-6">
                                    <form action="/OAS/admin/generatereportpost" method="post" target="_blank">
                                        <div class="row">
                                            <div class="form-group">
                                                <div class="col-md-6">
                                                <label>Course</label>
                                                    <select class="form-control" name="course" id="course">
                                                        <c:forEach var="course" items="${requestScope.courses}">
                                                            <option value="${course.id}">${course.name}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="form-group">
                                                <div class="col-md-6">
                                                <label>Class Rooom</label>
                                                    <select required="true" class="form-control" name="classroom" id="classroom">
                                                        
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="form-group">
                                                <div class="col-md-6">
                                                <label>Start Date</label>
                                                    <input required="true" class="form-control" type="date" name="startdate">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="form-group">
                                                <div class="col-md-6">
                                                <label>End Date</label>
                                                    <input required="true" class="form-control" type="date" name="enddate">
                                                </div>
                                            </div>
                                        </div>


                                        <div class="row">
                                            <div class="col-md-12">
                                                <input value="Generate Report" class="btn btn-primary pull-left push-bottom" data-loading-text="Loading..." type="submit">
                                            </div>
                                        </div>
                                    </form>
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
            <script src="/OAS/scripts/adminreport.js"></script>
            <script src="/OAS/scripts/mustache.js"></script>
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