/**
 * @author karlovsky
 * @since 4.0, 2/14/14
 */

(function (ipstore) {
    var modules = [];

    ipstore.addModule = function (moduleDeclaration) {
        modules.push(moduleDeclaration);
    };

    ipstore.initModules = function () {
        for (var i = 0; i < modules.length; i++) {
            modules[i].call(this);
        }
    };

}(window.ipstore = window.ipstore || {}));

ipstore.addModule(function () {

    $.extend($.tablesorter.themes.bootstrap, {
        // these classes are added to the table. To see other table classes available,
        // look here: http://twitter.github.com/bootstrap/base-css.html#tables
        table: 'table table-hover table-condensed table-bordered',
        caption: 'caption',
        header: 'bootstrap-header', // give the header a gradient background
        footerRow: '',
        footerCells: '',
        icons: 'icon-table', // add "icon-white" to make them white; this icon class is added to the <i> in the header
        sortNone: 'glyphicon glyphicon-sort', //bootstrap-icon-unsorted
        sortAsc: 'glyphicon glyphicon-sort-by-attributes',     // includes classes for Bootstrap v2 & v3  icon-chevron-up glyphicon glyphicon-chevron-up
        sortDesc: 'glyphicon glyphicon-sort-by-attributes-alt', // includes classes for Bootstrap v2 & v3  icon-chevron-down glyphicon glyphicon-chevron-down
        active: '', // applied when column is sorted
        hover: '', // use custom css here - bootstrap class may not override it
        filterRow: '', // filter row class
        even: '', // odd row zebra striping
        odd: ''  // even row zebra striping
    });
    $("table.tablesorter").tablesorter({
        // this will apply the bootstrap theme if "uitheme" widget is included
        // the widgetOptions.uitheme is no longer required to be set
        theme : "bootstrap",
        sortList: [[0,0]],
        widthFixed: true,
        headerTemplate : '{content} {icon}', // new in v2.7. Needed to add the bootstrap icon!
        // widget code contained in the jquery.tablesorter.widgets.js file
        // use the zebra stripe widget if you plan on hiding any rows (filter widget)
        widgets : [ "uitheme"]
    });
});

ipstore.addModule(function () {
    $("#delete_btn").click(function (e) {
        e.preventDefault();
        var location = $(this).attr("href");
        bootbox.confirm({
            closeButton: false,
            message: "Do you really want to delete ?",
            buttons: {
                confirm: {
                    label: "Yes",
                    className: "btn-primary"
                },
                cancel: {
                    label: "No",
                    className: "btn-default"
                }
            },
            callback: function (yes) {
                if (yes) {
                    window.location.replace(location);
                }
            }
        });
    });
});

ipstore.addModule(function(){
    $("#rebuild_index").click(function (e) {
        e.preventDefault();
        $.get(window['contextPath'] + "/rebuild", function (data) {
            bootbox.alert({
                closeButton: false,
                message: data
            });
        });
    });
});

ipstore.addModule(function(){
    $("#generate_password").click(function (e) {
        e.preventDefault();
        $.get(window['contextPath'] + "/generate_password", { length: $('#max_length').val() }, function (data) {
            $("#pwd").val(data);
        });
    });
});

ipstore.addModule(function () {
    $.each(["from", "to", "date"], function () {
        $("input#" + this).datepicker({
            format: 'dd.mm.yyyy',
            weekStart: 1
        });
    });
});

ipstore.addModule(function () {

    function init() {
        var changeTypes = $("#changeType");
        if (changeTypes.length != 0) {
            $.getJSON(window['contextPath'] + "/change_types", function (data) {
                changeTypes.change(function () {
                    change($(this), data);
                });
                var changeType = changeTypes.find("option:selected").val();
                if (changeType != "ALL") {
                    var fieldTypes = $("#fieldType");
                    var selectedFieldType = fieldTypes.find("option:selected").val();
                    fieldTypes.html("");
                    var fields = data[changeType];
                    for (var i = 0; i < fields.length; i++) {
                        var fieldType = fields[i];
                        fieldTypes.append($('<option>', { value: fieldType }).text(fieldType));
                        if (fieldType == selectedFieldType) {
                            fieldTypes.val(selectedFieldType);
                        }
                    }
                }
            });
        }
    }

    function change(changeTypes, data) {
        var changeType = changeTypes.find("option:selected").val();
        var fieldTypes = $("#fieldType");
        fieldTypes.html("");
        if (changeType == "ALL") {
            fieldTypes.hide();
        } else {
            var fields = data[changeType];
            for (var i = 0; i < fields.length; i++) {
                var fieldType = fields[i];
                fieldTypes.append($('<option>', { value: fieldType }).text(fieldType));
            }
            fieldTypes.show();
        }
    }

    init();
});

ipstore.addModule(function () {
    var auth = $("#authorities");
    if (auth.length != 0) {
        var authorities = auth.val().split(",");

        for (var i = 0; i < authorities.length; i++) {
            var authority = $.trim(authorities[i]);
            if (authority != '') {
                $("input#" + authority).prop("checked", true);
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
                $("label.authority > input[type='checkbox'][id!='ROOT']").prop("checked", jthis.is(":checked"));
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
                        viewElement.prop("checked", true);
                        if (managerElement.length != 0) {
                            managerElement.prop("checked", true);
                        }
                    } else if (id == view && !jthis.is(":checked")) {
                        editElement.prop("checked", false);
                        if (managerElement.length != 0) {
                            managerElement.prop("checked", false);
                        }
                    } else if (id == manager && jthis.is(":checked")) {
                        viewElement.prop("checked", true);

                    } else if (id == manager && !jthis.is(":checked")) {
                        editElement.prop("checked", false);
                    }
                }
            }
        });
    }
});

$(document).ready(function () {
    ipstore.initModules();
});