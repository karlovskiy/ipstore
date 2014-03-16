<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div class="row">
    <security:authorize access="hasRole('ROLE_ROOT')" var="hasRootRole"/>
    <security:authorize access="hasRole('ROLE_EQUIPMENT_VIEW')">
        <div class="col-md-6">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        Equipments <a href="<c:url value="/equipment"/>">
                        <span class="badge"><c:out value="${equipmentWidget.total}"/></span></a>
                    </h3>
                </div>
                <div class="panel-body">
                    <table class="status-table">
                        <thead>
                        <tr>
                            <th colspan="2">Password status</th>
                            <th colspan="2">Timeout status</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>
                                <span class="text-danger">NEED_UPDATE</span>
                                <a href="<c:url value="/equipment?passwordStatus=NEED_UPDATE"/>">
                                    <span class="badge">
                                        <c:out value="${equipmentWidget.needUpdate}"/>
                                    </span>
                                </a>
                            </td>
                            <td>
                                <span class="text-warning">OLD</span>
                                <a href="<c:url value="/equipment?passwordStatus=OLD"/>">
                                    <span class="badge">
                                        <c:out value="${equipmentWidget.old}"/>
                                    </span>
                                </a>
                            </td>
                            <td>
                                <span class="text-danger">WARNING</span>
                                <a href="<c:url value="/equipment?telnetStatus=WARNING"/>">
                                    <span class="badge">
                                        <c:out value="${equipmentWidget.warning}"/>
                                    </span>
                                </a>
                            </td>
                            <td>
                                <span class="text-warning">TIMEOUT</span>
                                <a href="<c:url value="/equipment?telnetStatus=TIMEOUT"/>">
                                    <span class="badge">
                                        <c:out value="${equipmentWidget.timeout}"/>
                                    </span>
                                </a>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <table class="table table-condensed panel-table">
                        <thead>
                        <tr>
                            <th>Change date</th>
                            <th>Change type</th>
                            <th>User</th>
                            <th>Equipment</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${equipmentWidget.lastChanges}" var="equipment">
                            <tr>
                                <td>
                                    <c:choose>
                                        <c:when test="${hasRootRole}">
                                            <a href="<c:out value="/actions/${equipment.actionId}"/>">
                                                <fmt:formatDate value="${equipment.actionTimestamp}" type="both"
                                                                pattern="dd.MM.yyyy HH:mm:ss"/>
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatDate value="${equipment.actionTimestamp}" type="both"
                                                            pattern="dd.MM.yyyy HH:mm:ss"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <span class="text-muted">
                                        <c:out value="${equipment.actionType}"/>
                                    </span>
                                </td>
                                <td><c:out value="${equipment.username}"/></td>
                                <td>
                                    <a href="<c:url value="/equipment/${equipment.entityId}"/>">
                                        <c:out value="${equipment.entityName}"/>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </security:authorize>

    <security:authorize access="hasRole('ROLE_ACCOUNT_VIEW')">
        <div class="col-md-6">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        Accounts <a href="<c:url value="/accounts"/>">
                        <span class="badge"><c:out value="${accountsWidget.total}"/></span></a>
                    </h3>
                </div>
                <div class="panel-body">
                    <table class="status-table">
                        <thead>
                        <tr>
                            <th colspan="2">Status</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>
                                <span class="text-success">NORMAL</span>
                                <a href="<c:url value="/accounts?status=NORMAL"/>">
                                    <span class="badge">
                                        <c:out value="${accountsWidget.normal}"/>
                                    </span>
                                </a>
                            </td>
                            <td>
                                <span class="text-danger">WARNING</span>
                                <a href="<c:url value="/accounts?status=WARNING"/>">
                                    <span class="badge">
                                        <c:out value="${accountsWidget.warning}"/>
                                    </span>
                                </a>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <table class="table table-condensed panel-table">
                        <thead>
                        <tr>
                            <th>Change date</th>
                            <th>Change type</th>
                            <th>User</th>
                            <th>Account</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${accountsWidget.lastChanges}" var="account">
                        <tr>
                            <td>
                                <c:choose>
                                    <c:when test="${hasRootRole}">
                                        <a href="<c:out value="/actions/${account.actionId}"/>">
                                            <fmt:formatDate value="${account.actionTimestamp}" type="both"
                                                            pattern="dd.MM.yyyy HH:mm:ss"/>
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:formatDate value="${account.actionTimestamp}" type="both"
                                                        pattern="dd.MM.yyyy HH:mm:ss"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <span class="text-muted">
                                    <c:out value="${account.actionType}"/>
                                </span>
                            </td>
                            <td><c:out value="${account.username}"/></td>
                            <td>
                                <a href="<c:url value="/accounts/${account.entityId}"/>">
                                    <c:out value="${account.entityName}"/>
                                </a>
                            </td>
                        </tr>
                        </c:forEach>
                        <tbody>
                    </table>
                </div>
            </div>
        </div>
    </security:authorize>
 </div>
