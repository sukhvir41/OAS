<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>

        <!-- Basic -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">	

        <title>Forgot Password</title>	


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
                                <h1>Forgot password</h1>
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
                                                <h4 class="heading-primary text-uppercase mb-md">Forgot Password</h4>
                                                <!-- from tag starts from here-->
                                                <form>
                                                    <div class="row">
                                                        <div class="form-group">
                                                            <div class="col-md-12">
                                                                <label>E-mail Address</label>
                                                                <input type="text" class="form-control input-lg"  tabindex="1" name="email" id="email" tabindex="1">
                                                                <br>
                                                                <label>An E-mail will be send to this E-mail address to reset your password </label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <!-- SHOW THIS WHEN THRER IS AN ERROR -->
                                                        <div class="col-md-12 ">
                                                            <div class="alert alert-danger" hidden id="emailblank">
                                                                <strong>Error!</strong> Please enter a valid E-mail address
                                                            </div>
                                                        </div> 
                                                    </div>
                                                    <div class="row">
                                                        <!-- SHOW THIS WHEN THRER IS AN ERROR -->
                                                        <div class="col-md-12 ">
                                                            <div class="alert alert-success" hidden id="emailsent">
                                                                <strong>Success!</strong> E-mail has been sent
                                                            </div>
                                                        </div> 
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <button type="button" class="btn btn-primary pull-right"  tabindex="2" id="submit">Submit</button>
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
        <script src="scripts/forgotpassword.js"></script>

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
