<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.tablesorter.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/list.js"/>"></script>
<div class="container">
    <table id="list_table" class="table table-condensed tablesorter">
        <thead>
        <tr>
            <th class="left-col">Ip address</th>
            <th>Type</th>
            <th>Client name</th>
            <th>Placement Address</th>
            <th>Password status</th>
            <th>Telnet status</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${equipment}" var="equipment">
            <tr>
                <td class="left-col">
                    <a href="/equipment/view/${equipment.id}">
                        <c:out value="${equipment.ipAddress}"/>
                    </a>
                </td>
                <td><c:out value="${equipment.type}"/></td>
                <td><c:out value="${equipment.clientName}"/></td>
                <td><c:out value="${equipment.placementAddress}"/></td>
                <td class="${equipment.passwordStatus}"><c:out value="${equipment.passwordStatus}"/></td>
                <td class="${equipment.telnetStatus}"><c:out value="${equipment.telnetStatus}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>