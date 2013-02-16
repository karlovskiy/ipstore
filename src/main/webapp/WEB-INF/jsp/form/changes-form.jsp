<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form:form commandName="filterForm" class="navbar-form pull-right" action="/ipstore/changes">
    <form:input id="from" path="from" cssClass="input-small" placeholder="From"/>
    <form:input id="to" path="to" cssClass="input-small" placeholder="To"/>
    <form:input id="ip" path="ip" cssClass="input-small" placeholder="Ip"/>
    <button type="submit" class="btn btn-primary">Search</button>
</form:form>