package utilits.entity;

import org.hibernate.search.annotations.*;
import org.hibernate.validator.constraints.NotEmpty;
import utilits.controller.Communigate.CommunigateStatus;

import javax.persistence.*;
import java.io.Serializable;

import static utilits.Utils.INDEX_ANALYZER_DEFINITION;

/**
 * Created with IntelliJ IDEA.
 * User: Sidorov Oleg
 * Date: 22.03.13
 */

@Entity
@Table(name = "COMMUNIGATECLASS5")
@Indexed
@Analyzer(definition = INDEX_ANALYZER_DEFINITION)
public class CommunigateClass5  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")
    @SequenceGenerator(name = "id_seq", sequenceName = "ID_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Field(index = Index.TOKENIZED, store = Store.YES)
    @NotEmpty
    @Column(name = "DOMAINNAME", nullable = false, unique = true, length = 128)
    private String domainName;

    @NotEmpty
    @Column(name = "PREFIXTRY", nullable = false, length = 128)
    private String prefixTry;

    @Field(index = Index.TOKENIZED, store = Store.YES)
    @Column(name = "CLIENTNAME", length = 128)
    private String clientName;

    @Field(index = Index.TOKENIZED, store = Store.YES)
    @Column(name = "TIKETNUMBER", length = 128)
    private String tiketNumber;

    @Field(index = Index.TOKENIZED, store = Store.YES)
    @Column(name = "VOLLINE", length = 128)
    private String volLine;

    @Field(index = Index.TOKENIZED, store = Store.YES)
    @Column(name = "VOLSPACE", length = 128)
    private String volSpace;

    @Field(index = Index.TOKENIZED, store = Store.YES)
    @Column(name = "SERVICE", length = 128)
    private String service;

    @Field(index = Index.TOKENIZED, store = Store.YES)
    @Column(name = "CONTRACT", length = 128)
    private String contract;

    @Field(index = Index.TOKENIZED, store = Store.YES)
    @Column(name = "LOGIN", length = 128)
    private String login;


    @Field(index = Index.TOKENIZED, store = Store.YES)
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

    public String getPrefixTry() {
        return prefixTry;
    }

    public void setPrefixTry(String prefixTry) {
        this.prefixTry = prefixTry;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getTiketNumber() {
        return tiketNumber;
    }

    public void setTiketNumber(String tiketNumber) {
        this.tiketNumber = tiketNumber;
    }

    public String getVolLine() {
        return volLine;
    }

    public void setVolLine(String volLine) {
        this.volLine = volLine;
    }

    public String getVolSpace() {
        return volSpace;
    }

    public void setVolSpace(String volSpace) {
        this.volSpace = volSpace;
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

    public String getLogin () {
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

    public CommunigateStatus getStatus() {
        return status;
    }

    public void setStatus(CommunigateStatus status) {
        this.status = status;
    }


}
