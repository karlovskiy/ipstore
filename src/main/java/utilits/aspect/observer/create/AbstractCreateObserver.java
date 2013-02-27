package utilits.aspect.observer.create;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import utilits.aspect.ActionType;
import utilits.aspect.IChangeType;
import utilits.aspect.observer.AbstractObserver;
import utilits.entity.Action;
import utilits.service.ActionService;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
public abstract class AbstractCreateObserver<E, C> extends AbstractObserver {

    protected final ActionService actionService;

    protected Long id;

    protected final Class<E> clazz;

    private final IChangeType[] changeTypes;

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public AbstractCreateObserver(ActionType actionType, IChangeType[] changeTypes, ActionService actionService,
                                  Class<E> clazz, Long id) {
        super(actionType);
        this.actionService = actionService;
        this.clazz = clazz;
        this.id = id;
        this.changeTypes = changeTypes;
    }

    @Override
    public Object observe() throws Throwable {
        Action action = buildAction();
        E entity = actionService.loadEntity(id, clazz);
        Collection<C> changes = buildChanges(entity);
        addChanges(action, changes);
        actionService.saveAction(action);
        return null;
    }

    protected abstract void addChanges(Action action, Collection<C> changes);

    protected abstract C buildChange(IChangeType changeType, String newValue);

    protected Collection<C> buildChanges(E entity) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Collection<C> result = new ArrayList<C>();
        for (IChangeType changeType : changeTypes) {
            Object object = PropertyUtils.getProperty(entity, changeType.getFieldName());
            C change = makeChange(object, changeType);
            if (change != null) {
                result.add(change);
            }
        }
        return result;
    }

    private C makeChange(Object object, IChangeType changeType) {
        C change = null;
        if (object instanceof String) {
            String value = (String) object;
            if (StringUtils.isNotEmpty(value)) {
                change = buildChange(changeType, value);
            }
        } else if (object instanceof Date) {
            Date value = (Date) object;
            change = buildChange(changeType, sdf.format(value));
        } else if (object instanceof Enum) {
            Enum enumType = (Enum) object;
            change = buildChange(changeType, enumType.name());
        }
        return change;
    }
}
