<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>VoIPStore</title>
    <link href="<c:url value="/assets/css/bootstrap.css" />" rel="stylesheet">
    <style type="text/css">
        body {
            padding-top: 40px;
            padding-bottom: 40px;
            background-color: #f5f5f5;
        }

        .container-narrow {
            margin: 0 auto;
            max-width: 700px;
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

        .form-signin .form-signin-heading,
        .form-signin .checkbox {
            margin-bottom: 10px;
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
</head>
<body>
<div class="container-narrow">

    <div class="masthead">
        <ul class="nav nav-pills pull-right">
            <li><a href="#">About</a></li>
            <li><a href="#">Contact</a></li>
        </ul>
        <h3 class="muted">VoIPStore</h3>
    </div>
</div>
<br><br><br>

<div class="container">

    <form class="form-signin" action="<c:url value='j_spring_security_check' />" method='POST'>
        <c:if test="${not empty error}">
            <div class="alert alert-error">
                <span class="errorblock">${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</span>
            </div>
        </c:if>
        <h2 class="form-signin-heading">Please sign in</h2>
        <input type="text" class="input-block-level" name='j_username' placeholder="User Name">
        <input type="password" class="input-block-level" name='j_password' placeholder="Password">
        <button name="submit" type="submit" class="btn btn-large btn-block btn-primary"> Sign in</button>
    </form>

</div>
</body>
</html>