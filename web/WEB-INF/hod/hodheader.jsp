<%-- 
    Document   : hodheader
    Created on : 12 Mar, 2017, 11:45:03 PM
    Author     : icr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<header id="header" data-plugin-options='{"stickyEnabled": true, "stickyEnableOnBoxed": true, "stickyEnableOnMobile": true, "stickyStartAt": 57, "stickySetTop": "-10px", "stickyChangeLogo": true}'>
    <div class="header-body" style=" min-height: 90px;">
        <div class="header-container container">
            <div class="header-row">
            <div class="header-column">
                        <div class="header-logo">
                            <a href="/OAS/teacher">
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
                                        <a href="/OAS/teacher/hod">
                                            Home
                                        </a>
                                    </li>
                                    <li class="dropdown">
                                        <a class="dropdown-toggle">
                                            Users
                                        </a>
                                        <ul class="dropdown-menu">
                                            <li class="dropdown-submenu">
                                                <a>Teacher</a>
                                                <ul class="dropdown-menu">
                                                    <li><a href="/OAS/teacher/hod/teachers">Teachers</a></li>
                                                    <li><a href="/OAS/teacher/hod/teachers/unaccountedteachers">Unaccounted Teachers</a></li>
                                                </ul>
                                            </li>
                                            <li class="dropdown-submenu">
                                                <a>Student</a>
                                                <ul class= "dropdown-menu">
                                                    <li><a href="/OAS/teacher/hod/students">Students</a></li>
                                                    <li><a href="/OAS/teacher/hod/students/unaccountedstudents">Unaccounted Students</a></li>
                                                </ul>
                                            </li>
                                        </ul>
                                    </li>
                                    <li class="dropdown">
                                        <a class="dropdown-toggle">
                                            Entities
                                        </a>
                                        <ul class="dropdown-menu">
                                            <li><a href="/OAS/teacher/hod/courses">Courses</a></li>
                                            <li><a href="/OAS/teacher/hod/classrooms">Class Rooms</a></li>
                                            <li><a href="/OAS/teacher/hod/subjects">Subjects</a></li>
                                        </ul>
                                    </li>
                                    <li>
                                        <a href="/OAS/teacher/hod/lectures">
                                            Lectures
                                        </a>
                                    </li>
                                    <li class="dropdown">
                                        <a class="dropdown-toggle">
                                            Reports
                                        </a>
                                        <ul class="dropdown-menu">
                                            <li><a href="/OAS/teacher/hod/reports">Reports</a></li>
                                            <li><a href="/OAS/teacher/hod/oldreports">Old Reports</a></li>
                                        </ul>
                                    </li>
                                    <li>
                                        <a href="/OAS/teacher/hod/statistics">
                                            Statistics
                                        </a>
                                    </li>
                                    <li>
                                        <a href="/OAS/teacher/hod/teachersubjectmapping">
                                            Teacher - Subject Mapping
                                        </a>
                                    </li>   
                                    <c:choose>
                                        <c:when test="${sessionScope.teacher.hod==true}" >
                                            <li class="dropdown">
                                                <a class="Classroomn-toggle">Switch Account</a>
                                                <ul class="dropdown-menu">
                                                    <li>
                                                        <a href="/OAS/teacher"> As a Teacher</a>
                                                    </li>
                                                    <c:forEach var="department" items="${sessionScope.teacher.hodOf}">
                                                        <li>
                                                            <a href="/OAS/teacher/hod?departmentId=${department.id}">${department} - HOD</a>
                                                        </li>
                                                    </c:forEach>
                                                </ul>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                        </c:otherwise>
                                    </c:choose>
                                    <li class="dropdown dropdown-mega dropdown-mega-signin signin logged" id="headerAccount">
                                        <a class="dropdown-toggle">
                                            <i class="fa fa-user"></i> ${sessionScope.teacher}
                                        </a>
                                        <ul class="dropdown-menu">
                                            <li>
                                                <div class="dropdown-mega-content">
                                                    <div class="row">
                                                        <div class="col-md-8">
                                                            <div class="user-avatar">
                                                                <p><strong>${sessionScope.teacher}</strong><span>Head of Department</span></p>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-4">
                                                            <ul class="list-account-options">
                                                                <li>
                                                                    <a href="/OAS/teacher/hod/myaccount">My Account</a>
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
