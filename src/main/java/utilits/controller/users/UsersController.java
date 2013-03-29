package utilits.controller.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import utilits.entity.User;
import utilits.service.UsersService;

import javax.annotation.Resource;
import javax.validation.Valid;

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

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String viewUsers(Model model) {
        logger.info("Received request to load users.");
        model.addAttribute("users", userService.loadUsers());
        return "users";
    }

    @RequestMapping(value = "/users/view/{id}", method = RequestMethod.GET)
    public String viewUser(@PathVariable Long id, Model model) {
        User user = userService.loadUser(id);
        model.addAttribute("user", user);
        return "view-user";
    }

    @RequestMapping(value = "/users/block/{id}", method = RequestMethod.GET)
    public String blockUser(@PathVariable Long id) {
        userService.blockUser(id);
        return "redirect:/ipstore/users/view/" + id;
    }

    @RequestMapping(value = "/users/unblock/{id}", method = RequestMethod.GET)
    public String unblockUser(@PathVariable Long id) {
        userService.unblockUser(id);
        return "redirect:/ipstore/users/view/" + id;
    }

    @RequestMapping(value = "/users/reset/{id}", method = RequestMethod.GET)
    public String resetUserPassword(@PathVariable Long id) {
        userService.resetUserPassword(id);
        return "redirect:/ipstore/users/view/" + id;
    }

    @RequestMapping(value = "/users/edit/{id}", method = RequestMethod.GET)
    public String editUser(@PathVariable Long id, Model model) {
        User user = userService.loadUser(id);
        model.addAttribute("user", user);
        model.addAttribute("formAction", "/ipstore/users/edit/" + id);
        return "edit-user";
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.GET)
    public String addUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("formAction", "/ipstore/users/add");
        return "edit-user";
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public String createUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("formAction", "/ipstore/users/add");
            return "edit-user";
        }
        String username = user.getUsername();
        User existsUser = userService.loadUser(username);
        if (existsUser != null) {
            model.addAttribute("formAction", "/ipstore/users/add");
            model.addAttribute("existsUser", existsUser);
            return "edit-user";
        }
        Long id = userService.createUser(user);
        return "redirect:/ipstore/users/view/" + id;
    }

    @RequestMapping(value = "/users/edit/{id}", method = RequestMethod.POST)
    public String updateUser(@Valid User user, BindingResult result, @PathVariable Long id, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("formAction", "/ipstore/users/edit/" + id);
            return "edit-user";
        }
        String oldUsername = userService.loadUser(id).getUsername();
        String newUsername = user.getUsername();
        if (!oldUsername.equals(newUsername)) {
            User existsUser = userService.loadUser(newUsername);
            if (existsUser != null) {
                model.addAttribute("formAction", "/ipstore/users/edit/" + id);
                model.addAttribute("existsUser", existsUser);
                return "edit-user";
            }
        }
        userService.updateUser(id, user);
        return "redirect:/ipstore/users/view/" + id;
    }

}
