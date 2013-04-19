package utilits.controller.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import utilits.aspect.change.ChangeType;
import utilits.aspect.change.IChangeField;
import utilits.service.ActionService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

import static utilits.Utils.*;

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
        model.addAttribute("actionsForm", DEFAULT_ACTIONS_FORM);
        model.addAttribute("actions", actionService.loadActions(DEFAULT_ACTIONS_FORM));
        model.addAttribute("actionTypes", ACTIONS_TYPES);
        return "actions";
    }

    @RequestMapping(value = "/actions", method = RequestMethod.POST)
    public String filteredActionList(@Valid ActionsForm actionsForm, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            model.addAttribute("actionsForm", actionsForm);
            model.addAttribute("actions", actionService.loadActions(actionsForm));
            model.addAttribute("actionTypes", ACTIONS_TYPES);
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
        model.addAttribute("changesForm", DEFAULT_CHANGES_FORM);
        model.addAttribute("changes", actionService.loadChanges(DEFAULT_CHANGES_FORM));
        model.addAttribute("changesTypes", CHANGES_TYPES);
        return "changes";
    }

    @RequestMapping(value = "/changes", method = RequestMethod.POST)
    public String filteredChangesList(@Valid ChangesForm changesForm, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            model.addAttribute("changesForm", changesForm);
            model.addAttribute("changes", actionService.loadChanges(changesForm));
            model.addAttribute("changesTypes", CHANGES_TYPES);
        }
        return "changes";
    }

    @RequestMapping(value = "/change_types", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, List<String>> loadChangeTypes() {
        logger.info("Request for change types");
        Map<String, List<String>> changeTypes = new LinkedHashMap<String, List<String>>();
        changeTypes.put(ALL, Collections.<String>emptyList());
        for (ChangeType changeType : ChangeType.values()) {
            if (changeType != ChangeType.NONE) {
                List<String> fields = new ArrayList<String>();
                fields.add(ALL);
                for (IChangeField changeField : changeType.getIChangeFields()) {
                    fields.add(changeField.getFieldType());
                }
                changeTypes.put(changeType.name(), fields);
            }
        }
        return changeTypes;
    }
}
