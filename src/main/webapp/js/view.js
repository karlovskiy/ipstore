/**
 * @author karlovsky
 */
$(document).ready(function () {
    var del = $("#equipment_delete");
    if (del.length != 0) {
        del.click(function (e) {
            e.preventDefault();
            var location = $(this).attr("href");
            bootbox.confirm("Do you really want to delete equipment?","No", "Yes", function (yes) {
                if (yes) {
                    window.location.replace(location);
                }
            });
        });
    }
    var copy_to_clipboard = new ZeroClipboard($("#copy_to_clipboard"), { moviePath: "/flash/zeroclipboard.swf"});
});

