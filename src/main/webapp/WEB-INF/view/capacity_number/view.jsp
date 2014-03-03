<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div class="form-horizontal">

    <div class="form-group">
        <label class="control-label col-md-2">Number</label>
        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${capacityNumber.number}"/>
            </p>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-md-2">Type</label>
        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${capacityNumber.type.name}"/>
            </p>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-md-2">Status</label>
        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${capacityNumber.status}"/>
            </p>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-md-2">LSC username</label>
        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${capacityNumber.lscUsername}"/>
            </p>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-md-2">LSC timestamp</label>
        <div class="col-md-5">
            <p class="form-control-static">
                <fmt:formatDate value="${capacityNumber.lscTimestamp}"
                                type="both" pattern="dd.MM.yyyy HH:mm:ss"/>
            </p>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-md-2">ClientName</label>
        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${capacityNumber.clientName}"/>
            </p>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-md-2">Address</label>
        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${capacityNumber.address}"/>
            </p>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-md-2">Address</label>
        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${capacityNumber.application}"/>
            </p>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-md-2">Admittance date</label>
        <div class="col-md-5">
            <p class="form-control-static">
                <fmt:formatDate value="${capacityNumber.admittanceDate}"
                                type="date" pattern="dd.MM.yyyy"/>
            </p>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-md-2">Lines count</label>
        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${capacityNumber.linesCount}"/>
            </p>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-md-2">Comments</label>
        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${capacityNumber.comments}"/>
            </p>
        </div>
    </div>

    <div class="form-group">
        <div class="col-md-offset-2 col-md-5">

            <a href="<c:url value="/capacity/${capacityNumber.type.id}"/>" class="btn btn-primary">Capacity</a>

            <security:authorize access="hasRole('ROLE_CAPACITY_MANAGER')">

                <c:if test="${capacityNumber.status ne 'RESERVED'}">
                    <a href="<c:url value="/capacity/${capacityNumber.type.id}/numbers/${capacityNumber.id}/reserve"/>"
                       class="btn btn-primary">Reserve</a>
                </c:if>

                <security:authentication var="username" property="principal.username"/>
                <security:authorize var="edit" access="hasRole('ROLE_CAPACITY_EDIT')"/>

                <c:if test="${capacityNumber.status eq 'RESERVED' and (capacityNumber.lscUsername eq username or edit)}">
                    <a href="<c:url value="/capacity/${capacityNumber.type.id}/numbers/${capacityNumber.id}/unreserve"/>"
                       class="btn btn-primary">Unreserve</a>
                </c:if>
            </security:authorize>

            <security:authorize access="hasRole('ROLE_CAPACITY_EDIT')">

                <a href="<c:url value="/capacity/${capacityNumber.type.id}/numbers/${capacityNumber.id}/edit"/>"
                   class="btn btn-primary">Edit</a>

                <c:if test="${capacityNumber.status ne 'FREE'}">
                    <a href="<c:url value="/capacity/${capacityNumber.type.id}/numbers/${capacityNumber.id}/free"/>"
                       class="btn btn-primary">Free</a>
                </c:if>

                <c:if test="${capacityNumber.status ne 'SELL'}">
                    <a href="<c:url value="/capacity/${capacityNumber.type.id}/numbers/${capacityNumber.id}/sell"/>"
                       class="btn btn-primary">Sell</a>
                </c:if>

                <c:if test="${capacityNumber.status ne 'TEST'}">
                    <a href="<c:url value="/capacity/${capacityNumber.type.id}/numbers/${capacityNumber.id}/test"/>"
                       class="btn btn-primary">Test</a>
                </c:if>

                <c:if test="${capacityNumber.status ne 'TRANSFERED'}">
                    <a href="<c:url value="/capacity/${capacityNumber.type.id}/numbers/${capacityNumber.id}/transfer"/>"
                       class="btn btn-primary">Transfer</a>
                </c:if>
            </security:authorize>
        </div>
    </div>
</div>