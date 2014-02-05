<%--
  User: Sidorov Oleg
  Date: 26.03.13
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="<c:url value="/resources/js/view.js"/>"></script>
<div class="container">
    <div id="view-fields">
        <div class="row">
            <div class="span2">Domain name</div>
            <div class="span5">
                <span class="block">
                    <a href="http://${communigateDomain.domainName}" target="_blank">
                        <c:out value="${communigateDomain.domainName}"/></a>
                </span>
            </div>
        </div>
        <div class="row">
            <div class="span2">TRY prefix</div>
            <div class="span5">
                <span class="block">
                    <c:out value="${communigateDomain.tryPrefix}"/>
                </span>
            </div>
        </div>
        <div class="row">
            <div class="span2">Client Name</div>
            <div class="span5">
                <span class="block">
                    <c:out value="${communigateDomain.clientName}"/>
                </span>
            </div>
        </div>
        <div class="row">
            <div class="span2">Ticket</div>
            <div class="span5">
                <span class="block">
                    <c:out value="${communigateDomain.ticket}"/>
                </span>
            </div>
        </div>
        <div class="row">
            <div class="span2">Number Line</div>
            <div class="span5">
                <span class="block">
                    <c:out value="${communigateDomain.numberLine}"/>
                </span>
            </div>
        </div>
        <div class="row">
            <div class="span2">Disk Capacity</div>
            <div class="span5">
                <span class="block">
                    <c:out value="${communigateDomain.diskCapacity}"/>
                </span>
            </div>
        </div>
        <div class="row">
            <div class="span2">Service</div>
            <div class="span5">
                <span class="block">
                    <c:out value="${communigateDomain.service}"/>
                </span>
            </div>
        </div>
        <div class="row">
            <div class="span2">Contract</div>
            <div class="span5">
                <span class="block">
                    <c:out value="${communigateDomain.contract}"/>
                </span>
            </div>
        </div>
        <div class="row">
            <div class="span2">Login</div>
            <div class="span5">
                <span class="block">
                    <c:out value="${communigateDomain.login}"/>
                </span>
            </div>
        </div>
        <div class="row">
            <div class="span2">Date</div>
            <div class="span5">
                <span class="block">
                    <fmt:formatDate value="${communigateDomain.date}" type="date"
                                    pattern="dd.MM.yyyy"/>
                </span>
            </div>
        </div>
        <div class="row">
            <div class="span2">Description</div>
            <div class="span5">
                <span class="block">
                    <c:out value="${communigateDomain.description}"/>
                </span>
            </div>
        </div>
        <div class="row">
            <div class="span2">Status</div>
            <div class="span5">
                <span class="${communigateDomain.status} block"><c:out value="${communigateDomain.status}"/></span>
            </div>
        </div>

        <div id="view-buttons">
            <div class="row">
                <div class="span1">
                    <a href="<c:url value="/communigate"/>" class="btn btn-primary btn-block">List</a>
                </div>
                <security:authorize access="hasRole('ROLE_COMMUNIGATE_EDIT')">
                <div class="span1 ml-10px">
                    <a href="<c:url value="/communigate/edit/${communigateDomain.id}"/>"
                       class="btn btn-primary btn-block">Edit</a>
                </div>
                <security:authorize access="hasRole('ROLE_ROOT')">
                    <c:if test="${communigateDomain.status == 'DELETED'}">
                        <div class="span1 ml-10px">
                            <a href="<c:url value="/communigate/activate/${communigateDomain.id}"/>"
                               class="btn btn-success btn-block">Activate</a>
                        </div>
                    </c:if>
                </security:authorize>
                <c:if test="${communigateDomain.status != 'DELETED'}">
                    <c:if test="${communigateDomain.status == 'NORMAL'}">
                        <div class="span1 ml-10px">
                            <a href="<c:url value="/communigate/block/${communigateDomain.id}"/>"
                               class="btn btn-warning btn-block">Block</a>
                        </div>
                    </c:if>
                    <c:if test="${communigateDomain.status == 'BLOCKED'}">
                        <div class="span1 ml-10px">
                            <a href="<c:url value="/communigate/unblock/${communigateDomain.id}"/>"
                               class="btn btn-warning btn-block">Unblock</a>
                        </div>
                    </c:if>
                    <security:authorize access="hasRole('ROLE_ROOT')">
                        <div class="span1 ml-10px">
                            <a href="<c:url value="/communigate/delete/${communigateDomain.id}"/>"
                               class="btn btn-danger btn-block" id="delete_btn">Delete</a>
                        </div>
                    </security:authorize>
                </c:if>
                </security:authorize>
            </div>
        </div>
    </div>
</div>