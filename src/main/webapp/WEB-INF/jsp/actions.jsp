<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>VoIPStore - Actions Page</title>
    <link rel="shortcut icon" href="<c:url value="/assets/ico/favicon.png"/>">
    <link href="<c:url value="/assets/css/bootstrap.css" />" rel="stylesheet">
    <link href="<c:url value="/assets/css/bootstrap-responsive.css"/>" rel="stylesheet">
    <script type="text/javascript" src="<c:url value="/js/jquery-1.8.3.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/assets/js/bootstrap.min.js"/>"></script>
    <style type="text/css">
        .table tbody tr.info td {
            background-color: #ffffff;
        }
    </style>
</head>
<body>
<div class="container">
    <span style="display: block; padding-top: 10px;">
        <a class="btn btn-primary" href="<c:url value="/ipstore/equipment" />">Back</a>
        <a class="btn btn-danger" href="<c:url value="/j_spring_security_logout" />">LogOff</a>
    </span>
    <table class="table table-hover table-condensed">
        <thead>
        <tr>
            <th>Date</th>
            <th>Ip address</th>
            <th>Host</th>
            <th>Type</th>
            <th>User Agent</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${actions}" var="action">
            <tr class="info">
                <td><fmt:formatDate value="${action.actionTimestamp}" type="both" pattern="dd.MM.yyyy HH:mm:ss"/></td>
                <td><c:out value="${action.ip}"/></td>
                <td><c:out value="${action.host}"/></td>
                <td><c:out value="${action.type}"/></td>
                <td><c:out value="${action.userAgent}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>