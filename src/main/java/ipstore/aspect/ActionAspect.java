package ipstore.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ipstore.aspect.change.ChangeMode;
import ipstore.aspect.observer.*;
import ipstore.service.ActionService;

import javax.annotation.Resource;

import static ipstore.aspect.change.ChangeMode.IMPORT;
import static ipstore.aspect.change.ChangeMode.UPDATE;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
@Component
@Aspect
public class ActionAspect {

    private static final Logger logger = LoggerFactory.getLogger(ActionAspect.class);

    @Resource(name = "actionService")
    private ActionService actionService;

    @Around("within(ipstore.controller..*) && @annotation(action)")
    public Object observe(ProceedingJoinPoint pjp, Action action) throws Throwable {
        ChangeMode changeMode = action.changeMode();
        ActionType actionType = action.value();
        logger.info("Observing acton with type {}, changeMode {}, changeType {}",
                actionType, action.changeType(), changeMode);
        IObserver observer = changeMode == IMPORT
                ? new ActionWithImportChangesObserver(action, actionService, pjp)
                : changeMode == UPDATE
                ? new ActionWithUpdateChangesObserver(action, actionService, pjp)
                : new ActionObserver(actionType, actionService, pjp);
        return observer.observe();
    }

    @Order
    @AfterReturning(pointcut = "within(ipstore.service.*) && @annotation(action)",
            returning = "id")
    public void afterAction(Long id, Action action) throws Throwable {
        logger.info("Observing acton with type {}, changeMode {}, changeType {}",
                action.value(), action.changeType(), action.changeMode());
        IObserver observer = new ActionWithCreateChangesObserver(action, actionService, id);
        observer.observe();
    }
}
