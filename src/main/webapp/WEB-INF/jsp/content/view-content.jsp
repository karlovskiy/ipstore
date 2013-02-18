<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="<c:url value="/js/bootbox.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/zeroclipboard.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/view.js"/>"></script>
<div class="container">
    <table class="table" style="width: 0;">
        <tbody>
        <tr>
            <td>IpAddress</td>
            <td colspan="2"><c:choose>
                <c:when test="${equipment.type == 'GS'}">
                    <a href="http://${equipment.ipAddress}" target="_blank"><c:out value="${equipment.ipAddress}"/></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${equipment.ipAddress}"/>
                </c:otherwise>
            </c:choose>
            </td>
        </tr>
        <tr>
            <td>Type</td>
            <td colspan="2"><c:out value="${equipment.type}"/></td>
        </tr>
        <tr>
            <td>Username</td>
            <td colspan="2"><c:out value="${equipment.username}"/></td>
        </tr>
        <tr>
            <td>Login</td>
            <td colspan="2"><c:out value="${equipment.login}"/></td>
        </tr>
        <tr>
            <td style="vertical-align: middle;">Password</td>
            <td colspan="2">
                <span>
                    <button id="copy_to_clipboard" class="btn btn-primary"
                            data-clipboard-text="${equipment.password}">Copy
                    </button>
                </span>
            </td>
        </tr>
        <tr>
            <td>Password date</td>
            <td colspan="2" style="white-space: nowrap;">
                <fmt:formatDate value="${equipment.passwordDate}" type="both" pattern="dd.MM.yyyy HH:mm:ss"/></td>
        </tr>
        <tr>
            <td>Password status</td>
            <td colspan="2" class="${equipment.passwordStatus}"><c:out value="${equipment.passwordStatus}"/></td>
        </tr>
        <tr>
            <td>ClientName</td>
            <td colspan="2"><c:out value="${equipment.clientName}"/></td>
        </tr>
        <tr>
            <td>PlacementAddress</td>
            <td><c:out value="${equipment.placementAddress}"/></td>
        </tr>
        <tr>
            <td>ApplicationNumber</td>
            <td colspan="2"><c:out value="${equipment.applicationNumber}"/></td>
        </tr>
        <tr>
            <td>Description</td>
            <td colspan="2"><c:out value="${equipment.description}"/></td>
        </tr>
        <c:if test="${equipment.status == 'DELETED'}">
            <tr>
                <td>Status</td>
                <td colspan="2"><span class="text-error"><c:out value="${equipment.status}"/></span></td>
            </tr>
        </c:if>
        </tbody>
        <security:authorize access="hasRole('ROLE_ADMIN')">
            <tfoot>
            <tr>
                <td>
                    <a class="btn btn-primary btn-block" href="<c:url value="/ipstore/edit/${equipment.id}" />">Edit</a>
                </td>
                <td>
                    <a class="btn btn-danger" id="equipment_delete"
                       href="<c:url value="/ipstore/delete/${equipment.id}"/>">Delete</a>
                </td>
                <c:if test="${equipment.status == 'DELETED'}">
                    <td>
                        <a class="btn btn-warning"
                           href="<c:url value="/ipstore/activate/${equipment.id}"/>">Activate</a>
                    </td>
                </c:if>
            </tr>
            </tfoot>
        </security:authorize>
    </table>
</div>