package ipstore.controller.users;

import org.hibernate.validator.constraints.Email;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 2.4.2, 3/30/13
 */
public class ChangeUserInfo {

    @Email
    private String email;
    private String firstName;
    private String lastName;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
