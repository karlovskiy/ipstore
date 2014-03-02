<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<form:form id="form" commandName="user" action="${formAction}" cssClass="form-horizontal" role="form">

    <spring:bind path="username">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="username">Username</label>

            <div class="col-md-5">
                <form:input id="username" path="username" cssClass="form-control"/>
                <form:errors path="username" cssClass="help-block"/>
                <c:if test="${not empty existsUser}">
                    <span class="help-block">
                        User with username
                        <a href="<c:url value="/users/view/${existsUser.id}"/>" target="_blank">
                            <c:out value="${existsUser.username}"/>
                        </a>
                        already exists!
                    </span>
                </c:if>
            </div>
        </div>
    </spring:bind>

    <div class="form-group">
        <span class="control-label col-md-2">Authority</span>

        <div class="col-md-5">
            <c:forEach items="${user.allAuthorities}" var="authority">
                <div class="checkbox">
                    <label class="authority">
                        <input id="${authority}" type="checkbox" value="">
                        <span>${authority}</span>
                    </label>
                </div>
            </c:forEach>
        </div>
        <form:hidden id="authorities" path="authorities"/>
    </div>

    <spring:bind path="email">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="email">Email</label>

            <div class="col-md-5">
                <form:input id="email" path="email" cssClass="form-control"/>
                <form:errors path="email" cssClass="help-block"/>
            </div>
        </div>
    </spring:bind>

    <spring:bind path="firstName">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="firstName">FirstName</label>

            <div class="col-md-5">
                <form:input id="firstName" path="firstName" cssClass="form-control"/>
                <form:errors path="firstName" cssClass="help-block"/>
            </div>
        </div>
    </spring:bind>

    <spring:bind path="lastName">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="lastName">LastName</label>

            <div class="col-md-5">
                <form:input id="lastName" path="lastName" cssClass="form-control"/>
                <form:errors path="lastName" cssClass="help-block"/>
            </div>
        </div>
    </spring:bind>

    <div class="form-group">
        <div class="col-md-offset-2 col-md-10">

            <c:if test="${not empty user.id}">
                <a href="<c:url value="/users/view/${user.id}"/>" class="btn btn-primary">View</a>
            </c:if>

            <button type="submit" class="btn btn-primary">Save</button>
        </div>
    </div>

</form:form>