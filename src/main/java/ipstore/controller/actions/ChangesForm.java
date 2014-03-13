package ipstore.controller.actions;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 2.5.4, 4/18/13
 */
public class ChangesForm {

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date from;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date to;
    private String username;
    private String changeType;
    private String fieldType;

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

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("from=").append(from)
                .append(", to=").append(to)
                .append(", username=").append(username)
                .append(", changeType=").append(changeType)
                .append(", fieldType=").append(fieldType);
        return result.toString();
    }
}
