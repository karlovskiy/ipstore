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
import utilits.entity.User;
import utilits.service.UsersService;

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

    @Action(value = USERS_LIST)
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String viewUsers(Model model) {
        logger.info("Received request to load users.");
        model.addAttribute("users", userService.loadUsers());
        return "c-list-users";
    }

    @Action(value = USERS_VIEW_PAGE)
    @RequestMapping(value = "/users/view/{id}", method = RequestMethod.GET)
    public String viewUser(@PathVariable Long id, Model model) {
        User user = userService.loadUser(id);
        model.addAttribute("user", user);
        return "c-view-users";
    }

    @Action(value = USERS_EDIT_PAGE)
    @RequestMapping(value = "/users/edit/{id}", method = RequestMethod.GET)
    public String editUser(@PathVariable Long id, Model model) {
        User user = userService.loadUser(id);
        model.addAttribute("user", user);
        model.addAttribute("formAction", "/users/edit/" + id);
        return "c-edit-users";
    }

    @Action(value = USERS_ADD_PAGE)
    @RequestMapping(value = "/users/add", method = RequestMethod.GET)
    public String addUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("formAction", "/users/add");
        return "c-edit-users";
    }

    @Action(value = USERS_BLOCK, changeType = USERS, changeMode = UPDATE)
    @RequestMapping(value = "/users/block/{id}", method = RequestMethod.GET)
    public String blockUser(@PathVariable Long id) {
        userService.blockUser(id);
        return "redirect:/users/view/" + id;
    }

    @Action(value = USERS_UNBLOCK, changeType = USERS, changeMode = UPDATE)
    @RequestMapping(value = "/users/unblock/{id}", method = RequestMethod.GET)
    public String unblockUser(@PathVariable Long id) {
        userService.unblockUser(id);
        return "redirect:/users/view/" + id;
    }

    @Action(value = USERS_RESET_PASSWORD, changeType = USERS, changeMode = UPDATE)
    @RequestMapping(value = "/users/reset/{id}", method = RequestMethod.GET)
    public String resetUserPassword(@PathVariable Long id) {
        userService.resetUserPassword(id);
        return "redirect:/users/view/" + id;
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public String createUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("formAction", "/users/add");
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
        return "redirect:/users/view/" + id;
    }

    @Action(value = USERS_UPDATE, changeType = USERS, changeMode = UPDATE)
    @RequestMapping(value = "/users/edit/{id}", method = RequestMethod.POST)
    public String updateUser(@Valid User user, BindingResult result, @PathVariable Long id, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("formAction", "/users/edit/" + id);
            return "c-edit-users";
        }
        User oldUser = userService.loadUser(id);
        String oldUsername = oldUser.getUsername();
        String newUsername = user.getUsername();
        if (!oldUsername.equals(newUsername)) {
            User existsUser = userService.loadUser(newUsername);
            if (existsUser != null) {
                model.addAttribute("formAction", "/users/edit/" + id);
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
        return "redirect:/users/view/" + id;
    }

}
