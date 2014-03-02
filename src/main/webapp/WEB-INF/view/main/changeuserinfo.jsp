<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form:form commandName="changeUserInfo" action="/changeuserinfo" cssClass="form-horizontal" role="form">
    <div class="form-group">
        <label class="control-label col-md-1">Username</label>
        <div class="col-md-5">
            <p class="form-control-static"><security:authentication property="principal.username"/></p>
        </div>
    </div>
    <spring:bind path="password">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-1" for="password">Password</label>
            <div class="col-md-5">
                <form:password id="password" path="password" cssClass="form-control"/>
                <form:errors path="password" cssClass="help-block"/>
            </div>
        </div>
    </spring:bind>
    <spring:bind path="email">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-1" for="email">Email</label>
            <div class="col-md-5">
                <form:input id="email" path="email" cssClass="form-control"/>
                <form:errors path="email" cssClass="help-block"/>
            </div>
        </div>
    </spring:bind>
    <spring:bind path="firstName">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-1" for="firstName">FirstName</label>
            <div class="col-md-5">
                <form:input id="firstName" path="firstName" cssClass="form-control"/>
                <form:errors path="firstName" cssClass="help-block"/>
            </div>
        </div>
    </spring:bind>
    <spring:bind path="lastName">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-1" for="lastName">LastName</label>
            <div class="col-md-5">
                <form:input id="lastName" path="lastName" cssClass="form-control"/>
                <form:errors path="lastName" cssClass="help-block"/>
            </div>
        </div>
     </spring:bind>
    <div class="form-group">
        <div class="col-md-offset-1 col-md-5">
            <button type="submit" class="btn btn-primary">Save</button>
        </div>
    </div>
</form:form>