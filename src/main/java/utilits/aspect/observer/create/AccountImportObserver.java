package utilits.aspect.observer.create;

import org.aspectj.lang.ProceedingJoinPoint;
import utilits.aspect.ActionType;
import utilits.entity.Account;
import utilits.entity.AccountChange;
import utilits.entity.Action;
import utilits.service.ActionService;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
public class AccountImportObserver extends AccountCreateObserver {

    private final ProceedingJoinPoint pjp;

    public AccountImportObserver(ActionType actionType, ActionService actionService,
                                 ProceedingJoinPoint pjp) {
        super(actionType, actionService, null);
        this.pjp = pjp;
    }

    @Override
    public Object observe() throws Throwable {
        Set<Long> beforeImportIds = new HashSet<Long>();
        for (Account account : actionService.loadEntities(clazz)) {
            beforeImportIds.add(account.getId());
        }
        Object result = pjp.proceed();
        Action action = buildAction();
        for (Account account : actionService.loadEntities(clazz)) {
            id = account.getId();
            if (!beforeImportIds.contains(id)) {
                Collection<AccountChange> changes = buildChanges(account);
                addChanges(action, changes);
            }
        }
        actionService.saveAction(action);
        return result;
    }

}