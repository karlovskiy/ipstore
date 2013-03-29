<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="container">
    <div class="hero-unit">
        <h1>Welcome to VoIPStore, <security:authentication property="principal.username"/>!</h1>

        <p>
            You have
            <security:authorize access="hasRole('ROLE_ADMIN')">
                <span class="text-success">administration</span>
            </security:authorize>
            <security:authorize access="hasRole('ROLE_ROOT')">
                and <span class="text-success">management</span>
            </security:authorize> rights.
            <br/>
            Use the menu above below to start working with the equipment or accounts.
            <br/>

        </p>

        <p>
            Good luck.
        </p>
    </div>
</div>