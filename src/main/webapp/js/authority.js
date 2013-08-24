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
    var all = [];
    $("label.authority > span").each(function () {
        var authority = $.trim($(this).html());
        if (authority != "ROOT") {
            var value = authority.split("_")[0];
            if ($.inArray(value, all) == -1) {
                all.push(value);
            }
        }
    });
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
    $("label.authority > input[type='checkbox']").change(function (e) {
        var jthis = $(this);
        var id = jthis.attr("id");
        if (id == "ROOT") {
            $("label.authority > input[type='checkbox'][id!='ROOT']").attr("checked", jthis.is(":checked"));
        } else {
            for (var i = 0; i < all.length; i++) {
                var mode = all[i];
                var edit = mode + "_EDIT";
                var view = mode + "_VIEW";
                var manager = mode + "_MANAGER";
                var editElement = $("label.authority > input[type='checkbox'][id='" + edit + "']");
                var viewElement = $("label.authority > input[type='checkbox'][id='" + view + "']");
                var managerElement = $("label.authority > input[type='checkbox'][id='" + manager + "']");
                if (id == edit && jthis.is(":checked")) {
                    viewElement.attr("checked", true);
                    if (managerElement.length != 0) {
                        managerElement.attr("checked", true);
                    }
                } else if (id == view && !jthis.is(":checked")) {
                    editElement.attr("checked", false);
                    if (managerElement.length != 0) {
                        managerElement.attr("checked", false);
                    }
                } else if (id == manager && jthis.is(":checked")) {
                    viewElement.attr("checked", true);

                } else if (id == manager && !jthis.is(":checked")) {
                    editElement.attr("checked", false);
                }
            }
        }
    });
});

