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
import utilits.controller.ImportResultType;
import utilits.controller.communigate.CommunigateStatus;
import utilits.entity.CommunigateDomain;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static utilits.aspect.ActionType.COMMUNIGATE_CREATE;
import static utilits.aspect.change.ChangeType.COMMUNIGATE;
import static utilits.aspect.change.ChangeMode.CREATE;

/**
 * User: Sidorov Oleg
 * Date: 25.03.13
 */
@Service("communigateService")
@Transactional
public class CommunigateService {

    protected static Logger logger = LoggerFactory.getLogger(CommunigateService.class);

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;


    public void updateCommunigate(Long id, CommunigateDomain communigateDomain) {
        logger.info("updating communigate domain with id=" + id);
        Session session = sessionFactory.getCurrentSession();
        CommunigateDomain oldCommunigate = (CommunigateDomain) session.get(CommunigateDomain.class, id);
        oldCommunigate.setDomainName(communigateDomain.getDomainName());
        oldCommunigate.setTryPrefix(communigateDomain.getTryPrefix());
        oldCommunigate.setClientName(communigateDomain.getClientName());
        oldCommunigate.setTicket(communigateDomain.getTicket());
        oldCommunigate.setNumberLine(communigateDomain.getNumberLine());
        oldCommunigate.setDiskCapacity(communigateDomain.getDiskCapacity());
        oldCommunigate.setService(communigateDomain.getService());
        oldCommunigate.setContract(communigateDomain.getContract());
        oldCommunigate.setLogin(communigateDomain.getLogin());
        oldCommunigate.setDate(communigateDomain.getDate());
        oldCommunigate.setDescription(communigateDomain.getDescription());

    }

    @Action(value = COMMUNIGATE_CREATE, changeType = COMMUNIGATE, changeMode = CREATE)
    public Long createCommunigate(CommunigateDomain communigateDomain) {
        logger.info("saving new communigate domain...");
        Session session = sessionFactory.getCurrentSession();
        communigateDomain.setStatus(CommunigateStatus.NORMAL);
        return (Long) session.save(communigateDomain);
    }

    @SuppressWarnings("unchecked")
    public List<CommunigateDomain> loadCommunigate() {
        logger.debug("Start loading communigate domains.");
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(CommunigateDomain.class);
        criteria.add(Restrictions.ne("status", CommunigateStatus.DELETED));
        return criteria.addOrder(Order.asc("domainName")).list();
    }

    public CommunigateDomain loadCommunigate(Long id) {
        logger.info("loading communigate domain with id=" + id);
        Session session = sessionFactory.getCurrentSession();
        return (CommunigateDomain) session.get(CommunigateDomain.class, id);
    }

    public CommunigateDomain loadCommunigate(String domainName) {
        logger.info("loading communigate domain with domainName=" + domainName);
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(CommunigateDomain.class);
        criteria.add(Restrictions.eq("domainName", domainName));
        return (CommunigateDomain) criteria.uniqueResult();
    }

    public CommunigateDomain loadCommunigateByTryPrefix(String tryPrefix) {
        logger.info("loading communigate domain with tryPrefix=" + tryPrefix);
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(CommunigateDomain.class);
        criteria.add(Restrictions.eq("tryPrefix", tryPrefix));
        return (CommunigateDomain) criteria.uniqueResult();
    }

    public CommunigateDomain loadCommunigate(String domainName, String tryPrefix) {
        logger.info("loading communigate domain with domainName=" + domainName + ", and tryPrefix=" + tryPrefix);
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(CommunigateDomain.class);
        criteria.add(Restrictions.disjunction()
                .add(Restrictions.eq("domainName", domainName))
                .add(Restrictions.eq("tryPrefix", tryPrefix)));
        return (CommunigateDomain) criteria.uniqueResult();
    }

    public void activateCommunigate(Long id) {
        logger.info("activate account with id=" + id);
        Session session = sessionFactory.getCurrentSession();
        CommunigateDomain communigateDomain = (CommunigateDomain) session.get(CommunigateDomain.class, id);
        communigateDomain.setStatus(CommunigateStatus.NORMAL);
    }

    public void blockCommunigate(Long id) {
        logger.info("block account with id=" + id);
        Session session = sessionFactory.getCurrentSession();
        CommunigateDomain communigateDomain = (CommunigateDomain) session.get(CommunigateDomain.class, id);
        communigateDomain.setStatus(CommunigateStatus.BLOCKED);
    }

    public void deleteCommunigate(Long id) {
        logger.info("delete account with id=" + id);
        Session session = sessionFactory.getCurrentSession();
        CommunigateDomain communigateDomain = (CommunigateDomain) session.get(CommunigateDomain.class, id);
        communigateDomain.setStatus(CommunigateStatus.DELETED);
    }

    public ImportResultType<CommunigateDomain> importFile(InputStream is) throws IOException, InvalidFormatException {
        ImportResultType<CommunigateDomain> result = new ImportResultType<CommunigateDomain>();
        Workbook wb = WorkbookFactory.create(is);
        Sheet sheet = wb.getSheetAt(0);
        int i = 0;
        for (Row row : sheet) {
            i++;
            if (i > 1) {
                CommunigateDomain communigateDomain = makeCommunigate(row);
                Session session = sessionFactory.getCurrentSession();
                CommunigateDomain oldCommunigateDomain = (CommunigateDomain) session.createCriteria(CommunigateDomain.class)
                        .add(Restrictions.disjunction()
                                .add(Restrictions.eq("domainName", communigateDomain.getDomainName()))
                                .add(Restrictions.eq("tryPrefix", communigateDomain.getTryPrefix()))
                        ).uniqueResult();
                if (oldCommunigateDomain != null) {
                    result.addExists(oldCommunigateDomain);
                } else {
                    Long id = (Long) session.save(communigateDomain);
                    communigateDomain.setId(id);
                    result.addAdded(communigateDomain);
                }
            }
        }
        return result;
    }

    private CommunigateDomain makeCommunigate(Row row) {
        CommunigateDomain communigateDomain = new CommunigateDomain();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        for (int j = 0; j <= 10; j++) {
            Cell cell = row.getCell(j, Row.CREATE_NULL_AS_BLANK);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            String value = cell.getStringCellValue();
            switch (j) {
                case 0:
                    communigateDomain.setDomainName(value);
                    break;
                case 1:
                    communigateDomain.setTryPrefix(value);
                    break;
                case 2:
                    communigateDomain.setClientName(value);
                    break;
                case 3:
                    communigateDomain.setTicket(value);
                    break;
                case 4:
                    communigateDomain.setNumberLine(value);
                    break;
                case 5:
                    communigateDomain.setDiskCapacity(value);
                    break;
                case 6:
                    communigateDomain.setService(value);
                    break;
                case 7:
                    communigateDomain.setContract(value);
                    break;
                case 8:
                    communigateDomain.setLogin(value);
                    break;
                case 9:
                    try {
                        communigateDomain.setDate(sdf.parse(value));
                    } catch (ParseException ignored) {
                    }
                    break;
                case 10:
                    communigateDomain.setDescription(value);
                    break;
            }
        }
        communigateDomain.setStatus(CommunigateStatus.NORMAL);
        return communigateDomain;
    }

}
