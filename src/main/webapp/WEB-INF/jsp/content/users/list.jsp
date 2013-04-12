<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="<c:url value="/js/jquery.tablesorter.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/list.js"/>"></script>
<div class="container">
    <table id="list_table" class="table table-hover table-condensed tablesorter">
        <thead>
        <tr>
            <th class="left-col">Username</th>
            <th>Status</th>
            <th>Authority</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>
                    <a href="/ipstore/users/view/${user.id}">
                        <c:out value="${user.username}"/>
                    </a>
                </td>
                <td><c:out value="${user.userStatus}"/></td>
                <td><c:out value="${user.authorities}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>