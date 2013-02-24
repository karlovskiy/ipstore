/**
 * @author karlovsky
 */
$(document).ready(function () {
    var tbl = $("#list_table");
    tbl.tablesorter();
    tbl.find("thead th:first").click();
});