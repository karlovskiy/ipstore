<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="<c:url value="/js/jquery.tablesorter.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/list.js"/>"></script>
<div class="container">
    <form:form commandName="capacityForm" class="form-inline" action="/ipstore/capacity" method="get">
        <form:select id="capacityType" path="capacityType" items="${capacityTypes}" cssClass="input-xlarge"/>
        <form:input id="keyword" path="keyword" cssClass="input-xlarge" placeholder="keyword"/>
        <button type="submit" class="btn btn-primary">Load</button>
    </form:form>

    <c:if test="${not empty result}">
        <c:forEach items="${result}" var="capacity" varStatus="status">
            <h4>
                <c:out value="${capacity.name}"/>
                <security:authorize access="hasRole('ROLE_CAPACITY_EDIT')">
                    <a class="btn" href="<c:url value="/ipstore/capacity/edit/${capacity.id}"/>"><i class="icon-pencil"></i></a>
                    <a class="btn" href="<c:url value="/ipstore/capacity/${capacity.id}/add"/>"><i class="icon-plus"></i></a>
                </security:authorize>

            </h4>
            <table id="list_table_${status.index}" class="table table-hover table-condensed tablesorter" style="margin-top: 20px;">
                <thead>
                <tr>
                    <th class="left-col">Number</th>
                    <th>ClientName</th>
                    <th>Address</th>
                    <th>Status</th>
                    <th>Last change</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${capacity.numbers}" var="number">
                    <tr class="${number.status}">
                        <td class="left-col">
                            <a href="/ipstore/capacity/number/view/${number.id}">
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

</div>