package utilits.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import utilits.entity.Action;
import utilits.service.ActionService;

import javax.annotation.Resource;
import javax.validation.Valid;
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

    public static Logger logger = Logger.getLogger(ActionController.class);

    @Resource(name = "actionService")
    private ActionService actionService;

    @RequestMapping(value = "/actions", method = RequestMethod.GET)
    public String actionList(Model model) {
        logger.info("Received request to load actions");
        ActionFilterForm filterForm = new ActionFilterForm();
        Calendar calendar = Calendar.getInstance();
        filterForm.setTo(calendar.getTime());
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        filterForm.setFrom(calendar.getTime());
        model.addAttribute("actionFilterForm", filterForm);
        List<Action> actions = actionService.loadActions(filterForm);
        model.addAttribute("actions", actions);
        return "actions";
    }

    @RequestMapping(value = "/actions", method = RequestMethod.POST)
    public String filteredActionList(@Valid ActionFilterForm actionFilterForm, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            model.addAttribute("actionFilterForm", actionFilterForm);
            List<Action> actions = actionService.loadActions(actionFilterForm);
            model.addAttribute("actions", actions);
        }
        return "actions";
    }

}
