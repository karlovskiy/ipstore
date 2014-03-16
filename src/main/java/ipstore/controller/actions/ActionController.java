package ipstore.controller.actions;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ipstore.aspect.change.ChangeType;
import ipstore.aspect.change.IChangeField;
import ipstore.breadcrumb.Breadcrumb;
import ipstore.entity.Action;
import ipstore.service.ActionService;
import ipstore.spring.BreadcrumbInterceptor;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

import static ipstore.Utils.*;

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

    @Breadcrumb(label = "Actions")
    @RequestMapping(value = "/actions", method = RequestMethod.GET)
    public String actionList(Model model) {
        logger.info("Received request to load actions");
        model.addAttribute("actionsForm", DEFAULT_ACTIONS_FORM);
        model.addAttribute("actions", actionService.loadActions(DEFAULT_ACTIONS_FORM));
        model.addAttribute("actionTypes", ACTIONS_TYPES);
        return "c-list-actions";
    }

    @RequestMapping(value = "/actions", method = RequestMethod.POST)
    public String filteredActionList(@Valid ActionsForm actionsForm, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            model.addAttribute("actionsForm", actionsForm);
            model.addAttribute("actions", actionService.loadActions(actionsForm));
            model.addAttribute("actionTypes", ACTIONS_TYPES);
        }
        return "c-list-actions";
    }

    @Breadcrumb
    @RequestMapping(value = "/actions/{id}", method = RequestMethod.GET)
    public String viewAction(@PathVariable Long id, Model model) {
        Action action = actionService.loadAction(id);
        model.addAttribute("action", action);
        String actionTimestamp = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(action.getActionTimestamp());
        model.addAttribute(BreadcrumbInterceptor.TITLE_ATTRIBUTE, "View action ( " + actionTimestamp + " )");
        return "c-view-actions";
    }

    @Breadcrumb(label = "Changes")
    @RequestMapping(value = "/changes", method = RequestMethod.GET)
    public String changesList(@RequestParam(value = "changeType", required = false) String changeType, Model model) {
        logger.info("Received request to load changes");
        ChangesForm changesForm = DEFAULT_CHANGES_FORM;
        if (StringUtils.isNotEmpty(changeType)) {
            changesForm = new ChangesForm();
            changesForm.setFrom(DEFAULT_ACTIONS_FORM.getFrom());
            changesForm.setTo(DEFAULT_ACTIONS_FORM.getTo());
            changesForm.setChangeType(changeType);
            changesForm.setFieldType(ALL);
        }
        model.addAttribute("changesForm", changesForm);
        model.addAttribute("changes", actionService.loadChanges(changesForm));
        model.addAttribute("changesTypes", CHANGES_TYPES);
        return "c-list-changes";
    }

    @RequestMapping(value = "/changes", method = RequestMethod.POST)
    public String filteredChangesList(@Valid ChangesForm changesForm, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            model.addAttribute("changesForm", changesForm);
            model.addAttribute("changes", actionService.loadChanges(changesForm));
            model.addAttribute("changesTypes", CHANGES_TYPES);
        }
        return "c-list-changes";
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
