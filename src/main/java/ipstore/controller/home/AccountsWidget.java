package ipstore.controller.home;

import java.util.List;

/**
 * Here will be javadoc
 *
 * @author ravenstar
 * @since 4.0, 2/28/14
 */
public class AccountsWidget {

    private Long total;
    private Long normal;
    private Long warning;
    private List<LastChange> lastChanges;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getNormal() {
        return normal;
    }

    public void setNormal(Long normal) {
        this.normal = normal;
    }

    public Long getWarning() {
        return warning;
    }

    public void setWarning(Long warning) {
        this.warning = warning;
    }

    public List<LastChange> getLastChanges() {
        return lastChanges;
    }

    public void setLastChanges(List<LastChange> lastChanges) {
        this.lastChanges = lastChanges;
    }
}
