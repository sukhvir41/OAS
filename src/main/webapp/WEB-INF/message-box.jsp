<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:choose>
    <c:when test="${requestScope.error && not empty requestScope.message }">
        <div class="row">
            <div class="col-md-12">
                <div class="alert alert-danger">
                    <strong>Message!</strong> 
                    <c:out value="${requestScope.message}" />
                </div>
            <div>    
        </div>
    </c:when>
     
    <c:when test="${requestScope.success && not empty requestScope.message }">
        <div class="row">
            <div class="col-md-12">
                <div class="alert alert-success">
                    <strong>Message!</strong> 
                    <c:out value="${requestScope.message}" />
                </div>
            <div>    
        </div>
    </c:when> 

    <c:when test="${not empty requestScope.message}">
        <div class="row">
            <div class="col-md-12">
                <div class="alert alert-warning">
                    <strong>Message!</strong> 
                    <c:out value="${requestScope.message}" />
                </div>
            <div>    
        </div>
    </c:when> 
</c:choose>