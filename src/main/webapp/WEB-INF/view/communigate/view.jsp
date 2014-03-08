<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="ipstore" tagdir="/WEB-INF/tags/ipstore" %>

<div class="form-horizontal">
    <div class="form-group">
        <label class="control-label col-md-2">Domain name</label>

        <div class="col-md-5">
            <div class="form-control-static">
                <a href="http://${communigateDomain.domainName}" target="_blank">
                    <c:out value="${communigateDomain.domainName}"/>
                </a>
            </div>
        </div>
    </div>
    <ipstore:field label="TRY prefix" value="${communigateDomain.tryPrefix}"/>
    <ipstore:field label="Status" value="${communigateDomain.status}"
                   cssClass="${application.labelClass(communigateDomain.status)}"/>
    <ipstore:field label="Client Name" value="${communigateDomain.clientName}"/>
    <ipstore:field label="Ticket" value="${communigateDomain.ticket}"/>
    <ipstore:field label="Number Line" value="${communigateDomain.numberLine}"/>
    <ipstore:field label="Disk Capacity" value="${communigateDomain.diskCapacity}"/>
    <ipstore:field label="Service" value="${communigateDomain.service}"/>
    <ipstore:field label="Contract" value="${communigateDomain.contract}"/>
    <ipstore:field label="Login" value="${communigateDomain.login}"/>
    <ipstore:field label="Date" value="${communigateDomain.date}" type="date"/>
    <ipstore:field label="Description" value="${communigateDomain.description}"/>

    <div class="form-group">
        <div class="col-md-offset-2 col-md-5">
            <security:authorize access="hasRole('ROLE_COMMUNIGATE_EDIT')">
                <a href="<c:url value="/communigate/${communigateDomain.id}/edit"/>"
                       class="btn btn-primary">Edit</a>
                <security:authorize access="hasRole('ROLE_ROOT')">
                    <c:if test="${communigateDomain.status == 'DELETED'}">
                        <a href="<c:url value="/communigate/${communigateDomain.id}/activate"/>"
                               class="btn btn-success">Activate</a>
                    </c:if>
                </security:authorize>
                <c:if test="${communigateDomain.status != 'DELETED'}">
                    <c:if test="${communigateDomain.status == 'NORMAL'}">
                        <a href="<c:url value="/communigate/${communigateDomain.id}/block"/>"
                               class="btn btn-warning">Block</a>
                    </c:if>
                    <c:if test="${communigateDomain.status == 'BLOCKED'}">
                        <a href="<c:url value="/communigate/${communigateDomain.id}/unblock"/>"
                               class="btn btn-warning">Unblock</a>
                    </c:if>
                    <security:authorize access="hasRole('ROLE_ROOT')">
                        <a href="<c:url value="/communigate/${communigateDomain.id}/delete"/>"
                               class="btn btn-danger" id="delete_btn">Delete</a>
                    </security:authorize>
                </c:if>
            </security:authorize>
        </div>
    </div>
</div>