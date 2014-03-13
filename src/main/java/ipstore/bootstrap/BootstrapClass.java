package ipstore.bootstrap;

import static org.apache.commons.lang.StringUtils.EMPTY;

/**
 * Here will be javadoc
 *
 * @author ravenstar
 * @since 4.0, 2/25/14
 */
public enum BootstrapClass {

    DEFAULT(EMPTY, EMPTY, "text-muted", "label label-default"),
    PRIMARY(EMPTY, EMPTY, "text-primary", "label label-primary"),
    SUCCESS(EMPTY, "alert alert-success", "text-success", "label label-success"),
    INFO(EMPTY, "alert alert-info", "text-info", "label label-info"),
    WARNING("warning", "alert alert-warning", "text-warning", "label label-warning"),
    DANGER("danger", "alert alert-danger", "text-danger", "label label-danger");

    private String rowClass;
    private String alertClass;
    private String textClass;
    private String labelClass;

    BootstrapClass(String rowClass, String alertClass, String textClass, String labelClass) {
        this.rowClass = rowClass;
        this.alertClass = alertClass;
        this.textClass = textClass;
        this.labelClass = labelClass;
    }

    public String getRowClass() {
        return rowClass;
    }

    public String getAlertClass() {
        return alertClass;
    }

    public String getTextClass() {
        return textClass;
    }

    public String getLabelClass() {
        return labelClass;
    }
}
