<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    <div class="form-group">
        <label class="control-label col-md-2">TRY prefix</label>

        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${communigateDomain.tryPrefix}"/>
            </p>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2">Status</label>

        <div class="col-md-5">
            <p class="form-control-static">
                <span class="${bootstrap.labelClass(communigateDomain.status)}">
                    <c:out value="${communigateDomain.status}"/>
                </span>
            </p>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2">Client Name</label>

        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${communigateDomain.clientName}"/>
            </p>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2">Ticket</label>

        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${communigateDomain.ticket}"/>
            </p>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2">Number Line</label>

        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${communigateDomain.numberLine}"/>
            </p>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2">Disk Capacity</label>

        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${communigateDomain.diskCapacity}"/>
            </p>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2">Service</label>

        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${communigateDomain.service}"/>
            </p>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2">Contract</label>

        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${communigateDomain.contract}"/>
            </p>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2">Login</label>

        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${communigateDomain.login}"/>
            </p>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2">Date</label>

        <div class="col-md-5">
            <p class="form-control-static">
                <fmt:formatDate value="${communigateDomain.date}" type="date" pattern="dd.MM.yyyy"/>
            </p>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2">Description</label>

        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${communigateDomain.description}"/>
            </p>
        </div>
    </div>

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