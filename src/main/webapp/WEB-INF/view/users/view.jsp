<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div class="form-horizontal">

    <div class="form-group">
        <label class="control-label col-md-2">Username</label>

        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${user.username}"/>
            </p>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-md-2">Status</label>

        <div class="col-md-5">
            <p class="form-control-static">
                <span class="${bootstrap.labelClass(user.userStatus)}">
                    <c:out value="${user.userStatus}"/>
                </span>
            </p>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-md-2">Authority</label>

        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${user.authorities}"/>
            </p>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-md-2">FirstName</label>

        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${user.firstName}"/>
            </p>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-md-2">LastName</label>

        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${user.lastName}"/>
            </p>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-md-2">Email</label>

        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${user.email}"/>
            </p>
        </div>
    </div>

    <div class="form-group">
        <div class="col-md-offset-2 col-md-5">

            <a href="<c:url value="/users/edit/${user.id}"/>" class="btn btn-primary">Edit</a>

            <a href="<c:url value="/users/reset/${user.id}"/>" class="btn btn-danger">Reset password</a>

            <c:if test="${user.userStatus == 'BLOCKED'}">
                <a href="<c:url value="/users/unblock/${user.id}"/>" class="btn btn-success">Unblock</a>
            </c:if>

            <c:if test="${user.userStatus == 'ACTIVE'}">
                <a href="<c:url value="/users/block/${user.id}"/>" class="btn btn-warning">Block</a>
            </c:if>
        </div>
    </div>
</div>