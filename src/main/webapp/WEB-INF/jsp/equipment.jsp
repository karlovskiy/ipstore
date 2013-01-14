<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>VoIPStore - Equipment Page</title>
    <link href="<c:url value="/assets/css/bootstrap.css" />" rel="stylesheet">
    <style type="text/css">
        body {
            padding-top: 40px;
            padding-bottom: 40px;
            background-color: #f5f5f5;
        }

        .form-search {
            float: right;
        }

        .left-col {
            padding-left: 0;
        }
    </style>
    <link href="<c:url value="/assets/css/bootstrap-responsive.css"/>" rel="stylesheet">
    <link rel="apple-touch-icon-precomposed" sizes="144x144"
          href="<c:url value="/assets/ico/apple-touch-icon-144-precomposed.png"/>">
    <link rel="apple-touch-icon-precomposed" sizes="114x114"
          href="<c:url value="/assets/ico/apple-touch-icon-114-precomposed.png"/>..">
    <link rel="apple-touch-icon-precomposed" sizes="72x72"
          href="<c:url value="/assets/ico/apple-touch-icon-72-precomposed.png"/>">
    <link rel="apple-touch-icon-precomposed" href="<c:url value="/assets/ico/apple-touch-icon-57-precomposed.png"/>">
    <link rel="shortcut icon" href="<c:url value="/assets/ico/favicon.png"/>">
</head>
<body>

<div class="container">
    <form class="form-search" action="<c:url value="/ipstore/equipment"/>">
        <input type="text" name="search" value="${search}" class="input-medium search-query">
        <button type="submit" class="btn btn-primary" placeholder=".span1">Search</button>
    </form>
    <span style="display: block; padding-top: 10px; float: left;">
        <security:authorize access="hasRole('ROLE_ADMIN')">
            <a class="btn btn-primary" href="<c:url value="/ipstore/add"/>">Add</a>
        </security:authorize>
        <security:authorize access="hasRole('ROLE_ADMIN')">
            <a class="btn btn-primary" href="<c:url value="/ipstore/import"/>">Import</a>
        </security:authorize>
        <a class="btn btn-danger" href="<c:url value="/j_spring_security_logout" />">LogOff</a>
    </span>
    <table class="table table-striped">
        <thead>
        <tr>
            <th class="left-col">Ip address</th>
            <th>Type</th>
            <th>Login</th>
            <th>Client Name</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${equipment}" var="equipment">
            <tr>
                <td class="left-col">
                    <a href="/ipstore/equipment/${equipment.id}">
                        <c:out value="${equipment.ipAddress}"/>
                    </a>
                </td>
                <td><c:out value="${equipment.type}"/></td>
                <td><c:out value="${equipment.login}"/></td>
                <td><c:out value="${equipment.clientName}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>