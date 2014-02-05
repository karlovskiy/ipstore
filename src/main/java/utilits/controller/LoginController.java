package utilits.controller;

import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import utilits.aspect.Action;
import utilits.controller.users.ChangePassword;
import utilits.controller.users.ChangeUserInfo;
import utilits.entity.User;
import utilits.service.UsersService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static utilits.aspect.ActionType.*;
import static utilits.controller.users.CustomAuthenticationFailureHandler.CREDENTIALS_EXPIRED_USERNAME_KEY;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
@Controller
public class LoginController {

    @Resource(name = "userService")
    private UsersService userService;

    @Resource(name = "passwordEncoder")
    private PasswordEncoder passwordEncoder;

    @Action(value = LOGIN_PAGE)
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute(CREDENTIALS_EXPIRED_USERNAME_KEY) != null) {
            model.addAttribute("changePassword", new ChangePassword());
        }
        return "login";
    }

    @Action(value = LOGIN_FAILURE)
    @RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
    public String loginerror(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute(CREDENTIALS_EXPIRED_USERNAME_KEY) != null) {
            model.addAttribute("changePassword", new ChangePassword());
        }
        return "login";
    }

    @RequestMapping(value = "/mustchangepassword", method = RequestMethod.POST)
    public String mustChangePassword(@Valid ChangePassword changePassword, BindingResult result,
                                     HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute(CREDENTIALS_EXPIRED_USERNAME_KEY);
        if (username == null) {
            throw new RuntimeException("Luke, we are hacked!");
        }
        if (result.hasErrors()) {
            return "login";
        }
        String newPassword = changePassword.getNewPassword();
        if (!newPassword.equals(changePassword.getRepeatNewPassword())) {
            result.rejectValue("repeatNewPassword", "none", "Repeat new password!");
            return "login";
        }
        User user = userService.loadUser(username);
        String storedPasswordHash = user.getPassword();
        String enteredPasswordHash = passwordEncoder.encodePassword(changePassword.getOldPassword(), username);
        if (!storedPasswordHash.equals(enteredPasswordHash)) {
            result.rejectValue("oldPassword", "none", "Incorrect password!");
            return "login";
        }
        user.setPassword(passwordEncoder.encodePassword(newPassword, username));
        user.setCredentialsNonExpired(true);
        userService.updateUser(user.getId(), user);
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping(value = "/changepassword", method = RequestMethod.GET)
    public String changePassword(Model model) {
        model.addAttribute("changePassword", new ChangePassword());
        return "changepassword";
    }

    @RequestMapping(value = "/changepassword", method = RequestMethod.POST)
    public String changePassword(@Valid ChangePassword changePassword, BindingResult result,
                                 HttpServletRequest request) {
        if (result.hasErrors()) {
            return "changepassword";
        }
        String newPassword = changePassword.getNewPassword();
        if (!newPassword.equals(changePassword.getRepeatNewPassword())) {
            result.rejectValue("repeatNewPassword", "none", "Repeat new password!");
            return "changepassword";
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.loadUser(username);
        String storedPasswordHash = user.getPassword();
        String enteredPasswordHash = passwordEncoder.encodePassword(changePassword.getOldPassword(), username);
        if (!storedPasswordHash.equals(enteredPasswordHash)) {
            result.rejectValue("oldPassword", "none", "Incorrect password!");
            return "changepassword";
        }
        user.setPassword(passwordEncoder.encodePassword(newPassword, username));
        userService.updateUser(user.getId(), user);
        request.getSession().invalidate();
        return "redirect:/";
    }

    @RequestMapping(value = "/changeuserinfo", method = RequestMethod.GET)
    public String changeUserInfo(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.loadUser(username);
        ChangeUserInfo userInfo = new ChangeUserInfo();
        userInfo.setEmail(user.getEmail());
        userInfo.setFirstName(user.getFirstName());
        userInfo.setLastName(user.getLastName());
        model.addAttribute("changeUserInfo", userInfo);
        return "changeuserinfo";
    }

    @RequestMapping(value = "/changeuserinfo", method = RequestMethod.POST)
    public String changeUserInfo(@Valid ChangeUserInfo changeUserInfo, BindingResult result) {
        if (result.hasErrors()) {
            return "changeuserinfo";
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.loadUser(username);
        String storedPasswordHash = user.getPassword();
        String enteredPasswordHash = passwordEncoder.encodePassword(changeUserInfo.getPassword(), username);
        if (!storedPasswordHash.equals(enteredPasswordHash)) {
            result.rejectValue("password", "none", "Incorrect password!");
            return "changeuserinfo";
        }
        user.setEmail(changeUserInfo.getEmail());
        user.setFirstName(changeUserInfo.getFirstName());
        user.setLastName(changeUserInfo.getLastName());
        userService.updateUser(user.getId(), user);
        return "redirect:/";
    }

    @Action(value = LOGOUT)
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        return "login";
    }

    @Action(value = HOME_PAGE)
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "home";
    }

    @Action(value = CONTACT_PAGE)
    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public String contact() {
        return "contact";
    }

}
