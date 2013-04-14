package utilits.service;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.EntityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utilits.entity.Account;
import utilits.entity.Equipment;

import javax.annotation.Resource;
import java.util.List;

import static utilits.Utils.QUERY_ANALYZER_DEFINITION;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
@Service("searchService")
@Transactional
public class SearchService {

    private static final Logger logger = LoggerFactory.getLogger(SearchService.class);

    private static final String[] EQUIPMENT_INDEX_FIELDS = {"ipAddress", "value", "username", "login",
            "clientName", "placementAddress", "applicationNumber", "description"};

    private static final String[] ACCOUNTS_INDEX_FIELDS = {"login", "clientName", "number", "description"};

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public String rebuildIndex() {
        logger.info("Start rebuilding index...");
        Session session = sessionFactory.getCurrentSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        String message = "Index successfully rebuild.";
        try {
            fullTextSession.createIndexer().startAndWait();
            logger.info(message);
        } catch (Exception e) {
            logger.error("Error while rebuilding database index.", e);
            return "Index rebuilding error.";
        }
        return message;
    }

    @SuppressWarnings("unchecked")
    public List<Equipment> searchEquipment(String search) {
        logger.info("Searching equipment, search=" + search);
        return searchEntities(search, Equipment.class, EQUIPMENT_INDEX_FIELDS);
    }

    @SuppressWarnings("unchecked")
    public List<Account> searchAccounts(String search) {
        logger.info("Searching accounts, search=" + search);
        return searchEntities(search, Account.class, ACCOUNTS_INDEX_FIELDS);
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> searchEntities(String search, Class<T> clazz, String... fields) {
        Session session = sessionFactory.getCurrentSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        EntityContext entityContext = fullTextSession.getSearchFactory()
                .buildQueryBuilder().forEntity(clazz);
        overridesForFields(entityContext, QUERY_ANALYZER_DEFINITION, fields);
        org.apache.lucene.search.Query query = entityContext.get().keyword()
                .onFields(fields)
                .matching(search.toLowerCase()).createQuery();
        return fullTextSession.createFullTextQuery(query, clazz).list();
    }

    private EntityContext overridesForFields(EntityContext entityContext, String analyzer, String... fields) {
        for (String field : fields) {
            entityContext.overridesForField(field, analyzer);
        }
        return entityContext;
    }

}
