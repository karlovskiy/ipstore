package utilits.aspect.observer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import utilits.aspect.ActionType;
import utilits.entity.Action;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Calendar;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
public abstract class AbstractObserver implements IObserver {

    protected final ActionType actionType;

    public AbstractObserver(ActionType actionType) {
        this.actionType = actionType;
    }

    protected Action buildAction() throws UnsupportedEncodingException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Action action = new utilits.entity.Action();
        action.setIp(request.getRemoteAddr());
        action.setHost(request.getRemoteHost());
        action.setType(actionType);
        action.setUserAgent(request.getHeader("user-agent"));
        StringBuffer requestURL = request.getRequestURL();
        String queryParams = request.getQueryString();
        if (StringUtils.isNotEmpty(queryParams)) {
            requestURL.append("?").append(URLDecoder.decode(queryParams, "UTF-8"));
        }
        action.setRequestURL(requestURL.toString());
        action.setActionTimestamp(Calendar.getInstance().getTime());
        action.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        return action;
    }

}
