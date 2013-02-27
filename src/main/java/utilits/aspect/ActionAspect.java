package utilits.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import utilits.aspect.observer.IObserver;
import utilits.aspect.observer.ObserverFactory;
import utilits.service.ActionService;

import javax.annotation.Resource;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
@Component
@Aspect
public class ActionAspect {

    private static final Logger logger = Logger.getLogger(ActionAspect.class);

    @Resource(name = "actionService")
    private ActionService actionService;

    @Around("within(utilits.controller..*) && @annotation(action)")
    public Object observe(ProceedingJoinPoint pjp, Action action) throws Throwable {
        IObserver observer = ObserverFactory.getInstance(action.type(), actionService, pjp);
        return observer.observe();
    }

    @Order
    @AfterReturning(pointcut = "within(utilits.service.*) && @annotation(action)",
            returning = "id")
    public void afterAction(Long id, Action action) throws Throwable {
        IObserver observer = ObserverFactory.getInstance(action.type(), actionService, id);
        observer.observe();
    }
}
