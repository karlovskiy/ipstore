package utilits.controller;

import utilits.aspect.ActionType;
import utilits.entity.Action;

import java.util.Date;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
public class ActionWrapper {

    public static final String VIEW_PAGE_PREFIX = "/ipstore/equipment/";

    private final Action action;
    private final String url;

    public ActionWrapper(Action action) {
        this.action = action;
        ActionType actionType = action.getType();
        String requestURL = action.getRequestURL();
        switch (actionType) {
            case EQUIPMENT_LIST:
            case ACCESS_VIEW_PAGE:
            case ACCESS_EDIT_PAGE:
                url = requestURL;
                break;
            case EQUIPMENT_UPDATE:
                String update = "save/";
                int saveIndex = requestURL.indexOf(update);
                url = saveIndex > -1 ? VIEW_PAGE_PREFIX + requestURL.substring(saveIndex + update.length()) : null;
                break;
            case EQUIPMENT_DELETE:
                String delete = "delete/";
                url = VIEW_PAGE_PREFIX + requestURL.substring(requestURL.indexOf(delete) + delete.length());
                break;
            default:
                url = null;
        }
    }

    public Date getActionTimestamp() {
        return action.getActionTimestamp();
    }

    public String getIp() {
        return action.getIp();
    }

    public String getRequestURL() {
        return action.getRequestURL();
    }

    public ActionType getType() {
        return action.getType();
    }

    public String getUrl() {
        return url;
    }
}
