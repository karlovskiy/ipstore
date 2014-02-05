<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="container">
    <form:form commandName="changePassword" action="/changepassword" cssClass="form-horizontal">
        <div class="control-group">
            <label class="control-label" for="username">Username</label>

            <div class="controls">
                <input id="username" type="text"
                       value="<security:authentication property="principal.username"/>"
                       disabled="disabled">
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="oldPassword">Old password</label>

            <div class="controls">
                <form:password id="oldPassword" path="oldPassword"/>
                <span class="errorblock">
                    <form:errors path="oldPassword"/>
                </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="newPassword">New password</label>

            <div class="controls">
                <form:password id="newPassword" path="newPassword"/>
                <span class="errorblock">
                    <form:errors path="newPassword"/>
                </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="repeatNewPassword">Repeat new password</label>

            <div class="controls">
                <form:password id="repeatNewPassword" path="repeatNewPassword"/>
                <span class="errorblock">
                    <form:errors path="repeatNewPassword"/>
                </span>
            </div>
        </div>
        <div class="form-actions">
            <button type="submit" class="btn btn-primary">Change password</button>
        </div>
    </form:form>
</div>