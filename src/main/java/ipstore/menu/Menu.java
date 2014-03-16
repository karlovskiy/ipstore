package ipstore.menu;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Here will be javadoc
 *
 * @author ravenstar
 * @since 4.0, 2/14/14
 */
public enum Menu {
    EQUIPMENTS(         "/equipment", "/equipment/{id}", "/equipment/{id}/edit"),
    ADD_EQUIPMENT(      "/equipment/new"),
    IMPORT_EQUIPMENTS(  "/equipment/import"),
    USER_SETTINGS(      "/changeuserinfo", "/changepassword"),
    CONTACT(            "/contact"),
    ACCOUNTS(           "/accounts", "/accounts/{id}", "/accounts/{id}/edit"),
    ADD_ACCOUNT(        "/accounts/new"),
    IMPORT_ACCOUNTS(    "/accounts/import"),
    DOMAINS(            "/communigate", "/communigate/{id}", "/communigate/{id}/edit"),
    ADD_DOMAIN(         "/communigate/new"),
    IMPORT_DOMAIN(      "/communigate/import"),
    CAPACITY(           "/capacity", "/capacity/{id}", "/capacity/{capacityId}/edit", "/capacity/{capacityId}/numbers/{id}",
                        "/capacity/{capacityId}/numbers/{id}/edit", "/capacity/{capacityId}/numbers/new"),
    ADD_CAPACITY(       "/capacity/new"),
    MANAGEMENT(         "/actions", "/actions/{id}", "/changes", "/users", "/users/{id}", "/users/{id}/edit",
                        "/users/new");

    Set<String> mappings = new HashSet<String>();

    Menu(String... mappings) {
        this.mappings.addAll(Arrays.asList(mappings));
    }

    public boolean checkMapping(String mapping) {
        return this.mappings.contains(mapping);
    }
}
