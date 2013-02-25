package utilits.service;

import org.apache.commons.lang3.StringUtils;
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
import utilits.controller.accounts.AccountStatus;
import utilits.entity.Account;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
@Service("accountsService")
@Transactional
public class AccountsService {

    protected static Logger logger = LoggerFactory.getLogger(AccountsService.class);

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public void updateAccount(Long id, Account account) {
        logger.info("updating account with id=" + id);
        Session session = sessionFactory.getCurrentSession();
        Account oldAccount = (Account) session.get(Account.class, id);
        oldAccount.setLogin(account.getLogin());
        oldAccount.setPassword(account.getPassword());
        oldAccount.setClientName(account.getClientName());
        oldAccount.setNumber(account.getNumber());
        oldAccount.setDescription(account.getDescription());
    }

    @Action(type = ActionType.ACCOUNTS_CREATE)
    public Long createAccount(Account account) {
        logger.info("saving new account...");
        Session session = sessionFactory.getCurrentSession();
        account.setStatus(AccountStatus.NORMAL);
        return (Long) session.save(account);
    }

    public void activateAccount(Long id) {
        logger.info("activate account with id=" + id);
        Session session = sessionFactory.getCurrentSession();
        Account account = (Account) session.get(Account.class, id);
        account.setStatus(AccountStatus.NORMAL);
    }

    public void blockAccount(Long id) {
        logger.info("block account with id=" + id);
        Session session = sessionFactory.getCurrentSession();
        Account account = (Account) session.get(Account.class, id);
        account.setStatus(AccountStatus.WARNING);
    }

    public void deleteAccount(Long id) {
        logger.info("delete account with id=" + id);
        Session session = sessionFactory.getCurrentSession();
        Account account = (Account) session.get(Account.class, id);
        account.setStatus(AccountStatus.DELETED);
    }

    @SuppressWarnings("unchecked")
    public List<Account> loadAccounts(String search) {
        logger.debug("Start loading accounts, search string: " + search);
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Account.class);
        if (StringUtils.isNotEmpty(search)) {
            criteria.add(Restrictions.like("login", "%" + search + "%"));
        }
        criteria.add(Restrictions.ne("status", AccountStatus.DELETED));
        return criteria.addOrder(Order.asc("login")).list();
    }

    public Account loadAccount(Long id) {
        logger.info("loading account with id=" + id);
        Session session = sessionFactory.getCurrentSession();
        return (Account) session.get(Account.class, id);
    }

    public Account loadAccount(String login) {
        logger.info("loading account with login=" + login);
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Account.class);
        criteria.add(Restrictions.eq("login", login));
        return (Account) criteria.uniqueResult();
    }

    public ImportResultType<Account> importFile(InputStream is) throws IOException, InvalidFormatException {
        ImportResultType<Account> result = new ImportResultType<Account>();
        Workbook wb = WorkbookFactory.create(is);
        Sheet sheet = wb.getSheetAt(0);
        for (Row row : sheet) {
            Account account = makeAccount(row);
            Session session = sessionFactory.getCurrentSession();
            Account oldAccount = (Account) session.createCriteria(Account.class)
                    .add(Restrictions.eq("login", account.getLogin()))
                    .uniqueResult();
            if (oldAccount != null) {
                result.addExists(oldAccount);
            } else {
                Long id = (Long) session.save(account);
                account.setId(id);
                result.addAdded(account);
            }
        }
        return result;
    }

    private Account makeAccount(Row row) {
        Account account = new Account();
        for (int j = 0; j <= 3; j++) {
            Cell cell = row.getCell(j, Row.CREATE_NULL_AS_BLANK);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            String value = cell.getStringCellValue();
            switch (j) {
                case 0:
                    account.setLogin(value);
                    break;
                case 1:
                    account.setPassword(value);
                    break;
                case 2:
                    account.setClientName(value);
                    break;
            }
        }
        account.setStatus(AccountStatus.NORMAL);
        return account;
    }

}
