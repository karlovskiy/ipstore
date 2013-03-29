<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form:form commandName="filterForm" class="navbar-form pull-right" action="/ipstore/actions">
    <form:input id="from" path="from" cssClass="input-small" placeholder="From"/>
    <form:input id="to" path="to" cssClass="input-small" placeholder="To"/>
    <form:input id="username" path="username" cssClass="input-small" placeholder="Username"/>
    <button type="submit" class="btn btn-primary">Search</button>
</form:form>