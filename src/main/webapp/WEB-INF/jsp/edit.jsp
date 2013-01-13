<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>VoIPStore - Edit Page</title>
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
        <a class="btn btn-primary" href="${backButtonUrl}">Back</a>
        <a class="btn btn-danger" href="<c:url value="/j_spring_security_logout" />">LogOff</a>
    </span>
    <form:form commandName="equipment" action="${formAction}" cssStyle="margin-top: 10px;">
        <div>
            <label>IpAddress</label>

            <div>
                <form:input path="ipAddress"/>
                <span class="errorblock">
                    <form:errors path="ipAddress"/>
                </span>
            </div>
        </div>
        <div>
            <label>Type</label>

            <div>
                <form:input path="type"/>
                <span class="errorblock">
                    <form:errors path="type"/>
                </span>
            </div>
        </div>
        <div>
            <label>Username</label>

            <div>
                <form:input path="username"/>
                <span class="errorblock">
                    <form:errors path="username"/>
                </span>
            </div>
        </div>
        <div>
            <label>Login</label>

            <div>
                <form:input path="login"/>
                <span class="errorblock">
                    <form:errors path="login"/>
                </span>
            </div>
        </div>
        <div>
            <label>Password</label>

            <div>
                <form:input path="password"/>
                <span class="errorblock">
                    <form:errors path="password"/>
                </span>
            </div>
        </div>
        <div>
            <label>ClientName</label>

            <div>
                <form:input path="clientName"/>
                <span class="errorblock">
                    <form:errors path="clientName"/>
                </span>
            </div>
        </div>
        <div>
            <label>PlacementAddress</label>

            <div>
                <form:input path="placementAddress"/>
                <span class="errorblock">
                    <form:errors path="placementAddress"/>
                </span>
            </div>
        </div>
        <div>
            <label>ApplicationNumber</label>

            <div>
                <form:input path="applicationNumber"/>
                <span class="errorblock">
                    <form:errors path="applicationNumber"/>
                </span>
            </div>
        </div>
        <div>
            <label>Description</label>

            <div>
                <form:input path="description"/>
                <span class="errorblock">
                    <form:errors path="description"/>
                </span>
            </div>
        </div>
        <button type="submit" class="btn btn-primary">Save</button>
    </form:form>
</div>
</body>
</html>