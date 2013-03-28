package utilits.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import utilits.controller.wrapper.AccountsChangeWrapper;
import utilits.controller.wrapper.ActionWrapper;
import utilits.controller.wrapper.ChangeWrapper;
import utilits.entity.AccountChange;
import utilits.entity.Action;
import utilits.entity.Change;
import utilits.service.ActionService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
@Controller
public class ActionController {

    private static final Logger logger = LoggerFactory.getLogger(ActionController.class);

    @Resource(name = "actionService")
    private ActionService actionService;

    @RequestMapping(value = "/actions", method = RequestMethod.GET)
    public String actionList(Model model) {
        logger.info("Received request to load actions");
        ActionFilterForm filterForm = makeDefaultFilterForm();
        model.addAttribute("filterForm", filterForm);
        model.addAttribute("actions", makeActions(filterForm));
        return "actions";
    }

    @RequestMapping(value = "/actions", method = RequestMethod.POST)
    public String filteredActionList(@Valid ActionFilterForm filterForm, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            model.addAttribute("filterForm", filterForm);
            model.addAttribute("actions", makeActions(filterForm));
        }
        return "actions";
    }

    @RequestMapping(value = "/changes", method = RequestMethod.GET)
    public String changesList(Model model) {
        logger.info("Received request to load changes");
        ActionFilterForm filterForm = makeDefaultFilterForm();
        model.addAttribute("filterForm", filterForm);
        model.addAttribute("formAction", "/ipstore/changes");
        model.addAttribute("changes", makeChanges(filterForm));
        return "changes";
    }

    @RequestMapping(value = "/changes", method = RequestMethod.POST)
    public String filteredChangesList(@Valid ActionFilterForm filterForm, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            model.addAttribute("filterForm", filterForm);
            model.addAttribute("formAction", "/ipstore/changes");
            model.addAttribute("changes", makeChanges(filterForm));
        }
        return "changes";
    }

    @RequestMapping(value = "/accounts/changes", method = RequestMethod.GET)
    public String accountChangesList(Model model) {
        logger.info("Received request to load account changes");
        ActionFilterForm filterForm = makeDefaultFilterForm();
        model.addAttribute("filterForm", filterForm);
        model.addAttribute("formAction", "/ipstore/accounts/changes");
        model.addAttribute("changes", makeAccountChanges(filterForm));
        return "changes";
    }

    @RequestMapping(value = "/accounts/changes", method = RequestMethod.POST)
    public String filteredAccountChangesList(@Valid ActionFilterForm filterForm, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            model.addAttribute("filterForm", filterForm);
            model.addAttribute("formAction", "/ipstore/accounts/changes");
            model.addAttribute("changes", makeAccountChanges(filterForm));
        }
        return "changes";
    }

    private List<AccountsChangeWrapper> makeAccountChanges(ActionFilterForm filterForm) {
        List<AccountChange> changesList = actionService.loadChanges(filterForm, AccountChange.class);
        List<AccountsChangeWrapper> changes = new ArrayList<AccountsChangeWrapper>(changesList.size());
        for (AccountChange change : changesList) {
            changes.add(new AccountsChangeWrapper(change));
        }
        return changes;
    }

    private List<ChangeWrapper> makeChanges(ActionFilterForm filterForm) {
        List<Change> changesList = actionService.loadChanges(filterForm, Change.class);
        List<ChangeWrapper> changes = new ArrayList<ChangeWrapper>(changesList.size());
        for (Change change : changesList) {
            changes.add(new ChangeWrapper(change));
        }
        return changes;
    }

    private List<ActionWrapper> makeActions(ActionFilterForm filterForm) {
        List<Action> actionsList = actionService.loadActions(filterForm);
        List<ActionWrapper> actions = new ArrayList<ActionWrapper>(actionsList.size());
        for (Action action : actionsList) {
            actions.add(new ActionWrapper(action));
        }
        return actions;
    }

    private ActionFilterForm makeDefaultFilterForm() {
        ActionFilterForm filterForm = new ActionFilterForm();
        Calendar calendar = Calendar.getInstance();
        filterForm.setTo(calendar.getTime());
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        filterForm.setFrom(calendar.getTime());
        return filterForm;
    }

}
