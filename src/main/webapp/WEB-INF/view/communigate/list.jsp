<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<table class="tablesorter">
    <thead>
    <tr>
        <th>Doname Name</th>
        <th>TRY prefix</th>
        <th>Client Name</th>
        <th>Date</th>
        <th>Status</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${communigateDomains}" var="communigateDomain">
        <tr class="${bootstrap.rowClass(communigateDomain.status)}">
            <td>
                <a href="<c:url value="/communigate/${communigateDomain.id}"/>">
                    <c:out value="${communigateDomain.domainName}"/>
                </a>
            </td>
            <td><c:out value="${communigateDomain.tryPrefix}"/></td>
            <td><c:out value="${communigateDomain.clientName}"/></td>
            <td><fmt:formatDate value="${communigateDomain.date}" type="date" pattern="dd.MM.yyyy"/></td>
            <td>
                <span class="${bootstrap.labelClass(communigateDomain.status)}">
                    <c:out value="${communigateDomain.status}"/>
                </span>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>