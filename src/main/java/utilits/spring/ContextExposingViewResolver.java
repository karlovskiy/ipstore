package utilits.spring;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.core.Ordered;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.ContextExposingHttpServletRequest;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

/**
 * Here will be javadoc
 *
 * @author ravenstar
 * @since 4.0, 2/14/14
 */
public class ContextExposingViewResolver extends ApplicationObjectSupport implements ViewResolver, Ordered {

    private int order = Integer.MAX_VALUE;

    /**
     * Set the order in which this {@link org.springframework.web.servlet.ViewResolver}
     * is evaluated.
     */
    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * Return the order in which this {@link org.springframework.web.servlet.ViewResolver}
     * is evaluated.
     */
    public int getOrder() {
        return this.order;
    }

    private class ContextExposingView implements View {

        private final View view;

        public ContextExposingView(View view) {
            this.view = view;
        }

        public String getContentType() {
            return this.view.getContentType();
        }

        public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
            this.view.render(model, getRequestToExpose(request), response);
        }
    }

    private boolean exposeContextBeansAsAttributes = false;

    private Set<String> exposedContextBeanNames;

    private final ViewResolver viewResolver;

    public ContextExposingViewResolver(ViewResolver viewResolver) {
        this.viewResolver = viewResolver;
    }

    public View resolveViewName(String viewName, Locale locale) throws Exception {
        View view = viewResolver.resolveViewName(viewName, locale);
        return view != null ? new ContextExposingView(viewResolver.resolveViewName(viewName, locale)) : null;
    }

    public void setExposeContextBeansAsAttributes(boolean exposeContextBeansAsAttributes) {
        this.exposeContextBeansAsAttributes = exposeContextBeansAsAttributes;
    }

    public void setExposedContextBeanNames(Set<String> exposedContextBeanNames) {
        this.exposedContextBeanNames = exposedContextBeanNames;
    }

    protected HttpServletRequest getRequestToExpose(HttpServletRequest originalRequest) {
        if (this.exposeContextBeansAsAttributes || this.exposedContextBeanNames != null) {
            return new ContextExposingHttpServletRequest(originalRequest, getWebApplicationContext(),
                    this.exposedContextBeanNames);
        }
        return originalRequest;
    }

    protected final WebApplicationContext getWebApplicationContext() throws IllegalStateException {
        ApplicationContext ctx = getApplicationContext();
        if (ctx instanceof WebApplicationContext) {
            return (WebApplicationContext) getApplicationContext();
        } else if (isContextRequired()) {
            throw new IllegalStateException("WebApplicationObjectSupport instance [" + this
                    + "] does not run in a WebApplicationContext but in: " + ctx);
        } else {
            return null;
        }
    }

    protected boolean isContextRequired() {
        return false;
    }
}
