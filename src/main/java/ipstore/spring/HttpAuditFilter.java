package ipstore.spring;

import ak.audr.protocol.Header;
import ak.audr.protocol.Parameter;
import ak.audr.protocol.RequestDump;
import ak.audr.protocol.RequestDumpSerializer;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

/**
 * Here will be javadoc
 *
 * @author karlovskiy
 * @since 1.0, 6/6/14
 */
@Component("httpAuditFilter")
public class HttpAuditFilter implements Filter {

    @Resource(name = "httpAudit")
    private JmsTemplate httpAudit;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        RequestDump dump = new RequestDump();
        dump.setStartTime(new Date());
        dump.setRequestURI(httpRequest.getRequestURI());
        dump.setReqAuthType(httpRequest.getAuthType());
        dump.setCharacterEncoding(request.getCharacterEncoding());
        dump.setContentLength(request.getContentLength());
        dump.setReqContentType(request.getContentType());
        dump.setContextPath(httpRequest.getContextPath());
        dump.setLocale(request.getLocale().toString());
        dump.setMethod(httpRequest.getMethod());
        dump.setPathInfo(httpRequest.getPathInfo());
        dump.setProtocol(request.getProtocol());
        dump.setQueryString(httpRequest.getQueryString());
        dump.setRemoteAddr(request.getRemoteAddr());
        dump.setRemoteHost(request.getRemoteHost());
        dump.setReqRemoteUser(httpRequest.getRemoteUser());
        dump.setRequestedSessionId(httpRequest.getRequestedSessionId());
        dump.setScheme(request.getScheme());
        dump.setServerName(request.getServerName());
        dump.setServerPort(request.getServerPort());
        dump.setSecure(request.isSecure());

        List<ak.audr.protocol.Cookie> cookieList = dump.getCookies();
        Cookie cookies[] = httpRequest.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                ak.audr.protocol.Cookie cookie = new ak.audr.protocol.Cookie();
                cookie.setName(c.getName());
                cookie.setValue(c.getValue());
                cookie.setComment(c.getComment());
                cookie.setDomain(c.getDomain());
                cookie.setMaxAge(c.getMaxAge());
                cookie.setPath(c.getPath());
                cookie.setHttpOnly(c.isHttpOnly());
                cookie.setSecure(c.getSecure());
                cookie.setVersion(c.getVersion());
                cookieList.add(cookie);
            }
        }

        List<Header> reqHeaders = dump.getReqHeaders();
        Enumeration<String> hnames = httpRequest.getHeaderNames();
        while (hnames.hasMoreElements()) {
            String hname = hnames.nextElement();
            Enumeration<String> hvalues = httpRequest.getHeaders(hname);
            while (hvalues.hasMoreElements()) {
                String hvalue = hvalues.nextElement();
                Header header = new Header();
                header.setName(hname);
                header.setValue(hvalue);
                reqHeaders.add(header);
            }
        }

        List<Parameter> parameters = dump.getParameters();
        Enumeration<String> pnames = request.getParameterNames();
        while (pnames.hasMoreElements()) {
            String pname = pnames.nextElement();
            String pvalues[] = request.getParameterValues(pname);
            for (String pvalue : pvalues) {
                Parameter parameter = new Parameter();
                parameter.setName(pname);
                parameter.setValue(pvalue);
                parameters.add(parameter);
            }

        }

        chain.doFilter(request, response);

        dump.setResAuthType(httpRequest.getAuthType());
        dump.setResContentType(response.getContentType());
        dump.setResRemoteUser(httpRequest.getRemoteUser());
        dump.setStatus(httpResponse.getStatus());
        dump.setEndTime(new Date());

        List<Header> resHeaders = dump.getResHeaders();
        Iterable<String> rhnames = httpResponse.getHeaderNames();
        for (String rhname : rhnames) {
            Iterable<String> rhvalues = httpResponse.getHeaders(rhname);
            for (String rhvalue : rhvalues) {
                Header header = new Header();
                header.setName(rhname);
                header.setValue(rhvalue);
                resHeaders.add(header);
            }
        }

        RequestDumpSerializer serializer = new RequestDumpSerializer();
        httpAudit.convertAndSend(serializer.serialize(dump));
    }

    @Override
    public void destroy() {

    }
}
