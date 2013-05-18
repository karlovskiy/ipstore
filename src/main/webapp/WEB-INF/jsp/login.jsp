<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Sign in</title>
    <link href="<c:url value="/assets/css/bootstrap.css" />" rel="stylesheet">
    <style type="text/css">
        body {
            padding-top: 20px;
            padding-bottom: 40px;
            background-color: #f5f5f5;
        }

        .errorblock {
            color: #ff0000;
        }

        .form-signin {
            max-width: 300px;
            padding: 19px 29px 29px;
            margin: 0 auto 20px;
            background-color: #fff;
            border: 1px solid #e5e5e5;
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            border-radius: 5px;
            -webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
            -moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
            box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
        }

        .form-signin input[type="text"],
        .form-signin input[type="password"] {
            font-size: 16px;
            height: auto;
            margin-bottom: 15px;
            padding: 7px 9px;
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
</head>
<body>
<div class="container">
    <c:if test="${empty sessionScope['CREDENTIALS_EXPIRED_USERNAME']}">
        <form class="form-signin" action="<c:url value='j_spring_security_check' />" method='POST'>
            <c:if test="${not empty sessionScope['SPRING_SECURITY_LAST_EXCEPTION']}">
                <div class="alert alert-error">
                    <span class="errorblock">${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</span>
                </div>
            </c:if>
            <h2 class="form-signin-heading">Sign in</h2>
            <input type="text" class="input-block-level" name='j_username' placeholder="User Name">
            <input type="password" class="input-block-level" name='j_password' placeholder="Password">
            <button name="submit" type="submit" class="btn btn-large btn-block btn-primary"> Sign in</button>
        </form>
    </c:if>
    <c:if test="${not empty sessionScope['CREDENTIALS_EXPIRED_USERNAME']}">
        <form:form commandName="changePassword" action="/ipstore/mustchangepassword" cssClass="form-horizontal">
            <div class="control-group">
                <label class="control-label" for="username">Username</label>

                <div class="controls">
                    <input id="username" type="text" value="${sessionScope['CREDENTIALS_EXPIRED_USERNAME']}"
                           disabled="disabled">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="oldPassword">Old password</label>

                <div class="controls">
                    <form:password id="oldPassword" path="oldPassword"/>
                <span class="errorblock">
                    <form:errors path="oldPassword"/>
                </span>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="newPassword">New password</label>

                <div class="controls">
                    <form:password id="newPassword" path="newPassword"/>
                <span class="errorblock">
                    <form:errors path="newPassword"/>
                </span>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="repeatNewPassword">Repeat new password</label>

                <div class="controls">
                    <form:password id="repeatNewPassword" path="repeatNewPassword"/>
                <span class="errorblock">
                    <form:errors path="repeatNewPassword"/>
                </span>
                </div>
            </div>
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Change password</button>
            </div>
        </form:form>
    </c:if>
</div>
</body>
</html>