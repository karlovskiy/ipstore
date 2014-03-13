package ipstore.aspect.change;

/**
 * User: Sidorov Oleg
 * Date: 15.04.13
 */
public enum CommunigateChangeField implements IChangeField {

    DOMAIN_NAME("domainName"),
    TRY_PREFIX("tryPrefix"),
    CLIENT_NAME("clientName"),
    TICKET("ticket"),
    NUMBER_LINE("numberLine"),
    DISK_CAPACITY("diskCapacity"),
    SERVICE("service"),
    CONTRACT("contract"),
    LOGIN("login"),
    DATE("date"),
    DESCRIPTION("description"),
    STATUS("status");


    private final String fieldName;

    CommunigateChangeField(String fieldName) {
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