package ipstore.service;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ipstore.aspect.change.ChangeType;
import ipstore.controller.accounts.AccountStatus;
import ipstore.controller.communigate.CommunigateStatus;
import ipstore.controller.equipment.PasswordStatus;
import ipstore.controller.equipment.TelnetStatus;
import ipstore.controller.home.*;
import ipstore.entity.Action;
import ipstore.hibernate.LastChangeResultTransformer;

import javax.annotation.Resource;
import java.util.List;

/**
 * Here will be javadoc
 *
 * @author ravenstar
 * @since 4.0, 2/27/14
 */
@Service("homeService")
@Transactional
public class HomeService {

    private static final Logger logger = LoggerFactory.getLogger(HomeService.class);

    private static final int MAX_ROWS = 5;

    private static final String EQUIPMENT_STATUSES =
            "select count(*) as total, " +
                    "sum(case password_status when :oldStatus then 1 else 0 end) as old, " +
                    "sum(case password_status when :needUpdateStatus then 1 else 0 end) as needUpdate, " +
                    "sum(case telnet_status when :warningStatus then 1 else 0 end) as warning, " +
                    "sum(case telnet_status when :timeoutStatus then 1 else 0 end) as timeout from equipment";

    private static final String ACCOUNTS_STATUSES =
            "select count(*) as total, " +
                    "sum(case status when :normalStatus then 1 else 0 end) as normal, " +
                    "sum(case status when :warningStatus then 1 else 0 end) as warning from account";

    private static final String COMMUNIGATE_STATUSES =
            "select count(*) as total, " +
                    "sum(case status when :normalStatus then 1 else 0 end) as normal, " +
                    "sum(case status when :blockedStatus then 1 else 0 end) as blocked from communigate_domain";

    private static final String CHANGES_TOTAL =
            "select count(*) as total, count(c.id) as changes, " +
                    "sum(case c.type when :equipmentType then 1 else 0 end) as equipmentChanges,  " +
                    "sum(case c.type when :accountType then 1 else 0 end) as accountsChanges, " +
                    "sum(case c.type when :communigateType then 1 else 0 end) as communigateChanges, " +
                    "sum(case c.type when :userType then 1 else 0 end) as usersChanges " +
                    "from action a left join change c on a.id = c.action_id";

    private static final String LAST_CHANGED_EQUIPMENT =
            "select distinct a.id as actionId, e.id as entityId, e.ip_address as entityName, a.type as actionType, a.action_timestamp as actionTimestamp, " +
                    "a.username as username " +
                    "from equipment e, change c, action a  where c.entity_id = e.id and c.action_id = a.id " +
                    "order by a.action_timestamp desc";

    private static final String LAST_CHANGED_ACCOUNTS =
            "select distinct a.id as actionId, acc.id as entityId, acc.login as entityName, a.type as actionType, a.action_timestamp as actionTimestamp, " +
                    "a.username as username " +
                    "from account acc, change c, action a  where c.entity_id = acc.id and c.action_id = a.id " +
                    "order by a.action_timestamp desc";

    private static final String LAST_CHANGED_COMMUNIGATE =
            "select distinct a.id as actionId, comm.id as entityId, comm.domain_name as entityName, a.type as actionType, a.action_timestamp as actionTimestamp, " +
                    "a.username as username " +
                    "from communigate_domain comm, change c, action a  where c.entity_id = comm.id and c.action_id = a.id " +
                    "order by a.action_timestamp desc";

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public EquipmentWidget loadEquipmentsWidget() {
        EquipmentWidget equipmentWidget = (EquipmentWidget) sessionFactory.getCurrentSession()
                .createSQLQuery(EQUIPMENT_STATUSES)
                .addScalar("total", StandardBasicTypes.LONG)
                .addScalar("old", StandardBasicTypes.LONG)
                .addScalar("needUpdate", StandardBasicTypes.LONG)
                .addScalar("warning", StandardBasicTypes.LONG)
                .addScalar("timeout", StandardBasicTypes.LONG)
                .setParameter("oldStatus", PasswordStatus.OLD.ordinal())
                .setParameter("needUpdateStatus", PasswordStatus.NEED_UPDATE.ordinal())
                .setParameter("warningStatus", TelnetStatus.WARNING.ordinal())
                .setParameter("timeoutStatus", TelnetStatus.TIMEOUT.ordinal())
                .setResultTransformer(Transformers.aliasToBean(EquipmentWidget.class))
                .uniqueResult();
        equipmentWidget.setLastChanges(loadLastChange(LAST_CHANGED_EQUIPMENT));
        return equipmentWidget;
    }

