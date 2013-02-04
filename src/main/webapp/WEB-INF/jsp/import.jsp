<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
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
    <style type="text/css">
        body {
            padding-top: 60px;
            padding-bottom: 40px;
            background-color: #f5f5f5;
        }

        .errorblock {
            color: #ff0000;
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
    <c:if test="${not empty success}">
        <div class=" ${success ? 'alert':'alert alert-error'}" style="margin-top: 5px; margin-bottom: 0px;">
            <span class="${success ? '':'errorblock'}">${resultMessage}</span>
        </div>
    </c:if>
    <form method="post" action="/ipstore/import" enctype="multipart/form-data">
        <input type="file" name="file"/>
        <input class="btn btn-primary" type="submit" value="Import"/>
    </form>
</div>
</body>
</html>