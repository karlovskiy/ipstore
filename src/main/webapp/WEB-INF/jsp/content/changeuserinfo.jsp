<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="container">
    <form:form commandName="changeUserInfo" action="/changeuserinfo" cssClass="form-horizontal">
        <div class="control-group">
            <label class="control-label" for="username">Username</label>

            <div class="controls">
                <input id="username" type="text"
                       value="<security:authentication property="principal.username"/>"
                       disabled="disabled">
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="password">Password</label>

            <div class="controls">
                <form:password id="password" path="password"/>
                <span class="errorblock">
                    <form:errors path="password"/>
                </span>
            </div>
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
            <button type="submit" class="btn btn-primary">Save</button>
        </div>
    </form:form>
</div>