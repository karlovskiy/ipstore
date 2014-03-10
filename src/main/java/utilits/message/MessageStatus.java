package utilits.message;

/**
 * Here will be javadoc
 *
 * @author ravenstar
 * @since 4.1, 3/10/14
 */
public enum MessageStatus {

    SUCCESS("alert alert-success"),
    INFO("alert alert-info"),
    WARNING("alert alert-warning"),
    DANGER("alert alert-danger");

    private String cssClass;

    MessageStatus(String cssClass) {
        this.cssClass = cssClass;
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }
}
