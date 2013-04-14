package utilits.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import utilits.controller.wrapper.ChangeWrapper;
import utilits.entity.Change;
import utilits.service.ActionService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static utilits.Utils.DEFAULT_ACTION_FILTER;

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
        model.addAttribute("filterForm", DEFAULT_ACTION_FILTER);
        model.addAttribute("actions", actionService.loadActions(DEFAULT_ACTION_FILTER));
        return "actions";
    }

    @RequestMapping(value = "/actions", method = RequestMethod.POST)
    public String filteredActionList(@Valid ActionFilterForm filterForm, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            model.addAttribute("filterForm", filterForm);
            model.addAttribute("actions", actionService.loadActions(filterForm));
        }
        return "actions";
    }

    @RequestMapping(value = "/actions/view/{id}", method = RequestMethod.GET)
    public String viewAction(@PathVariable Long id, Model model) {
        model.addAttribute("action", actionService.loadAction(id));
        return "view-action";
    }

    @RequestMapping(value = "/changes", method = RequestMethod.GET)
    public String changesList(Model model) {
        logger.info("Received request to load changes");
        model.addAttribute("filterForm", DEFAULT_ACTION_FILTER);
        model.addAttribute("formAction", "/ipstore/equipment/changes");
        model.addAttribute("changes", makeChanges(DEFAULT_ACTION_FILTER));
        return "changes";
    }

    @RequestMapping(value = "/equipment/changes", method = RequestMethod.POST)
    public String filteredChangesList(@Valid ActionFilterForm filterForm, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            model.addAttribute("filterForm", filterForm);
            model.addAttribute("formAction", "/ipstore/equipment/changes");
            model.addAttribute("changes", makeChanges(filterForm));
        }
        return "changes";
    }

    private List<ChangeWrapper> makeChanges(ActionFilterForm filterForm) {
        List<Change> changesList = actionService.loadChanges(filterForm);
        List<ChangeWrapper> changes = new ArrayList<ChangeWrapper>(changesList.size());
        for (Change change : changesList) {
            changes.add(new ChangeWrapper(change));
        }
        return changes;
    }

}
