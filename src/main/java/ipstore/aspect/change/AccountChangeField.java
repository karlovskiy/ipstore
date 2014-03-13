package ipstore.aspect.change;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 2.4.6, 4/8/13
 */
public enum AccountChangeField implements IChangeField {

    LOGIN("login"),
    PASSWORD("password"),
    CLIENT_NAME("clientName"),
    NUMBER("number"),
    DESCRIPTION("description"),
    STATUS("status");

    private final String fieldName;

    AccountChangeField(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public String getFieldType() {
        return name();
    }

}
