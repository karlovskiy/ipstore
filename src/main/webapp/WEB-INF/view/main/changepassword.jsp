<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ipstore" tagdir="/WEB-INF/tags/ipstore" %>

<c:url var="formActionUrl" value="/changepassword"/>
<form:form commandName="changePassword" action="${formActionUrl}" cssClass="form-horizontal">
    <div class="form-group">
        <label class="control-label col-md-2">Username</label>
        <div class="col-md-5">
            <p class="form-control-static"><security:authentication property="principal.username"/></p>
        </div>
    </div>

    <ipstore:input path="oldPassword" label="Old password"/>
    <ipstore:input path="newPassword" label="New password"/>
    <ipstore:input path="repeatNewPassword" label="Repeat new password"/>

    <div class="form-group">
        <div class="col-md-offset-2 col-md-5">
            <button type="submit" class="btn btn-primary">Change password</button>
        </div>
    </div>
</form:form>