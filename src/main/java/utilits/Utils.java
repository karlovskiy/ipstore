package utilits;

import org.apache.commons.lang3.time.FastDateFormat;
import utilits.controller.ActionFilterForm;

import java.util.Calendar;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
public class Utils {

    public static final String INDEX_ANALYZER_DEFINITION = "indexAnalyzer";

    public static final String QUERY_ANALYZER_DEFINITION = "queryAnalyzer";

    public static final FastDateFormat DATE_TIME_WITH_SECONDS = FastDateFormat.getInstance("dd.MM.yyyy HH:mm:ss");

    public static final ActionFilterForm DEFAULT_ACTION_FILTER = new ActionFilterForm();

    static {
        Calendar calendar = Calendar.getInstance();
        DEFAULT_ACTION_FILTER.setTo(calendar.getTime());
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        DEFAULT_ACTION_FILTER.setFrom(calendar.getTime());
    }

}
