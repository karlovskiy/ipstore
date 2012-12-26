package utilits.service;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utilits.entity.Equipment;

import javax.annotation.Resource;
import java.util.List;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
@Service("equipmentService")
@Transactional
public class EquipmentService {

    protected static Logger logger = Logger.getLogger("service");

    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;

    public List<Equipment> getEquipment() {
        logger.debug("Start loading equipment...");
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM  Equipment ");
        return query.list();
    }
}