    public AccountsWidget loadAccountsWidget() {
        AccountsWidget accountsWidget = (AccountsWidget) sessionFactory.getCurrentSession()
                .createSQLQuery(ACCOUNTS_STATUSES)
                .addScalar("total", StandardBasicTypes.LONG)
                .addScalar("normal", StandardBasicTypes.LONG)
                .addScalar("warning", StandardBasicTypes.LONG)
                .setParameter("normalStatus", AccountStatus.NORMAL.ordinal())
                .setParameter("warningStatus", AccountStatus.WARNING.ordinal())
                .setResultTransformer(Transformers.aliasToBean(AccountsWidget.class))
                .uniqueResult();
        accountsWidget.setLastChanges(loadLastChange(LAST_CHANGED_ACCOUNTS));
        return accountsWidget;
    }

    public CommunigateWidget loadCommunigateWidget() {
        CommunigateWidget communigateWidget = (CommunigateWidget) sessionFactory.getCurrentSession()
                .createSQLQuery(COMMUNIGATE_STATUSES)
                .addScalar("total", StandardBasicTypes.LONG)
                .addScalar("normal", StandardBasicTypes.LONG)
                .addScalar("blocked", StandardBasicTypes.LONG)
                .setParameter("normalStatus", CommunigateStatus.NORMAL.ordinal())
                .setParameter("blockedStatus", CommunigateStatus.BLOCKED.ordinal())
                .setResultTransformer(Transformers.aliasToBean(CommunigateWidget.class))
                .uniqueResult();
        communigateWidget.setLastChanges(loadLastChange(LAST_CHANGED_COMMUNIGATE));
        return communigateWidget;
    }

    public ActionsWidget loadActionsWidget() {
        ActionsWidget actionsWidget = (ActionsWidget) sessionFactory.getCurrentSession()
                .createSQLQuery(CHANGES_TOTAL)
                .addScalar("total", StandardBasicTypes.LONG)
                .addScalar("changes", StandardBasicTypes.LONG)
                .addScalar("equipmentChanges", StandardBasicTypes.LONG)
                .addScalar("accountsChanges", StandardBasicTypes.LONG)
                .addScalar("communigateChanges", StandardBasicTypes.LONG)
                .addScalar("usersChanges", StandardBasicTypes.LONG)
                .setParameter("equipmentType", ChangeType.EQUIPMENT.ordinal())
                .setParameter("accountType", ChangeType.ACCOUNTS.ordinal())
                .setParameter("communigateType", ChangeType.COMMUNIGATE.ordinal())
                .setParameter("userType", ChangeType.USERS.ordinal())
                .setResultTransformer(Transformers.aliasToBean(ActionsWidget.class))
                .uniqueResult();
        @SuppressWarnings("unchecked")
        List<Action> lastActions = sessionFactory.getCurrentSession().createCriteria(Action.class)
                .add(Restrictions.ne("username", SecurityContextHolder.getContext().getAuthentication().getName()))
                .add(Restrictions.ne("username", "anonymousUser"))
                .addOrder(Order.desc("actionTimestamp"))
                .setMaxResults(MAX_ROWS)
                .list();
        actionsWidget.setLastActions(lastActions);
        return actionsWidget;
    }

    @SuppressWarnings("unchecked")
    private List<LastChange> loadLastChange(String query) {
        return sessionFactory.getCurrentSession()
                .createSQLQuery(query)
                .addScalar("actionId", StandardBasicTypes.LONG)
                .addScalar("entityId", StandardBasicTypes.LONG)
                .addScalar("entityName", StandardBasicTypes.STRING)
                .addScalar("actionType", StandardBasicTypes.INTEGER)
                .addScalar("actionTimestamp", StandardBasicTypes.TIMESTAMP)
                .addScalar("username", StandardBasicTypes.STRING)
                .setResultTransformer(new LastChangeResultTransformer(LastChange.class))
                .setMaxResults(MAX_ROWS)
                .list();
    }

}
