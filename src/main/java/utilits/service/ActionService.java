package utilits.service;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utilits.entity.Action;

import javax.annotation.Resource;
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
    public List<Action> loadActions() {
        logger.debug("Start loading actions.");
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Action").list();
    }

}
