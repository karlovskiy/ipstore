package utilits.controller;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
public class ActionFilterForm {

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date from;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date to;

    private String ip;

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("from=").append(from)
                .append(", to=").append(to)
                .append(", ip=").append(ip);
        return result.toString();
    }
}
