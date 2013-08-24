/**
 * @author karlovsky
 */
$(document).ready(function () {
    $("table[id^='list_table']").each(function(){
        var tbl = $(this);
        tbl.tablesorter();
        tbl.find("thead th:first").click();
    });
});