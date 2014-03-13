package ipstore.entity.capacity;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import ipstore.controller.capacity.CapacityNumberStatus;

import javax.persistence.*;
import java.util.Date;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 3.0, 8/20/13
 */
@Entity
@Table(name = "CAPACITY_NUMBER")
public class CapacityNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")
    @SequenceGenerator(name = "id_seq", sequenceName = "ID_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAPACITY_TYPE__ID", nullable = false)
    private CapacityType type;

    @NotEmpty
    @Column(name = "NUMBER", nullable = false, unique = true, length = 128)
    private String number;

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "STATUS", nullable = false)
    private CapacityNumberStatus status;

    @Column(name = "LINES_COUNT")
    private Integer linesCount;

    @Column(name = "CLIENT_NAME", length = 128)
    private String clientName;

    @Column(name = "ADDRESS", length = 256)
    private String address;

    @Column(name = "APPLICATION", length = 128)
    private String application;

    @Column(name = "COMMENTS", length = 512)
    private String comments;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Temporal(value = TemporalType.DATE)
    @Column(name = "ADMITTANCE_DATE")
    private Date admittanceDate;

    @Column(name = "LSC_USERNAME", length = 512, nullable = false)
    private String lscUsername;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "LSC_TIMESTAMP", nullable = false)
    private Date lscTimestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CapacityType getType() {
        return type;
    }

    public void setType(CapacityType type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public CapacityNumberStatus getStatus() {
        return status;
    }

    public void setStatus(CapacityNumberStatus status) {
        this.status = status;
    }

    public Integer getLinesCount() {
        return linesCount;
    }

    public void setLinesCount(Integer linesCount) {
        this.linesCount = linesCount;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getAdmittanceDate() {
        return admittanceDate;
    }

    public void setAdmittanceDate(Date admittanceDate) {
        this.admittanceDate = admittanceDate;
    }

    public String getLscUsername() {
        return lscUsername;
    }

    public void setLscUsername(String lscUsername) {
        this.lscUsername = lscUsername;
    }

    public Date getLscTimestamp() {
        return lscTimestamp;
    }

    public void setLscTimestamp(Date lscTimestamp) {
        this.lscTimestamp = lscTimestamp;
    }
}
