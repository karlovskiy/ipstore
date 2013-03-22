package utilits.service;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utilits.aspect.Action;
import utilits.aspect.ActionType;
import utilits.controller.ImportResultType;
import utilits.controller.Communigate.CommunigateStatus;
import utilits.entity.CommunigateClass5;


import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Sidorov Oleg
 * Date: 22.03.13
 */
@Service("communigateService")
public class CommunigateService {

    protected static Logger logger = LoggerFactory.getLogger(AccountsService.class);

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;


    // import
    public void updateCommunigate(Long id, CommunigateClass5 communigateClass5) {
        logger.info("updating communigate with id=" + id);
        Session session = sessionFactory.getCurrentSession();
        CommunigateClass5 oldCommunigateClass5 = (CommunigateClass5) session.get(CommunigateClass5.class, id);
        oldCommunigateClass5.setDomainName(communigateClass5.getDomainName());
        oldCommunigateClass5.setPrefixTry(communigateClass5.getPrefixTry());
        oldCommunigateClass5.setClientName(communigateClass5.getClientName());
        oldCommunigateClass5.setTiketNumber(communigateClass5.getTiketNumber());
        oldCommunigateClass5.setVolLine(communigateClass5.getVolLine());
        oldCommunigateClass5.setVolSpace(communigateClass5.getVolSpace());
        oldCommunigateClass5.setService(communigateClass5.getService());
        oldCommunigateClass5.setContract(communigateClass5.getContract());
        oldCommunigateClass5.setLogin(communigateClass5.getLogin());
        oldCommunigateClass5.setDescription(communigateClass5.getDescription());
    }

    @Action(type = ActionType.COMMUNIGATE_CREATE)
    public Long createCommunigate(CommunigateClass5 communigateClass5) {
        logger.info("saving new account...");
        Session session = sessionFactory.getCurrentSession();
        communigateClass5.setStatus(CommunigateStatus.NORMAL);
        return (Long) session.save(communigateClass5);
    }

    public void activateCommunigate(Long id) {
        logger.info("activate account with id=" + id);
        Session session = sessionFactory.getCurrentSession();
        CommunigateClass5 communigateClass5 = (CommunigateClass5) session.get(CommunigateClass5.class, id);
        communigateClass5.setStatus(CommunigateStatus.NORMAL);
    }

    public void blockCommunigate(Long id) {
        logger.info("block account with id=" + id);
        Session session = sessionFactory.getCurrentSession();
        CommunigateClass5 communigateClass5 = (CommunigateClass5) session.get(CommunigateClass5.class, id);
        communigateClass5.setStatus(CommunigateStatus.WARNING);
    }

    public void deleteCommunigate(Long id) {
        logger.info("delete account with id=" + id);
        Session session = sessionFactory.getCurrentSession();
        CommunigateClass5 communigateClass5 = (CommunigateClass5) session.get(CommunigateClass5.class, id);
        communigateClass5.setStatus(CommunigateStatus.DELETED);
    }

    @SuppressWarnings("unchecked")
    public List<CommunigateClass5> loadCommunigate() {
        logger.debug("Start loading accounts.");
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(CommunigateClass5.class);
        criteria.add(Restrictions.ne("status", CommunigateStatus.DELETED));
        return criteria.addOrder(Order.asc("domainName")).list();
    }

    public CommunigateClass5 loadAccount(Long id) {
        logger.info("loading account with id=" + id);
        Session session = sessionFactory.getCurrentSession();
        return (CommunigateClass5) session.get(CommunigateClass5.class, id);
    }

    public CommunigateClass5 loadAccount(String domainName) {
        logger.info("loading account with login=" + domainName);
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(CommunigateClass5.class);
        criteria.add(Restrictions.eq("domainName", domainName));
        return (CommunigateClass5) criteria.uniqueResult();
    }

    public ImportResultType<CommunigateClass5> importFile(InputStream is) throws IOException, InvalidFormatException {
        ImportResultType<CommunigateClass5> result = new ImportResultType<CommunigateClass5>();
        Workbook wb = WorkbookFactory.create(is);
        Sheet sheet = wb.getSheetAt(0);
        for (Row row : sheet) {
            CommunigateClass5 communigateClass5 = makeCommunigate(row);
            Session session = sessionFactory.getCurrentSession();
            CommunigateClass5 oldCommunigate = (CommunigateClass5) session.createCriteria(CommunigateClass5.class)
                    .add(Restrictions.eq("domainName", communigateClass5.getDomainName()))
                    .uniqueResult();
            if (oldCommunigate != null) {
                result.addExists(oldCommunigate);
            } else {
                Long id = (Long) session.save(communigateClass5);
                communigateClass5.setId(id);
                result.addAdded(communigateClass5);
            }
        }
        return result;
    }

    private CommunigateClass5 makeCommunigate(Row row) {
        CommunigateClass5 communigateClass5 = new CommunigateClass5();
        for (int j = 0; j <= 9; j++) {
            Cell cell = row.getCell(j, Row.CREATE_NULL_AS_BLANK);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            String value = cell.getStringCellValue();
            switch (j) {
                case 0:
                    communigateClass5.setDomainName(value);
                    break;
                case 1:
                    communigateClass5.setPrefixTry(value);
                    break;
                case 2:
                    communigateClass5.setClientName(value);
                    break;
                case 3:
                    communigateClass5.setTiketNumber(value);
                    break;
                case 4:
                    communigateClass5.setVolLine(value);
                    break;
                case 5:
                    communigateClass5.setVolSpace(value);
                    break;
                case 6:
                    communigateClass5.setService(value);
                    break;
                case 7:
                    communigateClass5.setContract(value);
                    break;
                case 8:
                    communigateClass5.setLogin(value);
                    break;
                case 9:
                    communigateClass5.setDescription(value);
                    break;
            }
        }
        communigateClass5.setStatus(CommunigateStatus.NORMAL);
        return communigateClass5;
    }





}
