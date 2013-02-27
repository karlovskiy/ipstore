package utilits.aspect.observer.update;

import org.aspectj.lang.ProceedingJoinPoint;
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
public class AccountUpdateObserver extends AbstractUpdateObserver<Account, AccountChange> {

    public AccountUpdateObserver(ActionType actionType, ProceedingJoinPoint pjp, ActionService actionService) {
        super(actionType, AccountChangeType.values(), Account.class, pjp, actionService);
    }

    @Override
    protected Long makeEntityId(Account account) {
        return account.getId();
    }

    @Override
    protected int getArgumentIndex(ActionType actionType) {
        return ActionType.ACCOUNTS_UPDATE == actionType ? 2 : 0;
    }

    @Override
    protected void addChanges(Action action, Collection<AccountChange> changes) {
        for (AccountChange accountChange : changes) {
            action.addAccountChange(accountChange);
        }
    }

    @Override
    protected AccountChange buildChange(IChangeType changeType, String oldValue, String newValue, Long equipmentId) {
        AccountChange accountChange = new AccountChange();
        accountChange.setType((AccountChangeType) changeType);
        accountChange.setNewValue(newValue);
        accountChange.setAccountId(equipmentId);
        accountChange.setOldValue(oldValue);
        return accountChange;
    }


}
