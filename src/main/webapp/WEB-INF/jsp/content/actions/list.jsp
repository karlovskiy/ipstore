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
            <th>Username</th>
            <th>Type</th>
            <th>Request URL</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${actions}" var="action">
            <tr class="info">
                <td class="nw"><fmt:formatDate value="${action.actionTimestamp}" type="both"
                                               pattern="dd.MM.yyyy HH:mm:ss"/></td>
                <td><c:out value="${action.ip}"/></td>
                <td><c:out value="${action.username}"/></td>
                <td>
                    <c:choose>
                        <c:when test="${not empty action.url}">
                            <a href="<c:url value="${action.url}"/>" target="_blank"><c:out value="${action.type}"/></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${action.type}"/>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td><c:out value="${action.requestURL}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>