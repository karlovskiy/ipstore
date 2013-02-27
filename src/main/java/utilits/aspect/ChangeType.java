package utilits.aspect;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
public enum ChangeType implements IChangeType {
    IP_ADDRESS("ipAddress"),
    TYPE("type"),
    USERNAME("username"),
    LOGIN("login"),
    PASSWORD("password"),
    PASSWORD_DATE("passwordDate"),
    PASSWORD_STATUS("passwordStatus"),
    CLIENT_NAME("clientName"),
    PLACEMENT_ADDRESS("placementAddress"),
    APPLICATION_NUMBER("applicationNumber"),
    DESCRIPTION("description"),
    STATUS("status");

    ChangeType(String fieldName) {
        this.fieldName = fieldName;
    }

    private final String fieldName;

    public String getFieldName() {
        return fieldName;
    }
}
