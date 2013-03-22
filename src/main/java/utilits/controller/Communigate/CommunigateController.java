package utilits.controller.Communigate;


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
import utilits.entity.CommunigateClass5;
import utilits.service.CommunigateService;
import utilits.service.SearchService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Sidorov Oleg
 * Date: 22.03.13
 */
public class CommunigateController {

    public static Logger logger = LoggerFactory.getLogger(CommunigateClass5.class);

    @Resource(name = "communigateService")
    private CommunigateService communigateService;

    @Resource(name = "searchService")
    private SearchService searchService;

    @Action(type = ActionType.COMMUNIGATE_IMPORT_PAGE)
    @RequestMapping(value = "/communigate/import", method = RequestMethod.GET)
    public String importCommunigate() {
        logger.debug("Loading accounts import page...");
        return "import-communigate";
    }

    @Action(type = ActionType.COMMUNIGATE_IMPORT)
    @RequestMapping(value = "/communigate/import", method = RequestMethod.POST)
    public String importCommunigate(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        logger.info("Communigate importing, file " + file.getOriginalFilename());
        if (!file.isEmpty()) {
            logger.info("File size: " + file.getSize());
            try {
                ImportResultType<CommunigateClass5> result = communigateService.importFile(file.getInputStream());
                model.addAttribute("result", result);
                logger.info("Communigate importing success, added=" + result.getAddedCount() +
                        " records, exists=" + result.getExistsCount());
            } catch (Exception e) {
                model.addAttribute("error", true);
                logger.error("Communigate importing error...", e);
            }
        }
        return "import-communigate";
    }

    @Action(type = ActionType.COMMUNIGATE_LIST)
    @RequestMapping(value = "/communigate", method = RequestMethod.GET)
    public String viewCommunigate(@RequestParam(value = "search", required = false) String search, Model model) {
        logger.info("Received request to load communigates.");
        if (StringUtils.isNotEmpty(search)) {
            model.addAttribute("communigate", searchService.searchAccounts(search));
            model.addAttribute("search", search);
        } else {
            model.addAttribute("communigate", communigateService.loadCommunigate());
        }
        return "communigate";
    }

    @Action(type = ActionType.COMMUNIGATE_VIEW_PAGE)
    @RequestMapping(value = "/communigate/view/{id}", method = RequestMethod.GET)
    public String viewCommunigate(@PathVariable Long id, Model model) {
        CommunigateClass5 communigateClass5 = communigateService.loadAccount(id);
        model.addAttribute("communigateClass5", communigateClass5);
        return "view-communigate";
    }

    @Action(type = ActionType.COMMUNIGATE_DELETE)
    @RequestMapping(value = "/communigate/delete/{id}", method = RequestMethod.GET)
    public String deleteCommunigate(@PathVariable Long id) {
        communigateService.deleteCommunigate(id);
        return "redirect:/ipstore/communigate";
    }

    @Action(type = ActionType.COMMUNIGATE_BLOCK)
    @RequestMapping(value = "/communigate/block/{id}", method = RequestMethod.GET)
    public String blockCommunigate(@PathVariable Long id) {
        communigateService.blockCommunigate(id);
        return "redirect:/ipstore/communigate/view/" + id;
    }

    @Action(type = ActionType.COMMUNIGATE_ACTIVATE)
    @RequestMapping(value = "/communigate/activate/{id}", method = RequestMethod.GET)
    public String activateCommunigate(@PathVariable Long id) {
        communigateService.activateCommunigate(id);
        return "redirect:/ipstore/accounts/view/" + id;
    }

    @Action(type = ActionType.COMMUNIGATE_UNBLOCK)
    @RequestMapping(value = "/communigate/unblock/{id}", method = RequestMethod.GET)
    public String unBlockCommunigate(@PathVariable Long id) {
        communigateService.activateCommunigate(id);
        return "redirect:/ipstore/communigate/view/" + id;
    }

    @Action(type = ActionType.COMMUNIGATE_EDIT_PAGE)
    @RequestMapping(value = "/communigate/edit/{id}", method = RequestMethod.GET)
    public String editCommunigate(@PathVariable Long id, Model model) {
        CommunigateClass5 communigateClass5 = CommunigateService.loadCommunigate(id);
        model.addAttribute("communigate", communigateClass5);
        model.addAttribute("edit", true);
        model.addAttribute("formAction", "/ipstore/communigate/edit/" + id);
        return "edit-communigate";
    }

    @Action(type = ActionType.COMMUNIGATE_ADD_PAGE)
    @RequestMapping(value = "/communigate/add", method = RequestMethod.GET)
    public String addCommunigate(Model model) {
        CommunigateClass5 communigateClass5 = new CommunigateClass5();
    //    model.addAttribute("edit", false);
        model.addAttribute("communigate", communigateClass5);
        model.addAttribute("formAction", "/ipstore/communigate/add");
        return "edit-communigate";
    }

    @RequestMapping(value = "/communigate/add", method = RequestMethod.POST)
    public String createCommunigate(@Valid CommunigateClass5 communigateClass5, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("formAction", "/ipstore/communigate/add");
         //   model.addAttribute("defaultPasswordLength", generatedPasswordDefaultLength);
            return "edit-communigate";
        }
        String login = communigateClass5.getLogin();
        CommunigateClass5 existsCommunigateClass5 = CommunigateService.loadCommunigate(login);
        if (existsCommunigateClass5 != null) {
            model.addAttribute("edit", false);
            model.addAttribute("formAction", "/ipstore/communigate/add");
        //    model.addAttribute("defaultPasswordLength", generatedPasswordDefaultLength);
            model.addAttribute("existsCommunigateClass5", existsCommunigateClass5);
            return "edit-communigate";
        }
        Long id = CommunigateService.createCommunigate(communigateClass5);
        return "redirect:/ipstore/communigate/view/" + id;
    }

    @Action(type = ActionType.COMMUNIGATE_UPDATE)
    @RequestMapping(value = "/communigate/edit/{id}", method = RequestMethod.POST)
    public String updateCommunigate(@Valid CommunigateClass5 communigateClass5, BindingResult result, @PathVariable Long id, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("edit", true);
            model.addAttribute("formAction", "/ipstore/communigate/edit/" + id);
       //     model.addAttribute("defaultPasswordLength", generatedPasswordDefaultLength);
            return "edit-communigate";
        }
        CommunigateService.updateCommunigate(id, communigateClass5);
        return "redirect:/ipstore/communigate/view/" + id;
    }







}
