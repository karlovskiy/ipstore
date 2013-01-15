<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>VoIPStore - View Page</title>
    <link href="<c:url value="/assets/css/bootstrap.css" />" rel="stylesheet">
    <style type="text/css">
        body {
            padding-top: 40px;
            padding-bottom: 40px;
            background-color: #f5f5f5;
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
    <script type="text/javascript" src="<c:url value="/js/jquery-1.8.3.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/assets/js/bootstrap.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/bootbox.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/view.js"/>"></script>
</head>
<body>
<div class="container">
    <span style="display: block; padding-top: 10px;">
        <a class="btn btn-primary" href="<c:url value="/ipstore/equipment" />">Back</a>
        <security:authorize access="hasRole('ROLE_ADMIN')">
            <a class="btn btn-primary" href="<c:url value="/ipstore/edit/${equipment.id}" />">Edit</a>
        </security:authorize>
        <security:authorize access="hasRole('ROLE_ADMIN')">
            <a id="equipment_delete" class="btn btn-primary" href="<c:url value="/ipstore/delete/${equipment.id}" />">Delete</a>
        </security:authorize>
        <a class="btn btn-danger" href="<c:url value="/j_spring_security_logout" />">LogOff</a>
    </span>
    <table class="table table-striped" style="width: 0;">
        <tr>
            <td>IpAddress</td>
            <td><c:out value="${equipment.ipAddress}"/></td>
        </tr>
        <tr>
            <td>Type</td>
            <td><c:out value="${equipment.type}"/></td>
        </tr>
        <tr>
            <td>Username</td>
            <td><c:out value="${equipment.username}"/></td>
        </tr>
        <tr>
            <td>Login</td>
            <td><c:out value="${equipment.login}"/></td>
        </tr>
        <tr>
            <td>Password</td>
            <td><c:out value="${equipment.password}"/></td>
        </tr>
        <tr>
            <td>ClientName</td>
            <td><c:out value="${equipment.clientName}"/></td>
        </tr>
        <tr>
            <td>PlacementAddress</td>
            <td><c:out value="${equipment.placementAddress}"/></td>
        </tr>
        <tr>
            <td>ApplicationNumber</td>
            <td><c:out value="${equipment.applicationNumber}"/></td>
        </tr>
        <tr>
            <td>Description</td>
            <td><c:out value="${equipment.description}"/></td>
        </tr>
    </table>
</div>
</body>
</html>