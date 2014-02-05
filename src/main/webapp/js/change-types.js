/**
 * Here will be javadoc
 * @author karlovsky
 * @since 2.5.4, 4/18/13
 */

var ChangeTypes = function () {

    this.load = function () {
        var _this = this;
        $.getJSON("/change_types", function (data) {
            _this.init(data);
        });
    };

    this.init = function (data) {
        var changeTypes = $("#changeType");
        var _this = this;
        changeTypes.change(function () {
            _this.change($(this), data);
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
    };

    this.change = function (changeTypes, data) {
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
};
$(document).ready(function () {
    var changeTypes = new ChangeTypes();
    changeTypes.load();
});
