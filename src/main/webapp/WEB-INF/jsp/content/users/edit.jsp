<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="<c:url value="/resources/js/authority.js"/>"></script>
<div class="container">
    <form:form id="form" commandName="user" action="${formAction}" cssClass="form-horizontal">
        <div class="control-group">
            <label class="control-label" for="username">Username</label>

            <div class="controls">
                <form:input id="username" path="username"/>
                <span class="errorblock">
                    <form:errors path="username"/>
                    <c:if test="${not empty existsUser}">
                        <span>
                            User with username
                            <a href="<c:url value="/users/view/${existsUser.id}"/>" target="_blank">
                                <c:out value="${existsUser.username}"/>
                            </a>
                            already exists!
                        </span>
                    </c:if>
                </span>
            </div>
        </div>
        <div class="control-group">
            <span class="control-label">Authority</span>

            <c:forEach items="${user.allAuthorities}" var="authority">
                <div class="controls">
                    <label class="checkbox authority">
                        <input id="${authority}" type="checkbox" value="">
                        <span>${authority}</span>
                    </label>
                </div>
            </c:forEach>
            <form:hidden id="authorities" path="authorities"/>
        </div>
        <div class="control-group">
            <label class="control-label" for="email">Email</label>

            <div class="controls">
                <form:input id="email" path="email"/>
                <span class="errorblock">
                    <form:errors path="email"/>
                </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="firstName">FirstName</label>

            <div class="controls">
                <form:input id="firstName" path="firstName" cssClass="input-xxlarge"/>
                <span class="errorblock">
                    <form:errors path="firstName"/>
                </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="lastName">LastName</label>

            <div class="controls">
                <form:input id="lastName" path="lastName" cssClass="input-xxlarge"/>
                <span class="errorblock">
                    <form:errors path="lastName"/>
                </span>
            </div>
        </div>
        <div class="form-actions">
            <c:if test="${empty user.id}">
                <a href="<c:url value="/users"/>" class="btn btn-primary">List</a>
            </c:if>
            <c:if test="${not empty user.id}">
                <a href="<c:url value="/users/view/${user.id}"/>" class="btn btn-primary">View</a>
            </c:if>
            <button type="submit" class="btn btn-primary">Save</button>
        </div>

    </form:form>
</div>