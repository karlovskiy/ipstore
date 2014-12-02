<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>

<c:url var="formActionUrl" value="${action}"/>
<form:form commandName="capacityType" action="${formActionUrl}" cssClass="form-horizontal" role="form">

    <spring:bind path="name">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-1" for="name">Name</label>
            <div class="col-md-5">
                <form:input id="name" path="name" cssClass="form-control"/>
                <form:errors path="name" cssClass="help-block"/>
                <c:if test="${not empty existsId}">
                        <span class="help-block">
                            Capacity with name
                            <a href="<c:url value="/capacity/${existsId}"/>" target="_blank">
                                <c:out value="${capacityType.name}"/>
                            </a>
                            already exists!
                        </span>
                </c:if>
            </div>
        </div>
    </spring:bind>

    <div class="form-group">
        <div class="col-md-offset-1 col-md-11">
            <c:if test="${not empty capacityType.id}">
                <a href="<c:url value="/capacity/${capacityType.id}"/>" class="btn btn-primary">Capacity</a>
            </c:if>
            <button type="submit" class="btn btn-primary">Save</button>
        </div>
    </div>

</form:form>