<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="<c:url value="/js/edit.js"/>"></script>
<div class="container">
    <form:form commandName="equipment" action="${formAction}" cssClass="form-horizontal" enctype="multipart/form-data">
        <div class="control-group">
            <label class="control-label" for="ipAddress">IpAddress</label>

            <div class="controls">
                <form:input id="ipAddress" path="ipAddress"/>
                <span class="errorblock">
                    <form:errors path="ipAddress"/>
                    <c:if test="${not empty existsEquipment}">
                        <span>
                            Equipment with ip
                            <a href="/ipstore/equipment/${existsEquipment.id}"
                                    target="_blank">
                                <c:out value="${existsEquipment.ipAddress}"/>
                            </a>
                            already exists!
                        </span>
                    </c:if>
                </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="type">Type</label>

            <div class="controls">
                <form:input id="type" path="type"/>
                <span class="errorblock">
                    <form:errors path="type"/>
                </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="username">Username</label>

            <div class="controls">
                <form:input id="username" path="username"/>
                <span class="errorblock">
                    <form:errors path="username"/>
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
            <label class="control-label" for="pwd">Password</label>

            <div class="controls">
                <form:input id="pwd" path="password"/>
                <span class="errorblock">
                    <form:errors path="password"/>
                </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="tch">Is checked by telnet</label>

            <div class="controls">
                <form:checkbox id="tch" path="telnetCheck"/>
                <span class="errorblock">
                    <form:errors path="telnetCheck"/>
                </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="clientName">ClientName</label>

            <div class="controls">
                <form:input id="clientName" path="clientName" cssClass="input-xxlarge"/>
                <span class="errorblock">
                    <form:errors path="clientName"/>
                </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="placementAddress">PlacementAddress</label>

            <div class="controls">
                <form:input id="placementAddress" path="placementAddress" cssClass="input-xxlarge"/>
                <span class="errorblock">
                    <form:errors path="placementAddress"/>
                </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="applicationNumber">ApplicationNumber</label>

            <div class="controls">
                <form:input id="applicationNumber" path="applicationNumber" cssClass="input-xxlarge"/>
                <span class="errorblock">
                    <form:errors path="applicationNumber"/>
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
        <c:choose>
            <c:when test="${empty equipment.id}">
                <div class="control-group">
                    <label class="control-label" for="new_equipment_cf">Config(Max 10Mb)</label>
                    <div class="controls">
                        <input id="new_equipment_cf" name="cf" class="input-xxlarge" type="file"/>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div id="new_cf" class="control-group" style="${empty equipment.configName ? '' : 'display: none;'}">
                    <label class="control-label" for="edit_equipment_cf">Config(Max 10Mb)</label>
                    <div class="controls">
                        <input id="edit_equipment_cf" name="cf" class="input-xxlarge" type="file"/>
                    </div>
                </div>
                <div id="exist_cf" class="control-group" style="${empty equipment.configName ? 'display: none;' : ''}">
                    <label class="control-label" for="cf_download">Config</label>
                    <div class="controls">
                        <input type="hidden" name="cf_reset" value=""/>
                        <a id="cf_download" href="<c:url value="/ipstore/equipment/load_config/${equipment.id}"/>">
                            <c:out value="${equipment.configName}"/></a>
                        <a class="btn btn-danger"
                           onclick="$('#exist_cf').hide();$('#new_cf').show();$('input[name=cf_reset]').val('reset');">Delete</a>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
        <div class="form-actions">
            <c:if test="${empty equipment.id}">
                <a href="<c:url value="/ipstore/equipment"/>" class="btn btn-primary">List</a>
            </c:if>
            <c:if test="${not empty equipment.id}">
                <a href="<c:url value="/ipstore/equipment/view/${equipment.id}"/>" class="btn btn-primary">View</a>
            </c:if>
            <button type="submit" class="btn btn-primary">Save</button>
            <a id="generate_password" class="btn btn-success">Generate password</a>
            <input id="max_length" type="text" name="length" style="width: 20px;" value="${defaultPasswordLength}">
        </div>

    </form:form>
</div>