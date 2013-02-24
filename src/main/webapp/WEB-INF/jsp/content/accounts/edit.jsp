<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="<c:url value="/js/edit.js"/>"></script>
<div class="container">
    <form:form commandName="account" action="${formAction}" cssClass="form-horizontal">
        <div class="control-group">
            <label class="control-label" for="login">Login</label>

            <div class="controls">
                <form:input id="login" path="login" readonly="${edit}" cssClass="${edit ? 'uneditable-input' : ''}"/>
                <span class="errorblock">
                    <form:errors path="login"/>
                    <c:if test="${not empty existsAccount}">
                        <span>
                            Account with login
                            <a href="<c:url value="/ipstore/accounts/view/${existsAccount.id}"/>" target="_blank">
                                <c:out value="${existsAccount.login}"/>
                            </a>
                            already exists!
                        </span>
                    </c:if>
                </span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="pwd">Password</label>

            <div class="controls">
                <form:input id="pwd" path="password"/>
                <span class="errorblock">
                    <form:errors path="password"/>
                </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="clientName">ClientName</label>

            <div class="controls">
                <form:input id="clientName" path="clientName"/>
                <span class="errorblock">
                    <form:errors path="clientName"/>
                </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="number">Number</label>

            <div class="controls">
                <form:input id="number" path="number"/>
                <span class="errorblock">
                    <form:errors path="number"/>
                </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="description">Description</label>

            <div class="controls">
                <form:input id="description" path="description" cssClass="input-xxlarge"/>
                <span class="errorblock">
                    <form:errors path="description"/>
                </span>
            </div>
        </div>
        <div class="form-actions">
            <button type="submit" class="btn btn-primary">Save</button>
            <a id="generate_password" class="btn btn-success">Generate password</a>
            <input id="max_length" type="text" name="length" style="width: 20px;" value="${defaultPasswordLength}">
        </div>

    </form:form>
</div>