<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>

    <!-- Basic -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">	

    <title>Change Password - Admin</title>	

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
                    <header id="header" data-plugin-options='{"stickyEnabled": true, "stickyEnableOnBoxed": true, "stickyEnableOnMobile": true, "stickyStartAt": 57, "stickySetTop": "-10px", "stickyChangeLogo": true}' >
                        <div class="header-body" style="min-height: 90px;">
                            <div class="header-container container">
                                <div class="header-row">
                                    <div class="header-column">
                                        <div class="header-column"></div>
                                        <div class="header-row">
                                            <div class="header-nav">
                                                <button class="btn header-btn-collapse-nav" data-toggle="collapse" data-target=".header-nav-main">
                                                    <i class="fa fa-bars"></i>
                                                </button>
                                                <div class="header-nav-main header-nav-main-effect-1 header-nav-main-sub-effect-1 collapse">
                                                    <nav>
                                                        <ul class="nav nav-pills" id="mainNav">
                                                            <li class="dropdown">
                                                                <a class="dropdown-toggle" href="index.html">
                                                                    Home(change the options)
                                                                </a>
                                                                <ul class="dropdown-menu">
                                                                    <li>
                                                                        <a href="index.html">Landing Page</a>
                                                                    </li>
                                                                    <li class="dropdown-submenu">
                                                                        <a href="index-classic.html">Classic</a>
                                                                        <ul class="dropdown-menu">
                                                                            <li><a href="index-classic.html">Classic - Original <span class="tip tip-dark">hot</span></a></li>
                                                                            <li><a href="index-classic-color.html">Classic - Color</a></li>
                                                                            <li><a href="index-classic-light.html">Classic - Light</a></li>
                                                                            <li><a href="index-classic-video.html">Classic - Video</a></li>
                                                                            <li><a href="index-classic-video-light.html">Classic - Video - Light</a></li>
                                                                        </ul>
                                                                    </li>
                                                                    <li class="dropdown-submenu">
                                                                        <a href="index-corporate.html">Corporate <span class="tip">new</span></a>
                                                                        <ul class="dropdown-menu">
                                                                            <li><a href="index-corporate.html">Corporate - Version 1 <span class="tip tip-dark">hot</span></a></li>
                                                                            <li><a href="index-corporate-2.html">Corporate - Version 2</a></li>
                                                                            <li><a href="index-corporate-3.html">Corporate - Version 3</a></li>
                                                                            <li><a href="index-corporate-4.html">Corporate - Version 4</a></li>
                                                                            <li><a href="index-corporate-5.html">Corporate - Version 5</a></li>
                                                                            <li><a href="index-corporate-6.html">Corporate - Version 6</a></li>
                                                                            <li><a href="index-corporate-7.html">Corporate - Version 7</a></li>
                                                                            <li><a href="index-corporate-8.html">Corporate - Version 8</a></li>
                                                                            <li><a href="index-corporate-hosting.html">Corporate - Hosting <span class="tip">new</span></a></li>
                                                                        </ul>
                                                                    </li>
                                                                    <li class="dropdown-submenu">
                                                                        <a href="#">One Page</a>
                                                                        <ul class="dropdown-menu">
                                                                            <li><a href="index-one-page.html">One Page Original</a></li>
                                                                        </ul>
                                                                    </li>
                                                                </ul>
                                                            </li>
                                                            <li class="dropdown dropdown-mega">
                                                                <a class="dropdown-toggle" href="#">
                                                                    Shortcodes
                                                                </a>
                                                                <ul class="dropdown-menu">
                                                                    <li>
                                                                        <div class="dropdown-mega-content">
                                                                            <div class="row">
                                                                                <div class="col-md-3">
                                                                                    <span class="dropdown-mega-sub-title">Shortcodes 1</span>
                                                                                    <ul class="dropdown-mega-sub-nav">
                                                                                        <li><a href="shortcodes-accordions.html">Accordions</a></li>
                                                                                        <li><a href="shortcodes-toggles.html">Toggles</a></li>
                                                                                        <li><a href="shortcodes-tabs.html">Tabs</a></li>
                                                                                        <li><a href="shortcodes-icons.html">Icons</a></li>
                                                                                        <li><a href="shortcodes-icon-boxes.html">Icon Boxes</a></li>
                                                                                        <li><a href="shortcodes-carousels.html">Carousels</a></li>
                                                                                        <li><a href="shortcodes-modals.html">Modals</a></li>
                                                                                        <li><a href="shortcodes-lightboxes.html">Lightboxes</a></li>
                                                                                    </ul>
                                                                                </div>
                                                                                <div class="col-md-3">
                                                                                    <span class="dropdown-mega-sub-title">Shortcodes 2</span>
                                                                                    <ul class="dropdown-mega-sub-nav">
                                                                                        <li><a href="shortcodes-buttons.html">Buttons</a></li>
                                                                                        <li><a href="shortcodes-labels.html">Labels</a></li>
                                                                                        <li><a href="shortcodes-lists.html">Lists</a></li>
                                                                                        <li><a href="shortcodes-image-gallery.html">Image Gallery</a></li>
                                                                                        <li><a href="shortcodes-image-frames.html">Image Frames</a></li>
                                                                                        <li><a href="shortcodes-testimonials.html">Testimonials</a></li>
                                                                                        <li><a href="shortcodes-blockquotes.html">Blockquotes</a></li>
                                                                                        <li><a href="shortcodes-word-rotator.html">Word Rotator</a></li>
                                                                                    </ul>
                                                                                </div>
                                                                                <div class="col-md-3">
                                                                                    <span class="dropdown-mega-sub-title">Shortcodes 3</span>
                                                                                    <ul class="dropdown-mega-sub-nav">
                                                                                        <li><a href="shortcodes-call-to-action.html">Call to Action</a></li>
                                                                                        <li><a href="shortcodes-pricing-tables.html">Pricing Tables</a></li>
                                                                                        <li><a href="shortcodes-tables.html">Tables</a></li>
                                                                                        <li><a href="shortcodes-progressbars.html">Progress Bars</a></li>
                                                                                        <li><a href="shortcodes-counters.html">Counters</a></li>
                                                                                        <li><a href="shortcodes-sections-parallax.html">Sections &amp; Parallax</a></li>
                                                                                        <li><a href="shortcodes-tooltips-popovers.html">Tooltips &amp; Popovers</a></li>
                                                                                        <li><a href="shortcodes-sticky-elements.html">Sticky Elements</a></li>
                                                                                    </ul>
                                                                                </div>
                                                                                <div class="col-md-3">
                                                                                    <span class="dropdown-mega-sub-title">Shortcodes 4</span>
                                                                                    <ul class="dropdown-mega-sub-nav">
                                                                                        <li><a href="shortcodes-headings.html">Headings</a></li>
                                                                                        <li><a href="shortcodes-dividers.html">Dividers</a></li>
                                                                                        <li><a href="shortcodes-animations.html">Animations</a></li>
                                                                                        <li><a href="shortcodes-medias.html">Medias</a></li>
                                                                                        <li><a href="shortcodes-maps.html">Maps</a></li>
                                                                                        <li><a href="shortcodes-arrows.html">Arrows</a></li>
                                                                                        <li><a href="shortcodes-alerts.html">Alerts</a></li>
                                                                                        <li><a href="shortcodes-posts.html">Posts</a></li>
                                                                                    </ul>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </li>
                                                                </ul>
                                                            </li>
                                                            <li class="dropdown active">
                                                                <a class="dropdown-toggle" href="#">
                                                                    Features
                                                                </a>

                                                                <ul class="dropdown-menu">
                                                                    <li class="dropdown-submenu">
                                                                        <a href="#">Headers</a>
                                                                        <ul class="dropdown-menu">
                                                                            <li class="dropdown-submenu">
                                                                                <a href="#">Default</a>
                                                                                <ul class="dropdown-menu">
                                                                                    <li><a href="index-classic.html">Default</a></li>
                                                                                    <li><a href="index-header-language-dropdown.html">Default + Language Dropdown</a></li>
                                                                                    <li><a href="index-header-big-logo.html">Default + Big Logo</a></li>
                                                                                </ul>
                                                                            </li>
                                                                            <li class="dropdown-submenu">
                                                                                <a href="#">Flat</a>
                                                                                <ul class="dropdown-menu">
                                                                                    <li><a href="index-header-flat.html">Flat</a></li>
                                                                                    <li><a href="index-header-flat-top-bar.html">Flat + Top Bar</a></li>
                                                                                    <li><a href="index-header-flat-colored-top-bar.html">Flat + Colored Top Bar</a></li>
                                                                                    <li><a href="index-header-flat-top-bar-search.html">Flat + Top Bar with Search</a></li>
                                                                                </ul>
                                                                            </li>
                                                                            <li><a href="index-header-center.html">Center</a></li>
                                                                            <li><a href="index-header-below-slider.html">Below Slider</a></li>
                                                                            <li><a href="index-header-full-video.html">Full Video</a></li>
                                                                            <li><a href="index-header-narrow.html">Narrow</a></li>
                                                                            <li><a href="index-header-always-sticky.html">Always Sticky</a></li>
                                                                            <li class="dropdown-submenu">
                                                                                <a href="#">Transparent</a>
                                                                                <ul class="dropdown-menu">
                                                                                    <li><a href="index-header-transparent.html">Transparent</a></li>
                                                                                    <li><a href="index-header-transparent-bottom-border.html">Transparent - Bottom Border</a></li>
                                                                                    <li><a href="index-header-semi-transparent.html">Semi Transparent</a></li>
                                                                                    <li><a href="index-header-semi-transparent-light.html">Semi Transparent - Light</a></li>
                                                                                </ul>
                                                                            </li>
                                                                            <li><a href="index-header-full-width.html">Full-Width</a></li>
                                                                            <li class="dropdown-submenu">
                                                                                <a href="#">Navbar</a>
                                                                                <ul class="dropdown-menu">
                                                                                    <li><a href="index-header-navbar.html">Navbar</a></li>
                                                                                    <li><a href="index-header-navbar-extra-info.html">Navbar + Extra Info</a></li>
                                                                                </ul>
                                                                            </li>
                                                                            <li><a href="index-header-signin.html">Sign In / Sign Up</a></li>
                                                                            <li><a href="index-header-logged.html">Logged</a></li>
                                                                        </ul>
                                                                    </li>
                                                                    <li class="dropdown-submenu">
                                                                        <a href="#">Navigations</a>
                                                                        <ul class="dropdown-menu">
                                                                            <li><a href="index-classic.html">Default</a></li>
                                                                            <li><a href="index-navigation-stripe.html">Stripe</a></li>
                                                                            <li><a href="index-navigation-top-line.html">Top Line</a></li>
                                                                            <li><a href="index-navigation-dark-dropdown.html">Dark Dropdown</a></li>
                                                                            <li><a href="index-navigation-colors.html">Colors</a></li>
                                                                        </ul>
                                                                    </li>
                                                                    <li class="dropdown-submenu">
                                                                        <a href="#">Footers</a>
                                                                        <ul class="dropdown-menu">
                                                                            <li><a href="index-classic.html#footer">Default</a></li>
                                                                            <li><a href="index-footer-advanced.html#footer">Advanced</a></li>
                                                                            <li><a href="index-footer-simple.html#footer">Simple</a></li>
                                                                            <li><a href="index-footer-light.html#footer">Light</a></li>
                                                                            <li><a href="index-footer-light-narrow.html#footer">Light Narrow</a></li>
                                                                            <li class="dropdown-submenu">
                                                                                <a href="#">Colors</a>
                                                                                <ul class="dropdown-menu">
                                                                                    <li><a href="index-footer-color-primary.html#footer">Primary Color</a></li>
                                                                                    <li><a href="index-footer-color-secondary.html#footer">Secondary Color</a></li>
                                                                                    <li><a href="index-footer-color-tertiary.html#footer">Tertiary Color</a></li>
                                                                                    <li><a href="index-footer-color-quaternary.html#footer">Quaternary Color</a></li>
                                                                                </ul>
                                                                            </li>
                                                                            <li><a href="index-footer-latest-work.html#footer">Latest Work</a></li>
                                                                            <li><a href="index-footer-contact-form.html#footer">Contact Form</a></li>
                                                                        </ul>
                                                                    </li>
                                                                    <li class="dropdown-submenu">
                                                                        <a href="#">Page Headers</a>
                                                                        <ul class="dropdown-menu">
                                                                            <li><a href="index-page-header-default.html">Default</a></li>
                                                                            <li class="dropdown-submenu">
                                                                                <a href="#">Colors</a>
                                                                                <ul class="dropdown-menu">
                                                                                    <li><a href="index-page-header-color-primary.html">Primary Color</a></li>
                                                                                    <li><a href="index-page-header-color-secondary.html">Secondary Color</a></li>
                                                                                    <li><a href="index-page-header-color-tertiary.html">Tertiary Color</a></li>
                                                                                    <li><a href="index-page-header-color-quaternary.html">Quaternary Color</a></li>
                                                                                </ul>
                                                                            </li>
                                                                            <li><a href="index-page-header-light.html">Light</a></li>
                                                                            <li><a href="index-page-header-light-reverse.html">Light - Reverse</a></li>
                                                                            <li><a href="index-page-header-custom-background.html">Custom Background</a></li>
                                                                            <li><a href="index-page-header-parallax.html">Parallax</a></li>
                                                                            <li><a href="index-page-header-center.html">Center</a></li>
                                                                        </ul>
                                                                    </li>
                                                                    <li class="dropdown-submenu">
                                                                        <a href="#">Admin Extension <span class="tip tip-dark">hot</span> <em class="not-included">(Not Included)</em></a>
                                                                        <ul class="dropdown-menu">
                                                                            <li><a href="feature-admin-forms-basic.html">Forms Basic</a></li>
                                                                            <li><a href="feature-admin-forms-advanced.html">Forms Advanced</a></li>
                                                                            <li><a href="feature-admin-forms-wizard.html">Forms Wizard</a></li>
                                                                            <li><a href="feature-admin-forms-code-editor.html">Code Editor</a></li>
                                                                            <li><a href="feature-admin-tables-advanced.html">Tables Advanced</a></li>
                                                                            <li><a href="feature-admin-tables-responsive.html">Tables Responsive</a></li>
                                                                            <li><a href="feature-admin-tables-editable.html">Tables Editable</a></li>
                                                                            <li><a href="feature-admin-tables-ajax.html">Tables Ajax</a></li>
                                                                            <li><a href="feature-admin-charts.html">Charts</a></li>
                                                                        </ul>
                                                                    </li>
                                                                    <li class="dropdown-submenu">
                                                                        <a href="#">Sliders</a>
                                                                        <ul class="dropdown-menu">
                                                                            <li><a href="index-classic.html">Revolution Slider</a></li>
                                                                            <li><a href="index-slider-2.html">Nivo Slider</a></li>
                                                                        </ul>
                                                                    </li>
                                                                    <li class="dropdown-submenu">
                                                                        <a href="#">Layout Options</a>
                                                                        <ul class="dropdown-menu">
                                                                            <li><a href="feature-layout-boxed.html">Boxed</a></li>
                                                                            <li><a href="feature-layout-dark.html">Dark</a></li>
                                                                            <li><a href="feature-layout-rtl.html">RTL</a></li>
                                                                        </ul>
                                                                    </li>
                                                                    <li class="dropdown-submenu">
                                                                        <a href="#">Extra</a>
                                                                        <ul class="dropdown-menu">
                                                                            <li><a href="feature-typography.html">Typography</a></li>
                                                                            <li><a href="feature-grid-system.html">Grid System</a></li>
                                                                            <li><a href="feature-page-loading.html">Page Loading</a></li>
                                                                            <li><a href="feature-lazy-load.html">Lazy Load</a></li>
                                                                        </ul>
                                                                    </li>
                                                                </ul>
                                                            </li>
                                                            <li class="dropdown">
                                                                <a class="dropdown-toggle" href="#">
                                                                    Pages
                                                                </a>
                                                                <ul class="dropdown-menu">
                                                                    <li class="dropdown-submenu">
                                                                        <a href="#">Shop</a>
                                                                        <ul class="dropdown-menu">
                                                                            <li><a href="shop-full-width.html">Shop - Full Width</a></li>
                                                                            <li><a href="shop-sidebar.html">Shop - Sidebar</a></li>
                                                                            <li><a href="shop-product-full-width.html">Shop - Product Full Width</a></li>
                                                                            <li><a href="shop-product-sidebar.html">Shop - Product Sidebar</a></li>
                                                                            <li><a href="shop-cart.html">Shop - Cart</a></li>
                                                                            <li><a href="shop-login.html">Shop - Login</a></li>
                                                                            <li><a href="shop-checkout.html">Shop - Checkout</a></li>
                                                                        </ul>
                                                                    </li>
                                                                    <li class="dropdown-submenu">
                                                                        <a href="#">Blog</a>
                                                                        <ul class="dropdown-menu">
                                                                            <li><a href="blog-full-width.html">Blog Full Width</a></li>
                                                                            <li><a href="blog-large-image.html">Blog Large Image</a></li>
                                                                            <li><a href="blog-medium-image.html">Blog Medium Image</a></li>
                                                                            <li><a href="blog-timeline.html">Blog Timeline</a></li>
                                                                            <li><a href="blog-post.html">Single Post</a></li>
                                                                        </ul>
                                                                    </li>
                                                                    <li class="dropdown-submenu">
                                                                        <a href="#">Layouts</a>
                                                                        <ul class="dropdown-menu">
                                                                            <li><a href="page-full-width.html">Full width</a></li>
                                                                            <li><a href="page-left-sidebar.html">Left Sidebar</a></li>
                                                                            <li><a href="page-right-sidebar.html">Right Sidebar</a></li>
                                                                            <li><a href="page-left-and-right-sidebars.html">Left and Right Sidebars</a></li>
                                                                            <li><a href="page-sticky-sidebar.html">Sticky Sidebar</a></li>
                                                                            <li><a href="page-secondary-navbar.html">Secondary Navbar</a></li>
                                                                        </ul>
                                                                    </li>
                                                                    <li class="dropdown-submenu">
                                                                        <a href="#">Extra</a>
                                                                        <ul class="dropdown-menu">
                                                                            <li><a href="page-404.html">404 Error</a></li>
                                                                            <li><a href="page-coming-soon.html">Coming Soon</a></li>
                                                                            <li><a href="page-maintenance-mode.html">Maintenance Mode</a></li>
                                                                            <li><a href="sitemap.html">Sitemap</a></li>
                                                                        </ul>
                                                                    </li>
                                                                    <li><a href="page-custom-header.html">Custom Header</a></li>
                                                                    <li><a href="page-team.html">Team</a></li>
                                                                    <li><a href="page-services.html">Services</a></li>
                                                                    <li><a href="page-careers.html">Careers</a></li>
                                                                    <li><a href="page-our-office.html">Our Office</a></li>
                                                                    <li><a href="page-faq.html">FAQ</a></li>
                                                                    <li><a href="page-login.html">Login / Register</a></li>
                                                                </ul>
                                                            </li>
                                                            <li class="dropdown">
                                                                <a class="dropdown-toggle" href="#">
                                                                    Portfolio
                                                                </a>
                                                                <ul class="dropdown-menu">
                                                                    <li><a href="portfolio-4-columns.html">4 Columns</a></li>
                                                                    <li><a href="portfolio-3-columns.html">3 Columns</a></li>
                                                                    <li><a href="portfolio-2-columns.html">2 Columns</a></li>
                                                                    <li><a href="portfolio-lightbox.html">Portfolio Lightbox</a></li>
                                                                    <li><a href="portfolio-timeline.html">Portfolio Timeline</a></li>
                                                                    <li><a href="portfolio-full-width.html">Portfolio Full Width</a></li>
                                                                    <li><a href="portfolio-single-project.html">Single Project</a></li>
                                                                </ul>
                                                            </li>
                                                            <li class="dropdown">
                                                                <a class="dropdown-toggle" href="#">
                                                                    About Us
                                                                </a>
                                                                <ul class="dropdown-menu">
                                                                    <li><a href="about-us.html">About Us</a></li>
                                                                    <li><a href="about-us-basic.html">About Us - Basic</a></li>
                                                                    <li><a href="about-me.html">About Me</a></li>
                                                                </ul>
                                                            </li>
                                                            <li class="dropdown">
                                                                <a class="dropdown-toggle" href="#">
                                                                    Contact Us
                                                                </a>
                                                                <ul class="dropdown-menu">
                                                                    <li><a href="contact-us.html">Contact Us - Basic</a></li>
                                                                    <li><a href="contact-us-advanced.php">Contact Us - Advanced</a></li>
                                                                </ul>
                                                            </li>
                                                            <li class="dropdown dropdown-mega dropdown-mega-signin signin logged" id="headerAccount">
                                                                <a class="dropdown-toggle" href="page-login.html">
                                                                    <i class="fa fa-user"></i> username here of the admin
                                                                </a>
                                                                <ul class="dropdown-menu">
                                                                    <li>
                                                                        <div class="dropdown-mega-content">
                                                                            <div class="row">
                                                                                <div class="col-md-8">
                                                                                    <div class="user-avatar">
                                                                                <!-- <div class="img-thumbnail">
                                                                                        <img src="img/clients/client-1.jpg" alt="">
                                                                                    </div> -->
                                                                                    <p><strong>Username here of admin</strong><span>Administrator</span></p>
                                                                                </div>
                                                                            </div>
                                                                            <div class="col-md-4">
                                                                                <ul class="list-account-options">
                                                                                    <li>
                                                                                        <a href="#">My Account(add edit account changes page)</a>
                                                                                    </li>
                                                                                    <li>
                                                                                        <a href="/OAS/logout">Log Out(addd sesion deactivate page)</a>
                                                                                    </li>
                                                                                </ul>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </li>
                                                            </ul>
                                                        </li>
                                                    </ul>
                                                </nav>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </header>

                <div role="main" class="main">
                    <section class="page-header">
                        <div class="container">
                            <div class="row">
                                <div class="col-md-12">
                                    <ul class="breadcrumb">
                                        <li><a href="/OAS/admin">Home</a></li>
                                        <li><a href="/OAS/admin/admindetails">Admin Details</a></li>
                                        <li>Change Password</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <h1>Change Password</h1>
                                </div>
                            </div>
                        </div>
                    </section>

                    <div class="container">
                        <div class="row">
                            <div class="col-md-12">
                                <form action="/OAS/admin/admindetails/changepasswordpost" method="Post">
                                    <h4>Username : ${requestScope.username}</h4>
                                    <div class="row">
                                        
                                        <div class="form-group">
                                            <div class="col-md-4">
                                                <label>Old Password</label>
                                                <input class="form-control input-lg" type="Password" name="oldpassword" id="oldpassword" required="true">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="form-group">
                                            <div class="col-md-4">
                                                <label>New Password</label>
                                                <input class="form-control input-lg" type="Password" name="newpassword" id="newpassword" required="true">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="form-group">
                                            <div class="col-md-4">
                                                <label>Re Enter New Password</label>
                                                <input class="form-control input-lg" type="Password" name="renewpassword" id="renewpassword" required="true">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-12">
                                        <input value="Update Password" class="btn btn-primary pull-left push-bottom" data-loading-text="Loading..." type="submit">
                                        </div>
                                    </div>
                                </form>
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
