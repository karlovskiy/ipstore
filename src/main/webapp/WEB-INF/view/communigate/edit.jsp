<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ipstore" tagdir="/WEB-INF/tags/ipstore" %>

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
                            <a href="<c:url value="/communigate/${existsCommunigateDomain.id}"/>" target="_blank">
                                domainName <c:out value="${existsCommunigateDomain.domainName}"/> and tryPrefix
                                <c:out value="${existsCommunigateDomain.tryPrefix}"/>
                            </a>
                            already exists!
                        </span>
                </c:if>
            </div>
        </div>
    </spring:bind>

    <ipstore:input path="tryPrefix" label="TryPrefix"/>
    <ipstore:input path="clientName" label="ClientName"/>
    <ipstore:input path="ticket" label="Ticket"/>
    <ipstore:input path="numberLine" label="NumberLine"/>
    <ipstore:input path="diskCapacity" label="DiskCapacity"/>
    <ipstore:input path="service" label="Service"/>
    <ipstore:input path="contract" label="Contract"/>
    <ipstore:input path="login" label="Login"/>
    <ipstore:input path="date" label="Date"/>
    <ipstore:input path="description" label="Description"/>

    <div class="form-group">
        <div class="col-md-offset-2 col-md-10">
            <c:if test="${not empty communigateDomain.id}">
                <a href="<c:url value="/communigate/${communigateDomain.id}"/>" class="btn btn-primary">View</a>
            </c:if>
            <button type="submit" class="btn btn-primary">Save</button>
        </div>
    </div>

</form:form>