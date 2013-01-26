/**
 * @author karlovsky
 */
$(document).ready(function () {
    $.each(["from", "to"], function () {
        $("input#" + this).datepicker({
            format: 'dd.mm.yyyy',
            weekStart: 1
        });
    });
});
