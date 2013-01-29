<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>VoIPStore</title>
    <link href="<c:url value="/assets/css/bootstrap.css" />" rel="stylesheet">
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
    <style type="text/css">
        body {
            padding-top: 60px;
            padding-bottom: 40px;
            background-color: #f5f5f5;
        }

        .table tbody tr.info td {
            background-color: #ffffff;
        }
    </style>
</head>
<body>
<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
            <a class="brand" href="<c:url value="/ipstore/equipment" />">VoIPStore</a>
            <div class="nav-collapse collapse">
                <ul class="nav">
                    <li><a href="<c:url value="/ipstore/equipment" />">Home</a></li>
                    <security:authorize access="hasRole('ROLE_ROOT')">
                        <li><a href="<c:url value="/monitoring"/>">Monitoring</a></li>
                    </security:authorize>
                    <security:authorize access="hasRole('ROLE_ROOT')">
                        <li><a href="<c:url value="/ipstore/actions"/>">Actions</a></li>
                    </security:authorize>
                    <security:authorize access="hasRole('ROLE_ROOT')">
                        <li><a href="<c:url value="/ipstore/export"/>">Export</a></li>
                    </security:authorize>
                    <security:authorize access="hasRole('ROLE_ADMIN')">
                        <li><a href="<c:url value="/ipstore/import"/>">Import</a></li>
                    </security:authorize>
                    <li class="active"><a href="#">View</a></li>
                    <security:authorize access="hasRole('ROLE_ADMIN')">
                        <li><a href="<c:url value="/ipstore/edit/${equipment.id}" />">Edit</a></li>
                    </security:authorize>
                    <security:authorize access="hasRole('ROLE_ADMIN')">
                        <li><a id="equipment_delete" href="<c:url value="/ipstore/delete/${equipment.id}" />">Delete</a></li>
                    </security:authorize>
                    <li><a href="<c:url value="/j_spring_security_logout" />">Logout</a></li>
                </ul>
            </div>
        </div>
    </div>
</div>
<div class="container">
    <table class="table table-hover" style="width: 0;">
        <tr class="info">
            <td>IpAddress</td>
            <td><c:out value="${equipment.ipAddress}"/></td>
        </tr>
        <tr class="info">
            <td>Type</td>
            <td><c:out value="${equipment.type}"/></td>
        </tr>
        <tr class="info">
            <td>Username</td>
            <td><c:out value="${equipment.username}"/></td>
        </tr>
        <tr class="info">
            <td>Login</td>
            <td><c:out value="${equipment.login}"/></td>
        </tr>
        <tr class="info">
            <td>Password</td>
            <td><c:out value="${equipment.password}"/></td>
        </tr>
        <tr class="info">
            <td>Password date</td>
            <td><fmt:formatDate value="${equipment.passwordDate}" type="both" pattern="dd.MM.yyyy HH:mm:ss"/></td>
        </tr>
        <tr class="info">
            <td>Password status</td>
            <td><c:out value="${equipment.passwordStatus}"/></td>
        </tr>
        <tr class="info">
            <td>ClientName</td>
            <td><c:out value="${equipment.clientName}"/></td>
        </tr>
        <tr class="info">
            <td>PlacementAddress</td>
            <td><c:out value="${equipment.placementAddress}"/></td>
        </tr>
        <tr class="info">
            <td>ApplicationNumber</td>
            <td><c:out value="${equipment.applicationNumber}"/></td>
        </tr>
        <tr class="info">
            <td>Description</td>
            <td><c:out value="${equipment.description}"/></td>
        </tr>
    </table>
</div>
</body>
</html>