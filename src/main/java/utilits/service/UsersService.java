package utilits.service;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utilits.controller.users.UserStatus;
import utilits.entity.User;

import javax.annotation.Resource;
import java.util.List;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 2.4, 3/28/13
 */
@Service("userService")
@Transactional
public class UsersService {

    private static final Logger logger = LoggerFactory.getLogger(UsersService.class);

    @Value("${user.temporary_password}")
    private String defaultPassword;

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    @Resource(name = "passwordEncoder")
    private PasswordEncoder passwordEncoder;

    @SuppressWarnings("unchecked")
    public List<User> loadUsers() {
        logger.debug("Start loading users.");
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(User.class);
        return criteria.list();
    }

    public User loadUser(Long id) {
        logger.info("Start loading user with id=" + id);
        Session session = sessionFactory.getCurrentSession();
        return (User) session.get(User.class, id);
    }

    public User loadUser(String username) {
        logger.info("Start loading user with username=" + username);
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("username", username));
        return (User) criteria.uniqueResult();
    }

    public void blockUser(Long id) {
        logger.info("Start blocking user with id=" + id);
        Session session = sessionFactory.getCurrentSession();
        User user = (User) session.get(User.class, id);
        user.setUserStatus(UserStatus.BLOCKED);
    }

    public void unblockUser(Long id) {
        logger.info("Start unblocking user with id=" + id);
        Session session = sessionFactory.getCurrentSession();
        User user = (User) session.get(User.class, id);
        user.setUserStatus(UserStatus.ACTIVE);
    }

    public void resetUserPassword(Long id) {
        logger.info("Start resetting password of user with id=" + id);
        Session session = sessionFactory.getCurrentSession();
        User user = (User) session.get(User.class, id);
        user.setPassword(passwordEncoder.encodePassword(defaultPassword, user.getUsername()));
        user.setCredentialsNonExpired(false);
    }

    public Long createUser(User user) {
        logger.info("Start saving new user...");
        Session session = sessionFactory.getCurrentSession();
        user.setUserStatus(UserStatus.ACTIVE);
        user.setCredentialsNonExpired(false);
        user.setPassword(passwordEncoder.encodePassword(defaultPassword, user.getUsername()));
        return (Long) session.save(user);
    }

    public void updateUser(Long id, User user) {
        logger.info("Start updating user with id=" + id);
        Session session = sessionFactory.getCurrentSession();
        User oldUser = (User) session.get(User.class, id);
        oldUser.setUsername(user.getUsername());
        oldUser.setPassword(user.getPassword());
        oldUser.setFirstName(user.getFirstName());
        oldUser.setLastName(user.getLastName());
        oldUser.setEmail(user.getEmail());
        oldUser.setAuthorityMask(user.getAuthorityMask());
        oldUser.setCredentialsNonExpired(user.isCredentialsNonExpired());
    }
}
