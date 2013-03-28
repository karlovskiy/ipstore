<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="container">
    <div class="hero-unit">
        <h1>Welcome to VoIPStore!</h1>

        <p>
            You have
            <security:authorize access="hasRole('ROLE_ADMIN')">
                <span class="text-success">administration</span>
            </security:authorize>
            <security:authorize access="hasRole('ROLE_ROOT')">
                and <span class="text-success">management</span>
            </security:authorize> rights.
            <br/>
            Use the menu above or the buttons below to start working with the equipment or accounts.
            <br/>
            Good luck.
        </p>

        <p>
            <a href="<c:url value="/ipstore/equipment"/>"
               class="btn btn-primary btn-large">Equipment list »</a>
            <a href="<c:url value="/ipstore/accounts"/>"
               class="btn btn-primary btn-large">Accounts list »</a>
        </p>
    </div>
</div>