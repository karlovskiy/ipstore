<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<form:form commandName="changesForm" class="form-inline" action="/changes" role="form">

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
        <form:select id="changeType" path="changeType" items="${changesTypes}" cssClass="form-control"/>
    </div>

    <div class="form-group">
        <form:select id="fieldType" path="fieldType" cssClass="form-control"
                     cssStyle="${empty changesForm.fieldType ? 'display: none;' : ''}">
            <c:if test="${not empty changesForm.fieldType}">
                <form:option value="${changesForm.fieldType}"/>
            </c:if>
        </form:select>
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
        <th>Action type</th>
        <th>Type</th>
        <th>Field type</th>
        <th>Old value</th>
        <th>New value</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${changes}" var="change">
        <tr>
            <td>
                <a href="<c:url value="/actions/${change.actionId}"/>">
                    <fmt:formatDate value="${change.actionTimestamp}" type="both" pattern="dd.MM.yyyy HH:mm:ss"/>
                </a>
            </td>
            <td><c:out value="${change.ip}"/></td>
            <td><c:out value="${change.username}"/></td>
            <td><c:out value="${change.actionType}"/></td>
            <td>
                <a href="<c:url value="${change.type.viewPageURL}/${change.entityId}"/>" target="_blank">
                    <c:out value="${change.type}"/>
                </a>
            </td>
            <td><c:out value="${change.fieldType}"/></td>
            <td class="overflow-column" title="<c:out value="${change.oldValue}"/>">
                <c:out value="${change.oldValue}"/>
            </td>
            <td class="overflow-column" title="<c:out value="${change.newValue}"/>">
                <c:out value="${change.newValue}"/>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>