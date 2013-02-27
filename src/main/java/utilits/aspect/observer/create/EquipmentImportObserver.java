package utilits.aspect.observer.create;

import org.aspectj.lang.ProceedingJoinPoint;
import utilits.aspect.ActionType;
import utilits.entity.Action;
import utilits.entity.Change;
import utilits.entity.Equipment;
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
public class EquipmentImportObserver extends EquipmentCreateObserver {

    private final ProceedingJoinPoint pjp;

    public EquipmentImportObserver(ActionType actionType, ActionService actionService,
                                   ProceedingJoinPoint pjp) {
        super(actionType, actionService, null);
        this.pjp = pjp;
    }

    @Override
    public Object observe() throws Throwable {
        Set<Long> beforeImportIds = new HashSet<Long>();
        for (Equipment equipment : actionService.loadEntities(clazz)) {
            beforeImportIds.add(equipment.getId());
        }
        Object result = pjp.proceed();
        Action action = buildAction();
        for (Equipment equipment : actionService.loadEntities(clazz)) {
            id = equipment.getId();
            if (!beforeImportIds.contains(id)) {
                Collection<Change> changes = buildChanges(equipment);
                addChanges(action, changes);
            }
        }
        actionService.saveAction(action);
        return result;
    }
}
