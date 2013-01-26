package utilits.entity;

import utilits.aspect.ActionType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
@Entity
@Table(name = "ACTION")
public class Action implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")
    @SequenceGenerator(name = "id_seq", sequenceName = "ID_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "TYPE", nullable = false)
    private ActionType type;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "ACTION_TIMESTAMP", nullable = false)
    private Date actionTimestamp;

    @Column(name = "IP", nullable = false, length = 45)
    private String ip;

    @Column(name = "HOST", nullable = false, length = 128)
    private String host;

    @Column(name = "USER_AGENT", nullable = false, length = 256)
    private String userAgent;

    @Column(name = "REQUEST_URL", length = 256)
    private String requestURL;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ActionType getType() {
        return type;
    }

    public void setType(ActionType type) {
        this.type = type;
    }

    public Date getActionTimestamp() {
        return actionTimestamp;
    }

    public void setActionTimestamp(Date actionTimestamp) {
        this.actionTimestamp = actionTimestamp;
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

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }
}
