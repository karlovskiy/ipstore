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

<h1>Equipment</h1>

<table border="1">
    <thead>
    <tr>
        <th>Ip address</th>
        <th>Type</th>
        <th>Password</th>
        <th>Placement Address</th>
        <th>Description</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${equipment}" var="equipment">
        <tr>
            <td><c:out value="${equipment.ipAddress}"/></td>
            <td><c:out value="${equipment.type}"/></td>
            <td><c:out value="${equipment.password}"/></td>
            <td><c:out value="${equipment.placementAddress}"/></td>
            <td><c:out value="${equipment.description}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<a href="<c:url value="/j_spring_security_logout" />" > Logout</a>
</body>
</html>