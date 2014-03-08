<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="ipstore" tagdir="/WEB-INF/tags/ipstore" %>

<div class="form-horizontal">

    <ipstore:field label="Number" value="${capacityNumber.number}"/>
    <ipstore:field label="Type" value="${capacityNumber.type.name}"/>
    <ipstore:field label="Status" value="${capacityNumber.status}"/>
    <ipstore:field label="LSC username" value="${capacityNumber.lscUsername}"/>
    <ipstore:field label="LSC timestamp" value="${capacityNumber.lscTimestamp}" type="datetime"/>
    <ipstore:field label="ClientName" value="${capacityNumber.clientName}"/>
    <ipstore:field label="Address" value="${capacityNumber.address}"/>
    <ipstore:field label="Address" value="${capacityNumber.application}"/>
    <ipstore:field label="Admittance date" value="${capacityNumber.admittanceDate}" type="date"/>
    <ipstore:field label="Lines count" value="${capacityNumber.linesCount}"/>
    <ipstore:field label="Comments" value="${capacityNumber.comments}"/>

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