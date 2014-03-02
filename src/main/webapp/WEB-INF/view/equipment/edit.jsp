<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form:form commandName="equipment" action="${formAction}" cssClass="form-horizontal" enctype="multipart/form-data">
    <spring:bind path="ipAddress">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="ipAddress">IpAddress</label>
            <div class="col-md-5">
                <form:input id="ipAddress" path="ipAddress" cssClass="form-control"/>
                <form:errors path="ipAddress" cssClass="help-block"/>
                <c:if test="${not empty existsEquipment}">
                    <span class="help-block">
                        Equipment with ip
                        <a href="<c:url value="/equipment/view/${existsEquipment.id}"/>" target="_blank">
                            <c:out value="${existsEquipment.ipAddress}"/>
                        </a>
                        already exists!
                    </span>
                </c:if>
            </div>
        </div>
    </spring:bind>
    <spring:bind path="type">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="type">Type</label>
            <div class="col-md-5">
                <form:input id="type" path="type" cssClass="form-control"/>
                <form:errors path="type" cssClass="help-block"/>
            </div>
        </div>
    </spring:bind>
    <spring:bind path="username">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="username">Username</label>
            <div class="col-md-5">
                <form:input id="username" path="username" cssClass="form-control"/>
                <form:errors path="username" cssClass="help-block"/>
            </div>
        </div>
    </spring:bind>
    <spring:bind path="login">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="login">Login</label>
            <div class="col-md-5">
                <form:input id="login" path="login" cssClass="form-control"/>
                <form:errors path="login" cssClass="help-block"/>
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
    <spring:bind path="placementAddress">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="placementAddress">PlacementAddress</label>
            <div class="col-md-5">
                <form:input id="placementAddress" path="placementAddress" cssClass="form-control"/>
                <form:errors path="placementAddress" cssClass="help-block"/>
            </div>
        </div>
    </spring:bind>
    <spring:bind path="applicationNumber">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="applicationNumber">ApplicationNumber</label>
            <div class="col-md-5">
                <form:input id="applicationNumber" path="applicationNumber" cssClass="form-control"/>
                <form:errors path="applicationNumber" cssClass="help-block"/>
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
    <spring:bind path="telnetCheck">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="tch">Is checked by telnet</label>
            <div class="col-md-5">
                <div class="checkbox">
                    <form:checkbox id="tch" path="telnetCheck"/>
                </div>
                <form:errors path="telnetCheck" cssClass="help-block"/>
            </div>
        </div>
    </spring:bind>
    <c:choose>
        <c:when test="${empty equipment.id}">
            <div class="form-group">
                <label class="control-label col-md-2" style="padding-top: 4px;" for="new_equipment_cf">Config(Max 10Mb)</label>
                <div class="col-md-5">
                    <input id="new_equipment_cf" name="cf" type="file"/>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div id="new_cf" class="form-group" style="${empty equipment.configName ? '' : 'display: none;'}">
                <label class="control-label col-md-2" style="padding-top: 4px;" for="edit_equipment_cf">Config(Max 10Mb)</label>
                <div class="col-md-5">
                    <input id="edit_equipment_cf" name="cf" type="file"/>
                </div>
            </div>
            <div id="exist_cf" class="form-group" style="${empty equipment.configName ? 'display: none;' : ''}">
                <label class="control-label col-md-2" for="cf_download">Config</label>
                <div class="col-md-5">
                    <div class="form-control-static">
                        <input type="hidden" name="cf_reset" value=""/>
                        <a id="cf_download" href="<c:url value="/equipment/load_config/${equipment.id}"/>">
                            <c:out value="${equipment.configName}"/></a>
                        <a class="btn btn-danger btn-xs"
                           onclick="$('#exist_cf').hide();$('#new_cf').show();$('input[name=cf_reset]').val('reset');">Delete</a>
                    </div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
    <div class="form-group">
        <div class="col-md-offset-2 col-md-10">
            <c:if test="${not empty equipment.id}">
                <a href="<c:url value="/equipment/view/${equipment.id}"/>" class="btn btn-primary">View</a>
            </c:if>
            <button type="submit" class="btn btn-primary">Save</button>
            <a id="generate_password" class="btn btn-success">Generate password</a>
            <input id="max_length" type="text" name="length" class="form-control max_length" value="${defaultPasswordLength}">
        </div>
    </div>
</form:form>