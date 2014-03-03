<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="form-horizontal">

    <div class="form-group">
        <label class="control-label col-md-2">Login</label>
        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${account.login}"/>
            </p>
        </div>
    </div>

    <security:authorize access="hasRole('ROLE_ACCOUNT_EDIT')">
        <div class="form-group">
            <label class="control-label col-md-2">Password</label>

            <div class="col-md-5">
                <p class="form-control-static">
                    <c:out value="${account.password}"/>
                </p>
            </div>
        </div>
    </security:authorize>

    <div class="form-group">
        <label class="control-label col-md-2">Status</label>

        <div class="col-md-5">
            <p class="form-control-static">
                <span class="${bootstrap.labelClass(account.status)}">
                    <c:out value="${account.status}"/>
                </span>
            </p>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-md-2">ClientName</label>

        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${account.clientName}"/>
            </p>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-md-2">Number</label>

        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${account.number}"/>
            </p>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-md-2">Description</label>

        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${account.description}"/>
            </p>
        </div>
    </div>

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