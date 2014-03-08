<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="ipstore" tagdir="/WEB-INF/tags/ipstore" %>

<div class="form-horizontal">

    <ipstore:field label="Login" value="${account.login}"/>

    <security:authorize access="hasRole('ROLE_ACCOUNT_EDIT')">
        <ipstore:field label="Password" value="${account.password}"/>
    </security:authorize>

    <ipstore:field label="Status" value="${account.status}" cssClass="${application.labelClass(account.status)}"/>
    <ipstore:field label="ClientName" value="${account.clientName}"/>
    <ipstore:field label="Number" value="${account.number}"/>
    <ipstore:field label="Description" value="${account.description}"/>

    <div class="form-group">
        <div class="col-md-offset-2 col-md-5">
            <security:authorize access="hasRole('ROLE_ACCOUNT_EDIT')">

                <a href="<c:url value="/accounts/${account.id}/edit"/>" class="btn btn-primary">Edit</a>

                <security:authorize access="hasRole('ROLE_ROOT')">
                    <c:if test="${account.status == 'DELETED'}">
                        <a href="<c:url value="/accounts/${account.id}/activate"/>"
                           class="btn btn-success">Activate</a>
                    </c:if>
                </security:authorize>

                <c:if test="${account.status != 'DELETED'}">

                    <c:if test="${account.status == 'NORMAL'}">
                        <a href="<c:url value="/accounts/${account.id}/block"/>"
                           class="btn btn-warning">Block</a>
                    </c:if>

                    <c:if test="${account.status == 'WARNING'}">
                        <a href="<c:url value="/accounts/${account.id}/unblock"/>"
                           class="btn btn-warning">Unblock</a>
                    </c:if>

                    <security:authorize access="hasRole('ROLE_ROOT')">
                        <a href="<c:url value="/accounts/${account.id}/delete"/>"
                               class="btn btn-danger" id="delete_btn">Delete</a>
                    </security:authorize>
                </c:if>
            </security:authorize>
        </div>
    </div>
</div>