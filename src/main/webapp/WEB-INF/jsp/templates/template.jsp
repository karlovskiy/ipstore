<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><tiles:getAsString name="title"/></title>
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
    <script type="text/javascript" src="<c:url value="/js/bootstrap-datepicker.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/bootbox.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/menu.js"/>"></script>
    <link href="<c:url value="/css/datepicker.css"/>" rel="stylesheet">
    <link href="<c:url value="/css/style.css"/>" rel="stylesheet">
</head>
<body>
<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
            <a class="brand" href="<c:url value="/ipstore/" />">VoIPStore</a>

            <div class="nav-collapse collapse">
                <ul class="nav">
                    <security:authorize access="hasRole('ROLE_ADMIN')">
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                Administration
                                <b class="caret"></b>
                            </a>
                            <ul class="dropdown-menu">
                                <li class="nav-header">Equipment</li>
                                <li><a href="<c:url value="/ipstore/add"/>">Add equipment</a></li>
                                <security:authorize access="hasRole('ROLE_ROOT')">
                                    <li><a href="<c:url value="/ipstore/import"/>">Import equipment</a></li>
                                    <li><a href="<c:url value="/ipstore/export"/>">Export equipment</a></li>
                                </security:authorize>
                                <li class="nav-header">Accounts</li>
                                <li><a href="<c:url value="/ipstore/accounts/add"/>">Add account</a></li>
                                <security:authorize access="hasRole('ROLE_ROOT')">
                                    <li><a href="<c:url value="/ipstore/accounts/import"/>">Import accounts</a></li>
                                    <li><a href="<c:url value="/ipstore/accounts/export"/>">Export accounts</a></li>
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
                                <li><a id="rebuild_index" href="<c:url value="/ipstore/rebuild"/>">Rebuild index</a></li>
                                <li><a href="<c:url value="/ipstore/users"/>">Users</a></li>
                                <li><a href="<c:url value="/ipstore/users/add"/>">Add user</a></li>
                                <li class="nav-header">Equipment</li>
                                <li><a href="<c:url value="/ipstore/changes"/>">Equipment changes</a></li>
                                <li class="nav-header">Accounts</li>
                                <li><a href="<c:url value="/ipstore/accounts/changes"/>">Accounts changes</a></li>
                            </ul>
                        </li>
                    </security:authorize>
                    <li><a href="<c:url value="/j_spring_security_logout" />">Logout</a></li>
                </ul>
                <tiles:insertAttribute ignore="true" name="menu-form"/>
            </div>
        </div>
    </div>
</div>
<tiles:insertAttribute name="content"/>
</body>
</html>