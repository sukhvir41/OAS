
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>

        <!-- Basic -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">   
        
        <title>Login</title>    



        <!-- Mobile Metas -->
        <meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <meta name="theme-color" content="#4285f4">
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

        <!-- Skin CSS -->
        <link rel="stylesheet" href="css/skins/default.css">

        <!-- Theme Custom CSS -->
        <link rel="stylesheet" href="css/custom.css">

        <!-- Head Libs -->
        <script src="vendor/modernizr/modernizr.js"></script>
        <style type="text/css">
            .col-centered{
                float: none;
                margin: 0 auto;
            }
        </style>

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
            <div role="main" class="main">
                <section class="page-header">
                    <div class="container">
                        <div class="row">
                            <div class="col-md-12">
                                <h1>Login</h1>
                            </div>
                        </div>
                    </div>
                </section>

                <div class="container">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="featured-boxes">
                                <div class="row">
                                    <div class="col-sm-6 col-centered" >
                                        <div class="featured-box featured-box-primary align-left mt-xlg">
                                            <div class="box-content">
                                                <h4 class="heading-primary text-uppercase mb-md">Please Login</h4>
                                                <!-- from tag starts from here-->
                                                <form action="loginpost"  method="post" onsubmit="return submitCheck()">
                                                    <div class="row">
                                                        <div class="form-group">
                                                            <div class="col-md-12">
                                                                <label>Username or E-mail Address</label>
                                                                <input type="text" value="" class="form-control input-lg" tabindex="1" name="username" id="username">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="form-group">
                                                            <div class="col-md-12">
                                                                <a class="pull-right" href="forgotpassword">(Lost Password?)</a>
                                                                <label>Password</label>
                                                                <input type="password" value="" class="form-control input-lg" tabindex="2" name="password" id="password">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <c:choose>
                                                        <c:when test="${param.verified =='false'}">
                                                            <!-- SHOW THIS WHEN USERNAME AND PASSWORD IS INVLAID -->
                                                            <div class="row">
                                                                <div class="col-md-12 ">
                                                                    <div class="alert alert-danger" id="loginError">
                                                                        <strong>Error!</strong> Username or Password is wrong
                                                                    </div>
                                                                </div> 
                                                            </div> 
                                                        </c:when>
                                                        <c:otherwise>
                                                            <div class="row">
                                                                <div class="col-md-12 ">
                                                                    <div class="alert alert-danger" hidden id="loginError">
                                                                        <strong>Error!</strong> Username or Password is wrong
                                                                    </div>
                                                                </div> 
                                                            </div> 
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <div class="row">
                                                        <!-- SHOW THIS WHEN THRER IS AN ERROR -->
                                                        <div class="col-md-12 ">
                                                            <div class="alert alert-danger" hidden id="loginblank">
                                                                <strong>Error!</strong> Username or Password is empty
                                                            </div>
                                                        </div> 
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-6">
                                                            <span class="remember-box checkbox">
                                                                <label for="rememberme">
                                                                    <input type="checkbox" id="rememberme" name="rememberme" value="true">Stay signed in
                                                                </label>
                                                            </span>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <input type="submit" value="Login" class="btn btn-primary pull-right mb-xl" data-loading-text="Loading..." tabindex="3">
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <center>
                                                                <a  class="" href="register">Register Now</a>
                                                            </center>
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
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
        <script src="scripts/login.js"></script>

        <!-- Theme Base, Components and Settings -->
        <script src="js/theme.js"></script>

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
