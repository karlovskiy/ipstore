package utilits.entity;

import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import utilits.controller.communigate.CommunigateStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static org.hibernate.search.annotations.Index.TOKENIZED;
import static org.hibernate.search.annotations.Store.YES;
import static utilits.Utils.INDEX_ANALYZER_DEFINITION;

/**
 * User: Sidorov Oleg
 * Date: 25.03.13
 */

@Entity
@Indexed
@Analyzer(definition = INDEX_ANALYZER_DEFINITION)
@Table(name = "COMMUNIGATE_DOMAIN")
public class CommunigateDomain implements IHasId, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")
    @SequenceGenerator(name = "id_seq", sequenceName = "ID_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Field(index = TOKENIZED, store = YES)
    @NotEmpty
    @Column(name = "DOMAIN_NAME", nullable = false, unique = true, length = 64)
    private String domainName;

    @Field(index = TOKENIZED, store = YES)
    @NotEmpty
    @Column(name = "TRY_PREFIX", nullable = false, unique = true, length = 32)
    private String tryPrefix;

    @Field(index = TOKENIZED, store = YES)
    @Column(name = "CLIENT_NAME", length = 64)
    private String clientName;

    @Field(index = TOKENIZED, store = YES)
    @Column(name = "TICKET", length = 16)
    private String ticket;

    @Field(index = TOKENIZED, store = YES)
    @Column(name = "NUMBER_LINE", length = 16)
    private String numberLine;

    @Field(index = TOKENIZED, store = YES)
    @Column(name = "DISK_CAPACITY", length = 32)
    private String diskCapacity;

    @Field(index = TOKENIZED, store = YES)
    @Column(name = "SERVICE", length = 128)
    private String service;

    @Field(index = TOKENIZED, store = YES)
    @Column(name = "CONTRACT", length = 128)
    private String contract;

    @Field(index = TOKENIZED, store = YES)
    @Column(name = "LOGIN", length = 64)
    private String login;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Column(name = "DATE")
    private Date date;

    @Field(index = TOKENIZED, store = YES)
    @Column(name = "DESCRIPTION", length = 512)
    private String description;

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "STATUS", nullable = false)
    private CommunigateStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getTryPrefix() {
        return tryPrefix;
    }

    public void setTryPrefix(String tryPrefix) {
        this.tryPrefix = tryPrefix;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getNumberLine() {
        return numberLine;
    }

    public void setNumberLine(String numberLine) {
        this.numberLine = numberLine;
    }

    public String getDiskCapacity() {
        return diskCapacity;
    }

    public void setDiskCapacity(String diskCapacity) {
        this.diskCapacity = diskCapacity;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public CommunigateStatus getStatus() {
        return status;
    }

    public void setStatus(CommunigateStatus status) {
        this.status = status;
    }
}
