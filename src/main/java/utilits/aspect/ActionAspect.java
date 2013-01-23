package utilits.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import utilits.service.ActionService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

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

    @Around("within(utilits.controller.*) && @annotation(action)")
    public Object observe(ProceedingJoinPoint pjp, Action action) throws Throwable {
        Object result = pjp.proceed();
        HttpServletRequest request = null;
        for (Object arg : pjp.getArgs()) {
            if (arg instanceof HttpServletRequest) {
                request = (HttpServletRequest) arg;
                break;
            }
        }
        if (request == null) {
            logger.warn("Ignoring aspect " + ActionAspect.class + " parameter " + HttpServletRequest.class + " not found.");
        } else {
            utilits.entity.Action entity = new utilits.entity.Action();
            entity.setIp(request.getRemoteAddr());
            entity.setHost(request.getRemoteHost());
            entity.setType(action.type());
            entity.setUserAgent(request.getHeader("user-agent"));
            entity.setActionTimestamp(Calendar.getInstance().getTime());
            actionService.saveAction(entity);
        }
        return result;
    }

}
