package utilits.controller.wrapper;

import utilits.aspect.AccountChangeType;
import utilits.aspect.ActionType;
import utilits.entity.AccountChange;

import java.util.Date;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
public class AccountsChangeWrapper {
    private final AccountChange accountChange;
    private final String equipmentURL;

    public AccountsChangeWrapper(AccountChange accountChange) {
        this.accountChange = accountChange;
        this.equipmentURL = "/ipstore/accounts/view/" + accountChange.getAccountId();
    }

    public Date getActionTimestamp() {
        return accountChange.getAction().getActionTimestamp();
    }

    public String getIp() {
        return accountChange.getAction().getIp();
    }

    public String getUsername(){
        return accountChange.getAction().getUsername();
    }

    public ActionType getActionType() {
        return accountChange.getAction().getType();
    }

    public AccountChangeType getType() {
        return accountChange.getType();
    }

    public String getOldValue() {
        return accountChange.getOldValue();
    }

    public String getNewValue() {
        return accountChange.getNewValue();
    }

    public String getEquipmentURL() {
        return equipmentURL;
    }
}
