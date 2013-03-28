package utilits.controller.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 2.4, 3/28/13
 */
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);
    private String defaultFailureUrl;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    public static final String CREDENTIALS_EXPIRED_USERNAME_KEY = "CREDENTIALS_EXPIRED_USERNAME";

    public CustomAuthenticationFailureHandler() {
    }

    public CustomAuthenticationFailureHandler(String defaultFailureUrl) {
        setDefaultFailureUrl(defaultFailureUrl);
    }

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {
        if (defaultFailureUrl == null) {
            logger.debug("No failure URL set, sending 401 Unauthorized error");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed: " +
                    exception.getMessage());
        } else {
            logger.debug("Redirecting to " + defaultFailureUrl);
            HttpSession session = request.getSession();
            session.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
            if (exception instanceof CredentialsExpiredException) {
                session.setAttribute(CREDENTIALS_EXPIRED_USERNAME_KEY,
                        request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY));
            }
            redirectStrategy.sendRedirect(request, response, defaultFailureUrl);

        }
    }

    public void setDefaultFailureUrl(String defaultFailureUrl) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(defaultFailureUrl),
                "'" + defaultFailureUrl + "' is not a valid redirect URL");
        this.defaultFailureUrl = defaultFailureUrl;
    }
}
