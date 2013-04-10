<%--
  User: Sidorov Oleg
  Date: 01.04.13
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="<c:url value="/js/edit.js"/>"></script>
<div class="container">
    <form:form commandName="communigateDomain" action="${formAction}" cssClass="form-horizontal">
        <div class="control-group">
            <label class="control-label" for="domainName">DomainName</label>

            <div class="controls">
                <form:input id="domainName" path="domainName"/>
                <span class="errorblock">
                    <form:errors path="domainName"/>
                    <c:if test="${not empty existsCommunigateDomain}">
                      <span>
                            Communigate domains with DomainName
                            <a href="<c:url value="/ipstore/communigate/view/${existsCommunigateDomain.id}"/>" target="_blank">
                                <c:out value="${existsCommunigateDomain.domainName}"/>
                            </a>
                            already exists!
                        </span>
                    </c:if>
                </span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="tryPrefix">TryPrefix</label>
            <div class="controls">
                <form:input id="tryPrefix" path="tryPrefix"/>
                <span class="errorblock">
                       <form:errors path="tryPrefix"/>
                </span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="clientName">ClientName</label>
            <div class="controls">
                <form:input id="clientName" path="clientName"/>
                <span class="errorblock">
                    <form:errors path="clientName"/>
                </span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="ticket">Ticket</label>
            <div class="controls">
                <form:input id="ticket" path="ticket"/>
                <span class="errorblock">
                    <form:errors path="ticket"/>
                </span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="numberLine">NumberLine</label>
            <div class="controls">
                <form:input id="numberLine" path="numberLine"/>
                <span class="errorblock">
                    <form:errors path="numberLine"/>
                </span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="diskCapacity">DiskCapacity</label>
            <div class="controls">
                <form:input id="diskCapacity" path="diskCapacity"/>
                <span class="errorblock">
                    <form:errors path="diskCapacity"/>
                </span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="service">Service</label>
            <div class="controls">
                <form:input id="service" path="service"/>
                <span class="errorblock">
                    <form:errors path="service"/>
                </span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="contract">Contract</label>
            <div class="controls">
                <form:input id="contract" path="contract"/>
                <span class="errorblock">
                    <form:errors path="contract"/>
                </span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="login">Login</label>
            <div class="controls">
                <form:input id="login" path="login"/>
                <span class="errorblock">
                    <form:errors path="login"/>
                </span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="date">Date</label>
            <div class="controls">
                <form:input id="date" path="date"/>
                <span class="errorblock">
                    <form:errors path="date"/>
                </span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="description">Description</label>
            <div class="controls">
                <form:input id="description" path="description" cssClass="input-xxlarge"/>
                <span class="errorblock">
                    <form:errors path="description"/>
                </span>
            </div>
        </div>

        <div class="form-actions">
            <button type="submit" class="btn btn-primary">Save</button>
        </div>

    </form:form>
</div>