package utilits;

import org.apache.commons.lang3.time.FastDateFormat;
import utilits.aspect.ActionType;
import utilits.controller.ActionFilterForm;
import utilits.controller.actions.ActionsForm;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
public class Utils {

    public static final String INDEX_ANALYZER_DEFINITION = "indexAnalyzer";

    public static final String QUERY_ANALYZER_DEFINITION = "queryAnalyzer";

    public static final String ALL = "ALL";

    public static final FastDateFormat DATE_TIME_WITH_SECONDS = FastDateFormat.getInstance("dd.MM.yyyy HH:mm:ss");

    public static final ActionFilterForm DEFAULT_ACTION_FILTER = new ActionFilterForm();

    public static final ActionsForm DEFAULT_ACTIONS_FORM = new ActionsForm();

    public static final Map<String, String> ACTIONS_TYPES = new LinkedHashMap<String, String>();

    static {
        Calendar calendar = Calendar.getInstance();
        Date to = calendar.getTime();
        DEFAULT_ACTION_FILTER.setTo(to);
        DEFAULT_ACTIONS_FORM.setTo(to);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date from = calendar.getTime();
        DEFAULT_ACTION_FILTER.setFrom(from);
        DEFAULT_ACTIONS_FORM.setFrom(from);
        DEFAULT_ACTIONS_FORM.setActionType(ALL);
        ACTIONS_TYPES.put(ALL, ALL);
        for (ActionType actionType : ActionType.values()) {
            String actionTypeName = actionType.name();
            ACTIONS_TYPES.put(actionTypeName, actionTypeName);
        }
    }

}
