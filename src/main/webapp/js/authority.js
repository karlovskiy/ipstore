/**
 * Here will be javadoc
 * @author karlovsky
 * @since 2.4, 3/28/13
 */
$(document).ready(function () {
    var authorities = $("#authorities").val().split(",");
    for (var i = 0; i < authorities.length; i++) {
        var authority = $.trim(authorities[i]);
        if (authority != '') {
            $("input#" + authority).attr("checked", true);
        }
    }
    $("#form").submit(function (e) {
        var authorities = [];
        var form = $(this);
        form.find("label.authority").each(function () {
            var label = $(this);
            if (label.find("input").is(':checked')) {
                authorities.push(label.find("span").html());
            }
        });
        form.find("#authorities").val(authorities.join(","));
    });
});

