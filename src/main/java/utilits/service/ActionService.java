package utilits.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utilits.controller.ActionFilterForm;
import utilits.entity.Action;
import utilits.entity.Change;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
@Service("actionService")
@Transactional
public class ActionService {

    protected static Logger logger = LoggerFactory.getLogger(ActionService.class);

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public void saveAction(Action action) {
        Session session = sessionFactory.getCurrentSession();
        session.save(action);
    }

    @SuppressWarnings("unchecked")
    public <T> T loadEntity(Long id, Class<T> clazz) {
        Session session = sessionFactory.getCurrentSession();
        return (T) session.get(clazz, id);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> loadEntities(Class<T> clazz) {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(clazz).list();
    }

    @SuppressWarnings("unchecked")
    public List<Action> loadActions(ActionFilterForm filter) {
        logger.info("start loading actions, filter: " + filter);
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Action.class);
        return makeCriteria(criteria, filter).list();
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> loadChanges(ActionFilterForm filter, Class<T> clazz) {
        logger.info("start loading changes, filter: " + filter);
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(clazz).createCriteria("action");
        return makeCriteria(criteria, filter).list();
    }

    private Criteria makeCriteria(Criteria criteria, ActionFilterForm filter) {
        Date from = filter.getFrom();
        if (from != null) {
            criteria.add(Restrictions.ge("actionTimestamp", from));
        }
        Date to = filter.getTo();
        if (to != null) {
            Calendar calendar = Calendar.getInstance();
            Date now = calendar.getTime();
            if (DateUtils.isSameDay(to, now)) {
                to = now;
            } else {
                calendar.setTime(to);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                to = calendar.getTime();
            }
            criteria.add(Restrictions.le("actionTimestamp", to));
        }
        String ip = filter.getIp();
        if (StringUtils.isNotEmpty(ip)) {
            criteria.add(Restrictions.like("ip", "%" + ip + "%"));
        }
        return criteria.addOrder(Order.asc("actionTimestamp"));
    }

}
