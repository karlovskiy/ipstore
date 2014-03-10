package utilits.controller.communigate;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import utilits.aspect.Action;
import utilits.breadcrumb.Breadcrumb;
import utilits.entity.CommunigateDomain;
import utilits.message.Message;
import utilits.message.MessageStatus;
import utilits.message.MessageType;
import utilits.service.CommunigateService;
import utilits.controller.ImportResultType;
import utilits.service.SearchService;
import utilits.spring.BreadcrumbInterceptor;


import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;

import static utilits.aspect.ActionType.*;
import static utilits.aspect.ActionType.COMMUNIGATE_IMPORT;
import static utilits.aspect.ActionType.COMMUNIGATE_IMPORT_PAGE;
import static utilits.aspect.change.ChangeMode.UPDATE;
import static utilits.aspect.change.ChangeMode.IMPORT;
import static utilits.aspect.change.ChangeType.COMMUNIGATE;

/**
 * User: Sidorov Oleg
 * Date: 25.03.13
 */
@Controller
public class CommunigateController {

    public static Logger logger = LoggerFactory.getLogger(CommunigateController.class);

    @Resource(name = "communigateService")
    private CommunigateService communigateService;

    @Resource(name = "searchService")
    private SearchService searchService;

    @Breadcrumb(label = "Domains")
    @Action(value = COMMUNIGATE_LIST)
    @RequestMapping(value = "/communigate", method = RequestMethod.GET)
    public String viewCommunigate(String search, Model model) {
        logger.info("Received request to load communigate domains.");
        if (StringUtils.isNotEmpty(search)) {
            model.addAttribute("communigateDomains", searchService.searchCommunigateDomains(search));
            model.addAttribute("search", search);
        } else {
            model.addAttribute("communigateDomains", communigateService.loadCommunigate());
        }
        return "mfc-list-communigate";
    }

    @Breadcrumb
    @Action(value = COMMUNIGATE_VIEW_PAGE)
    @RequestMapping(value = "/communigate/{id}", method = RequestMethod.GET)
    public String viewCommunigate(@PathVariable Long id, Model model) {
        CommunigateDomain communigateDomain = communigateService.loadCommunigate(id);
        model.addAttribute("communigateDomain", communigateDomain);
        model.addAttribute(BreadcrumbInterceptor.TITLE_ATTRIBUTE, "View domain ( " + communigateDomain.getDomainName() + "  )");
        return "c-view-communigate";
    }

    @Breadcrumb(label = "Add domain")
    @Action(value = COMMUNIGATE_ADD_PAGE)
    @RequestMapping(value = "/communigate/new", method = RequestMethod.GET)
    public String addCommunigate(Model model) {
        CommunigateDomain communigateDomain = new CommunigateDomain();
        model.addAttribute("edit", false);
        model.addAttribute("communigateDomain", communigateDomain);
        model.addAttribute("formAction", "/communigate");
        return "c-edit-communigate";
    }

