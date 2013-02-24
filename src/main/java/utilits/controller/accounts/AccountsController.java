package utilits.controller.accounts;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import utilits.aspect.Action;
import utilits.aspect.ActionType;
import utilits.controller.ImportResultType;
import utilits.entity.Account;
import utilits.service.AccountsService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
@Controller
public class AccountsController {

    public static Logger logger = LoggerFactory.getLogger(AccountsController.class);

    private
    @Value("${random_password.default_length}")
    String generatedPasswordDefaultLength;

    @Resource(name = "accountsService")
    private AccountsService accountsService;

    @Action(type = ActionType.ACCOUNTS_IMPORT_PAGE)
    @RequestMapping(value = "/accounts/import", method = RequestMethod.GET)
    public String importAccounts() {
        logger.debug("Loading accounts import page...");
        return "import-accounts";
    }

    @Action(type = ActionType.ACCOUNTS_IMPORT)
    @RequestMapping(value = "/accounts/import", method = RequestMethod.POST)
    public String importAccounts(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        logger.info("Accounts importing, file " + file.getOriginalFilename());
        if (!file.isEmpty()) {
            logger.info("File size: " + file.getSize());
            try {
                ImportResultType<Account> result = accountsService.importFile(file.getInputStream());
                model.addAttribute("result", result);
                logger.info("Accounts importing success, added=" + result.getAddedCount() +
                        " accounts, exists=" + result.getExistsCount());
            } catch (Exception e) {
                model.addAttribute("error", true);
                logger.error("Accounts importing error...", e);
            }
        }
        return "import-accounts";
    }

    @Action(type = ActionType.ACCOUNTS_LIST)
    @RequestMapping(value = "/accounts", method = RequestMethod.GET)
    public String viewAccounts(@RequestParam(value = "search", required = false) String search, Model model) {
        logger.info("Received request to load accounts, search string: " + search);
        List<Account> accounts = accountsService.loadAccounts(search);
        model.addAttribute("accounts", accounts);
        if (StringUtils.isNotEmpty(search)) {
            model.addAttribute("search", search);
        }
        return "accounts";
    }

    @Action(type = ActionType.ACCOUNTS_VIEW_PAGE)
    @RequestMapping(value = "/accounts/view/{id}", method = RequestMethod.GET)
    public String viewAccount(@PathVariable Long id, Model model) {
        Account account = accountsService.loadAccount(id);
        model.addAttribute("account", account);
        return "view-account";
    }

    @Action(type = ActionType.ACCOUNTS_DELETE)
    @RequestMapping(value = "/accounts/delete/{id}", method = RequestMethod.GET)
    public String deleteAccount(@PathVariable Long id) {
        accountsService.deleteAccount(id);
        return "redirect:/ipstore/accounts";
    }

    @Action(type = ActionType.ACCOUNTS_BLOCK)
    @RequestMapping(value = "/accounts/block/{id}", method = RequestMethod.GET)
    public String blockAccount(@PathVariable Long id) {
        accountsService.blockAccount(id);
        return "redirect:/ipstore/accounts/view/" + id;
    }

    @Action(type = ActionType.ACCOUNTS_ACTIVATE)
    @RequestMapping(value = "/accounts/activate/{id}", method = RequestMethod.GET)
    public String activateAccount(@PathVariable Long id) {
        accountsService.activateAccount(id);
        return "redirect:/ipstore/accounts/view/" + id;
    }

    @Action(type = ActionType.ACCOUNTS_UNBLOCK)
    @RequestMapping(value = "/accounts/unblock/{id}", method = RequestMethod.GET)
    public String unBlockAccount(@PathVariable Long id) {
        accountsService.activateAccount(id);
        return "redirect:/ipstore/accounts/view/" + id;
    }

    @Action(type = ActionType.ACCOUNTS_EDIT_PAGE)
    @RequestMapping(value = "/accounts/edit/{id}", method = RequestMethod.GET)
    public String editAccount(@PathVariable Long id, Model model) {
        Account account = accountsService.loadAccount(id);
        model.addAttribute("account", account);
        model.addAttribute("edit", true);
        model.addAttribute("formAction", "/ipstore/accounts/edit/" + id);
        model.addAttribute("defaultPasswordLength", generatedPasswordDefaultLength);
        return "edit-account";
    }

    @Action(type = ActionType.ACCOUNTS_ADD_PAGE)
    @RequestMapping(value = "/accounts/add", method = RequestMethod.GET)
    public String addAccount(Model model) {
        Account account = new Account();
        model.addAttribute("edit", false);
        model.addAttribute("account", account);
        model.addAttribute("formAction", "/ipstore/accounts/add");
        model.addAttribute("defaultPasswordLength", generatedPasswordDefaultLength);
        return "edit-account";
    }

    @RequestMapping(value = "/accounts/add", method = RequestMethod.POST)
    public String createAccount(@Valid Account account, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("formAction", "/ipstore/accounts/add");
            model.addAttribute("defaultPasswordLength", generatedPasswordDefaultLength);
            return "edit-account";
        }
        String login = account.getLogin();
        Account existsAccount = accountsService.loadAccount(login);
        if (existsAccount != null) {
            model.addAttribute("edit", false);
            model.addAttribute("formAction", "/ipstore/accounts/add");
            model.addAttribute("defaultPasswordLength", generatedPasswordDefaultLength);
            model.addAttribute("existsAccount", existsAccount);
            return "edit-account";
        }
        Long id = accountsService.createAccount(account);
        return "redirect:/ipstore/accounts/view/" + id;
    }

    @Action(type = ActionType.ACCOUNTS_UPDATE)
    @RequestMapping(value = "/accounts/edit/{id}", method = RequestMethod.POST)
    public String updateAccount(@Valid Account account, BindingResult result, @PathVariable Long id, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("edit", true);
            model.addAttribute("formAction", "/ipstore/accounts/edit/" + id);
            model.addAttribute("defaultPasswordLength", generatedPasswordDefaultLength);
            return "edit-account";
        }
        accountsService.updateAccount(id, account);
        return "redirect:/ipstore/accounts/view/" + id;
    }
}
