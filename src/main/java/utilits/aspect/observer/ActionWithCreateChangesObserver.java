package utilits.aspect.observer;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import utilits.aspect.change.ChangeType;
import utilits.aspect.change.IChangeField;
import utilits.entity.Action;
import utilits.entity.Change;
import utilits.entity.IHasId;
import utilits.service.ActionService;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static utilits.Utils.DATE_TIME_WITH_SECONDS;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 2.5.0, 4/9/13
 */
public class ActionWithCreateChangesObserver extends BaseObserver {

    protected final ChangeType changeType;
    protected final ActionService actionService;
    protected Long id;

    public ActionWithCreateChangesObserver(utilits.aspect.Action action, ActionService actionService, Long id) {
        super(action.value());
        this.changeType = action.changeType();
        this.actionService = actionService;
        this.id = id;
    }

    @Override
    public Object observe() throws Throwable {
        Action action = buildAction();
        IHasId entity = actionService.loadEntity(id, changeType.getEntityClazz());
        Collection<Change> changes = buildChanges(entity);
        addChanges(action, changes);
        actionService.saveAction(action);
        return null;
    }

    protected Collection<Change> buildChanges(IHasId entity) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Collection<Change> result = new ArrayList<Change>();
        for (IChangeField field : changeType.getIChangeFields()) {
            Object object = PropertyUtils.getProperty(entity, field.getFieldName());
            Change change = makeChange(object, field);
            if (change != null) {
                result.add(change);
            }
        }
        return result;
    }

    protected void addChanges(Action action, Collection<Change> changes) {
        for (Change change : changes) {
            action.addChange(change);
        }
    }

    private Change makeChange(Object object, IChangeField field) {
        Change change = null;
        if (object instanceof String) {
            String value = (String) object;
            if (StringUtils.isNotEmpty(value)) {
                change = buildChange(field, value);
            }
        } else if (object instanceof Date) {
            Date value = (Date) object;
            change = buildChange(field, DATE_TIME_WITH_SECONDS.format(value));
        } else if (object instanceof Enum) {
            Enum enumType = (Enum) object;
            change = buildChange(field, enumType.name());
        } else if (object instanceof Integer) {
            Integer value = (Integer) object;
            change = buildChange(field, value.toString());
        } else if (object instanceof Boolean) {
            Boolean value = (Boolean) object;
            change = buildChange(field, value.toString());
        }
        return change;
    }

    private Change buildChange(IChangeField field, String newValue) {
        Change change = new Change();
        change.setType(changeType);
        change.setFieldType(field.getFieldType());
        change.setNewValue(newValue);
        change.setEntityId(id);
        return change;
    }
}
