/**
 * @author karlovsky
 */
$(document).ready(function () {
//    todo: refactoring
    var del = $("#account_delete");
    if (del.length != 0) {
        del.click(function (e) {
            e.preventDefault();
            var location = $(this).attr("href");
            bootbox.confirm("Do you really want to delete account?","No", "Yes", function (yes) {
                if (yes) {
                    window.location.replace(location);
                }
            });
        });
    }
});

