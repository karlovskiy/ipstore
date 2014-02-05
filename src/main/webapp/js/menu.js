/**
 * @author karlovsky
 */
$(document).ready(function () {
    $("#rebuild_index").click(function (e) {
        e.preventDefault();
        $.get("/rebuild", function (data) {
            bootbox.alert(data);
        });
    });
});
