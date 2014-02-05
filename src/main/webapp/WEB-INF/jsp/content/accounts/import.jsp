<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="container">
    <h3>Accounts import</h3>
    <c:if test="${error}">
        <span class="errorblock">Importing error!</span>
    </c:if>
    <form method="post" action="/accounts/import" enctype="multipart/form-data">
        <input type="file" name="file"/>
        <a href="<c:url value="/accounts"/>" class="btn btn-primary">List</a>
        <input class="btn btn-primary" type="submit" value="Import"/>
    </form>
    <c:if test="${not error and not empty result}">
        <table>
            <tr>
                <td valign="top">
                    <table class="table table-hover table-condensed">
                        <tr>
                            <td>
                                <span class="text-success">
                                    Successfully added <c:out value="${result.addedCount}"/> account items.
                                </span>
                            </td>
                        </tr>
                        <c:forEach items="${result.added}" var="added">
                            <tr>
                                <td>
                                    <span class="text-success">
                                        Account with login
                                        <a href="/accounts/view/${added.id}" target="_blank">
                                            <c:out value="${added.login}"/>
                                        </a>
                                        successfully added!
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
                                        Already exists and ignored <c:out value="${result.existsCount}"/> account items.
                                    </span>
                                </td>
                            </tr>
                            <c:forEach items="${result.exists}" var="exists">
                                <tr>
                                    <td>
                                        <span class="text-error">
                                            Account with login
                                            <a href="/accounts/view/${exists.id}" target="_blank">
                                                <c:out value="${exists.login}"/>
                                            </a>
                                            already exists!
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
</div>