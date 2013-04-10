package utilits.controller.communigate;

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
import utilits.aspect.Action;
import utilits.aspect.ActionType;
import utilits.entity.CommunigateDomain;
import utilits.service.CommunigateService;
import utilits.controller.ImportResultType;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;

/**
 * User: Sidorov Oleg
 * Date: 25.03.13
 */
@Controller
public class CommunigateController {

    public static Logger logger = LoggerFactory.getLogger(CommunigateController.class);

    @Resource(name = "communigateService")
    private CommunigateService communigateService;


    @RequestMapping(value = "/communigate", method = RequestMethod.GET)
    public String viewCommunigate(String search, Model model) {
        logger.info("Received request to load communigate domains.");
        model.addAttribute("communigateDomains", communigateService.loadCommunigate());
        return "communigate";
    }

    @RequestMapping(value = "/communigate/view/{id}", method = RequestMethod.GET)
    public String viewCommunigate(@PathVariable Long id, Model model) {
        CommunigateDomain communigateDomain = communigateService.loadCommunigate(id);
        model.addAttribute("communigateDomain", communigateDomain);
        return "view-communigate";
    }

    @RequestMapping(value = "/communigate/add", method = RequestMethod.GET)
    public String addCommunigate(Model model) {
        CommunigateDomain communigateDomain = new CommunigateDomain();
        model.addAttribute("edit", false);
        model.addAttribute("communigateDomain", communigateDomain);
        model.addAttribute("formAction", "/ipstore/communigate/add");
        return "edit-communigate";
    }

    @RequestMapping(value = "/communigate/add", method = RequestMethod.POST)
    public String createCommunigate(@Valid CommunigateDomain communigateDomain, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("formAction", "/ipstore/communigate/add");
            return "edit-communigate";
        }
        String domainName = communigateDomain.getDomainName();
        CommunigateDomain existsCommunigateDomain = communigateService.loadCommunigate(domainName);
        if (existsCommunigateDomain != null) {
            model.addAttribute("edit", false);
            model.addAttribute("formAction", "/ipstore/communigate/add");
            model.addAttribute("existsCommunigateDomain", existsCommunigateDomain);
            return "edit-communigate";
        }
        Long id = communigateService.createCommunigate(communigateDomain);
        return "redirect:/ipstore/communigate/view/" + id;
    }

    @RequestMapping(value = "/communigate/edit/{id}", method = RequestMethod.GET)
    public String editCommunigate(@PathVariable Long id, Model model) {
        CommunigateDomain communigateDomain = communigateService.loadCommunigate(id);
        model.addAttribute("communigateDomain", communigateDomain);
        model.addAttribute("edit", true);
        model.addAttribute("formAction", "/ipstore/communigate/edit/" + id);
        return "edit-communigate";
    }

    @RequestMapping(value = "/communigate/edit/{id}", method = RequestMethod.POST)
    public String updateCommunigate(@Valid CommunigateDomain communigateDomain, BindingResult result, @PathVariable Long id, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("formAction", "/ipstore/communigate/edit/" + id);
            return "edit-communigate";
        }
        CommunigateDomain oldCommunigate = communigateService.loadCommunigate(id);
        String oldDomainName = oldCommunigate.getDomainName();
        String newDomainName = communigateDomain.getDomainName();
        if (!oldDomainName.equals(newDomainName))
        {
            CommunigateDomain existsCommunigateDomain = communigateService.loadCommunigate(newDomainName);
            if (existsCommunigateDomain != null) {
                model.addAttribute("formAction", "/ipstore/communigate/edit/" + id);
                model.addAttribute("existsCommunigateDomain", existsCommunigateDomain);
                return "edit-communigate";
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
        return "redirect:/ipstore/communigate/view/" + id;
    }

    @RequestMapping(value = "/communigate/delete/{id}", method = RequestMethod.GET)
    public String deleteCommunigate(@PathVariable Long id) {
        communigateService.deleteCommunigate(id);
        return "redirect:/ipstore/communigate";
    }

    @RequestMapping(value = "/communigate/block/{id}", method = RequestMethod.GET)
    public String blockCommunigate(@PathVariable Long id) {
        communigateService.blockCommunigate(id);
        return "redirect:/ipstore/communigate/view/" + id;
    }

    @RequestMapping(value = "/communigate/activate/{id}", method = RequestMethod.GET)
    public String activateCommunigate(@PathVariable Long id) {
        communigateService.activateCommunigate(id);
        return "redirect:/ipstore/communigate/view/" + id;
    }

    @RequestMapping(value = "/communigate/unblock/{id}", method = RequestMethod.GET)
    public String unBlockCommunigate(@PathVariable Long id) {
        communigateService.activateCommunigate(id);
        return "redirect:/ipstore/communigate/view/" + id;
    }

    @RequestMapping(value = "/communigate/import", method = RequestMethod.GET)
    public String importCommunigate() {
        logger.debug("Loading communigate import page...");
        return "import-communigate";
    }

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
        return "import-communigate";
    }




}
