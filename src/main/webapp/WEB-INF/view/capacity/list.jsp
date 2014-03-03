<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<form:form commandName="capacityForm" class="form-inline" action="/capacity" method="get" role="form">
    <div class="form-group">
        <form:select id="capacityType" path="capacityType" items="${capacityTypes}" cssClass="form-control"/>
    </div>
    <div class="form-group">
        <label class="sr-only" for="keyword">Keyword</label>
        <form:input id="keyword" path="keyword" cssClass="form-control" placeholder="keyword"/>
    </div>
    <button type="submit" class="btn btn-primary">Load</button>
</form:form>

<c:if test="${not empty result}">
    <c:forEach items="${result}" var="capacity">
        <h4>
            <c:out value="${capacity.name}"/>
            <security:authorize access="hasRole('ROLE_CAPACITY_EDIT')">
                <a class="btn btn-primary btn-xs" href="<c:url value="/capacity/${capacity.id}/edit"/>">
                    <span class="glyphicon glyphicon-pencil"></span>
                </a>
                <a class="btn btn-primary btn-xs" href="<c:url value="/capacity/${capacity.id}/numbers/new"/>">
                    <span class="glyphicon glyphicon-plus"></span>
                </a>
            </security:authorize>
        </h4>

        <table class="tablesorter" style="margin-top: 20px;">
            <thead>
            <tr>
                <th>Number</th>
                <th>ClientName</th>
                <th>Address</th>
                <th>Status</th>
                <th>Last change</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${capacity.numbers}" var="number">
                <tr>
                    <td>
                        <a href="<c:url value="/capacity/${capacity.id}/numbers/${number.id}"/>">
                            <c:out value="${number.number}"/>
                        </a>
                    </td>
                    <td><c:out value="${number.clientName}"/></td>
                    <td><c:out value="${number.address}"/></td>
                    <td><c:out value="${number.status}"/></td>
                    <td>
                        <span>username: <c:out value="${number.lscUsername}"/></span>
                            <span>, time: <fmt:formatDate value="${number.lscTimestamp}"
                                                          type="both" pattern="dd.MM.yyyy HH:mm:ss"/></span>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:forEach>
</c:if>