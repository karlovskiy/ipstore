package ipstore.aspect.change;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 2.4.6, 4/8/13
 */
public enum EquipmentChangeField implements IChangeField {

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
    STATUS("status"),
    CONFIG_NAME("configName"),
    CONFIG_TYPE("configType");

    private final String fieldName;

    EquipmentChangeField(String fieldName) {
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