    @RequestMapping(value = "/communigate", method = RequestMethod.POST)
    public String createCommunigate(@Valid CommunigateDomain communigateDomain, BindingResult result, Model model,
                                    RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("formAction", "/communigate");
            return "c-edit-communigate";
        }
        String domainName = communigateDomain.getDomainName();
        String tryPrefix = communigateDomain.getTryPrefix();
        CommunigateDomain existsCommunigateDomain = communigateService.loadCommunigate(domainName, tryPrefix);
        if (existsCommunigateDomain != null) {
            model.addAttribute("edit", false);
            model.addAttribute("formAction", "/communigate");
            model.addAttribute("existsCommunigateDomain", existsCommunigateDomain);
            return "c-edit-communigate";
        }
        Long id = communigateService.createCommunigate(communigateDomain);
        redirectAttributes.addFlashAttribute(Message.MESSAGE_ATTRIBUTE,
                new Message("Domain " + communigateDomain.getDomainName() + " successfully created!",
                        MessageType.TEXT, MessageStatus.SUCCESS));
        return "redirect:/communigate/" + id;
    }

    @Breadcrumb
    @Action(value = COMMUNIGATE_EDIT_PAGE)
    @RequestMapping(value = "/communigate/{id}/edit", method = RequestMethod.GET)
    public String editCommunigate(@PathVariable Long id, Model model) {
        CommunigateDomain communigateDomain = communigateService.loadCommunigate(id);
        model.addAttribute("communigateDomain", communigateDomain);
        model.addAttribute("edit", true);
        model.addAttribute("formAction", "/communigate/" + id);
        model.addAttribute(BreadcrumbInterceptor.TITLE_ATTRIBUTE, "Edit domain ( " + communigateDomain.getDomainName() + "  )");
        return "c-edit-communigate";
    }

    @Action(value = COMMUNIGATE_UPDATE, changeType = COMMUNIGATE, changeMode = UPDATE)
    @RequestMapping(value = "/communigate/{id}", method = RequestMethod.POST)
    public String updateCommunigate(@Valid CommunigateDomain communigateDomain, BindingResult result,
                                    @PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("formAction", "/communigate/" + id);
            return "c-edit-communigate";
        }
        CommunigateDomain oldCommunigate = communigateService.loadCommunigate(id);
        String oldDomainName = oldCommunigate.getDomainName();
        String newDomainName = communigateDomain.getDomainName();
        if (!oldDomainName.equals(newDomainName)) {
            CommunigateDomain existsCommunigateDomain = communigateService.loadCommunigate(newDomainName);
            if (existsCommunigateDomain != null) {
                model.addAttribute("formAction", "/communigate/" + id);
                model.addAttribute("existsCommunigateDomain", existsCommunigateDomain);
                return "c-edit-communigate";
            }
        }
        String oldTryPrefix = oldCommunigate.getTryPrefix();
        String newTryPrefix = communigateDomain.getTryPrefix();
        if (!oldTryPrefix.equals(newTryPrefix)) {
            CommunigateDomain existsCommunigateDomain = communigateService.loadCommunigateByTryPrefix(newTryPrefix);
            if (existsCommunigateDomain != null) {
                model.addAttribute("formAction", "/communigate/" + id);
                model.addAttribute("existsCommunigateDomain", existsCommunigateDomain);
                return "c-edit-communigate";
            }
        }
        oldCommunigate.setDomainName(communigateDomain.getDomainName());
        oldCommunigate.setTryPrefix(communigateDomain.getTryPrefix());
        oldCommunigate.setClientName(communigateDomain.getClientName());
        oldCommunigate.setTicket(communigateDomain.getTicket());
        oldCommunigate.setNumberLine(communigateDomain.getNumberLine());
        oldCommunigate.setDiskCapacity(communigateDomain.getDiskCapacity());
        oldCommunigate.setService(communigateDomain.getService());
        oldCommunigate.setContract(communigateDomain.getContract());
        oldCommunigate.setLogin(communigateDomain.getLogin());
        oldCommunigate.setDate(communigateDomain.getDate());
        oldCommunigate.setDescription(communigateDomain.getDescription());

        communigateService.updateCommunigate(id, communigateDomain);
        redirectAttributes.addFlashAttribute(Message.MESSAGE_ATTRIBUTE,
                new Message("Domain " + communigateDomain.getDomainName() + " successfully updated!",
                        MessageType.TEXT, MessageStatus.SUCCESS));
        return "redirect:/communigate/" + id;
    }

    @Action(value = COMMUNIGATE_DELETE, changeType = COMMUNIGATE, changeMode = UPDATE)
    @RequestMapping(value = "/communigate/{id}/delete", method = RequestMethod.GET)
    public String deleteCommunigate(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        CommunigateDomain domain = communigateService.deleteCommunigate(id);
        redirectAttributes.addFlashAttribute(Message.MESSAGE_ATTRIBUTE,
                new Message(domain.getDomainName(), MessageType.TEXT_WITH_URL, MessageStatus.DANGER,
                        "/communigate/" + domain.getId(), "Domain ", "  successfully deleted!"));
        return "redirect:/communigate";
    }

    @Action(value = COMMUNIGATE_BLOCK, changeType = COMMUNIGATE, changeMode = UPDATE)
    @RequestMapping(value = "/communigate/{id}/block", method = RequestMethod.GET)
    public String blockCommunigate(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        CommunigateDomain domain = communigateService.blockCommunigate(id);
        redirectAttributes.addFlashAttribute(Message.MESSAGE_ATTRIBUTE,
                new Message("Domain " + domain.getDomainName() + " succesfully blocked",
                        MessageType.TEXT, MessageStatus.WARNING));
        return "redirect:/communigate/" + id;
    }

    @Action(value = COMMUNIGATE_ACTIVATE, changeType = COMMUNIGATE, changeMode = UPDATE)
    @RequestMapping(value = "/communigate/{id}/activate", method = RequestMethod.GET)
    public String activateCommunigate(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        CommunigateDomain domain = communigateService.activateCommunigate(id);
        redirectAttributes.addFlashAttribute(Message.MESSAGE_ATTRIBUTE,
                new Message("Domain " + domain.getDomainName() + " successfully activated",
                        MessageType.TEXT, MessageStatus.SUCCESS));
        return "redirect:/communigate/" + id;
    }

    @Action(value = COMMUNIGATE_UNBLOCK, changeType = COMMUNIGATE, changeMode = UPDATE)
    @RequestMapping(value = "/communigate/{id}/unblock", method = RequestMethod.GET)
    public String unBlockCommunigate(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        CommunigateDomain domain = communigateService.activateCommunigate(id);
        redirectAttributes.addFlashAttribute(Message.MESSAGE_ATTRIBUTE,
                new Message("Domain " + domain.getDomainName() + " successfully unblocked",
                        MessageType.TEXT, MessageStatus.WARNING));
        return "redirect:/communigate/" + id;
    }

    @Breadcrumb(label = "Import equipment")
    @Action(value = COMMUNIGATE_IMPORT_PAGE)
    @RequestMapping(value = "/communigate/import", method = RequestMethod.GET)
    public String importCommunigate() {
        logger.debug("Loading communigate import page...");
        return "c-import-communigate";
    }

    @Action(value = COMMUNIGATE_IMPORT, changeType = COMMUNIGATE, changeMode = IMPORT)
    @RequestMapping(value = "/communigate/import", method = RequestMethod.POST)
    public String importCommunigate(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        logger.info("Communigate importing, file " + file.getOriginalFilename());
        if (!file.isEmpty()) {
            logger.info("File size: " + file.getSize());
            try {
                ImportResultType<CommunigateDomain> result = communigateService.importFile(file.getInputStream());
                model.addAttribute("result", result);
                logger.info("Communigate importing success, added=" + result.getAddedCount() +
                        " communigate, exists=" + result.getExistsCount());
            } catch (Exception e) {
                model.addAttribute("error", true);
                logger.error("Communigate importing error...", e);
            }
        }
        return "c-import-communigate";
    }




}
