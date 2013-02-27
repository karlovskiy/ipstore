<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="<c:url value="/js/bootbox.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/zeroclipboard.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/accounts/view.js"/>"></script>
<div class="container">
    <div id="view-fields">
        <div class="row">
            <div class="span2">IpAddress</div>
            <div class="span5">
                <c:choose>
                <c:when test="${equipment.type == 'GS'}">
                    <a href="http://${equipment.ipAddress}" target="_blank"><c:out value="${equipment.ipAddress}"/></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${equipment.ipAddress}"/>
                </c:otherwise>
                </c:choose>
            </div>
            </div>
            <div class="row">
                <div class="span2">Type</div>
                <div class="span5">
                    <span class="block">
                         <c:out value="${equipment.type}"/>
                    </span>
                </div>
            </div>
            <div class="row">
                <div class="span2">Username</div>
                <div class="span5">
                    <span class="block">
                         <c:out value="${equipment.username}"/>
                    </span>
                </div>
            </div>
            <div class="row">
                <div class="span2">Login</div>
                <div class="span5">
                    <span class="block">
                         <c:out value="${equipment.login}"/>
                    </span>
                </div>
            </div>
            <div class="row">
                <div class="span2">Password</div>
                <div class="span5">
                    <span><button id="copy_to_clipboard" class="btn btn-primary"
                            data-clipboard-text="${equipment.password}"> Copy
                    </button> </span>
                </div>
            </div>
            <div class="row">
                <div class="span2">Password date</div>
                <div class="span5">
                    <fmt:formatDate value="${equipment.passwordDate}" type="both" pattern="dd.MM.yyyy HH:mm:ss"/>
                </div>
            </div>
            <div class="row">
                <div class="span2">Password status</div>
                <div class="span5">
                    <span class="${equipment.passwordStatus} block"><c:out value="${equipment.passwordStatus}"/></span>
                </div>
            </div>
            <div class="row">
                <div class="span2">ClientName</div>
                <div class="span5">
                    <span class="block">
                    <c:out value="${equipment.clientName}"/></span>
                </div>
            </div>
            <div class="row">
                <div class="span2">PlacementAddress</div>
                <div class="span5">
                    <span class="block"><c:out value="${equipment.placementAddress}"/></span>
                </div>
            </div>
            <div class="row">
                <div class="span2">ApplicationNumber</div>
                <div class="span5">
                    <span class="block"><c:out value="${equipment.applicationNumber}"/></span>
                </div>
            </div>
            <div class="row">
                <div class="span2">Description</div>
                <div class="span5">
                    <span class="block"><c:out value="${equipment.description}"/></span>
                </div>
            </div>
            <c:if test="${equipment.status == 'DELETED'}">
            <div class="row">
                <div class="span2">Status</div>
                <div class="span5">
                    <span class="text-error"><c:out value="${equipment.status}"/></span>
                </div>
            </div>
            </c:if>
        </div>
    <security:authorize access="hasRole('ROLE_ADMIN')">
          <div id="view-buttons">
            <div class="row">
                <div class="span1 ml-15px">
                    <a class="btn btn-primary btn-block" href="<c:url value="/ipstore/equipment"/>">List</a>
                </div>
                <div class="span1 ml-10px">
                    <a class="btn btn-primary btn-block" href="<c:url value="/ipstore/edit/${equipment.id}" />">Edit</a>
                </div>
            <c:if test="${equipment.status != 'DELETED'}">
                <div class="span1 ml-10px">
                    <a class="btn btn-danger" id="equipment_delete" href="<c:url value="/ipstore/delete/${equipment.id}"/>">Delete</a>
                </div>
            </c:if>
            <security:authorize access="hasRole('ROLE_ROOT')">
                <c:if test="${equipment.status == 'DELETED'}">
                    <div class="span1 ml-10px">
                        <a class="btn btn-warning"
                           href="<c:url value="/ipstore/activate/${equipment.id}"/>">Activate</a>
                    </div>
                </c:if>
            </security:authorize>
                </div>
            </div>
    </security:authorize>
</div>