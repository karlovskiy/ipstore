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
            <th>Action type</th>
            <th>Field type</th>
            <th>Old value</th>
            <th>New value</th>
            <th>Equipment</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${changes}" var="change">
            <tr class="info">
                <td class="nw"><fmt:formatDate value="${change.actionTimestamp}" type="both"
                                               pattern="dd.MM.yyyy HH:mm:ss"/></td>
                <td><c:out value="${change.ip}"/></td>
                <td><c:out value="${change.actionType}"/></td>
                <td><c:out value="${change.type}"/></td>
                <td><c:out value="${change.oldValue}"/></td>
                <td><c:out value="${change.newValue}"/></td>
                <td><a href="<c:out value="${change.equipmentURL}"/>" target="_blank">View</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>