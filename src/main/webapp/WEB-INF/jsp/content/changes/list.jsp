<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="<c:url value="/js/init-datepicker.js"/>"></script>
<div class="container">
    <table class="table table-hover table-condensed">
        <thead>
        <tr>
            <th>Date</th>
            <th class="nw">Ip address</th>
            <th>Username</th>
            <th>Action type</th>
            <th>Type</th>
            <th>Field type</th>
            <th>Old value</th>
            <th>New value</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${changes}" var="change">
            <tr class="info">
                <td class="nw">
                    <fmt:formatDate value="${change.actionTimestamp}"
                                    type="both" pattern="dd.MM.yyyy HH:mm:ss"/>
                </td>
                <td><c:out value="${change.ip}"/></td>
                <td><c:out value="${change.username}"/></td>
                <td><c:out value="${change.actionType}"/></td>
                <td>
                    <c:choose>
                        <c:when test="${change.type == 'EQUIPMENT'}">
                            <a href="/ipstore/equipment/view/${change.entityId}" target="_blank">
                                <c:out value="${change.type}"/>
                            </a>
                        </c:when>
                        <c:when test="${change.type == 'ACCOUNTS'}">
                            <a href="/ipstore/accounts/view/${change.entityId}" target="_blank">
                                <c:out value="${change.type}"/>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${change.type}"/>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td><c:out value="${change.fieldType}"/></td>
                <td><c:out value="${change.oldValue}"/></td>
                <td><c:out value="${change.newValue}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>