package ipstore.aspect.observer;

import org.aspectj.lang.ProceedingJoinPoint;
import ipstore.aspect.Action;
import ipstore.entity.Change;
import ipstore.entity.IHasId;
import ipstore.service.ActionService;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 2.5.0, 4/10/13
 */
public class ActionWithImportChangesObserver extends ActionWithCreateChangesObserver {

    private final ProceedingJoinPoint pjp;

    public ActionWithImportChangesObserver(Action action, ActionService actionService, ProceedingJoinPoint pjp) {
        super(action, actionService, null);
        this.pjp = pjp;
    }

    @Override
    public Object observe() throws Throwable {
        Set<Long> beforeImportIds = new HashSet<Long>();
        for (IHasId entity : actionService.loadEntities(changeType.getEntityClazz())) {
            beforeImportIds.add(entity.getId());
        }
        Object result = pjp.proceed();
        ipstore.entity.Action action = buildAction();
        for (IHasId entity : actionService.loadEntities(changeType.getEntityClazz())) {
            id = entity.getId();
            if (!beforeImportIds.contains(id)) {
                Collection<Change> changes = buildChanges(entity);
                addChanges(action, changes);
            }
        }
        actionService.saveAction(action);
        return result;
    }
}
