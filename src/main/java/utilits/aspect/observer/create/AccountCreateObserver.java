package utilits.aspect.observer.create;

import utilits.aspect.AccountChangeType;
import utilits.aspect.ActionType;
import utilits.aspect.IChangeType;
import utilits.entity.Account;
import utilits.entity.AccountChange;
import utilits.entity.Action;
import utilits.service.ActionService;

import java.util.Collection;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
public class AccountCreateObserver extends AbstractCreateObserver<Account, AccountChange> {


    public AccountCreateObserver(ActionType actionType, ActionService actionService, Long id) {
        super(actionType, AccountChangeType.values(), actionService, Account.class, id);
    }

    @Override
    protected void addChanges(Action action, Collection<AccountChange> changes) {
        for (AccountChange change : changes) {
            action.addAccountChange(change);
        }
    }

    @Override
    protected AccountChange buildChange(IChangeType changeType, String newValue) {
        AccountChange change = new AccountChange();
        change.setType((AccountChangeType) changeType);
        change.setNewValue(newValue);
        change.setAccountId(id);
        return change;
    }
}
