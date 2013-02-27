package utilits.aspect.observer.create;

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
public class EquipmentCreateObserver extends AbstractCreateObserver<Equipment, Change> {

    public EquipmentCreateObserver(ActionType actionType, ActionService actionService, Long id) {
        super(actionType, ChangeType.values(), actionService, Equipment.class, id);
    }

    @Override
    protected void addChanges(Action action, Collection<Change> changes) {
        for (Change change : changes) {
            action.addChange(change);
        }
    }

    @Override
    protected Change buildChange(IChangeType changeType, String newValue) {
        Change change = new Change();
        change.setType((ChangeType) changeType);
        change.setNewValue(newValue);
        change.setEquipmentId(id);
        return change;
    }
}
