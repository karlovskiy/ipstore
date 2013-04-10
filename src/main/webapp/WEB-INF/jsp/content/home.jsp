<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="container">
    <div class="hero-unit">
        <h1>Welcome to VoIPStore, <security:authentication property="principal.username"/>!</h1>

        <p>
            You have
            <security:authorize access="hasAnyRole('ROLE_EQUIPMENT_VIEW','ROLE_ACCOUNT_VIEW', 'ROLE_COMMUNIGATE_VIEW')">
                <span class="text-success">view</span>
            </security:authorize>
            <security:authorize access="hasAnyRole('ROLE_EQUIPMENT_EDIT','ROLE_ACCOUNT_EDIT', 'ROLE_COMMUNIGATE_EDIT')">
                <span class="text-success">, edit</span>
            </security:authorize>
            <security:authorize access="hasRole('ROLE_ROOT')">
                <span class="text-success">, management</span>
            </security:authorize> rights.
            <br/>
            Use the menu above or the buttons below to start working.
        </p>

        <p>
            <security:authorize access="hasAnyRole('ROLE_EQUIPMENT_VIEW','ROLE_EQUIPMENT_EDIT')">
                <a href="<c:url value="/ipstore/equipment"/>" class="btn btn-primary btn-large">Equipment list »</a>
            </security:authorize>
            <security:authorize access="hasAnyRole('ROLE_ACCOUNT_VIEW','ROLE_ACCOUNT_EDIT')">
                <a href="<c:url value="/ipstore/accounts"/>" class="btn btn-primary btn-large">Accounts list »</a>
            </security:authorize>
            <security:authorize access="hasAnyRole('ROLE_COMMUNIGATE_VIEW','ROLE_COMMUNIGATE_EDIT')">
                <a href="<c:url value="/ipstore/communigate"/>" class="btn btn-primary btn-large">Communigate domains list »</a>
            </security:authorize>
        </p>

        <p>
            Good luck.
        </p>
    </div>
</div>