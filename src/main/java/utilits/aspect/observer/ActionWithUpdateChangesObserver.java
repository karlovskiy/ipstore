package utilits.aspect.observer;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
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
 * @since 2.5.0, 4/10/13
 */
public class ActionWithUpdateChangesObserver extends BaseObserver {

    private final ActionService actionService;
    private final ProceedingJoinPoint pjp;
    private final ChangeType changeType;

    public ActionWithUpdateChangesObserver(utilits.aspect.Action action, ActionService actionService, ProceedingJoinPoint pjp) {
        super(action.value());
        this.actionService = actionService;
        this.pjp = pjp;
        this.changeType = action.changeType();
    }

    @Override
    public Object observe() throws Throwable {
        Long id = findIdArgument();
        IHasId oldEntity = actionService.loadEntity(id, changeType.getEntityClazz());
        Object result = pjp.proceed();
        IHasId newEntity = actionService.loadEntity(id, changeType.getEntityClazz());
        Collection<Change> changes = buildChanges(oldEntity, newEntity);
        Action action = buildAction();
        for (Change change : changes) {
            action.addChange(change);
        }
        actionService.saveAction(action);
        return result;
    }

    private Collection<Change> buildChanges(IHasId oldEntity, IHasId newEntity) throws IllegalAccessException,
            NoSuchMethodException, InvocationTargetException {
        Collection<Change> result = new ArrayList<Change>();
        Long id = newEntity.getId();
        for (IChangeField field : changeType.getIChangeFields()) {
            Object oldValue = PropertyUtils.getProperty(oldEntity, field.getFieldName());
            Object newValue = PropertyUtils.getProperty(newEntity, field.getFieldName());
            if (ObjectUtils.notEqual(oldValue, newValue)) {
                result.add(makeChange(field, oldValue, newValue, id));
            }
        }
        return result;
    }

    private Change makeChange(IChangeField field, Object oldValue, Object newValue, Long id) {
        Change change = null;
        if (oldValue instanceof String || newValue instanceof String) {
            change = buildChange(field, (String) oldValue, (String) newValue, id);
        } else if (oldValue instanceof Date && newValue instanceof Date) {
            Date oldDate = (Date) oldValue;
            Date newDate = (Date) newValue;
            change = buildChange(field, DATE_TIME_WITH_SECONDS.format(oldDate), DATE_TIME_WITH_SECONDS.format(newDate), id);
        } else if (oldValue instanceof Enum && newValue instanceof Enum) {
            Enum oldEnumType = (Enum) oldValue;
            Enum newEnumType = (Enum) newValue;
            change = buildChange(field, oldEnumType.name(), newEnumType.name(), id);
        } else if (oldValue instanceof Integer && newValue instanceof Integer) {
            Integer oldInteger = (Integer) oldValue;
            Integer newInteger = (Integer) newValue;
            change = buildChange(field, oldInteger.toString(), newInteger.toString(), id);
        } else if (oldValue instanceof Boolean && newValue instanceof Boolean) {
            Boolean oldBoolean = (Boolean) oldValue;
            Boolean newBoolean = (Boolean) newValue;
            change = buildChange(field, oldBoolean.toString(), newBoolean.toString(), id);
        }
        return change;
    }

    private Change buildChange(IChangeField field, String oldValue, String newValue, Long id) {
        Change change = new Change();
        change.setType(changeType);
        change.setNewValue(newValue);
        change.setFieldType(field.getFieldType());
        change.setEntityId(id);
        change.setOldValue(oldValue);
        return change;
    }

    private Long findIdArgument() {
        Object[] args = pjp.getArgs();
        try {
            Object object = args[0];
            if (object instanceof Long) {
                return (Long) object;
            } else {
                try {
                    object = args[2];
                    if (object instanceof Long) {
                        return (Long) object;
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
        throw new IllegalArgumentException("Cannot find id argument");
    }
}
