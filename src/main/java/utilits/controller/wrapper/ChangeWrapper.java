package utilits.controller.wrapper;

import utilits.aspect.ActionType;
import utilits.aspect.change.ChangeType;
import utilits.entity.Change;

import java.util.Date;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
public class ChangeWrapper {

    private final Change change;

    public ChangeWrapper(Change change) {
        this.change = change;
    }

    public Long getActionId() {
        return change.getAction().getId();
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

    public String getFieldType() {
        return change.getFieldType();
    }

    public String getOldValue() {
        return change.getOldValue();
    }

    public String getNewValue() {
        return change.getNewValue();
    }

    public Long getEntityId() {
        return change.getEntityId();
    }
}
