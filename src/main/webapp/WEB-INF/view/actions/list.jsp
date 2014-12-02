<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url var="formActionUrl" value="/actions"/>
<form:form commandName="actionsForm" class="form-inline" action="${formActionUrl}" role="form">

    <div class="form-group">
        <label class="sr-only" for="from">From</label>
        <form:input id="from" path="from" placeholder="From" cssClass="form-control"/>
    </div>

    <div class="form-group">
        <label class="sr-only" for="to">To</label>
        <form:input id="to" path="to" placeholder="To" cssClass="form-control"/>
    </div>

    <div class="form-group">
        <label class="sr-only" for="username">Username</label>
        <form:input id="username" path="username" placeholder="Username" cssClass="form-control"/>
    </div>

    <div class="form-group">
        <form:select path="actionType" items="${actionTypes}" cssClass="form-control"/>
    </div>

    <button type="submit" class="btn btn-primary">
        <span class="glyphicon glyphicon-search"></span> Search
    </button>
</form:form>

<table class="tablesorter" style="margin-top: 20px;">
    <thead>
    <tr>
        <th>Date</th>
        <th>Ip address</th>
        <th>Username</th>
        <th>Type</th>
        <th>Changes</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${actions}" var="action">
        <tr>
            <td>
                <a href="<c:url value="/actions/${action.id}"/>">
                    <fmt:formatDate value="${action.actionTimestamp}" type="both" pattern="dd.MM.yyyy HH:mm:ss"/>
                </a>
            </td>
            <td><c:out value="${action.ip}"/></td>
            <td><c:out value="${action.username}"/></td>
            <td><c:out value="${action.type}"/></td>
            <td>
                <c:if test="${action.changesCount != 0}">
                    <span class="badge">
                        <c:out value="${action.changesCount}"/>
                    </span>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>