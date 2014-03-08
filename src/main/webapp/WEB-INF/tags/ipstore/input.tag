<%@ tag dynamic-attributes="attrs" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="type" %>
<spring:bind path="${attrs.path}">
    <div class="form-group ${status.error ? "has-error" : ""}">
        <label class="control-label col-md-2"
               for="${empty attrs.id ? attrs.path : attrs.id}">
                ${attrs.label}
        </label>

        <div class="col-md-5">
            <c:choose>
                <c:when test="${type == 'checkbox'}">
                    <div class="checkbox">
                        <form:checkbox id="${empty attrs.id ? attrs.path : attrs.id}" path="${attrs.path}"/>
                    </div>
                </c:when>
                <c:otherwise>
                    <form:input id="${empty attrs.id ? attrs.path : attrs.id}" path="${attrs.path}"
                                cssClass="form-control"/>
                </c:otherwise>
            </c:choose>
            <form:errors path="${attrs.path}" cssClass="help-block"/>
        </div>
    </div>
</spring:bind>