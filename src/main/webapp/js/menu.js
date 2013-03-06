/**
 * @author karlovsky
 */
$(document).ready(function () {
    $("#rebuild_index").click(function (e) {
        e.preventDefault();
        $.get("/ipstore/rebuild", function (data) {
            bootbox.alert(data);
        });
    });
});
