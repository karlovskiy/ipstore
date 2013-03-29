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

    private String username;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("from=").append(from)
                .append(", to=").append(to)
                .append(", username=").append(username);
        return result.toString();
    }
}
