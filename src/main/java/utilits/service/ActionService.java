package utilits.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utilits.controller.ActionFilterForm;
import utilits.entity.Action;

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
    protected static Logger logger = Logger.getLogger(ActionService.class);

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public void saveAction(Action action) {
        Session session = sessionFactory.getCurrentSession();
        session.save(action);
    }

    @SuppressWarnings("unchecked")
    public List<Action> loadActions(ActionFilterForm filter) {
        logger.info("start loading actions, filter: " + filter);
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Action.class);
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
        String host = filter.getHost();
        if (StringUtils.isNotEmpty(host)) {
            criteria.add(Restrictions.like("host", "%" + host + "%"));
        }
        return criteria.addOrder(Order.asc("actionTimestamp")).list();
    }

}
