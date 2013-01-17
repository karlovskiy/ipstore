/**
 * @author karlovsky
 */
$(document).ready(function () {
    $("#generate_password").click(function (e) {
        e.preventDefault();
        $.get("/ipstore/generate_password", { length: $('#max_length').val() }, function (data) {
            $("#pwd").val(data);
        });
    });
});
