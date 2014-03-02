<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table class="tablesorter">
    <thead>
    <tr>
        <th>Login</th>
        <th>ClientName</th>
        <th>Number</th>
        <th>Status</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${accounts}" var="account">
        <tr class="${bootstrap.rowClass(account.status)}">
            <td>
                <a href="<c:url value="/accounts/view/${account.id}"/>">
                    <c:out value="${account.login}"/>
                </a>
            </td>
            <td><c:out value="${account.clientName}"/></td>
            <td><c:out value="${account.number}"/></td>
            <td>
                <span class="${bootstrap.labelClass(account.status)}">
                    <c:out value="${account.status}"/>
                </span>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>