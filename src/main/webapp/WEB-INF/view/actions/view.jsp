<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ipstore" tagdir="/WEB-INF/tags/ipstore" %>

<div class="form-horizontal">

    <ipstore:field label="Date" value="${action.actionTimestamp}" type="datetime"/>
    <ipstore:field label="Type" value="${action.type}"/>
    <ipstore:field label="Ip" value="${action.ip}"/>
    <ipstore:field label="Host" value="${action.host}"/>
    <ipstore:field label="Username" value="${action.username}"/>
    <ipstore:field label="User agent" value="${action.userAgent}"/>
    <ipstore:field label="Request URL" value="${action.requestURL}"/>
</div>
<c:if test="${not empty action.changes}">
    <table class="tablesorter">
        <thead>
        <tr>
            <th>Type</th>
            <th>Field type</th>
            <th>Old value</th>
            <th>New value</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${action.changes}" var="change">
            <tr>
                <td>
                    <a href="<c:url value="${change.type.viewPageURL}/${change.entityId}"/>" target="_blank">
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
</c:if>
<div class="row">
    <div class="col-md-offset-2 col-md-5">
        <a href="<c:url value="/actions"/>" class="btn btn-primary">List</a>
    </div>
</div>
