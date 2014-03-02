package utilits.hibernate;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.transform.AliasToBeanResultTransformer;
import utilits.aspect.ActionType;
import utilits.aspect.change.ChangeType;

/**
 * Here will be javadoc
 *
 * @author ravenstar
 * @since 4.0, 2/28/14
 */
public class LastChangeResultTransformer extends AliasToBeanResultTransformer {

    public LastChangeResultTransformer(Class resultClass) {
        super(resultClass);
    }

    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        for (int i = 0; i < aliases.length; i++) {
            String alias = aliases[i];
            if ("actionType".equals(alias)) {
                Integer ordinal = (Integer) tuple[i];
                ActionType actionType = ActionType.values()[ordinal];
                tuple[i] = StringUtils.replaceEach(actionType.toString(),
                        new String[]{"EQUIPMENT_", "ACCOUNTS_", "COMMUNIGATE_"},
                        new String[]{StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY});
            }
        }
        return super.transformTuple(tuple, aliases);
    }
}
