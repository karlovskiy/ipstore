<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="container">
    <div id="home" class="hero-unit">
        <div class="row">
            <h2>
                Welcome to VoIPStore, <security:authentication property="principal.username"/>!
            </h2>
            <security:authorize access="hasRole('ROLE_ROOT')">
                <p>
                    You have <span class="text-success"> application management</span> rights.
                </p>
            </security:authorize>
        </div>
        <div class="row" style="margin-left: -60px;">
            <security:authorize access="hasRole('ROLE_EQUIPMENT_VIEW')">
            <span class="span3">
                <h4>
                    Equipment
                </h4>
                <p class="rights">
                    Rights: <span class="text-success"> view
                    <security:authorize access="hasRole('ROLE_EQUIPMENT_EDIT')">, edit</security:authorize></span>
                </p>
                <p>
                    <a href="<c:url value="/ipstore/equipment"/>" class="btn btn-large">Equipment list »</a>
                </p>
            </span>
            </security:authorize>
            <security:authorize access="hasRole('ROLE_ACCOUNT_VIEW')">
            <span class="span3">
                <h4>
                    Accounts
                </h4>
                <p class="rights">
                    Rights: <span class="text-success"> view
                    <security:authorize access="hasRole('ROLE_ACCOUNT_EDIT')">, edit</security:authorize></span>
                </p>
                <p>
                    <a href="<c:url value="/ipstore/accounts"/>" class="btn btn-large">Accounts list »</a>
                </p>
            </span>
            </security:authorize>
            <security:authorize access="hasRole('ROLE_COMMUNIGATE_VIEW')">
            <span class="span3">
                <h4>
                    Communigate domains
                </h4>
                <p class="rights">
                    Rights: <span class="text-success"> view
                    <security:authorize access="hasRole('ROLE_COMMUNIGATE_EDIT')">, edit</security:authorize></span>
                </p>
                <p>
                    <a href="<c:url value="/ipstore/communigate"/>" class="btn btn-large">Communigate
                        domains list »</a>
                </p>
            </span>
            </security:authorize>
            <security:authorize access="hasRole('ROLE_CAPACITY_VIEW')">
            <span class="span3">
                <h4>
                    Phone capacity list
                </h4>
                <p class="rights">
                    Rights: <span class="text-success"> view
                    <security:authorize
                            access="hasRole('ROLE_CAPACITY_MANAGER')">, capacity management</security:authorize>
                    <security:authorize access="hasRole('ROLE_CAPACITY_EDIT')">, edit</security:authorize></span>
                </p>
                <p>
                    <a href="<c:url value="/ipstore/capacity"/>" class="btn btn-large">Phone capacity
                        list »</a>
                </p>
            </span>
            </security:authorize>
        </div>
    </div>
</div>