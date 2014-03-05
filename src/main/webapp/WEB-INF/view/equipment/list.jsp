<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table class="tablesorter">
    <thead>
    <tr>
        <th>Ip address</th>
        <th>Type</th>
        <th>Client name</th>
        <th>Placement Address</th>
        <th>Password status</th>
        <th>Telnet status</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${equipment}" var="equipment">
        <tr class="${application.rowClass(equipment.passwordStatus)}">
            <td>
                <a href="<c:url value="/equipment/${equipment.id}"/>">
                    <c:out value="${equipment.ipAddress}"/>
                </a>
            </td>
            <td><c:out value="${equipment.type}"/></td>
            <td><c:out value="${equipment.clientName}"/></td>
            <td><c:out value="${equipment.placementAddress}"/></td>
            <td>
                <span class="${application.labelClass(equipment.passwordStatus)}">
                    <c:out value="${equipment.passwordStatus}"/>
                </span>
            </td>
            <td>
                <span class="${application.labelClass(equipment.telnetStatus)}">
                    <c:out value="${equipment.telnetStatus}"/>
                </span>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>