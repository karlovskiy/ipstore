<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="container">
    <c:if test="${not empty success}">
        <div class=" ${success ? 'alert':'alert alert-error'}" style="margin-top: 5px; margin-bottom: 0px;">
            <span class="${success ? '':'errorblock'}">${resultMessage}</span>
        </div>
    </c:if>
    <form method="post" action="/ipstore/import" enctype="multipart/form-data">
        <input type="file" name="file"/>
        <input class="btn btn-primary" type="submit" value="Import"/>
    </form>
</div>