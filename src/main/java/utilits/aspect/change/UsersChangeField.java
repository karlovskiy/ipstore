package utilits.aspect.change;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 2.5.1, 4/15/13
 */
public enum UsersChangeField implements IChangeField {

    USERNAME("username"),
    PASSWORD("password"),
    STATUS("userStatus"),
    AUTHORITY("authorityMask"),
    CREDENTIALS_NON_EXPIRED("credentialsNonExpired"),
    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    EMAIL("email");

    private final String fieldName;

    UsersChangeField(String fieldName) {
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
