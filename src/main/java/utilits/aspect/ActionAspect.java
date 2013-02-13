package utilits.aspect;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import utilits.controller.PasswordStatus;
import utilits.controller.Status;
import utilits.entity.Change;
import utilits.entity.Equipment;
import utilits.service.ActionService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
        ActionType actionType = action.type();
        Object result;
        utilits.entity.Action entity;
        if (actionType == ActionType.EQUIPMENT_UPDATE || actionType == ActionType.EQUIPMENT_DELETE) {
            Long equipmentId = findEquipmentIdArgument(pjp.getArgs(), actionType);
            Equipment oldEquipment = actionService.loadEquipment(equipmentId);
            result = pjp.proceed();
            Equipment newEquipment = actionService.loadEquipment(equipmentId);
            entity = buildAction(actionType);
            List<Change> changes = buildCreateEquipmentChanges(oldEquipment, newEquipment);
            for (Change change : changes) {
                entity.addChange(change);
            }
        } else {
            result = pjp.proceed();
            entity = buildAction(actionType);
        }
        actionService.saveAction(entity);
        return result;
    }

    private List<Change> buildCreateEquipmentChanges(Equipment oldEquipment, Equipment newEquipment) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        List<Change> result = new ArrayList<Change>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Long equipmentId = newEquipment.getId();
        for (ChangeType changeType : ChangeType.values()) {
            Object oldValue = PropertyUtils.getProperty(oldEquipment, changeType.getFieldName());
            Object newValue = PropertyUtils.getProperty(newEquipment, changeType.getFieldName());
            if (ObjectUtils.notEqual(oldValue, newValue)) {
                result.add(makeChange(changeType, oldValue, newValue, equipmentId, sdf));
            }
        }
        return result;
    }

    private Change makeChange(ChangeType changeType, Object oldValue, Object newValue, Long equipmentId, SimpleDateFormat sdf) {
        Change change = null;
        if (oldValue instanceof String && newValue instanceof String) {
            String oldString = (String) oldValue;
            String newString = (String) newValue;
            change = buildChange(changeType, oldString, newString, equipmentId);
        } else if (oldValue instanceof Date && newValue instanceof Date) {
            Date oldDate = (Date) oldValue;
            Date newDate = (Date) newValue;
            change = buildChange(changeType, sdf.format(oldDate), sdf.format(newDate), equipmentId);
        } else if (oldValue instanceof PasswordStatus && newValue instanceof PasswordStatus) {
            PasswordStatus oldPasswordStatus = (PasswordStatus) oldValue;
            PasswordStatus newPasswordStatus = (PasswordStatus) newValue;
            change = buildChange(changeType, oldPasswordStatus.name(), newPasswordStatus.name(), equipmentId);
        } else if (oldValue instanceof Status && newValue instanceof Status) {
            Status oldStatus = (Status) oldValue;
            Status newStatus = (Status) newValue;
            change = buildChange(changeType, oldStatus.name(), newStatus.name(), equipmentId);
        }
        return change;
    }

    @Order
    @AfterReturning(pointcut = "within(utilits.service.*) && @annotation(action)",
            returning = "id")
    public void afterAction(Long id, Action action) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ActionType actionType = action.type();
        if (actionType != ActionType.EQUIPMENT_CREATE) {
            throw new IllegalArgumentException("Not supported action type:" + actionType);
        }
        utilits.entity.Action entity = buildAction(actionType);
        Equipment equipment = actionService.loadEquipment(id);
        List<Change> changes = buildCreateEquipmentChanges(equipment);
        for (Change change : changes) {
            entity.addChange(change);
        }
        actionService.saveAction(entity);
    }

    private Long findEquipmentIdArgument(Object[] args, ActionType actionType) {
        Long equipmentId = null;
        try {
            int index = actionType == ActionType.EQUIPMENT_UPDATE ? 2 : 0;
            Object object = args[index];
            if (object instanceof Long) {
                equipmentId = (Long) object;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            //ignored
        }
        return equipmentId;
    }

    private utilits.entity.Action buildAction(ActionType type) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        utilits.entity.Action entity = new utilits.entity.Action();
        entity.setIp(request.getRemoteAddr());
        entity.setHost(request.getRemoteHost());
        entity.setType(type);
        entity.setUserAgent(request.getHeader("user-agent"));
        StringBuffer requestURL = request.getRequestURL();
        String queryParams = request.getQueryString();
        if (StringUtils.isNotEmpty(queryParams)) {
            requestURL.append("?").append(queryParams);
        }
        entity.setRequestURL(requestURL.toString());
        entity.setActionTimestamp(Calendar.getInstance().getTime());
        return entity;
    }

    private List<Change> buildCreateEquipmentChanges(Equipment equipment) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        List<Change> result = new ArrayList<Change>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Long equipmentId = equipment.getId();
        for (ChangeType changeType : ChangeType.values()) {
            Object object = PropertyUtils.getProperty(equipment, changeType.getFieldName());
            Change change = makeChange(object, changeType, equipmentId, sdf);
            if (change != null) {
                result.add(change);
            }
        }
        return result;
    }

    private Change makeChange(Object object, ChangeType changeType, Long equipmentId, SimpleDateFormat sdf) {
        Change change = null;
        if (object instanceof String) {
            String value = (String) object;
            if (StringUtils.isNotEmpty(value)) {
                change = buildChange(changeType, null, value, equipmentId);
            }
        } else if (object instanceof Date) {
            Date value = (Date) object;
            change = buildChange(changeType, null, sdf.format(value), equipmentId);
        } else if (object instanceof PasswordStatus) {
            PasswordStatus passwordStatus = (PasswordStatus) object;
            change = buildChange(changeType, null, passwordStatus.name(), equipmentId);
        } else if (object instanceof Status) {
            Status status = (Status) object;
            change = buildChange(changeType, null, status.name(), equipmentId);
        }
        return change;
    }

    private Change buildChange(ChangeType changeType, String oldValue, String newValue, Long equipmentId) {
        Change change = new Change();
        change.setType(changeType);
        change.setNewValue(newValue);
        change.setEquipmentId(equipmentId);
        if (oldValue != null) {
            change.setOldValue(oldValue);
        }
        return change;
    }
}
