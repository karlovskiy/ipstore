<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ipstore" tagdir="/WEB-INF/tags/ipstore" %>

<c:url var="formActionUrl" value="/changeuserinfo"/>
<form:form commandName="changeUserInfo" action="${formActionUrl}" cssClass="form-horizontal" role="form">
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

    <spring:bind path="theme">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="theme">Theme</label>
            <div class="col-md-5">
                <form:select id="theme" path="theme" cssClass="form-control">
                    <form:option value="default" label="Default"/>
                    <form:option value="amelia" label="Amelia"/>
                    <form:option value="cerulean" label="Cerulean"/>
                    <form:option value="cosmo" label="Cosmo"/>
                    <form:option value="cyborg" label="Cyborg"/>
                    <form:option value="flatly" label="Flatly"/>
                    <form:option value="journal" label="Journal"/>
                    <form:option value="lumen" label="Lumen"/>
                    <form:option value="readable" label="Readable"/>
                    <form:option value="simplex" label="Simplex"/>
                    <form:option value="slate" label="Slate"/>
                    <form:option value="spacelab" label="Spacelab"/>
                    <form:option value="superhero" label="Superhero"/>
                    <form:option value="united" label="United"/>
                    <form:option value="yeti" label="Yeti"/>
                </form:select>
                <form:errors path="theme" cssClass="help-block"/>
            </div>
        </div>
    </spring:bind>

    <div class="form-group">
        <div class="col-md-offset-2 col-md-5">
            <button type="submit" class="btn btn-primary">Save</button>
        </div>
    </div>
</form:form>