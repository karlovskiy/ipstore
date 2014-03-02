<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form:form commandName="changePassword" action="/changepassword" cssClass="form-horizontal">
    <div class="form-group">
        <label class="control-label col-md-2">Username</label>
        <div class="col-md-5">
            <p class="form-control-static"><security:authentication property="principal.username"/></p>
        </div>
    </div>
    <spring:bind path="oldPassword">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="oldPassword">Old password</label>
            <div class="col-md-5">
                <form:password id="oldPassword" path="oldPassword" cssClass="form-control"/>
                <form:errors path="oldPassword" cssClass="help-block"/>
            </div>
        </div>
    </spring:bind>
    <spring:bind path="newPassword">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="newPassword">New password</label>
            <div class="col-md-5">
                <form:password id="newPassword" path="newPassword" cssClass="form-control"/>
                <form:errors path="newPassword" cssClass="help-block"/>
            </div>
        </div>
    </spring:bind>
    <spring:bind path="repeatNewPassword">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="repeatNewPassword">Repeat new password</label>
            <div class="col-md-5">
                <form:password id="repeatNewPassword" path="repeatNewPassword" cssClass="form-control"/>
                <form:errors path="repeatNewPassword" cssClass="help-block"/>
            </div>
        </div>
    </spring:bind>
    <div class="form-group">
        <div class="col-md-offset-2 col-md-5">
            <button type="submit" class="btn btn-primary">Change password</button>
        </div>
    </div>
</form:form>