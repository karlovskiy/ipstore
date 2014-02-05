<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="<c:url value="/resources/js/edit.js"/>"></script>
<div class="container">
    <form:form commandName="capacityType" action="${action}" cssClass="form-horizontal">
        <div class="control-group">
            <label class="control-label" for="name">Name</label>
            <div class="controls">
                <form:input id="name" path="name"/>
                <span class="errorblock">
                    <form:errors path="name"/>
                    <c:if test="${not empty existsId}">
                        <span>
                            Capacity with name
                            <a href="<c:url value="/capacity/view/${existsId}"/>" target="_blank">
                                <c:out value="${capacityType.name}"/>
                            </a>
                            already exists!
                        </span>
                    </c:if>
                </span>
            </div>
        </div>

        <div class="form-actions">
            <c:if test="${empty capacityType.id}">
                <a href="<c:url value="/capacity"/>" class="btn btn-primary">List</a>
            </c:if>
            <c:if test="${not empty capacityType.id}">
                <a href="<c:url value="/capacity/${capacityType.id}"/>" class="btn btn-primary">List</a>
            </c:if>
            <button type="submit" class="btn btn-primary">Save</button>
        </div>

    </form:form>
</div>