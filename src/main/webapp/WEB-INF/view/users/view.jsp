<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="ipstore" tagdir="/WEB-INF/tags/ipstore" %>

<div class="form-horizontal">

    <ipstore:field label="Username" value="${user.username}"/>
    <ipstore:field label="Status" value="${user.userStatus}"
                   cssClass="${application.labelClass(user.userStatus)}"/>
    <ipstore:field label="Default password" value="${user.credentialsNonExpired ? 'No' : 'Yes'}"/>
    <ipstore:field label="Authority" value="${user.authorities}"/>
    <ipstore:field label="FirstName" value="${user.firstName}"/>
    <ipstore:field lable="LastName" value="${user.lastName}"/>
    <ipstore:field label="Email" value="${user.email}"/>
    <ipstore:field label="Theme" value="${empty user.theme ? 'Default' : user.theme}"/>

    <div class="form-group">
        <div class="col-md-offset-2 col-md-5">

            <a href="<c:url value="/users/${user.id}/edit"/>" class="btn btn-primary">Edit</a>

            <a href="<c:url value="/users/${user.id}/reset"/>" class="btn btn-danger">Reset password</a>

            <c:if test="${user.userStatus == 'BLOCKED'}">
                <a href="<c:url value="/users/${user.id}/unblock"/>" class="btn btn-success">Unblock</a>
            </c:if>

            <c:if test="${user.userStatus == 'ACTIVE'}">
                <a href="<c:url value="/users/${user.id}/block"/>" class="btn btn-warning">Block</a>
            </c:if>
        </div>
    </div>
</div>