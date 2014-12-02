<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta content="IE=edge" http-equiv="X-UA-Compatible">
    <meta content="width=device-width, initial-scale=1" name="viewport">
    <title>Sign in</title>
    <link rel="stylesheet" href="<c:url value="/resources/${application.version}/css/bootstrap/cosmo/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/${application.version}/css/login.css"/>">
</head>
<body>
    <div class="container">
        <c:if test="${empty sessionScope['CREDENTIALS_EXPIRED_USERNAME']}">
            <form class="form-signin" action="<c:url value='j_spring_security_check'/>" method='POST' role="form">
                <h2>Sign in</h2>
                <c:if test="${not empty sessionScope['SPRING_SECURITY_LAST_EXCEPTION']}">
                    <div class="alert alert-danger">${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</div>
                </c:if>
                <c:if test="${not empty sessionScope['LOGIN_INFO_MESSAGE']}">
                    <div class="alert alert-success">${sessionScope["LOGIN_INFO_MESSAGE"]}</div>
                </c:if>
                <input class="form-control" name="j_username" type="text" placeholder="User Name" autofocus="" >
                <input class="form-control pass" name="j_password" type="password" placeholder="Password">
                <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
            </form>
        </c:if>
        <c:if test="${not empty sessionScope['CREDENTIALS_EXPIRED_USERNAME']}">
            <c:url var="formActionUrl" value="/mustchangepassword"/>
            <form:form commandName="changePassword" action="${formActionUrl}" cssClass="form-signin" role="form">
                <h2>Change password</h2>
                <c:if test="${not empty sessionScope['SPRING_SECURITY_LAST_EXCEPTION']}">
                    <div class="alert alert-danger">${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</div>
                </c:if>
                <input id="username" class="form-control" type="text" placeholder="Username"
                           value="${sessionScope['CREDENTIALS_EXPIRED_USERNAME']}" disabled>
                <spring:bind path="oldPassword">
                    <div class="form-group ${status.error ? "has-error" : ""}">
                        <form:password id="oldPassword" path="oldPassword" placeholder="Old password" cssClass="form-control pass-old" autofocus="autofocus"/>
                        <form:errors path="oldPassword" cssClass="help-block"/>
                    </div>
                </spring:bind>
                <spring:bind path="newPassword">
                    <div class="form-group ${status.error ? "has-error" : ""}">
                        <form:password id="newPassword" path="newPassword" placeholder="New password" cssClass="form-control pass-new"/>
                        <form:errors path="newPassword" cssClass="help-block"/>
                    </div>
                </spring:bind>
                <spring:bind path="repeatNewPassword">
                    <div class="form-group ${status.error ? "has-error" : ""}">
                        <form:password id="repeatNewPassword" path="repeatNewPassword" placeholder="Repeat new password" cssClass="form-control pass-repeat"/>
                        <form:errors path="repeatNewPassword" cssClass="help-block"/>
                    </div>
                </spring:bind>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Change password</button>
            </form:form>
        </c:if>
    </div>
</body>
</html>