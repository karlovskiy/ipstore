<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ipstore" tagdir="/WEB-INF/tags/ipstore" %>

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
                        <a href="<c:url value="/capacity/${capacityType.id}/numbers/${existsId}"/>" target="_blank">
                            <c:out value="${capacityNumber.number}"/>
                        </a>
                        already exists!
                    </span>
                </c:if>
            </div>
        </div>
    </spring:bind>

    <ipstore:input path="clientName" label="ClientName"/>
    <ipstore:input path="address" label="Address"/>
    <ipstore:input path="application" label="Application"/>
    <ipstore:input id="date" path="admittanceDate" label="Admittance date"/>
    <ipstore:input path="linesCount" label="Lines count"/>
    <ipstore:input path="comments" label="Comments"/>

    <div class="form-group">
        <div class="col-md-offset-2 col-md-10">
            <c:if test="${empty capacityNumber.id}">
                <a href="<c:url value="/capacity/${capacityType.id}"/>" class="btn btn-primary">Capacity</a>
            </c:if>
            <c:if test="${not empty capacityNumber.id}">
                <a href="<c:url value="/capacity/${capacityNumber.type.id}/numbers/${capacityNumber.id}"/>"
                   class="btn btn-primary">View</a>
            </c:if>
            <button type="submit" class="btn btn-primary">Save</button>
        </div>
    </div>

</form:form>