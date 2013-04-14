package utilits.aspect.observer;

import org.aspectj.lang.ProceedingJoinPoint;
import utilits.aspect.ActionType;
import utilits.entity.Action;
import utilits.service.ActionService;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
public class ActionObserver extends BaseObserver {

    private final ProceedingJoinPoint pjp;
    private final ActionService actionService;

    public ActionObserver(ActionType actionType, ActionService actionService,
                          ProceedingJoinPoint pjp) {
        super(actionType);
        this.pjp = pjp;
        this.actionService = actionService;
    }

    @Override
    public Object observe() throws Throwable {
        Object result = pjp.proceed();
        Action action = buildAction();
        actionService.saveAction(action);
        return result;
    }
}
