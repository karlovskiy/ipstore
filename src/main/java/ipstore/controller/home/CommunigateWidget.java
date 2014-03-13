package ipstore.controller.home;

import java.util.List;

/**
 * Here will be javadoc
 *
 * @author ravenstar
 * @since 4.0, 2/28/14
 */
public class CommunigateWidget {

    private Long total;
    private Long normal;
    private Long blocked;
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

    public Long getBlocked() {
        return blocked;
    }

    public void setBlocked(Long blocked) {
        this.blocked = blocked;
    }

    public List<LastChange> getLastChanges() {
        return lastChanges;
    }

    public void setLastChanges(List<LastChange> lastChanges) {
        this.lastChanges = lastChanges;
    }
}
