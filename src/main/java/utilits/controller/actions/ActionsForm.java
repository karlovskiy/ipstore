package utilits.controller.actions;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 2.5.3, 4/17/13
 */
public class ActionsForm {

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date from;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date to;

    private String username;

    private String actionType;

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

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("from=").append(from)
                .append(", to=").append(to)
                .append(", username=").append(username)
                .append(", actionType=").append(actionType);
        return result.toString();
    }
}
