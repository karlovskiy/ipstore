<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table class="tablesorter">
    <thead>
    <tr>
        <th>Username</th>
        <th>Status</th>
        <th>Authority</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${users}" var="user">
        <tr class="${bootstrap.rowClass(user.userStatus)}">
            <td>
                <a href="<c:url value="/users/${user.id}"/>">
                    <c:out value="${user.username}"/>
                </a>
            </td>
            <td>
                <span class="${bootstrap.labelClass(user.userStatus)}">
                    <c:out value="${user.userStatus}"/>
                </span>
            </td>
            <td><c:out value="${user.authorities}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>