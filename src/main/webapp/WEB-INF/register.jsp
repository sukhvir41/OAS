<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>

    <!-- Basic -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <title>Registration</title>


    <!-- Mobile Metas -->
    <meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">

    <!-- Android devices meta-->
    <meta name="theme-color" content="#0077b3">
    <link rel="manifest" href="/OAS/manifest.json">

    <!-- Apple devices metas -->
    <link rel="apple-touch-startup-image" href="icon.png">
    <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
    <link rel="apple-touch-icon" href="touch-icon-iphone.png">
    <link rel="apple-touch-icon" sizes="76x76" href="touch-icon-ipad.png">
    <link rel="apple-touch-icon" sizes="120x120" href="touch-icon-iphone-retina.png">
    <link rel="apple-touch-icon" sizes="152x152" href="touch-icon-ipad-retina.png">

    <!-- Windows devices metas-->
    <meta name="msapplication-square70x70logo" content="icon_smalltile.png">
    <meta name="msapplication-square150x150logo" content="icon_mediumtile.png">
    <meta name="msapplication-wide310x150logo" content="icon_widetile.png">
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
        .col-centered {
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
                            <h1>Please Register</h1>
                        </div>
                    </div>
                </div>
            </section>

            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-12">
                        <div class="featured-boxes">
                            <div class="row">
                                <div class="col-sm-6 col-centered">
                                    <div class="featured-box featured-box-primary align-left mt-xlg">
                                        <div class="box-content">
                                            <h4 class="heading-primary text-uppercase mb-md">Register An Account</h4>
                                            <!-- from tag starts from here-->
                                            <form id="register" method="post" action="register-post">

                                                <jsp:include page="/WEB-INF/message-box" />

                                                <div class="row">
                                                    <div class="form-group">
                                                        <div class="col-md-8">
                                                            <label>First Name</label>
                                                            <input class="form-control input-lg" placeholder="sam"
                                                                type="text" name="firstname" id="firstname"
                                                                value="${param.firstname}">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="form-group">
                                                        <div class="col-md-8">
                                                            <label>Last Name</label>
                                                            <input class="form-control input-lg" placeholder="smith"
                                                                type="text" name="lastname" id="lastname"
                                                                value="${param.lastname}">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="form-group">
                                                        <div class="col-md-8">
                                                            <label>E-mail Address</label>
                                                            <input class="form-control input-lg"
                                                                placeholder="sam.smith@mail.com" type="text"
                                                                name="email" id="email" value="${param.email}">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="form-group">
                                                        <div class="col-md-8">
                                                            <label>Username</label>
                                                            <input class="form-control input-lg" placeholder="samsmart"
                                                                type="text" name="username" id="username"
                                                                value="${param.username}">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="form-group">
                                                        <div class="col-md-8">
                                                            <label>Password</label>
                                                            <input class="form-control input-lg" placeholder="********"
                                                                type="password" name="password" id="password">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="form-group">
                                                        <div class="col-md-8">
                                                            <label>Re-enter Password</label>
                                                            <input class="form-control input-lg" placeholder="********"
                                                                type="password" id="repassword" name="repassword">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="form-group">
                                                        <div class="col-md-8">
                                                            <label>Number</label>
                                                            <input class="form-control input-lg"
                                                                placeholder="9922288888" type="number" name="number"
                                                                id="number" value="${param.number}">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="form-group">
                                                        <div class="col-md-8">
                                                            <label>Type of Account</label>
                                                            <div class="radio">
                                                                <label>
                                                                    <input name="type" value="student" checked="true"
                                                                        type="radio">
                                                                    Student
                                                                </label>
                                                            </div>
                                                            <div class="radio">
                                                                <label>
                                                                    <input name="type" value="teacher" type="radio">
                                                                    Teacher
                                                                </label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <hr class="tall">
                                                <!-- student details block-->
                                                <div class="row" id="student-details">
                                                    <div class="form-group">
                                                        <div class="col-md-8">

                                                            <h4>Details</h4>
                                                            <label>Roll number</label>
                                                            <input class="form-control input-lg" placeholder="50"
                                                                type="number" name="rollnumber" id="rollnumber">
                                                            <br>
                                                            <label>Course</label>
                                                            <select class="form-control mb-md" id="course"
                                                                name="course">
                                                            </select>
                                                            <br>
                                                            <label>Class</label>
                                                            <select class="form-control mb-md" id="classroom"
                                                                name="classroom">
                                                            </select>
                                                            <br>
                                                            <label>Subjects</label>
                                                            <br />
                                                            <label>Pre-selected subjects</label>
                                                            <div id="subjects">
                                                            </div>
                                                            <div id="electiveSubjects">
                                                            </div>

                                                        </div>
                                                    </div>
                                                </div>
                                                <!-- teacher details block-->
                                                <div class="row" id="teacher-details">
                                                    <div class="form-group">
                                                        <div class="col-md-8">
                                                            <h4>Details</h4>
                                                            <!--<label>Departments</label>-->
                                                            <div id="departments">
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <input value="Register"
                                                            class="btn btn-primary pull-right push-bottom"
                                                            data-loading-text="Loading..." type="submit">
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

    <!-- Theme Base, Components and Settings -->
    <script src="js/theme.js"></script>

    <!-- Theme Custom -->
    <script src="js/custom.js"></script>

    <!-- Theme Initialization Files -->
    <script src="js/theme.init.js"></script>

    <script src="scripts/jquery.validate.min.js"></script>
    <script src="scripts/mustache.js"></script>
    <script src="scripts/vue.js"></script>
    <script src="scripts/multi-select.js"></script>
    <script src="scripts/register.js"></script>


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