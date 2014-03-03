<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    <div class="form-group">
        <label class="control-label col-md-2">Type</label>
        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${equipment.type}"/>
            </p>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2">Username</label>
        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${equipment.username}"/>
            </p>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2">Login</label>
        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${equipment.login}"/>
            </p>
        </div>
    </div>
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
    <div class="form-group">
        <label class="control-label col-md-2">Password date</label>
        <div class="col-md-5">
            <p class="form-control-static">
                <fmt:formatDate value="${equipment.passwordDate}" type="both"
                                pattern="dd.MM.yyyy HH:mm:ss"/>
            </p>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2">Password status</label>
        <div class="col-md-5">
            <p class="form-control-static">
                <span class="${bootstrap.labelClass(equipment.passwordStatus)}">
                    <c:out value="${equipment.passwordStatus}"/>
                </span>
            </p>
        </div>
    </div>
    <c:if test="${equipment.status == 'DELETED' or equipment.status == 'ACTIVE_NO_EXPIRED'}">
        <div class="form-group">
            <label class="control-label col-md-2">Status</label>
            <div class="col-md-5">
                <p class="form-control-static">
                    <span class="${bootstrap.labelClass(equipment.status)}">
                        <c:out value="${equipment.status}"/>
                    </span>
                </p>
            </div>
        </div>
    </c:if>
    <div class="form-group">
        <label class="control-label col-md-2">Telnet status date</label>
        <div class="col-md-5">
            <p class="form-control-static">
                <fmt:formatDate value="${equipment.telnetStatusDate}" type="both"
                                pattern="dd.MM.yyyy HH:mm:ss"/>
            </p>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2">Telnet status</label>
        <div class="col-md-5">
            <p class="form-control-static">
                <span class="${bootstrap.labelClass(equipment.telnetStatus)}">
                    <c:out value="${equipment.telnetStatus}"/>
                </span>
            </p>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2">Is checked by telnet</label>
        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${empty equipment.telnetCheck or not equipment.telnetCheck ? 'false' : 'true'}"/>
            </p>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2">ClientName</label>
        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${equipment.clientName}"/>
            </p>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2">PlacementAddress</label>
        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${equipment.placementAddress}"/>
            </p>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2">ApplicationNumber</label>
        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${equipment.applicationNumber}"/>
            </p>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2">Description</label>
        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${equipment.description}"/>
            </p>
        </div>
    </div>
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