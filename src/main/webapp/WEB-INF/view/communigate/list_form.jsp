<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form class="navbar-form navbar-right" action="<c:url value="/communigate"/>">
    <div class="form-group">
        <input type="text" name="search" value="${search}" placeholder="search" class="form-control">
    </div>
    <button type="submit" class="btn btn-primary">
        <span class="glyphicon glyphicon-search"></span>
    </button>
</form>