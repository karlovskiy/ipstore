package utilits.controller.wrapper;

import utilits.aspect.ActionType;
import utilits.aspect.ChangeType;
import utilits.entity.Change;

import java.util.Date;

import static utilits.controller.wrapper.ActionWrapper.VIEW_PAGE_PREFIX;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
public class ChangeWrapper {

    private final Change change;
    private final String equipmentURL;

    public ChangeWrapper(Change change) {
        this.change = change;
        this.equipmentURL = VIEW_PAGE_PREFIX + change.getEquipmentId();
    }

    public Date getActionTimestamp() {
        return change.getAction().getActionTimestamp();
    }

    public String getIp() {
        return change.getAction().getIp();
    }

    public String getUsername() {
        return change.getAction().getUsername();
    }

    public ActionType getActionType() {
        return change.getAction().getType();
    }

    public ChangeType getType() {
        return change.getType();
    }

    public String getOldValue() {
        return change.getOldValue();
    }

    public String getNewValue() {
        return change.getNewValue();
    }

    public String getEquipmentURL() {
        return equipmentURL;
    }
}
