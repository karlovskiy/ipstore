<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ipstore" tagdir="/WEB-INF/tags/ipstore" %>

<c:url var="formActionUrl" value="${formAction}"/>
<form:form commandName="equipment" action="${formActionUrl}" cssClass="form-horizontal" enctype="multipart/form-data">
    <spring:bind path="ipAddress">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="ipAddress">IpAddress</label>
            <div class="col-md-5">
                <form:input id="ipAddress" path="ipAddress" cssClass="form-control"/>
                <form:errors path="ipAddress" cssClass="help-block"/>
                <c:if test="${not empty existsEquipment}">
                    <span class="help-block">
                        Equipment with ip
                        <a href="<c:url value="/equipment/${existsEquipment.id}"/>" target="_blank">
                            <c:out value="${existsEquipment.ipAddress}"/>
                        </a>
                        already exists!
                    </span>
                </c:if>
            </div>
        </div>
    </spring:bind>

    <ipstore:input path="type" label="Type" />
    <ipstore:input path="username" label="Username"/>
    <ipstore:input path="login" label="Login"/>
    <ipstore:input id="pwd" path="password" label="Password"/>
    <ipstore:input path="clientName" label="ClientName"/>
    <ipstore:input path="placementAddress" label="PlacementAddress"/>
    <ipstore:input path="applicationNumber" label="ApplicationNumber"/>
    <ipstore:input path="description" label="Description"/>
    <ipstore:input id="tch" path="telnetCheck" label="Is checked by telnet" type="checkbox"/>

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
                        <a id="cf_download" href="<c:url value="/equipment/${equipment.id}/load_config"/>">
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
                <a href="<c:url value="/equipment/${equipment.id}"/>" class="btn btn-primary">View</a>
            </c:if>
            <button type="submit" class="btn btn-primary">Save</button>
            <a id="generate_password" class="btn btn-success">Generate password</a>
            <input id="max_length" type="text" name="length" class="form-control max_length" value="${defaultPasswordLength}">
        </div>
    </div>
</form:form>