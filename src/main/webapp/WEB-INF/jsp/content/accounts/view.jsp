<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="<c:url value="/js/bootbox.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/accounts/view.js"/>"></script>
<div class="container">
    <div id="view-fields">
        <div class="row">
            <div class="span1">Login</div>
            <div class="span5">
                <span class="block">
                    <c:out value="${account.login}"/>
                </span>
            </div>
        </div>
        <div class="row">
            <div class="span1">Password</div>
            <div class="span5">
                <span class="block">
                    <c:out value="${account.password}"/>
                </span>
            </div>
        </div>
        <div class="row">
            <div class="span1">ClientName</div>
            <div class="span5">
                <span class="block">
                    <c:out value="${account.clientName}"/>
                </span>
            </div>
        </div>
        <div class="row">
            <div class="span1">Number</div>
            <div class="span5">
                <span class="block">
                    <c:out value="${account.number}"/>
                </span>
            </div>
        </div>
        <div class="row">
            <div class="span1">Description</div>
            <div class="span5">
                <span class="block">
                    <c:out value="${account.description}"/>
                </span>
            </div>
        </div>
        <div class="row">
            <div class="span1">Status</div>
            <div class="span5">
                <span class="${account.status} block"><c:out value="${account.status}"/></span>
            </div>
        </div>
    </div>
    <security:authorize access="hasRole('ROLE_ADMIN')">
        <div id="view-buttons">
            <div class="row">
                <div class="span1">
                    <a href="<c:url value="/ipstore/accounts"/>" class="btn btn-primary btn-block">List</a>
                </div>
                <div class="span1 ml-10px">
                    <a href="<c:url value="/ipstore/accounts/edit/${account.id}"/>"
                       class="btn btn-primary btn-block">Edit</a>
                </div>
                <security:authorize access="hasRole('ROLE_ROOT')">
                    <c:if test="${account.status == 'DELETED'}">
                        <div class="span1 ml-10px">
                            <a href="<c:url value="/ipstore/accounts/activate/${account.id}"/>"
                               class="btn btn-success btn-block">Activate</a>
                        </div>
                    </c:if>
                </security:authorize>
                <c:if test="${account.status != 'DELETED'}">
                    <c:if test="${account.status == 'NORMAL'}">
                        <div class="span1 ml-10px">
                            <a href="<c:url value="/ipstore/accounts/block/${account.id}"/>"
                               class="btn btn-warning btn-block">Block</a>
                        </div>
                    </c:if>
                    <c:if test="${account.status == 'WARNING'}">
                        <div class="span1 ml-10px">
                            <a href="<c:url value="/ipstore/accounts/unblock/${account.id}"/>"
                               class="btn btn-warning btn-block">Unblock</a>
                        </div>
                    </c:if>
                    <security:authorize access="hasRole('ROLE_ROOT')">
                        <div class="span1 ml-10px">
                            <a href="<c:url value="/ipstore/accounts/delete/${account.id}"/>"
                               class="btn btn-danger btn-block" id="account_delete">Delete</a>
                        </div>
                    </security:authorize>
                </c:if>
            </div>
        </div>
    </security:authorize>
</div>