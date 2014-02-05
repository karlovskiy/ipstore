<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.tablesorter.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/list.js"/>"></script>
<div class="container">
    <table id="list_table" class="table table-hover table-condensed tablesorter">
        <thead>
        <tr>
            <th class="left-col">Login</th>
            <th>ClientName</th>
            <th>Number</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${accounts}" var="account">
            <tr class="${account.status}">
                <td class="left-col">
                    <a href="/accounts/view/${account.id}">
                        <c:out value="${account.login}"/>
                    </a>
                </td>
                <td><c:out value="${account.clientName}"/></td>
                <td><c:out value="${account.number}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>