package utilits.controller.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import utilits.aspect.Action;
import utilits.breadcrumb.Breadcrumb;
import utilits.entity.User;
import utilits.service.UsersService;
import utilits.spring.BreadcrumbInterceptor;

import javax.annotation.Resource;
import javax.validation.Valid;

import static utilits.aspect.ActionType.*;
import static utilits.aspect.change.ChangeMode.UPDATE;
import static utilits.aspect.change.ChangeType.USERS;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 2.4, 3/28/13
 */
@Controller
public class UsersController {

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Resource(name = "userService")
    private UsersService userService;

    @Breadcrumb(label = "Users")
    @Action(value = USERS_LIST)
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String viewUsers(Model model) {
        logger.info("Received request to load users.");
        model.addAttribute("users", userService.loadUsers());
        return "c-list-users";
    }

    @Breadcrumb
    @Action(value = USERS_VIEW_PAGE)
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public String viewUser(@PathVariable Long id, Model model) {
        User user = userService.loadUser(id);
        model.addAttribute("user", user);
        model.addAttribute(BreadcrumbInterceptor.TITLE_ATTRIBUTE, "View user ( " + user.getUsername() + " )");
        return "c-view-users";
    }

    @Breadcrumb
    @Action(value = USERS_EDIT_PAGE)
    @RequestMapping(value = "/users/{id}/edit", method = RequestMethod.GET)
    public String editUser(@PathVariable Long id, Model model) {
        User user = userService.loadUser(id);
        model.addAttribute("user", user);
        model.addAttribute("formAction", "/users/" + id);
        model.addAttribute(BreadcrumbInterceptor.TITLE_ATTRIBUTE, "Edit user ( " + user.getUsername() + " )");
        return "c-edit-users";
    }

    @Breadcrumb(label = "Add user")
    @Action(value = USERS_ADD_PAGE)
    @RequestMapping(value = "/users/new", method = RequestMethod.GET)
    public String addUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("formAction", "/users");
        return "c-edit-users";
    }

    @Action(value = USERS_BLOCK, changeType = USERS, changeMode = UPDATE)
    @RequestMapping(value = "/users/{id}/block", method = RequestMethod.GET)
    public String blockUser(@PathVariable Long id) {
        userService.blockUser(id);
        return "redirect:/users/" + id;
    }

    @Action(value = USERS_UNBLOCK, changeType = USERS, changeMode = UPDATE)
    @RequestMapping(value = "/users/{id}/unblock", method = RequestMethod.GET)
    public String unblockUser(@PathVariable Long id) {
        userService.unblockUser(id);
        return "redirect:/users/" + id;
    }

    @Action(value = USERS_RESET_PASSWORD, changeType = USERS, changeMode = UPDATE)
    @RequestMapping(value = "/users/{id}/reset", method = RequestMethod.GET)
    public String resetUserPassword(@PathVariable Long id) {
        userService.resetUserPassword(id);
        return "redirect:/users/" + id;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String createUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("formAction", "/users");
            return "c-edit-users";
        }
        String username = user.getUsername();
        User existsUser = userService.loadUser(username);
        if (existsUser != null) {
            model.addAttribute("formAction", "/users/add");
            model.addAttribute("existsUser", existsUser);
            return "c-edit-users";
        }
        Long id = userService.createUser(user);
        return "redirect:/users/" + id;
    }

    @Action(value = USERS_UPDATE, changeType = USERS, changeMode = UPDATE)
    @RequestMapping(value = "/users/{id}", method = RequestMethod.POST)
    public String updateUser(@Valid User user, BindingResult result, @PathVariable Long id, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("formAction", "/users/" + id);
            return "c-edit-users";
        }
        User oldUser = userService.loadUser(id);
        String oldUsername = oldUser.getUsername();
        String newUsername = user.getUsername();
        if (!oldUsername.equals(newUsername)) {
            User existsUser = userService.loadUser(newUsername);
            if (existsUser != null) {
                model.addAttribute("formAction", "/users/" + id);
                model.addAttribute("existsUser", existsUser);
                return "c-edit-users";
            }
        }
        oldUser.setUsername(user.getUsername());
        oldUser.setAuthorities(user.getAuthorities());
        oldUser.setEmail(user.getEmail());
        oldUser.setFirstName(user.getFirstName());
        oldUser.setLastName(user.getLastName());
        userService.updateUser(id, oldUser);
        return "redirect:/users/" + id;
    }

}
