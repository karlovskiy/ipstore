package utilits.entity;

import utilits.aspect.AccountChangeType;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
@Entity
@Table(name = "ACCOUNT_CHANGE")
public class AccountChange implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")
    @SequenceGenerator(name = "id_seq", sequenceName = "ID_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "TYPE", nullable = false)
    private AccountChangeType type;

    @Column(name = "ACCOUNT_ID")
    private Long accountId;

    @Column(name = "OLD_VALUE", length = 512)
    private String oldValue;

    @Column(name = "NEW_VALUE", length = 512)
    private String newValue;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ACTION_ID", nullable = false)
    private Action action;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccountChangeType getType() {
        return type;
    }

    public void setType(AccountChangeType type) {
        this.type = type;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
