/**
 * @author karlovsky
 */
$(document).ready(function () {
    $.each(["from", "to", "date"], function () {
        var dateField = $("input#" + this);
        if (dateField.length != 0) {
            dateField.datepicker({
                format: 'dd.mm.yyyy',
                weekStart: 1
            });
        }
    });
});
