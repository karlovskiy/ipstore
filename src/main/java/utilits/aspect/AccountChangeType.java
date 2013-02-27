package utilits.aspect;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
public enum AccountChangeType implements IChangeType {
    LOGIN("login"),
    PASSWORD("password"),
    CLIENTNAME("clientName"),
    NUMBER("number"),
    DESCRIPTION("description"),
    STATUS("status");

    AccountChangeType(String fieldName) {
        this.fieldName = fieldName;
    }

    private final String fieldName;

    public String getFieldName() {
        return fieldName;
    }
}
