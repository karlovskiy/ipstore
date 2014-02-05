<%--
  User: Sidorov Oleg
  Date: 25.03.13
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.tablesorter.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/list.js"/>"></script>
<div class="container">
    <table id="list_table" class="table table-hover table-condensed tablesorter">
        <thead>
        <tr>
            <th class="left-col">Doname Name</th>
            <th>TRY prefix</th>
            <th>Client Name</th>
            <th>Date</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${communigateDomains}" var="communigateDomains">
            <tr class="${communigateDomains.status}">
                <td class="left-col">
                    <a href="/communigate/view/${communigateDomains.id}">
                        <c:out value="${communigateDomains.domainName}"/>
                    </a>
                </td>
                <td><c:out value="${communigateDomains.tryPrefix}"/></td>
                <td><c:out value="${communigateDomains.clientName}"/></td>
                <td><fmt:formatDate value="${communigateDomains.date}" type="date"
                                    pattern="dd.MM.yyyy"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>