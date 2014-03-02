package utilits.controller.home;

import java.util.List;

/**
 * Here will be javadoc
 *
 * @author ravenstar
 * @since 4.0, 2/27/14
 */
public class EquipmentWidget {

    private Long total;
    private Long old;
    private Long needUpdate;
    private Long warning;
    private Long timeout;
    private List<LastChange> lastChanges;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getOld() {
        return old;
    }

    public void setOld(Long old) {
        this.old = old;
    }

    public Long getNeedUpdate() {
        return needUpdate;
    }

    public void setNeedUpdate(Long needUpdate) {
        this.needUpdate = needUpdate;
    }

    public Long getWarning() {
        return warning;
    }

    public void setWarning(Long warning) {
        this.warning = warning;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public List<LastChange> getLastChanges() {
        return lastChanges;
    }

    public void setLastChanges(List<LastChange> lastChanges) {
        this.lastChanges = lastChanges;
    }
}
