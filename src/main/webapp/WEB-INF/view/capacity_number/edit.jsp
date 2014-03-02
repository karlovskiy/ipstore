<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<form:form commandName="capacityNumber" action="${action}" cssClass="form-horizontal">

    <spring:bind path="number">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="number">Number</label>
            <div class="col-md-5">
                <form:input id="number" path="number" cssClass="form-control"/>
                <form:errors path="number" cssClass="help-block"/>
                <c:if test="${not empty existsId}">
                    <span class="help-block">
                        Capacity number
                        <a href="<c:url value="/capacity/number/view/${existsId}"/>" target="_blank">
                            <c:out value="${capacityNumber.number}"/>
                        </a>
                        already exists!
                    </span>
                </c:if>
            </div>
        </div>
    </spring:bind>

    <spring:bind path="clientName">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="clientName">ClientName</label>
            <div class="col-md-5">
                <form:input id="clientName" path="clientName" cssClass="form-control"/>
                <form:errors path="clientName" cssClass="help-block"/>
            </div>
        </div>
    </spring:bind>

    <spring:bind path="address">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="address">Address</label>
            <div class="col-md-5">
                <form:input id="address" path="address" cssClass="form-control"/>
                <form:errors path="address" cssClass="help-block"/>
            </div>
        </div>
    </spring:bind>

    <spring:bind path="application">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="application">Application</label>
            <div class="col-md-5">
                <form:input id="application" path="application" cssClass="form-control"/>
                <form:errors path="application" cssClass="help-block"/>
            </div>
        </div>
    </spring:bind>

    <spring:bind path="admittanceDate">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="date">Admittance date</label>
            <div class="col-md-5">
                <form:input id="date" path="admittanceDate" cssClass="form-control"/>
                <form:errors path="admittanceDate" cssClass="help-block"/>
            </div>
        </div>
    </spring:bind>

    <spring:bind path="linesCount">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="linesCount">Lines count</label>
            <div class="col-md-5">
                <form:input id="linesCount" path="linesCount" cssClass="form-control"/>
                <form:errors path="linesCount" cssClass="help-block"/>
            </div>
        </div>
    </spring:bind>

    <spring:bind path="comments">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="comments">Comments</label>
            <div class="col-md-5">
                <form:input id="comments" path="comments" cssClass="form-control"/>
                <form:errors path="comments" cssClass="help-block"/>
            </div>
        </div>
    </spring:bind>

    <div class="form-group">
        <div class="col-md-offset-2 col-md-10">
            <c:if test="${empty capacityNumber.id}">
                <a href="<c:url value="/capacity/${capacityType.id}"/>" class="btn btn-primary">Capacity</a>
            </c:if>
            <c:if test="${not empty capacityNumber.id}">
                <a href="<c:url value="/capacity/number/view/${capacityNumber.id}"/>" class="btn btn-primary">View</a>
            </c:if>
            <button type="submit" class="btn btn-primary">Save</button>
        </div>
    </div>

</form:form>