package utilits.entity;

import org.hibernate.search.annotations.*;
import org.hibernate.validator.constraints.NotEmpty;
import utilits.controller.accounts.AccountStatus;

import javax.persistence.*;
import java.io.Serializable;

import static utilits.Utils.INDEX_ANALYZER_DEFINITION;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
@Entity
@Table(name = "ACCOUNT")
@Indexed
@Analyzer(definition = INDEX_ANALYZER_DEFINITION)
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")
    @SequenceGenerator(name = "id_seq", sequenceName = "ID_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Field(index = Index.TOKENIZED, store = Store.YES)
    @NotEmpty
    @Column(name = "LOGIN", nullable = false, unique = true, length = 128)
    private String login;

    @NotEmpty
    @Column(name = "PASSWORD", nullable = false, length = 128)
    private String password;

    @Field(index = Index.TOKENIZED, store = Store.YES)
    @Column(name = "CLIENTNAME", length = 128)
    private String clientName;

    @Field(index = Index.TOKENIZED, store = Store.YES)
    @Column(name = "NUMBER", length = 128)
    private String number;

    @Field(index = Index.TOKENIZED, store = Store.YES)
    @Column(name = "DESCRIPTION", length = 512)
    private String description;

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "STATUS", nullable = false)
    private AccountStatus status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }
}
