package utilits.aspect.change;

import utilits.entity.Account;
import utilits.entity.CommunigateDomain;
import utilits.entity.Equipment;
import utilits.entity.IHasId;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 2.5.0, 4/8/13
 */
public enum ChangeType {

    NONE,                                                               // 0
    ACCOUNTS(Account.class, AccountChangeField.class),                  // 1
    EQUIPMENT(Equipment.class, EquipmentChangeField.class),             // 2
    COMMUNIGATE(CommunigateDomain.class, CommunigateChangeFild.class);  // 3

    private Class<? extends IHasId> entityClazz;
    private Class<? extends IChangeField> fieldsClazz;

    private ChangeType() {
    }

    private ChangeType(Class<? extends IHasId> entityClazz,
                       Class<? extends IChangeField> fieldsClazz) {
        this.entityClazz = entityClazz;
        this.fieldsClazz = fieldsClazz;
    }

    public IChangeField[] getIChangeFields() {
        return fieldsClazz != null ? fieldsClazz.getEnumConstants() : null;
    }

    public Class<? extends IHasId> getEntityClazz() {
        return entityClazz;
    }
}
