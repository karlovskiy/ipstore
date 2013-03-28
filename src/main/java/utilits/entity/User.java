package utilits.entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import utilits.controller.users.Authority;
import utilits.controller.users.UserStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 2.4, 3/28/13
 */
@Entity
@Table(name = "USER")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")
    @SequenceGenerator(name = "id_seq", sequenceName = "ID_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @NotEmpty
    @Column(name = "USERNAME", nullable = false, unique = true, length = 128)
    private String username;

    @Column(name = "PASSWORD", length = 512)
    private String password;

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "STATUS", nullable = false)
    private UserStatus userStatus;

    @Column(name = "AUTHORITY", nullable = false)
    private int authorityMask;

    @Column(name = "FIRST_NAME", length = 64)
    private String firstName;

    @Column(name = "LAST_NAME", length = 32)
    private String lastName;

    @Email
    @Column(name = "EMAIL", length = 128)
    private String email;

    @Column(name = "CREDENTIALS_NON_EXPIRED", nullable = false)
    private boolean credentialsNonExpired;

    @Transient
    public Collection<? extends GrantedAuthority> getAllAuthorities() {
        return Arrays.asList(Authority.values());
    }

    @Transient
    public Collection<? extends GrantedAuthority> getAuthoritiesCollection() {
        return Authority.getAuthorities(authorityMask);
    }

    @Transient
    public String getAuthorities() {
        return Authority.getAuthorities(authorityMask).toString();
    }

    @Transient
    public void setAuthorities(String authorities) {
        authorityMask = Authority.getAuthorityMask(authorities);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public int getAuthorityMask() {
        return authorityMask;
    }

    public void setAuthorityMask(int authorityMask) {
        this.authorityMask = authorityMask;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }
}
