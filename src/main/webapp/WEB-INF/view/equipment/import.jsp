<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form method="post" action="<c:url value="/equipment/import"/>" enctype="multipart/form-data" class="form-horizontal" role="form">
    <h3>Import equipment</h3>
    <c:if test="${error}">
        <div class="form-group">
            <div class="col-md-5">
                <div class="alert alert-danger" style="margin-bottom: 0;">Importing error!</div>
            </div>
        </div>
    </c:if>
    <div class="form-group">
        <div class="col-md-5">
            <input type="file" name="file"/>
        </div>
    </div>
    <div class="form-group">
        <div class="col-md-5">
            <input class="btn btn-primary" type="submit" value="Import"/>
        </div>
     </div>
</form>
<c:if test="${not error and not empty result}">
    <table>
        <tr>
            <td valign="top">
                <table class="table table-hover table-condensed">
                    <tr>
                        <td>
                                <span class="text-success">
                                    Successfully added <c:out value="${result.addedCount}"/> equipment items.
                                </span>
                        </td>
                    </tr>
                    <c:forEach items="${result.added}" var="added">
                        <tr>
                            <td>
                                    <span class="text-success">
                                        Equipment with ip <a href="/equipment/view/${added.id}"
                                                             target="_blank">
                                        <c:out value="${added.ipAddress}"/>
                                    </a> successfully added!
                                    </span>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </td>
            <td valign="top">
                <c:if test="${not empty result.exists}">
                    <table class="table table-hover table-condensed">
                        <tr>
                            <td>
                                    <span class="text-error">
                                        Already exists and ignored <c:out value="${result.existsCount}"/> equipment items.
                                    </span>
                            </td>
                        </tr>
                        <c:forEach items="${result.exists}" var="exists">
                            <tr>
                                <td>
                                        <span class="text-error">
                                            Equipment with ip <a href="/equipment/view/${exists.id}"
                                                                 target="_blank">
                                            <c:out value="${exists.ipAddress}"/>
                                        </a> already exists!
                                        </span>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:if>
            </td>
        </tr>
    </table>

</c:if>