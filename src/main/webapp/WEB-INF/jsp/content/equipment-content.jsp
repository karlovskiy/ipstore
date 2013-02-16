<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="<c:url value="/js/jquery.tablesorter.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/equipment.js"/>"></script>
<div class="container">
    <table id="equipment_table" class="table table-hover table-condensed tablesorter">
        <thead>
        <tr>
            <th class="left-col">Ip address</th>
            <th>Type</th>
            <th>Password status</th>
            <th>Client name</th>
            <th>Placement Address</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${equipment}" var="equipment">
            <tr class="${equipment.passwordStatus}">
                <td class="left-col">
                    <a href="/ipstore/equipment/${equipment.id}">
                        <c:out value="${equipment.ipAddress}"/>
                    </a>
                </td>
                <td><c:out value="${equipment.type}"/></td>
                <td><c:out value="${equipment.passwordStatus}"/></td>
                <td><c:out value="${equipment.clientName}"/></td>
                <td><c:out value="${equipment.placementAddress}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>