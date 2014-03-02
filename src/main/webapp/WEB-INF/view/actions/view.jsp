<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="form-horizontal">

    <div class="form-group">
        <label class="control-label col-md-2">Date</label>

        <div class="col-md-5">
            <p class="form-control-static">
                <fmt:formatDate value="${action.actionTimestamp}" type="both" pattern="dd.MM.yyyy HH:mm:ss"/>
            </p>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-md-2">Type</label>

        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${action.type}"/>
            </p>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-md-2">Ip</label>

        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${action.ip}"/>
            </p>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-md-2">Host</label>

        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${action.host}"/>
            </p>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-md-2">Username</label>

        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${action.username}"/>
            </p>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-md-2">User agent</label>

        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${action.userAgent}"/>
            </p>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-md-2">Request URL</label>

        <div class="col-md-5">
            <p class="form-control-static">
                <c:out value="${action.requestURL}"/>
            </p>
        </div>
    </div>
</div>
<c:if test="${not empty action.changes}">
    <table class="tablesorter">
        <thead>
        <tr>
            <th>Type</th>
            <th>Field type</th>
            <th>Old value</th>
            <th>New value</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${action.changes}" var="change">
            <tr>
                <td>
                    <a href="<c:url value="${change.type.viewPageURL}/${change.entityId}"/>" target="_blank">
                        <c:out value="${change.type}"/>
                    </a>
                </td>
                <td><c:out value="${change.fieldType}"/></td>
                <td><c:out value="${change.oldValue}"/></td>
                <td><c:out value="${change.newValue}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
<div class="row">
    <div class="col-md-offset-2 col-md-5">
        <a href="<c:url value="/actions"/>" class="btn btn-primary">List</a>
    </div>
</div>
