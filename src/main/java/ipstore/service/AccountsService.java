package ipstore.service;

import org.apache.commons.lang.StringUtils;
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
import ipstore.aspect.Action;
import ipstore.controller.ImportResultType;
import ipstore.controller.accounts.AccountStatus;
import ipstore.entity.Account;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static ipstore.aspect.ActionType.ACCOUNTS_CREATE;
import static ipstore.aspect.change.ChangeType.ACCOUNTS;
import static ipstore.aspect.change.ChangeMode.CREATE;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
@Service("accountsService")
@Transactional
public class AccountsService {

    private static final Logger logger = LoggerFactory.getLogger(AccountsService.class);

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

    @Action(value = ACCOUNTS_CREATE, changeType = ACCOUNTS, changeMode = CREATE)
    public Long createAccount(Account account) {
        logger.info("saving new account...");
        Session session = sessionFactory.getCurrentSession();
        account.setStatus(AccountStatus.NORMAL);
        return (Long) session.save(account);
    }

    public Account activateAccount(Long id) {
        logger.info("activate account with id=" + id);
        Session session = sessionFactory.getCurrentSession();
        Account account = (Account) session.get(Account.class, id);
        account.setStatus(AccountStatus.NORMAL);
        return account;
    }

    public Account blockAccount(Long id) {
        logger.info("block account with id=" + id);
        Session session = sessionFactory.getCurrentSession();
        Account account = (Account) session.get(Account.class, id);
        account.setStatus(AccountStatus.WARNING);
        return account;
    }

    public Account deleteAccount(Long id) {
        logger.info("delete account with id=" + id);
        Session session = sessionFactory.getCurrentSession();
        Account account = (Account) session.get(Account.class, id);
        account.setStatus(AccountStatus.DELETED);
        return account;
    }

    @SuppressWarnings("unchecked")
    public List<Account> loadAccounts() {
        return loadAccounts(null);
    }

    @SuppressWarnings("unchecked")
    public List<Account> loadAccounts(String status) {
        logger.debug("Start loading accounts.");
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Account.class);
        if (StringUtils.isEmpty(status)) {
            criteria.add(Restrictions.ne("status", AccountStatus.DELETED));
        } else {
            criteria.add(Restrictions.eq("status", AccountStatus.valueOf(status)));
        }
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
        int i = 0;
        for (Row row : sheet) {
            i++;
            if (i > 1) {
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
        }
        return result;
    }

    private Account makeAccount(Row row) {
        Account account = new Account();
        for (int j = 0; j <= 4; j++) {
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
                case 3:
                    account.setNumber(value);
                case 4:
                    account.setDescription(value);
            }
        }
        account.setStatus(AccountStatus.NORMAL);
        return account;
    }

}
