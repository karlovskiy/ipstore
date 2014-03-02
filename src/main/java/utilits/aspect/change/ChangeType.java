package utilits.aspect.change;

import utilits.entity.*;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 2.5.0, 4/8/13
 */
public enum ChangeType {

    NONE,                                                                                       // 0
    ACCOUNTS(Account.class, AccountChangeField.class, "/accounts/view/"),                       // 1
    EQUIPMENT(Equipment.class, EquipmentChangeField.class, "/equipment/view"),                  // 2
    COMMUNIGATE(CommunigateDomain.class, CommunigateChangeField.class, "/communigate/view"),    // 3
    USERS(User.class, UsersChangeField.class, "/users/view");                                   // 4

    private Class<? extends IHasId> entityClazz;
    private Class<? extends IChangeField> fieldsClazz;
    private String viewPageURL;

    private ChangeType() {
    }

    private ChangeType(Class<? extends IHasId> entityClazz,
                       Class<? extends IChangeField> fieldsClazz,
                       String viewPageURL) {
        this.entityClazz = entityClazz;
        this.fieldsClazz = fieldsClazz;
        this.viewPageURL = viewPageURL;
    }

    public IChangeField[] getIChangeFields() {
        return fieldsClazz != null ? fieldsClazz.getEnumConstants() : null;
    }

    public Class<? extends IHasId> getEntityClazz() {
        return entityClazz;
    }

    public String getViewPageURL() {
        return viewPageURL;
    }
}
