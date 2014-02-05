<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="<c:url value="/resources/js/init-datepicker.js"/>"></script>
<div class="container">
    <form:form commandName="capacityNumber" action="${action}" cssClass="form-horizontal">

        <div class="control-group">
            <label class="control-label" for="number">Number</label>

            <div class="controls">
                <form:input id="number" cssClass="input-xxlarge" path="number"/>
                <span class="errorblock">
                    <form:errors path="number"/>
                        <c:if test="${not empty existsId}">
                            <span>
                                Capacity number
                                <a href="<c:url value="/capacity/number/view/${existsId}"/>" target="_blank">
                                    <c:out value="${capacityNumber.number}"/>
                                </a>
                                already exists!
                            </span>
                        </c:if>
                </span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="clientName">ClientName</label>

            <div class="controls">
                <form:input id="clientName" cssClass="input-xxlarge" path="clientName"/>
                <span class="errorblock">
                    <form:errors path="clientName"/>
                </span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="address">Address</label>

            <div class="controls">
                <form:input id="address" cssClass="input-xxlarge" path="address"/>
                <span class="errorblock">
                    <form:errors path="address"/>
                </span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="application">Application</label>

            <div class="controls">
                <form:input id="application" cssClass="input-xxlarge" path="application"/>
                <span class="errorblock">
                    <form:errors path="application"/>
                </span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="date">Admittance date</label>

            <div class="controls">
                <form:input id="date" cssClass="input-small" path="admittanceDate"/>
                <span class="errorblock">
                    <form:errors path="admittanceDate"/>
                </span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="linesCount">Lines count</label>

            <div class="controls">
                <form:input id="linesCount" cssClass="input-small" path="linesCount"/>
                <span class="errorblock">
                    <form:errors path="linesCount"/>
                </span>
            </div>
        </div>


        <div class="control-group">
            <label class="control-label" for="comments">Comments</label>

            <div class="controls">
                <form:input id="comments" cssClass="input-xxlarge" path="comments"/>
                <span class="errorblock">
                    <form:errors path="comments"/>
                </span>
            </div>
        </div>

        <div class="form-actions">
            <c:if test="${empty capacityNumber.id}">
                <a href="<c:url value="/capacity/${capacityType.id}"/>" class="btn btn-primary">List</a>
            </c:if>
            <c:if test="${not empty capacityNumber.id}">
                <a href="<c:url value="/capacity/number/view/${capacityNumber.id}"/>" class="btn btn-primary">View</a>
            </c:if>
            <button type="submit" class="btn btn-primary">Save</button>
        </div>

    </form:form>
</div>