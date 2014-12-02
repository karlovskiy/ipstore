<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ipstore" tagdir="/WEB-INF/tags/ipstore" %>

<c:url var="formActionUrl" value="${formAction}"/>
<form:form id="form" commandName="user" action="${formActionUrl}" cssClass="form-horizontal" role="form">

    <spring:bind path="username">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="username">Username</label>

            <div class="col-md-5">
                <form:input id="username" path="username" cssClass="form-control"/>
                <form:errors path="username" cssClass="help-block"/>
                <c:if test="${not empty existsUser}">
                    <span class="help-block">
                        User with username
                        <a href="<c:url value="/users/${existsUser.id}"/>" target="_blank">
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

    <ipstore:input path="email" label="Email"/>
    <ipstore:input path="firstName" label="FirstName"/>
    <ipstore:input path="lastName" label="LastName"/>

    <div class="form-group">
        <div class="col-md-offset-2 col-md-10">

            <c:if test="${not empty user.id}">
                <a href="<c:url value="/users/${user.id}"/>" class="btn btn-primary">View</a>
            </c:if>

            <button type="submit" class="btn btn-primary">Save</button>
        </div>
    </div>

</form:form>