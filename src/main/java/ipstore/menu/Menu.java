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
    EQUIPMENTS(         "/equipment", "/equipment/view/{id}", "/equipment/edit/{id}"),
    ADD_EQUIPMENT(      "/equipment/add"),
    IMPORT_EQUIPMENTS(  "/equipment/import"),
    USER_SETTINGS(      "/changeuserinfo", "/changepassword"),
    CONTACT(            "/contact"),
    ACCOUNTS(           "/accounts", "/accounts/view/{id}", "/accounts/edit/{id}"),
    ADD_ACCOUNT         ("/accounts/add"),
    IMPORT_ACCOUNTS(    "/accounts/import"),
    DOMAINS(            "/communigate", "/communigate/view/{id}", "/communigate/edit/{id}"),
    ADD_DOMAIN(         "/communigate/add"),
    IMPORT_DOMAIN(      "/communigate/import"),
    CAPACITY(           "/capacity", "/capacity/{id}", "/capacity/edit/{capacityId}", "/capacity/number/view/{id}",
                        "/capacity/number/edit/{id}", "/capacity/{capacityId}/add"),
    ADD_CAPACITY(       "/capacity/add"),
    MANAGEMENT(         "/actions", "/actions/view/{id}", "/changes", "/users", "/users/view/{id}", "/users/edit/{id}",
                        "/users/add");

    Set<String> mappings = new HashSet<String>();

    Menu(String... mappings) {
        this.mappings.addAll(Arrays.asList(mappings));
    }

    public boolean checkMapping(String mapping) {
        return this.mappings.contains(mapping);
    }
}
