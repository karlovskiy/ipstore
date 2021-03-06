<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>Error</title>
  <link rel="stylesheet" href="<c:url value="/resources/unversioned/bootstrap.min.css"/>"/>
  <link rel="stylesheet" href="<c:url value="/resources/unversioned/error.css"/>"/>
</head>
<body>
<div class="site-wrapper">
  <div class="site-wrapper-inner">
    <div class="cover-container">
      <div class="inner cover">
        <h1 class="cover-heading">Houston we have a problem!</h1>

        <p class="lead">
          Sorry, but there was an error processing the last request.
          You are on error page, to go to the main page, click <a href="<c:url value="/"/>">here</a>
        </p>
      </div>
    </div>
  </div>

</div>
</body>
</html>