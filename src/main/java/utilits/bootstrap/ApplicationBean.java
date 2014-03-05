package utilits.bootstrap;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;
import utilits.controller.accounts.AccountStatus;
import utilits.controller.communigate.CommunigateStatus;
import utilits.controller.equipment.PasswordStatus;
import utilits.controller.equipment.Status;
import utilits.controller.equipment.TelnetStatus;
import utilits.controller.users.UserStatus;
import utilits.menu.Menu;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Here will be javadoc
 *
 * @author ravenstar
 * @since 4.0, 2/19/14
 */
@Component("application")
@Scope("singleton")
public class ApplicationBean {

    private static final Map<Object, BootstrapClass> bootstrapStyleClasses = Collections.unmodifiableMap(
            new HashMap<Object, BootstrapClass>() {
                {
                    put(PasswordStatus.NEED_UPDATE, BootstrapClass.DANGER);
                    put(PasswordStatus.NEW, BootstrapClass.SUCCESS);
                    put(PasswordStatus.OLD, BootstrapClass.WARNING);
                    put(Status.DELETED, BootstrapClass.DEFAULT);
                    put(Status.ACTIVE_NO_EXPIRED, BootstrapClass.WARNING);
                    put(TelnetStatus.OK, BootstrapClass.SUCCESS);
                    put(TelnetStatus.WARNING, BootstrapClass.DANGER);
                    put(TelnetStatus.TIMEOUT, BootstrapClass.WARNING);
                    put(TelnetStatus.IGNORED, BootstrapClass.DEFAULT);
                    put(AccountStatus.NORMAL, BootstrapClass.SUCCESS);
                    put(AccountStatus.WARNING, BootstrapClass.DANGER);
                    put(AccountStatus.DELETED, BootstrapClass.DEFAULT);
                    put(CommunigateStatus.NORMAL, BootstrapClass.SUCCESS);
                    put(CommunigateStatus.BLOCKED, BootstrapClass.DANGER);
                    put(CommunigateStatus.DELETED, BootstrapClass.DEFAULT);
                    put(UserStatus.ACTIVE, BootstrapClass.SUCCESS);
                    put(UserStatus.BLOCKED, BootstrapClass.DEFAULT);
                }
            });

    private
    @Value("${application.version}")
    String version;

    public String checkMenuActive(String menu) {
        Menu checkMenu = Menu.valueOf(menu);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (checkMenu.checkMapping((String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE))) {
            return "active";
        }
        return "";
    }

    public String labelClass(Object status) {
        BootstrapClass styleClass = bootstrapStyleClasses.get(status);
        return StringUtils.defaultString(styleClass.getLabelClass());
    }

    public String alertClass(Object status) {
        BootstrapClass styleClass = bootstrapStyleClasses.get(status);
        return StringUtils.defaultString(styleClass.getAlertClass());
    }

    public String textClass(Object status) {
        BootstrapClass styleClass = bootstrapStyleClasses.get(status);
        return StringUtils.defaultString(styleClass.getTextClass());
    }

    public String rowClass(Object status) {
        BootstrapClass styleClass = bootstrapStyleClasses.get(status);
        return StringUtils.defaultString(styleClass.getRowClass());
    }

    public String getVersion() {
        return version;
    }
}
