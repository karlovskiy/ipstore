<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="container">
    <div id="view-fields">
        <div class="row">
            <div class="span1">Date</div>
            <div class="span5">
                <span class="block">
                    <fmt:formatDate value="${action.actionTimestamp}" type="both"
                                    pattern="dd.MM.yyyy HH:mm:ss"/>
                </span>
            </div>
        </div>
        <div class="row">
            <div class="span1">Type</div>
            <div class="span5">
                <span class="block">
                    <c:out value="${action.type}"/>
                </span>
            </div>
        </div>
        <div class="row">
            <div class="span1">Ip</div>
            <div class="span5">
                <span class="block">
                    <c:out value="${action.ip}"/>
                </span>
            </div>
        </div>
        <div class="row">
            <div class="span1">Host</div>
            <div class="span5">
                <span class="block">
                    <c:out value="${action.host}"/>
                </span>
            </div>
        </div>
        <div class="row">
            <div class="span1">Username</div>
            <div class="span5">
                <span class="block">
                    <c:out value="${action.username}"/>
                </span>
            </div>
        </div>
        <div class="row">
            <div class="span1">User agent</div>
            <div class="span5">
                <span class="block">
                    <c:out value="${action.userAgent}"/>
                </span>
            </div>
        </div>
        <div class="row">
            <div class="span1 nw">Request URL</div>
            <div class="span5">
                <span class="block">
                    <c:out value="${action.requestURL}"/>
                </span>
            </div>
        </div>
    </div>
    <c:if test="${not empty action.changes}">
        <table class="table table-hover table-condensed">
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
                <tr class="info">
                    <td>
                        <a href="${change.type.viewPageURL}/${change.entityId}" target="_blank">
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
    <div id="view-buttons">
        <div class="row">
            <div class="span1">
                <a href="<c:url value="/ipstore/actions"/>" class="btn btn-primary btn-block">List</a>
            </div>
        </div>
    </div>
</div>