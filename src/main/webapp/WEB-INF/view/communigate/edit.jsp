<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form:form commandName="communigateDomain" action="${formAction}" cssClass="form-horizontal">
    <spring:bind path="domainName">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="domainName">DomainName</label>
            <div class="col-md-5">
                <form:input id="domainName" path="domainName" cssClass="form-control"/>
                <form:errors path="domainName" cssClass="help-block"/>
                <c:if test="${not empty existsCommunigateDomain}">
                      <span class="help-block">
                            Communigate domain with
                            <a href="<c:url value="/communigate/view/${existsCommunigateDomain.id}"/>" target="_blank">
                                domainName <c:out value="${existsCommunigateDomain.domainName}"/> and tryPrefix
                                <c:out value="${existsCommunigateDomain.tryPrefix}"/>
                            </a>
                            already exists!
                        </span>
                </c:if>
            </div>
        </div>
    </spring:bind>

    <spring:bind path="tryPrefix">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="tryPrefix">TryPrefix</label>
            <div class="col-md-5">
                <form:input id="tryPrefix" path="tryPrefix" cssClass="form-control"/>
                <form:errors path="tryPrefix" cssClass="help-block"/>
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

    <spring:bind path="ticket">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="ticket">Ticket</label>
            <div class="col-md-5">
                <form:input id="ticket" path="ticket" cssClass="form-control"/>
                <form:errors path="ticket" cssClass="help-block"/>
            </div>
        </div>
    </spring:bind>

    <spring:bind path="numberLine">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="numberLine">NumberLine</label>
            <div class="col-md-5">
                <form:input id="numberLine" path="numberLine" cssClass="form-control"/>
                <form:errors path="numberLine" cssClass="help-block"/>
            </div>
        </div>
    </spring:bind>

    <spring:bind path="diskCapacity">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="diskCapacity">DiskCapacity</label>
            <div class="col-md-5">
                <form:input id="diskCapacity" path="diskCapacity" cssClass="form-control"/>
                <form:errors path="diskCapacity" cssClass="help-block"/>
            </div>
        </div>
    </spring:bind>

    <spring:bind path="service">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="service">Service</label>
            <div class="col-md-5">
                <form:input id="service" path="service" cssClass="form-control"/>
                <form:errors path="service" cssClass="help-block"/>
            </div>
        </div>
    </spring:bind>

    <spring:bind path="contract">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="contract">Contract</label>
            <div class="col-md-5">
                <form:input id="contract" path="contract" cssClass="form-control"/>
                <form:errors path="contract" cssClass="help-block"/>
            </div>
        </div>
    </spring:bind>

    <spring:bind path="login">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="login">Login</label>
            <div class="col-md-5">
                <form:input id="login" path="login" cssClass="form-control"/>
                <form:errors path="login" cssClass="help-block"/>
            </div>
        </div>
    </spring:bind>

    <spring:bind path="date">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="date">Date</label>
            <div class="col-md-5">
                <form:input id="date" path="date" cssClass="form-control"/>
                <form:errors path="date" cssClass="help-block"/>
            </div>
        </div>
    </spring:bind>

    <spring:bind path="description">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-md-2" for="description">Description</label>
            <div class="col-md-5">
                <form:input id="description" path="description" cssClass="form-control"/>
                <form:errors path="description" cssClass="help-block"/>
            </div>
        </div>
    </spring:bind>

    <div class="form-group">
        <div class="col-md-offset-2 col-md-10">
            <c:if test="${not empty communigateDomain.id}">
                <a href="<c:url value="/communigate/view/${communigateDomain.id}"/>" class="btn btn-primary">View</a>
            </c:if>
            <button type="submit" class="btn btn-primary">Save</button>
        </div>
    </div>

</form:form>