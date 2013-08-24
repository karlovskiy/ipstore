package utilits.service;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utilits.controller.capacity.CapacityNumberStatus;
import utilits.entity.capacity.CapacityNumber;
import utilits.entity.capacity.CapacityType;

import javax.annotation.Resource;
import java.util.*;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 3.0, 8/20/13
 */
@Service("capacityService")
@Transactional
public class CapacityService {
    private static final Logger logger = LoggerFactory.getLogger(CapacityService.class);
    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public Map<String, String> loadAllCapacityNames() {
        logger.info("load all capacity names");
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(CapacityType.class);
        criteria.addOrder(Order.asc("name"));
        @SuppressWarnings("unchecked")
        List<CapacityType> types = criteria.list();
        logger.info("all capacity names loaded, count=" + types.size());
        Map<String, String> result = new LinkedHashMap<String, String>();
        result.put("ALL", "ALL");
        for (CapacityType capacityType : types) {
            result.put(String.valueOf(capacityType.getId()), capacityType.getName());
        }
        return result;
    }

    public Long loadExistsCapacity(String name) {
        logger.info("load exists capacity with name{}", name);
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(CapacityType.class);
        criteria.add(Restrictions.eq("name", name));
        CapacityType capacityType = (CapacityType) criteria.uniqueResult();
        return capacityType != null ? capacityType.getId() : null;
    }

    public Long createCapacityType(CapacityType capacityType) {
        logger.info("saving new capacity type...");
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.save(capacityType);
    }

    public CapacityType loadCapacityType(Long id) {
        logger.info("load capacity with id={}", id);
        Session session = sessionFactory.getCurrentSession();
        return (CapacityType) session.get(CapacityType.class, id);
    }

    public Long loadExistsCapacityNumber(String number) {
        logger.info("load exists capacity number {}", number);
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(CapacityNumber.class);
        criteria.add(Restrictions.eq("number", number));
        CapacityNumber capacityType = (CapacityNumber) criteria.uniqueResult();
        return capacityType != null ? capacityType.getId() : null;
    }

    public Long createCapacityNumber(CapacityNumber capacityNumber, Long id) {
        logger.info("saving new capacity number...");
        Session session = sessionFactory.getCurrentSession();
        capacityNumber.setLscTimestamp(new Date());
        capacityNumber.setLscUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        capacityNumber.setStatus(CapacityNumberStatus.FREE);
        CapacityType capacityType = (CapacityType) session.load(CapacityType.class, id);
        capacityNumber.setType(capacityType);
        return (Long) session.save(capacityNumber);
    }

    public CapacityNumber loadCapacityNumber(Long id) {
        logger.info("load capacity number with id={}", id);
        Session session = sessionFactory.getCurrentSession();
        CapacityNumber capacityNumber = (CapacityNumber) session.get(CapacityNumber.class, id);
        Hibernate.initialize(capacityNumber.getType());
        return capacityNumber;
    }

    public List<CapacityType> loadAllCapacityTypesWithNumbers() {
        logger.info("load all capacity types");
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(CapacityType.class);
        criteria.addOrder(Order.asc("name"));
        @SuppressWarnings("unchecked")
        List<CapacityType> result = criteria.list();
        for (CapacityType capacityType : result) {
            Hibernate.initialize(capacityType.getNumbers());
        }
        return result;
    }

    public List<CapacityType> loadCapacityTypeWithNumbers(Long id) {
        logger.info("load capacity with id={}", id);
        Session session = sessionFactory.getCurrentSession();
        CapacityType capacityType = (CapacityType) session.get(CapacityType.class, id);
        Hibernate.initialize(capacityType.getNumbers());
        List<CapacityType> result = new ArrayList<CapacityType>(1);
        result.add(capacityType);
        return result;
    }

    public void updateCapacityType(Long id, CapacityType capacityType) {
        logger.info("updating capacity with id=" + id);
        Session session = sessionFactory.getCurrentSession();
        CapacityType oldCapacityType = (CapacityType) session.get(CapacityType.class, id);
        oldCapacityType.setName(capacityType.getName());
    }

    public void updateCapacityNumber(Long id, CapacityNumber capacityNumber) {
        logger.info("updating capacity number with id=" + id);
        Session session = sessionFactory.getCurrentSession();
        CapacityNumber oldCapacityNumber = (CapacityNumber) session.get(CapacityNumber.class, id);
        oldCapacityNumber.setAddress(capacityNumber.getAddress());
        oldCapacityNumber.setAdmittanceDate(capacityNumber.getAdmittanceDate());
        oldCapacityNumber.setApplication(capacityNumber.getApplication());
        oldCapacityNumber.setClientName(capacityNumber.getClientName());
        oldCapacityNumber.setLinesCount(capacityNumber.getLinesCount());
        oldCapacityNumber.setComments(capacityNumber.getComments());
        oldCapacityNumber.setNumber(capacityNumber.getNumber());
    }

    public void updateCapacityNumberStatus(Long id, CapacityNumberStatus status) {
        logger.info("updating capacity number with id=" + id + " to status " + status.name());
        Session session = sessionFactory.getCurrentSession();
        CapacityNumber capacityNumber = (CapacityNumber) session.get(CapacityNumber.class, id);
        capacityNumber.setStatus(status);
        capacityNumber.setLscTimestamp(new Date());
        capacityNumber.setLscUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
