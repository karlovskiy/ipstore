<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="<c:url value="/resources/js/init-datepicker.js"/>"></script>
<div class="container">
    <form:form commandName="actionsForm" class="form-inline" action="/actions">
        <form:input id="from" path="from" cssClass="input-small" placeholder="From"/>
        <form:input id="to" path="to" cssClass="input-small" placeholder="To"/>
        <form:input id="username" path="username" cssClass="input-small" placeholder="Username"/>
        <form:select path="actionType" items="${actionTypes}" cssClass="input-xlarge"/>
        <button type="submit" class="btn btn-primary">Search</button>
    </form:form>
    <table class="table table-hover table-condensed">
        <thead>
        <tr>
            <th>Date</th>
            <th class="nw">Ip address</th>
            <th>Username</th>
            <th>Type</th>
            <th>Changes</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${actions}" var="action">
            <tr class="info">
                <td class="nw">
                    <a href="/actions/view/${action.id}">
                        <fmt:formatDate value="${action.actionTimestamp}"
                                        type="both" pattern="dd.MM.yyyy HH:mm:ss"/>
                    </a>
                </td>
                <td><c:out value="${action.ip}"/></td>
                <td><c:out value="${action.username}"/></td>
                <td><c:out value="${action.type}"/></td>
                <td>
                    <c:if test="${action.changesCount != 0}">
                        <c:out value="${action.changesCount}"/>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>