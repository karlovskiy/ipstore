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

    //    @NotEmpty
    private String type;

    private String ip;

    private String host;


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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("from=").append(from)
                .append(", to=").append(to)
                .append(", type=").append(type)
                .append(", ip=").append(ip)
                .append(", host=").append(host);
        return result.toString();
    }
}
