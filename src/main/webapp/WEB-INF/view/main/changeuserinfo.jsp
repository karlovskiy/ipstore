<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ipstore" tagdir="/WEB-INF/tags/ipstore" %>

<form:form commandName="changeUserInfo" action="/changeuserinfo" cssClass="form-horizontal" role="form">
    <div class="form-group">
        <label class="control-label col-md-2">Username</label>
        <div class="col-md-5">
            <p class="form-control-static"><security:authentication property="principal.username"/></p>
        </div>
    </div>

    <ipstore:input path="password" label="Password"/>
    <ipstore:input path="email" label="Email"/>
    <ipstore:input path="firstName" label="FirstName"/>
    <ipstore:input path="lastName" label="LastName"/>

    <div class="form-group">
        <div class="col-md-offset-2 col-md-5">
            <button type="submit" class="btn btn-primary">Save</button>
        </div>
    </div>
</form:form>