<div class="row">
    <security:authorize access="hasRole('ROLE_COMMUNIGATE_VIEW')">
        <div class="col-md-6">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        Communigate domains <a href="<c:url value="/communigate"/>">
                        <span class="badge"><c:out value="${communigateWidget.total}"/></span></a>
                    </h3>
                </div>
                <div class="panel-body">
                    <table class="status-table">
                        <thead>
                        <tr>
                            <th colspan="2">Status</th>
                        </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>
                                    <span class="text-success">NORMAL</span>
                                    <a href="<c:url value="/communigate?status=NORMAL"/>">
                                            <span class="badge">
                                                <c:out value="${communigateWidget.normal}"/>
                                            </span>
                                    </a>
                                </td>
                                <td>
                                    <span class="text-danger">BLOCKED</span>
                                    <a href="<c:url value="/communigate?status=BLOCKED"/>">
                                            <span class="badge">
                                                <c:out value="${communigateWidget.blocked}"/>
                                            </span>
                                    </a>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <table class="table table-condensed panel-table">
                        <thead>
                        <tr>
                            <th>Change date</th>
                            <th>Change type</th>
                            <th>User</th>
                            <th>Domain</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${communigateWidget.lastChanges}" var="communigate">
                        <tr>
                            <td>
                                <c:choose>
                                    <c:when test="${hasRootRole}">
                                        <a href="<c:out value="/actions/${communigate.actionId}"/>">
                                            <fmt:formatDate value="${communigate.actionTimestamp}" type="both"
                                                            pattern="dd.MM.yyyy HH:mm:ss"/>
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:formatDate value="${communigate.actionTimestamp}" type="both"
                                                        pattern="dd.MM.yyyy HH:mm:ss"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <span class="text-muted">
                                    <c:out value="${communigate.actionType}"/>
                                </span>
                            </td>
                            <td><c:out value="${communigate.username}"/></td>
                            <td>
                                <a href="<c:url value="/communigate/${communigate.entityId}"/>">
                                    <c:out value="${communigate.entityName}"/>
                                </a>
                            </td>
                        </tr>
                        </c:forEach>
                        <tbody>
                    </table>
                </div>
            </div>
        </div>
    </security:authorize>
    <security:authorize access="hasRole('ROLE_ROOT')">
        <div class="col-md-6">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        Actions <a href="<c:url value="/actions"/>">
                        <span class="badge"><c:out value="${actionsWidget.total}"/></span></a>
                        &amp; Changes <a href="<c:url value="/changes"/>">
                        <span class="badge"><c:out value="${actionsWidget.changes}"/></span></a>
                    </h3>
                </div>
                <div class="panel-body">
                    <table class="status-table">
                        <thead>
                        <tr>
                            <th colspan="4">Change types</th>
                        </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>
                                    <span class="text-muted">EQUIPMENT</span>
                                    <a href="<c:url value="/changes?changeType=EQUIPMENT"/>">
                                        <span class="badge">
                                            <c:out value="${actionsWidget.equipmentChanges}"/>
                                        </span>
                                    </a>
                                </td>
                                <td>
                                    <span class="text-muted">ACCOUNTS</span>
                                    <a href="<c:url value="/changes?changeType=ACCOUNTS"/>">
                                        <span class="badge">
                                            <c:out value="${actionsWidget.accountsChanges}"/>
                                        </span>
                                    </a>
                                </td>
                                <td>
                                    <span class="text-muted">COMMUNIGATE</span>
                                    <a href="<c:url value="/changes?changeType=COMMUNIGATE"/>">
                                        <span class="badge">
                                            <c:out value="${actionsWidget.communigateChanges}"/>
                                        </span>
                                    </a>
                                </td>
                                <td>
                                    <span class="text-muted">USERS</span>
                                    <a href="<c:url value="/changes?changeType=USERS"/>">
                                        <span class="badge">
                                            <c:out value="${actionsWidget.usersChanges}"/>
                                        </span>
                                    </a>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <table class="table table-condensed panel-table">
                        <thead>
                        <tr>
                            <th>Action date</th>
                            <th>Action type</th>
                            <th>User</th>
                            <th>Changes</th>
                        </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${actionsWidget.lastActions}" var="action">
                            <tr>
                                <td>
                                    <a href="<c:out value="/actions/${action.id}"/>">
                                        <fmt:formatDate value="${action.actionTimestamp}" type="both" pattern="dd.MM.yyyy HH:mm:ss"/>
                                    </a>
                                </td>
                                <td>
                                    <span class="text-muted">
                                        <c:out value="${action.type}"/>
                                    </span>
                                </td>
                                <td><c:out value="${action.username}"/></td>
                                <td>
                                    <c:if test="${action.changesCount != 0}">
                                        <span class="badge">
                                            <c:out value="${action.changesCount}"/>
                                        </span>
                                    </c:if>
                                </td>
                            </tr>
                            </c:forEach>
                        <tbody>
                    </table>
                </div>
            </div>
        </div>
    </security:authorize>
</div>