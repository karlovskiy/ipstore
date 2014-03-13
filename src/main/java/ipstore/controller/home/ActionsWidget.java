package ipstore.controller.home;

import ipstore.entity.Action;

import java.util.List;

/**
 * Here will be javadoc
 *
 * @author ravenstar
 * @since 4.0, 3/1/14
 */
public class ActionsWidget {
    private Long total;
    private Long changes;
    private Long equipmentChanges;
    private Long accountsChanges;
    private Long communigateChanges;
    private Long usersChanges;
    private List<Action> lastActions;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getChanges() {
        return changes;
    }

    public void setChanges(Long changes) {
        this.changes = changes;
    }

    public Long getEquipmentChanges() {
        return equipmentChanges;
    }

    public void setEquipmentChanges(Long equipmentChanges) {
        this.equipmentChanges = equipmentChanges;
    }

    public Long getAccountsChanges() {
        return accountsChanges;
    }

    public void setAccountsChanges(Long accountsChanges) {
        this.accountsChanges = accountsChanges;
    }

    public Long getCommunigateChanges() {
        return communigateChanges;
    }

    public void setCommunigateChanges(Long communigateChanges) {
        this.communigateChanges = communigateChanges;
    }

    public Long getUsersChanges() {
        return usersChanges;
    }

    public void setUsersChanges(Long usersChanges) {
        this.usersChanges = usersChanges;
    }

    public List<Action> getLastActions() {
        return lastActions;
    }

    public void setLastActions(List<Action> lastActions) {
        this.lastActions = lastActions;
    }
}
