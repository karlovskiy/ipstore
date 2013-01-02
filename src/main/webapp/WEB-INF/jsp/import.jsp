<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ipstore equipment</title>
</head>
<body>
<c:if test="${not empty result}">
    <div>${result}</div>
</c:if>

<h1>Please upload a file to import</h1>
<form method="post" action="/ipstore/import" enctype="multipart/form-data">
    <input type="file" name="file"/>
    <input type="submit" value="Import"/>
</form>
<span style="display: block; padding-top: 10px;">
    <a href="<c:url value="/ipstore/equipment" />">Back</a>
</span>
</body>
</html>