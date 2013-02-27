package utilits.aspect.observer.update;

import org.aspectj.lang.ProceedingJoinPoint;
import utilits.aspect.ActionType;
import utilits.aspect.ChangeType;
import utilits.aspect.IChangeType;
import utilits.entity.Action;
import utilits.entity.Change;
import utilits.entity.Equipment;
import utilits.service.ActionService;

import java.util.Collection;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
public class EquipmentUpdateObserver extends AbstractUpdateObserver<Equipment, Change> {

    public EquipmentUpdateObserver(ActionType actionType, ProceedingJoinPoint pjp,
                                   ActionService actionService) {
        super(actionType, ChangeType.values(), Equipment.class, pjp, actionService);
    }

    @Override
    protected int getArgumentIndex(ActionType actionType) {
        return ActionType.EQUIPMENT_UPDATE == actionType ? 2 : 0;
    }

    @Override
    protected Long makeEntityId(Equipment equipment) {
        return equipment.getId();
    }

    @Override
    protected void addChanges(Action action, Collection<Change> changes) {
        for (Change change : changes) {
            action.addChange(change);
        }
    }

    @Override
    protected Change buildChange(IChangeType changeType, String oldValue, String newValue, Long id) {
        Change change = new Change();
        change.setType((ChangeType) changeType);
        change.setNewValue(newValue);
        change.setEquipmentId(id);
        change.setOldValue(oldValue);
        return change;
    }


}
