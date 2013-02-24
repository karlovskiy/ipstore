package utilits.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import utilits.aspect.Action;
import utilits.aspect.ActionType;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
@Controller
public class LoginController {

    @Action(type = ActionType.LOGIN_PAGE)
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @Action(type = ActionType.LOGIN_FAILURE)
    @RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
    public String loginerror(ModelMap model) {
        model.addAttribute("error", "true");
        return "login";
    }

    @Action(type = ActionType.LOGOUT)
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        return "login";
    }

    @Action(type = ActionType.HOME_PAGE)
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "home";
    }

}
