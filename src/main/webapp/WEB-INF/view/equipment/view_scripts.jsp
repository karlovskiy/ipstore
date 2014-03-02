<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="<c:url value="/resources/js/zeroclipboard.min.js"/>"></script>
<script type="text/javascript">
    //<![CDATA[
    $(function () {
        var copy_to_clipboard = new ZeroClipboard($("#copy_to_clipboard"), { moviePath: "/resources/flash/zeroclipboard.swf"});
    });
    //]]>
</script>