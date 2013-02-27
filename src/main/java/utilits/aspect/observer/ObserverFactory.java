package utilits.aspect.observer;

import org.aspectj.lang.ProceedingJoinPoint;
import utilits.aspect.ActionType;
import utilits.aspect.observer.create.AccountCreateObserver;
import utilits.aspect.observer.create.AccountImportObserver;
import utilits.aspect.observer.create.EquipmentCreateObserver;
import utilits.aspect.observer.create.EquipmentImportObserver;
import utilits.aspect.observer.update.AccountUpdateObserver;
import utilits.aspect.observer.update.EquipmentUpdateObserver;
import utilits.service.ActionService;

import java.util.Arrays;
import java.util.List;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
public class ObserverFactory {

    private static final List<ActionType> EQUIPMENT_UPDATE_TYPES = Arrays.asList(
            ActionType.EQUIPMENT_UPDATE, ActionType.EQUIPMENT_DELETE, ActionType.EQUIPMENT_ACTIVATE
    );

    private static final List<ActionType> ACCOUNTS_UPDATE_TYPES = Arrays.asList(
            ActionType.ACCOUNTS_UPDATE, ActionType.ACCOUNTS_DELETE, ActionType.ACCOUNTS_ACTIVATE,
            ActionType.ACCOUNTS_UNBLOCK, ActionType.ACCOUNTS_BLOCK);

    public static IObserver getInstance(ActionType actionType, ActionService actionService, Long id) {
        if (actionType == ActionType.ACCOUNTS_CREATE) {
            return new AccountCreateObserver(actionType, actionService, id);
        } else if (actionType == ActionType.EQUIPMENT_CREATE) {
            return new EquipmentCreateObserver(actionType, actionService, id);
        } else {
            throw new IllegalArgumentException("Unsupported action type: " + actionType);
        }
    }

    public static IObserver getInstance(ActionType actionType, ActionService actionService,
                                        ProceedingJoinPoint pjp) {
        if (actionType == null) {
            throw new IllegalArgumentException("ActionType cannot be null");
        } else if (EQUIPMENT_UPDATE_TYPES.contains(actionType)) {
            return new EquipmentUpdateObserver(actionType, pjp, actionService);
        } else if (ACCOUNTS_UPDATE_TYPES.contains(actionType)) {
            return new AccountUpdateObserver(actionType, pjp, actionService);
        } else if (actionType == ActionType.ACCOUNTS_IMPORT) {
            return new AccountImportObserver(actionType, actionService, pjp);
        } else if (actionType == ActionType.EQUIPMENT_IMPORT) {
            return new EquipmentImportObserver(actionType, actionService, pjp);
        } else {
            return new SimpleObserver(actionType, pjp, actionService);
        }
    }
}
