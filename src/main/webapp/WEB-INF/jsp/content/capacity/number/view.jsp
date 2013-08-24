<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="<c:url value="/js/view.js"/>"></script>
<style>
    .w110{
        width: 110px;
    }
</style>
<div class="container">
    <div id="view-fields">
        <div class="row">
            <div class="span w110">Number</div>
            <div class="span5">
                <span class="block">
                    <c:out value="${capacityNumber.number}"/>
                </span>
            </div>
        </div>
        <div class="row">
            <div class="span w110">Type</div>
            <div class="span5">
                <span class="block">
                    <c:out value="${capacityNumber.type.name}"/>
                </span>
            </div>
        </div>
        <div class="row">
            <div class="span w110">Status</div>
            <div class="span5">
                <span class="block">
                    <c:out value="${capacityNumber.status}"/>
                </span>
            </div>
        </div>
        <div class="row">
            <div class="span w110">LSC username</div>
            <div class="span5">
                <span class="block">
                    <c:out value="${capacityNumber.lscUsername}"/>
                </span>
            </div>
        </div>
        <div class="row">
            <div class="span w110">LSC timestamp</div>
            <div class="span5">
                <span class="block">
                    <fmt:formatDate value="${capacityNumber.lscTimestamp}"
                                    type="both" pattern="dd.MM.yyyy HH:mm:ss"/>
                </span>
            </div>
        </div>
        <div class="row">
            <div class="span w110">ClientName</div>
            <div class="span5">
                <span class="block">
                    <c:out value="${capacityNumber.clientName}"/>
                </span>
            </div>
        </div>
        <div class="row">
            <div class="span w110">Address</div>
            <div class="span5">
                <span class="block">
                    <c:out value="${capacityNumber.address}"/>
                </span>
            </div>
        </div>
        <div class="row">
            <div class="span w110">Application</div>
            <div class="span5">
                <span class="block">
                    <c:out value="${capacityNumber.application}"/>
                </span>
            </div>
        </div>
        <div class="row">
            <div class="span w110">Admittance date</div>
            <div class="span5">
                <span class="block">
                    <fmt:formatDate value="${capacityNumber.admittanceDate}"
                                    type="date" pattern="dd.MM.yyyy"/>
                </span>
            </div>
        </div>
        <div class="row">
            <div class="span w110">Lines count</div>
            <div class="span5">
                <span class="block">
                    <c:out value="${capacityNumber.linesCount}"/>
                </span>
            </div>
        </div>
        <div class="row">
            <div class="span w110">Comments</div>
            <div class="span5">
                <span class="block">
                    <c:out value="${capacityNumber.comments}"/>
                </span>
            </div>
        </div>
    </div>
    <div id="view-buttons">
        <div class="row">
            <div class="span1">
                <a href="<c:url value="/ipstore/capacity/${capacityNumber.type.id}"/>" class="btn btn-primary btn-block">List</a>
            </div>
            <security:authorize access="hasRole('ROLE_CAPACITY_MANAGER')">
                <c:if test="${capacityNumber.status ne 'RESERVED'}">
                    <div class="span1 ml-10px">
                        <a href="<c:url value="/ipstore/capacity/number/reserve/${capacityNumber.id}"/>"
                           class="btn btn-primary btn-block">Reserve</a>
                    </div>
                </c:if>
                <security:authentication var="username" property="principal.username"/>
                <security:authorize var="edit" access="hasRole('ROLE_CAPACITY_EDIT')"/>
                <c:if test="${capacityNumber.status eq 'RESERVED' and (capacityNumber.lscUsername eq username or edit)}">
                    <div class="span1 ml-10px">
                        <a href="<c:url value="/ipstore/capacity/number/unreserve/${capacityNumber.id}"/>"
                           class="btn btn-primary btn-block">Unreserve</a>
                    </div>
                </c:if>
            </security:authorize>
            <security:authorize access="hasRole('ROLE_CAPACITY_EDIT')">
                <div class="span1 ml-10px">
                    <a href="<c:url value="/ipstore/capacity/number/edit/${capacityNumber.id}"/>"
                       class="btn btn-primary btn-block">Edit</a>
                </div>
                <c:if test="${capacityNumber.status ne 'FREE'}">
                    <div class="span1 ml-10px">
                        <a href="<c:url value="/ipstore/capacity/number/free/${capacityNumber.id}"/>"
                           class="btn btn-primary btn-block">Free</a>
                    </div>
                </c:if>
                <c:if test="${capacityNumber.status ne 'SELL'}">
                    <div class="span1 ml-10px">
                        <a href="<c:url value="/ipstore/capacity/number/sell/${capacityNumber.id}"/>"
                           class="btn btn-primary btn-block">Sell</a>
                    </div>
                </c:if>
                <c:if test="${capacityNumber.status ne 'TEST'}">
                    <div class="span1 ml-10px">
                        <a href="<c:url value="/ipstore/capacity/number/test/${capacityNumber.id}"/>"
                           class="btn btn-primary btn-block">Test</a>
                    </div>
                </c:if>
                <c:if test="${capacityNumber.status ne 'TRANSFERED'}">
                    <div class="span1 ml-10px">
                        <a href="<c:url value="/ipstore/capacity/number/transfer/${capacityNumber.id}"/>"
                           class="btn btn-primary btn-block">Transfer</a>
                    </div>
                </c:if>
            </security:authorize>
        </div>
    </div>
</div>