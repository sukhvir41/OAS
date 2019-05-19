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



        <style type="text/css">
            .page-header {
                margin: 0 0 0 0;
            }

            section.section {

                margin: 0 0;

            }
        </style>

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

                    <section class="section section-primary mb-none">
                        <div class="container">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="counters counters-text-light"> 
                                        <div class="col-md-4 col-xs-12 col-sm-12">
                                            <div class="counter">
                                                <i class="fa fa-users"></i>
                                                <strong id="studentCount" data-to="${requestScope.teacher}" >${requestScope.student}</strong>
                                            <label>Students</label>
                                        </div>
                                    </div>
                                    <div class="col-md-4 col-xs-12 col-sm-12">
                                        <div class="counter">
                                            <i class="fa fa-users"></i>
                                            <strong id="teacherCount" data-to="${requestScope.teacher}" >${requestScope.teacher}</strong>
                                            <label>Teachers</label>
                                        </div>
                                    </div>
                                    <div class="col-md-4 col-xs-12 col-sm-12">
                                        <div class="counter">

                                            <i class="fa fa-users">
                                            </i>

                                            <strong id="adminCount" data-to="${requestScope.admin}" >${requestScope.admin}</strong>
                                            <label>Admins</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>


                <div class="container">
                    <div class="row">
                        <div class="col-md-12">

                            <div class="row"> 
                                <div class="col-md-12">
                                    <hr class="tall">
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-12">
                                    <c:choose>
                                        <c:when test="${requestScope.handlerReady}">
                                            <div class="alert alert-success">
                                                <strong>Message!</strong> The network setting have been applied. Details ${requestScope.details}
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="alert alert-danger">
                                                <strong>Alert!</strong> Set the network settings. click <a href="/OAS/admin/network-settings" class="alert-link">here</a>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>

                            <div class="row"> 
                                <div class="col-md-12">
                                    <hr class="tall">
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-12">
                                    <div class="heading heading-primary heading-border heading-bottom-border">
                                        <h1 class="heading-primary">Server performance</h1>
                                    </div>
                                    <canvas id="chart" class="col-md-12"></canvas>
                                </div>
                                <div class="col-md-12" id="chartError" hidden="true" >
                                    <div class="alert alert-danger">
                                        <strong>Oh!</strong> The Machine is feeling shy to share it's details 
                                    </div>
                                </div>
                            </div>

                            <div class="row"> 
                                <div class="col-md-12">
                                    <hr class="tall">
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

    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.2/Chart.min.js" integrity="sha256-CfcERD4Ov4+lKbWbYqXD6aFM9M51gN4GUEtDhkWABMo=" crossorigin="anonymous"></script>
    <script src="/OAS/scripts/admin/admin-home.js"></script>

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
