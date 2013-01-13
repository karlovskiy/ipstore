<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>VoIPStore - Import Page</title>
    <link href="<c:url value="/assets/css/bootstrap.css" />" rel="stylesheet">
    <style type="text/css">
        body {
            padding-top: 40px;
            padding-bottom: 40px;
            background-color: #f5f5f5;
        }

        .errorblock {
            color: #ff0000;
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
    <span style="display: block; padding-top: 10px;">
        <a class="btn btn-primary" href="<c:url value="/ipstore/equipment" />">Back</a>
        <a class="btn btn-danger" href="<c:url value="/j_spring_security_logout" />">LogOff</a>
    </span>
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