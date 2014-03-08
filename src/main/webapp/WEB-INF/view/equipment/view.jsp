<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ipstore" tagdir="/WEB-INF/tags/ipstore" %>

<div class="form-horizontal">
    <div class="form-group">
        <label class="control-label col-md-2">IpAddress</label>
        <div class="col-md-5">
            <div class="form-control-static">
                <c:choose>
                    <c:when test="${equipment.type == 'GS'}">
                        <a href="http://${equipment.ipAddress}" target="_blank">
                            <c:out value="${equipment.ipAddress}"/>
                        </a>
                    </c:when>
                    <c:otherwise>
                        <c:out value="${equipment.ipAddress}"/>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
    <ipstore:field label="Type" value="${equipment.type}"/>
    <ipstore:field label="Username" value="${equipment.username}"/>
    <ipstore:field label="Login" value="${equipment.login}"/>

    <security:authorize access="hasRole('ROLE_EQUIPMENT_EDIT')">
        <div class="form-group">
            <label class="control-label col-md-2">Password</label>
            <div class="col-md-5">
                <div class="form-control-static">
                    <button id="copy_to_clipboard" class="btn btn-primary btn-xs" type="button"
                            data-clipboard-text="${equipment.password}">
                        <span class="glyphicon glyphicon-pencil"></span>
                    </button>
                </div>
            </div>
        </div>
    </security:authorize>

    <ipstore:field label="Password date" value="${equipment.passwordDate}" type="datetime"/>
    <ipstore:field label="Password status" value="${equipment.passwordStatus}"
                   cssClass="${application.labelClass(equipment.passwordStatus)}"/>

    <c:if test="${equipment.status == 'DELETED' or equipment.status == 'ACTIVE_NO_EXPIRED'}">
        <ipstore:field label="Status" value="${equipment.status}"
                       cssClass="${application.labelClass(equipment.status)}"/>
    </c:if>

    <ipstore:field label="Telnet status date" value="${equipment.telnetStatusDate}" type="datetime"/>
    <ipstore:field label="Telnet status" value="${equipment.telnetStatus}"
                   cssClass="${application.labelClass(equipment.telnetStatus)}"/>
    <ipstore:field label="Is checked by telnet" value="${empty equipment.telnetCheck or not equipment.telnetCheck ? 'No' : 'Yes'}"/>
    <ipstore:field label="ClientName" value="${equipment.clientName}"/>
    <ipstore:field label="PlacementAddress" value="${equipment.placementAddress}"/>
    <ipstore:field label="ApplicationNumber" value="${equipment.applicationNumber}"/>
    <ipstore:field label="Description" value="${equipment.description}"/>

    <security:authorize access="hasRole('ROLE_EQUIPMENT_EDIT')">
        <c:if test="${not empty equipment.configName}">
            <div class="form-group">
                <label class="control-label col-md-2">Config</label>
                <div class="col-md-5">
                    <div class="form-control-static">
                        <a id="cf_download" href="<c:url value="/equipment/${equipment.id}/load_config"/>">
                            <c:out value="${equipment.configName}"/></a>
                    </div>
                </div>
            </div>
        </c:if>
    </security:authorize>

    <div class="form-group">
        <div class="col-md-offset-2 col-md-5">
            <security:authorize access="hasRole('ROLE_EQUIPMENT_EDIT')">
                <a class="btn btn-primary" href="<c:url value="/equipment/${equipment.id}/edit" />">Edit</a>
                <c:if test="${equipment.status != 'DELETED'}">
                    <a class="btn btn-danger" id="delete_btn"
                       href="<c:url value="/equipment/${equipment.id}/delete"/>">Delete</a>
                </c:if>
            </security:authorize>
            <security:authorize access="hasRole('ROLE_ROOT')">
                <c:if test="${equipment.status == 'DELETED' or equipment.status == 'ACTIVE_NO_EXPIRED'}">
                    <a class="btn btn-warning"
                       href="<c:url value="/equipment/${equipment.id}/activate"/>">Activate</a>
                </c:if>
                <c:if test="${equipment.status == 'ACTIVE'}">
                    <a class="btn btn-warning"
                       href="<c:url value="/equipment/${equipment.id}/activate_no_expired"/>">ActivateWithNoExpired</a>
                </c:if>
            </security:authorize>
        </div>
    </div>
</div>