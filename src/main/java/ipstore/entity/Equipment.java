package ipstore.entity;

import org.apache.solr.analysis.*;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.validator.constraints.NotEmpty;
import ipstore.controller.equipment.PasswordStatus;
import ipstore.controller.equipment.Status;
import ipstore.controller.equipment.TelnetStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static ipstore.Utils.INDEX_ANALYZER_DEFINITION;
import static ipstore.Utils.QUERY_ANALYZER_DEFINITION;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
@Entity
@Table(name = "EQUIPMENT")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
@Indexed
@AnalyzerDefs({
        @AnalyzerDef(name = INDEX_ANALYZER_DEFINITION,
                tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
                filters = {
                        @TokenFilterDef(factory = StandardFilterFactory.class),
                        @TokenFilterDef(factory = LowerCaseFilterFactory.class),
                        @TokenFilterDef(factory = StopFilterFactory.class),
                        @TokenFilterDef(factory = NGramFilterFactory.class,
                                params = {
                                        @Parameter(name = "minGramSize", value = "1"),
                                        @Parameter(name = "maxGramSize", value = "16")})
                }),
        @AnalyzerDef(name = QUERY_ANALYZER_DEFINITION,
                tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
                filters = {
                        @TokenFilterDef(factory = StandardFilterFactory.class),
                        @TokenFilterDef(factory = LowerCaseFilterFactory.class)
                })
})
@Analyzer(definition = INDEX_ANALYZER_DEFINITION)
public class Equipment implements IHasId, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")
    @SequenceGenerator(name = "id_seq", sequenceName = "ID_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Field(index = Index.TOKENIZED, store = Store.YES)
    @NotEmpty
    @Column(name = "IP_ADDRESS", nullable = false, unique = true, length = 45)
    private String ipAddress;

    @Field(index = Index.TOKENIZED, store = Store.YES)
    @Column(name = "TYPE", length = 128)
    private String type;

    @Field(index = Index.TOKENIZED, store = Store.YES)
    @Column(name = "USERNAME", length = 128)
    private String username;

    @Field(index = Index.TOKENIZED, store = Store.YES)
    @Column(name = "LOGIN", length = 128)
    private String login;

    @NotEmpty
    @Column(name = "PASSWORD", nullable = false, length = 128)
    private String password;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "PASSWORD_DATE", nullable = false)
    private Date passwordDate;

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "PASSWORD_STATUS", nullable = false)
    private PasswordStatus passwordStatus;

    @Field(index = Index.TOKENIZED, store = Store.YES)
    @Column(name = "CLIENT_NAME", length = 128)
    private String clientName;

    @Field(index = Index.TOKENIZED, store = Store.YES)
    @Column(name = "PLACEMENT_ADDRESS", length = 512)
    private String placementAddress;

    @Field(index = Index.TOKENIZED, store = Store.YES)
    @Column(name = "APPLICATION_NUMBER", length = 128)
    private String applicationNumber;

    @Field(index = Index.TOKENIZED, store = Store.YES)
    @Column(name = "DESCRIPTION", length = 512)
    private String description;

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "STATUS", nullable = false)
    private Status status;

    @Basic(fetch = FetchType.LAZY)
    @Lob
    @Column(name = "CONF_DATA", columnDefinition = "BLOB(10M)")
    private byte[] configData;

    @Column(name = "CONF_NAME", length = 128)
    private String configName;

    @Column(name = "CONF_TYPE", length = 128)
    private String configType;

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "TELNET_STATUS")
    private TelnetStatus telnetStatus;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "TELNET_DATE")
    private Date telnetStatusDate;

    @Column(name = "TELNET_CHECK")
    private Boolean telnetCheck;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getPasswordDate() {
        return passwordDate;
    }

    public void setPasswordDate(Date passwordDate) {
        this.passwordDate = passwordDate;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getPlacementAddress() {
        return placementAddress;
    }

    public void setPlacementAddress(String placementAddress) {
        this.placementAddress = placementAddress;
    }

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PasswordStatus getPasswordStatus() {
        return passwordStatus;
    }

    public void setPasswordStatus(PasswordStatus passwordStatus) {
        this.passwordStatus = passwordStatus;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public byte[] getConfigData() {
        return configData;
    }

    public void setConfigData(byte[] configData) {
        this.configData = configData;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
    }

    public TelnetStatus getTelnetStatus() {
        return telnetStatus;
    }

    public void setTelnetStatus(TelnetStatus telnetStatus) {
        this.telnetStatus = telnetStatus;
    }

    public Date getTelnetStatusDate() {
        return telnetStatusDate;
    }

    public void setTelnetStatusDate(Date telnetStatusDate) {
        this.telnetStatusDate = telnetStatusDate;
    }

    public Boolean getTelnetCheck() {
        return telnetCheck;
    }

    public void setTelnetCheck(Boolean telnetCheck) {
        this.telnetCheck = telnetCheck;
    }
}
