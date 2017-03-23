<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>

        <!-- Basic -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">   

        <title>Teacher Subject Mapping - HOD</title>   

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
        <style>
            .sort{
                border: 1px solid #eee;
                width: 300px;
                min-height: 20px;
                list-style-type: none;
                margin: 0;
                padding: 5px 0 0 0;
                float: left;
                margin-right: 10px;
                border-radius: 5px
            }
            .sort li {
                margin: 0 5px 5px 5px;
                padding: 5px;
                font-size: 1.2em;
                width: 300px;
                border-radius: 5px
            }
        </style>

    </head>
    <body>
        <div class="body">
            <jsp:include page="/WEB-INF/hod/hodheader.jsp"></jsp:include>

                <div role="main" class="main">
                    <section class="page-header">
                        <div class="container">
                            <div class="row">
                                <div class="col-md-12">
                                    <ul class="breadcrumb">
                                        <li><a href="/OAS/teacher/hod">Home</a></li>
                                        <li>Teacher Subject Map</a></li>
                                    </ul>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <h1>Teacher Subject Mapping</h1>
                                </div>
                            </div>
                        </div>
                    </section>

                    <div class="container">
                        <div class="row">
                            <div class="col-md-12">
                                <h4>Drag and Drop subjects to respective teacher<button id="apply" class="btn btn-primary pull-right">Apply</button></h4>

                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="tabs tabs-vertical tabs-left">
                                            <ul class="nav nav-tabs col-sm-3">
                                            <c:forEach var="teacher" items="${requestScope.teachers}">
                                                <li>
                                                    <a href="#${teacher.id}" data-toggle="tab">${teacher.fName} ${teacher.lName}</a>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                        <div class="tab-content">
                                            <c:forEach var="teacher" items="${requestScope.teachers}">
                                                <div id="${teacher.id}" class="tab-pane active">
                                                    <ul id="${teacher.id}" class="list list-icons list-icons-sm sort connectedSortable">
                                                        <c:forEach var="teaching" items="${teacher.teaches}"> 
                                                                <c:if test="${teaching.classRoom.course.department.id eq requestScope.department.id}">
                                                                   <li value="${teaching.id}" class="active"><i class="fa fa-caret-right"></i> ${teaching}</li>
                                                                </c:if>
                                                        </c:forEach>
                                                    </ul>
                                                </div>    
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="tabs tabs-vertical tabs-left">
                                        <ul class="nav nav-tabs col-sm-3">
                                            <li class="active">
                                                <a href="#popular11" data-toggle="tab">Unassigned Subjects with Class</a>
                                            </li>
                                        </ul>
                                        <div class="tab-content">
                                            <ul class="list list-icons list-icons-sm sort connectedSortable">
                                                <c:forEach var="teaching" items="${requestScope.teachings}">
                                                    <li value="${teaching.id}"><i class="fa fa-caret-right"></i>${teaching}
                                                    </li>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <button id="apply" class="btn btn-primary pull-right">Apply</button>
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
        <script src="/OAS/scripts/jquery-ui.js"></script>
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

        <script>
            $(function () {
                $(".sort").sortable({
                    connectWith: ".connectedSortable"
                }).disableSelection();
            });

            $(document).ready(function () {
                $("#apply").click(function(){
                    console.log("clicked")
                    sendData();
                });
            });

            var sendData = function () {
                $.ajax({
                    url: "/OAS/teacher/hod/teachersubjectmappingpost",
                    
                    data: {
                        <c:forEach var="teacher" items="${requestScope.teachers}">
                        ${teacher.id} : gettcs($("#${teacher.id} li")),
                        </c:forEach>
                    },
                    method: "post",
                    success: function (data) {
                       if (data =="true") {
                            console.log("true")
                       }else{
                        console.log("false")
                       }
                       window.location.replace("/OAS/teacher/hod/teachersubjectmapping");
                    }
                });

            }

            var gettcs = function (teacher) {
                var values = new Array();
                teacher.each(function () {
                    values.push($(this).val());
                });
                return values;
            }
        </script>
    </body>
</html>
