<%-- 
    Document   : adminheader
    Created on : Feb 21, 2017, 11:47:15 PM
    Author     : sukhvir
    --%>

    <%@page contentType="text/html" pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <header id="header" data-plugin-options='{"stickyEnabled": true, "stickyEnableOnBoxed": true, "stickyEnableOnMobile": true, "stickyStartAt": 57, "stickySetTop": "-10px", "stickyChangeLogo": true}'>
        <div class="header-body" style=" min-height: 90px;">
            <div class="header-container container">
                <div class="header-row">
                    <div class="header-column">
                        <div class="header-logo">
                            <a href="/OAS/admin">
                            <img alt="Porto" width="111" height="54" d src="/OAS/image/logo.png">
                            </a>
                        </div>
                    </div>
                    <div class="header-column">
                        <div class="header-nav">
                            <button class="btn header-btn-collapse-nav" data-toggle="collapse" data-target=".header-nav-main">
                                <i class="fa fa-bars"></i>
                            </button>
                            <div class="header-nav-main header-nav-main-effect-1 header-nav-main-sub-effect-1 collapse">
                                <nav>
                                    <ul class="nav nav-pills" id="mainNav">
                                        <li>
                                            <a href="/OAS/admin">
                                                Home
                                            </a>
                                        </li>
                                        <li class="dropdown">
                                            <a class="dropdown-toggle">
                                                Users
                                            </a>
                                            <ul class="dropdown-menu">
                                                <li>
                                                    <a href="/OAS/admin/admins">Admin</a>
                                                </li>
                                                <li class="dropdown-submenu">
                                                    <a>Teacher</a>
                                                    <ul class="dropdown-menu">
                                                        <li><a href="/OAS/admin/teachers">Teachers</a></li>
                                                        <li><a href="/OAS/admin/teachers/unaccountedteachers">Unaccounted Teachers</a></li>
                                                    </ul>
                                                </li>
                                                <li class="dropdown-submenu">
                                                    <a>Student</a>
                                                    <ul class= "dropdown-menu">
                                                        <li><a href="/OAS/admin/students">Students</a></li>
                                                        <li><a href="/OAS/admin/students/unaccountedstudents">Unaccounted Students</a></li>
                                                    </ul>
                                                </li>
                                            </ul>
                                        </li>
                                        <li class="dropdown">
                                            <a class="dropdown-toggle">
                                                Entities
                                            </a>
                                            <ul class="dropdown-menu">
                                                <li><a href="/OAS/admin/departments">Department</a></li>
                                                <li><a href="/OAS/admin/courses">Courses</a></li>
                                                <li><a href="/OAS/admin/classrooms">Class Rooms</a></li>
                                                <li><a href="/OAS/admin/subjects">Subjects</a></li>
                                            </ul>
                                        </li>
                                        <li>
                                            <a href="/OAS/admin/lectures">
                                                Lectures
                                            </a>
                                        </li>
                                        <li class="dropdown">
                                            <a class="dropdown-toggle">
                                                Reports
                                            </a>
                                            <ul class="dropdown-menu">
                                                <li><a href="/OAS/admin/reports">Reports</a></li>
                                                <li><a href="/OAS/admin/oldreports">Old Reports</a></li>
                                            </ul>
                                        </li>
                                        <li>
                                            <a href="/OAS/admin/endsemister">
                                                End Semister
                                            </a>
                                        </li>
                                        <li>
                                            <a href="/OAS/admin/networksettings">Network settings</a>

                                        </li>
                                        <li class="dropdown dropdown-mega dropdown-mega-signin signin logged" id="headerAccount">
                                            <a class="dropdown-toggle">
                                                <i class="fa fa-user"></i> ${sessionScope.admin.username}
                                            </a>
                                            <ul class="dropdown-menu">
                                                <li>
                                                    <div class="dropdown-mega-content">
                                                        <div class="row">
                                                            <div class="col-md-8">
                                                                <div class="user-avatar">
                                                                    <p><strong>${sessionScope.admin.username}</strong><span>Administrator ${sessionScope.admin.type}</span></p>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-4">
                                                                <ul class="list-account-options">
                                                                    <li>
                                                                        <a href="/OAS/admin/myaccount">My Account</a>
                                                                    </li>
                                                                    <li>
                                                                        <a href="/OAS/logout">Log Out</a>
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
    </header>
