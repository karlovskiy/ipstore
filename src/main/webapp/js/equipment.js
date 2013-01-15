/**
 * @author karlovsky
 */
$(document).ready(function () {
    var tbl = $("#equipment_table");
    tbl.tablesorter();
    tbl.find("thead th:first").click();
});