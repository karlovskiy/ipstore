package ipstore.spring;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import ipstore.Utils;
import ipstore.breadcrumb.Breadcrumb;
import ipstore.breadcrumb.BreadcrumbItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Here will be javadoc
 *
 * @author ravenstar
 * @since 4.1, 3/8/14
 */
public class BreadcrumbInterceptor extends HandlerInterceptorAdapter {

    public static final String BREADCRUMB = BreadcrumbInterceptor.class.getName() + ".BREADCRUMB";
    public static final String BREADCRUMB_ATTRIBUTE = "BREADCRUMB";
    public static final String TITLE_ATTRIBUTE = "PAGE_TITLE";
    public static final int BREADCRUMB_SIZE = 7;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (handler instanceof HandlerMethod && Utils.isAuthenticated()) {
            Breadcrumb breadcrumb = ((HandlerMethod) handler).getMethod().getAnnotation(Breadcrumb.class);
            if (breadcrumb != null) {
                Map<String, Object> model = modelAndView.getModel();
                String label = (String) model.get(TITLE_ATTRIBUTE);
                if (StringUtils.isEmpty(label)) {
                    String breadCrumbLabel = breadcrumb.label();
                    label = StringUtils.isEmpty(breadCrumbLabel) ? "NOT_DEFINED" : breadCrumbLabel;
                }
                BreadcrumbItem breadcrumbItem = new BreadcrumbItem(request.getRequestURI(), label);
                HttpSession session = request.getSession();
                @SuppressWarnings("unchecked")
                CircularFifoQueue<BreadcrumbItem> breadcrumbQueue = (CircularFifoQueue<BreadcrumbItem>) session.getAttribute(BREADCRUMB);
                if (breadcrumbQueue == null) {
                    breadcrumbQueue = new CircularFifoQueue<BreadcrumbItem>(BREADCRUMB_SIZE);
                    session.setAttribute(BREADCRUMB, breadcrumbQueue);
                }
                if(!breadcrumbQueue.contains(breadcrumbItem)){
                    breadcrumbQueue.add(breadcrumbItem);
                }
                List<BreadcrumbItem> items = new ArrayList<BreadcrumbItem>(BREADCRUMB_SIZE);
                for (int i = breadcrumbQueue.size() - 1; i >= 0; i--) {
                    BreadcrumbItem item = breadcrumbQueue.get(i);
                    items.add(item);
                    item.setCurrent(item.equals(breadcrumbItem));
                }
                session.setAttribute(BREADCRUMB_ATTRIBUTE, items);
            }
        }
    }
}
