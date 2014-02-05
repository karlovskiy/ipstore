<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="<c:url value="/resources/js/init-datepicker.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/change-types.js"/>"></script>
<div class="container">
    <form:form commandName="changesForm" class="form-inline" action="/changes">
        <form:input id="from" path="from" cssClass="input-small" placeholder="From"/>
        <form:input id="to" path="to" cssClass="input-small" placeholder="To"/>
        <form:input id="username" path="username" cssClass="input-small" placeholder="Username"/>
        <form:select id="changeType" path="changeType" cssClass="input-medium" items="${changesTypes}"/>
        <form:select id="fieldType" path="fieldType" cssClass="input-large"
                     cssStyle="${empty changesForm.fieldType ? 'display: none;' : ''}">
            <c:if test="${not empty changesForm.fieldType}">
                <form:option value="${changesForm.fieldType}"/>
            </c:if>
        </form:select>
        <button type="submit" class="btn btn-primary">Search</button>
    </form:form>
    <table class="table table-hover table-condensed">
        <thead>
        <tr>
            <th>Date</th>
            <th class="nw">Ip address</th>
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
            <tr class="info">
                <td class="nw">
                    <fmt:formatDate value="${change.actionTimestamp}"
                                    type="both" pattern="dd.MM.yyyy HH:mm:ss"/>
                </td>
                <td><c:out value="${change.ip}"/></td>
                <td><c:out value="${change.username}"/></td>
                <td><c:out value="${change.actionType}"/></td>
                <td>
                    <a href="${change.type.viewPageURL}/${change.entityId}" target="_blank">
                        <c:out value="${change.type}"/>
                    </a>
                </td>
                <td><c:out value="${change.fieldType}"/></td>
                <td><c:out value="${change.oldValue}"/></td>
                <td><c:out value="${change.newValue}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>