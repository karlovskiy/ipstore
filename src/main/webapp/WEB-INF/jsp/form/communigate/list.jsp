<%--
  User: fizikatela
  Date: 25.03.13
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form class="navbar-form pull-right" action="<c:url value="/ipstore/communigate"/>">
    <input type="text" name="search" value="${search}" placeholder="search" class="input-medium search-query">
</form>