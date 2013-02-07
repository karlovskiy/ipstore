package utilits.aspect;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
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
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        utilits.entity.Action entity = new utilits.entity.Action();
        entity.setIp(request.getRemoteAddr());
        entity.setHost(request.getRemoteHost());
        entity.setType(action.type());
        entity.setUserAgent(request.getHeader("user-agent"));
        StringBuffer requestURL = request.getRequestURL();
        String queryParams = request.getQueryString();
        if (StringUtils.isNotEmpty(queryParams)) {
            requestURL.append("?").append(queryParams);
        }
        entity.setRequestURL(requestURL.toString());
        entity.setActionTimestamp(Calendar.getInstance().getTime());
        actionService.saveAction(entity);
        return result;
    }

}
