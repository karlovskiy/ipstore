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
    <script type="text/javascript" src="<c:url value="/js/zeroclipboard.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/view.js"/>"></script>
    <style type="text/css">
        body {
            padding-top: 60px;
            padding-bottom: 40px;
            background-color: #f5f5f5;
        }

        .table td.OLD {
            color: #ffff00;
        }

        .table td.NEED_UPDATE {
            color: #ff0000;
        }

        .table td.NEW {
            color: #00ff00;
        }

        .zeroclipboard-is-hover {
            color: #ffffff;
            background-color: #0044cc;
            *background-color: #003bb3;
        }

        .zeroclipboard-is-active {
            color: #ffffff;
            background-color: #0044cc;
            *background-color: #003bb3;
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
                    <security:authorize access="hasRole('ROLE_ADMIN')">
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                Administration
                                <b class="caret"></b>
                            </a>
                            <ul class="dropdown-menu">
                                <li><a href="<c:url value="/ipstore/add"/>">Add</a></li>
                                <security:authorize access="hasRole('ROLE_ROOT')">
                                    <li><a href="<c:url value="/ipstore/import"/>">Import</a></li>
                                    <li><a href="<c:url value="/ipstore/export"/>">Export</a></li>
                                </security:authorize>
                            </ul>
                        </li>
                    </security:authorize>
                    <security:authorize access="hasRole('ROLE_ROOT')">
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                Management
                                <b class="caret"></b>
                            </a>
                            <ul class="dropdown-menu">
                                <li><a href="<c:url value="/ipstore/actions"/>">Actions</a></li>
                                <li><a href="<c:url value="/monitoring"/>">Monitoring</a></li>
                            </ul>
                        </li>
                    </security:authorize>
                    <li><a href="<c:url value="/j_spring_security_logout" />">Logout</a></li>
                </ul>
            </div>
        </div>
    </div>
</div>
<div class="container">
    <table class="table" style="width: 0;">
        <tbody>
        <tr>
            <td>IpAddress</td>
            <td><c:choose>
                <c:when test="${equipment.type == 'GS'}">
                    <a href="http://${equipment.ipAddress}" target="_blank"><c:out value="${equipment.ipAddress}"/></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${equipment.ipAddress}"/>
                </c:otherwise>
                </c:choose>
            </td>
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
            <td style="vertical-align: middle;">Password</td>
            <td><span><button id="copy_to_clipboard" class="btn btn-primary"
                        data-clipboard-text="${equipment.password}">Copy</button></span>
            </td>
        </tr>
        <tr>
            <td>Password date</td>
            <td style="white-space: nowrap;">
                <fmt:formatDate value="${equipment.passwordDate}" type="both" pattern="dd.MM.yyyy HH:mm:ss"/></td>
        </tr>
        <tr>
            <td>Password status</td>
            <td class="${equipment.passwordStatus}"><c:out value="${equipment.passwordStatus}"/></td>
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
        </tbody>
        <security:authorize access="hasRole('ROLE_ADMIN')">
            <tfoot>
            <tr>
                <td>
                    <a class="btn btn-primary btn-block" href="<c:url value="/ipstore/edit/${equipment.id}" />">Edit</a>
                </td>
                <td>
                    <a class="btn btn-danger" id="equipment_delete"
                       href="<c:url value="/ipstore/delete/${equipment.id}"/>">Delete</a>
                </td>
            </tr>
            </tfoot>
        </security:authorize>
    </table>
</div>
</body>
</html>