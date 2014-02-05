<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form class="navbar-form pull-right" action="<c:url value="/equipment"/>">
    <input type="text" name="search" value="${search}" placeholder="search" class="input-medium search-query">
    <button type="submit" class="btn btn-primary">Search</button>
</form>