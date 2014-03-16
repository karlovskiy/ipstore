package ipstore.service;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ipstore.controller.users.UserStatus;
import ipstore.entity.User;

import javax.annotation.Resource;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 2.4, 3/28/13
 */
@Service("userDetailsService")
@Transactional
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsService.class);

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            if (username == null) {
                throw new IllegalArgumentException("Username cannot be null");
            }
            username = username.trim();
            if (username.isEmpty()) {
                throw new IllegalArgumentException("Username cannot be empty");
            }
            logger.info("Loading user details by username " + username);
            User user = loadUser(username);
            Principal principal = new Principal(
                    user.getUsername(),
                    user.getPassword(),
                    UserStatus.ACTIVE == user.getUserStatus(), true, user.isCredentialsNonExpired(), true,
                    user.getAuthoritiesCollection());
            principal.setTheme(user.theme());
            return principal;
        } catch (Exception e) {
            throw new UsernameNotFoundException("User " + username + " not found!", e);
        }
    }

    private User loadUser(String username) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("username", username));
        User user = (User) criteria.uniqueResult();
        if (user == null) {
            throw new IllegalArgumentException("User " + username + " not exist!");
        }
        return user;

    }

}
