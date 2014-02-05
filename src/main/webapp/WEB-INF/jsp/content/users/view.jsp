<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="<c:url value="/resources/js/view.js"/>"></script>
<div class="container">
    <div id="view-fields">
        <div class="row">
            <div class="span1">Username</div>
            <div class="span5">
                <span class="block">
                    <c:out value="${user.username}"/>
                </span>
            </div>
        </div>
        <div class="row">
            <div class="span1">Status</div>
            <div class="span5">
                <span class="${user.userStatus} block"><c:out value="${user.userStatus}"/></span>
            </div>
        </div>
        <div class="row">
            <div class="span1">Authority</div>
            <div class="span2">
                <span class="block">
                    <c:out value="${user.authorities}"/>
                </span>
            </div>
        </div>
        <div class="row">
            <div class="span1">FirstName</div>
            <div class="span5">
                <span class="block">
                    <c:out value="${user.firstName}"/>
                </span>
            </div>
        </div>
        <div class="row">
            <div class="span1">LastName</div>
            <div class="span5">
                <span class="block">
                    <c:out value="${user.lastName}"/>
                </span>
            </div>
        </div>
        <div class="row">
            <div class="span1">Email</div>
            <div class="span5">
                <span class="block">
                    <c:out value="${user.email}"/>
                </span>
            </div>
        </div>
    </div>
    <div id="view-buttons">
        <div class="row">
            <div class="span1">
                <a href="<c:url value="/users"/>" class="btn btn-primary btn-block">List</a>
            </div>
            <div class="span1 ml-10px">
                <a href="<c:url value="/users/edit/${user.id}"/>"
                   class="btn btn-primary btn-block">Edit</a>
            </div>
            <div class="span2 ml-10px">
                <a href="<c:url value="/users/reset/${user.id}"/>"
                   class="btn btn-danger btn-block">Reset password</a>
            </div>
            <c:if test="${user.userStatus == 'BLOCKED'}">
                <div class="span1 ml-10px">
                    <a href="<c:url value="/users/unblock/${user.id}"/>"
                       class="btn btn-success btn-block">Unblock</a>
                </div>
            </c:if>
            <c:if test="${user.userStatus == 'ACTIVE'}">
                <div class="span1 ml-10px">
                    <a href="<c:url value="/users/block/${user.id}"/>"
                       class="btn btn-warning btn-block">Block</a>
                </div>
            </c:if>
        </div>
    </div>
</div>