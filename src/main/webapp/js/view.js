/**
 * @author karlovsky
 */
$(document).ready(function () {
    var del = $("#delete_btn");
    if (del.length != 0) {
        del.click(function (e) {
            e.preventDefault();
            var location = $(this).attr("href");
            bootbox.confirm("Do you really want to delete ?","No", "Yes", function (yes) {
                if (yes) {
                    window.location.replace(location);
                }
            });
        });
    }
});

