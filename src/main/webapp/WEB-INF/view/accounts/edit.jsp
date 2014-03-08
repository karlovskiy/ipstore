<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ipstore" tagdir="/WEB-INF/tags/ipstore" %>

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
                            <a href="<c:url value="/accounts/${existsAccount.id}"/>" target="_blank">
                                <c:out value="${existsAccount.login}"/>
                            </a>
                            already exists!
                        </span>
                </c:if>
            </div>
        </div>
    </spring:bind>

    <ipstore:input id="pwd" path="password" label="Password"/>
    <ipstore:input path="clientName" label="ClientName"/>
    <ipstore:input path="number" label="Number"/>
    <ipstore:input path="description" label="Description"/>

    <div class="form-group">
        <div class="col-md-offset-2 col-md-5">
            <c:if test="${not empty account.id}">
                <a href="<c:url value="/accounts/${account.id}"/>" class="btn btn-primary">View</a>
            </c:if>
            <button type="submit" class="btn btn-primary">Save</button>
            <a id="generate_password" class="btn btn-success">Generate password</a>
            <input id="max_length" type="text" name="length" class="form-control max_length"
                   value="${defaultPasswordLength}">
        </div>
    </div>

</form:form>