<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<form:form commandName="account" action="${formAction}" cssClass="form-horizontal" role="form">

    <spring:bind path="login">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="login">Login</label>

            <div class="col-md-5">
                <form:input id="login" path="login" readonly="${edit}"
                            cssClass="form-control ${edit ? 'uneditable-input' : ''}"/>
                <form:errors path="login" cssClass="help-block"/>
                <c:if test="${not empty existsAccount}">
                        <span class="help-block">
                            Account with login
                            <a href="<c:url value="/accounts/view/${existsAccount.id}"/>" target="_blank">
                                <c:out value="${existsAccount.login}"/>
                            </a>
                            already exists!
                        </span>
                </c:if>
            </div>
        </div>
    </spring:bind>

    <spring:bind path="password">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="pwd">Password</label>

            <div class="col-md-5">
                <form:input id="pwd" path="password" cssClass="form-control"/>
                <form:errors path="password" cssClass="help-block"/>
            </div>
        </div>
    </spring:bind>

    <spring:bind path="clientName">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="clientName">ClientName</label>

            <div class="col-md-5">
                <form:input id="clientName" path="clientName" cssClass="form-control"/>
                <form:errors path="clientName" cssClass="help-block"/>
            </div>
        </div>
    </spring:bind>

    <spring:bind path="number">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="number">Number</label>

            <div class="col-md-5">
                <form:input id="number" path="number" cssClass="form-control"/>
                <form:errors path="number" cssClass="help-block"/>
            </div>
        </div>
    </spring:bind>

    <spring:bind path="description">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="description">Description</label>

            <div class="col-md-5">
                <form:input id="description" path="description" cssClass="form-control"/>
                <form:errors path="description" cssClass="help-block"/>
            </div>
        </div>
    </spring:bind>

    <div class="form-group">
        <div class="col-md-offset-2 col-md-5">
            <c:if test="${not empty account.id}">
                <a href="<c:url value="/accounts/view/${account.id}"/>" class="btn btn-primary">View</a>
            </c:if>
            <button type="submit" class="btn btn-primary">Save</button>
            <a id="generate_password" class="btn btn-success">Generate password</a>
            <input id="max_length" type="text" name="length" class="form-control max_length"
                   value="${defaultPasswordLength}">
        </div>
    </div>

</form:form>