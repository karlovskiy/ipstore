package utilits.controller.equipment;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import utilits.aspect.Action;
import utilits.controller.ImportResultType;
import utilits.entity.Equipment;
import utilits.service.EquipmentService;
import utilits.service.SearchService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;

import static utilits.aspect.ActionType.*;
import static utilits.aspect.change.ChangeMode.IMPORT;
import static utilits.aspect.change.ChangeMode.UPDATE;
import static utilits.aspect.change.ChangeType.EQUIPMENT;


/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
@Controller
public class EquipmentController {

    private static final Logger logger = LoggerFactory.getLogger(EquipmentController.class);
    private
    @Value("${random_password.default_length}")
    String generatedPasswordDefaultLength;
    @Resource(name = "equipmentService")
    private EquipmentService equipmentService;
    @Resource(name = "searchService")
    private SearchService searchService;

    @Action(value = EQUIPMENT_LIST)
    @RequestMapping(value = "/equipment", method = RequestMethod.GET)
    public String viewEquipment(@RequestParam(value = "search", required = false) String search, Model model) {
        logger.info("Received request to load equipment.");
        if (StringUtils.isNotEmpty(search)) {
            model.addAttribute("equipment", searchService.searchEquipment(search));
            model.addAttribute("search", search);
        } else {
            model.addAttribute("equipment", equipmentService.loadEquipments());
        }
        return "mfc-list-equipment";
    }

    @Action(value = EQUIPMENT_IMPORT_PAGE)
    @RequestMapping(value = "/equipment/import", method = RequestMethod.GET)
    public String importEquipment() {
        logger.debug("Received request to import equipment...");
        return "c-import-equipment";
    }

    @Action(value = EQUIPMENT_IMPORT, changeType = EQUIPMENT, changeMode = IMPORT)
    @RequestMapping(value = "/equipment/import", method = RequestMethod.POST)
    public String importEquipment(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        logger.info("Importing file " + file.getOriginalFilename());
        if (!file.isEmpty()) {
            logger.info("File size: " + file.getSize());
            try {
                ImportResultType<Equipment> result = equipmentService.importFile(file.getInputStream());
                model.addAttribute("result", result);
                logger.info("Importing success, added=" + result.getAddedCount() +
                        ", exists=" + result.getExistsCount());
            } catch (Exception e) {
                model.addAttribute("error", true);
                logger.error("Importing error...", e);
            }
        }
        return "c-import-equipment";
    }

    @Action(value = EQUIPMENT_VIEW_PAGE)
    @RequestMapping(value = "/equipment/{id}", method = RequestMethod.GET)
    public String viewEquipment(@PathVariable Long id, Model model) {
        Equipment equipment = equipmentService.loadEquipment(id);
        model.addAttribute("equipment", equipment);
        return "asc-view-equipment";
    }

    @Action(value = EQUIPMENT_ADD_PAGE)
    @RequestMapping(value = "/equipment/new", method = RequestMethod.GET)
    public String addEquipment(Model model) {
        Equipment equipment = new Equipment();
        model.addAttribute("equipment", equipment);
        model.addAttribute("formAction", "/equipment");
        model.addAttribute("defaultPasswordLength", generatedPasswordDefaultLength);
        return "c-edit-equipment";
    }

    @Action(value = EQUIPMENT_EDIT_PAGE)
    @RequestMapping(value = "/equipment/{id}/edit", method = RequestMethod.GET)
    public String editEquipment(@PathVariable Long id, Model model) {
        Equipment equipment = equipmentService.loadEquipment(id);
        model.addAttribute("equipment", equipment);
        model.addAttribute("formAction", "/equipment/" + id);
        model.addAttribute("defaultPasswordLength", generatedPasswordDefaultLength);
        return "c-edit-equipment";
    }

    @Action(value = EQUIPMENT_DELETE, changeType = EQUIPMENT, changeMode = UPDATE)
    @RequestMapping(value = "/equipment/{id}/delete", method = RequestMethod.GET)
    public String deleteEquipment(@PathVariable Long id) {
        equipmentService.deleteEquipment(id);
        return "redirect:/equipment";
    }

    @Action(value = EQUIPMENT_ACTIVATE, changeType = EQUIPMENT, changeMode = UPDATE)
    @RequestMapping(value = "/equipment/{id}/activate", method = RequestMethod.GET)
    public String activateEquipment(@PathVariable Long id) {
        equipmentService.activateEquipment(id);
        return "redirect:/equipment/" + id;
    }

    @Action(value = EQUIPMENT_ACTIVATE_NO_EXPIRED, changeType = EQUIPMENT, changeMode = UPDATE)
    @RequestMapping(value = "/equipment/{id}/activate_no_expired", method = RequestMethod.GET)
    public String activateNoExpiredEquipment(@PathVariable Long id) {
        equipmentService.activateWithNoExpiredEquipment(id);
        return "redirect:/equipment/" + id;
    }

    @RequestMapping(value = "/equipment", method = RequestMethod.POST)
    public String createEquipment(@Valid Equipment equipment, BindingResult result,
                                  @RequestParam("cf") MultipartFile config, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("formAction", "/equipment");
            model.addAttribute("defaultPasswordLength", generatedPasswordDefaultLength);
            return "c-edit-equipment";
        }
        String ipAddress = equipment.getIpAddress();
        Equipment existsEquipment = equipmentService.loadEquipment(ipAddress);
        if (existsEquipment != null) {
            model.addAttribute("formAction", "/equipment");
            model.addAttribute("defaultPasswordLength", generatedPasswordDefaultLength);
            model.addAttribute("existsEquipment", existsEquipment);
            return "c-edit-equipment";
        }
        Long id = equipmentService.createEquipment(equipment, config);
        return "redirect:/equipment/" + id;
    }

    @Action(value = EQUIPMENT_UPDATE, changeType = EQUIPMENT, changeMode = UPDATE)
    @RequestMapping(value = "/equipment/{id}", method = RequestMethod.POST)
    public String updateEquipment(@Valid Equipment equipment, BindingResult result, @PathVariable Long id,
                                  @RequestParam("cf") MultipartFile config, @RequestParam("cf_reset") String cfReset,
                                  Model model) {
        if (result.hasErrors()) {
            model.addAttribute("formAction", "/equipment/" + id);
            model.addAttribute("defaultPasswordLength", generatedPasswordDefaultLength);
            return "c-edit-equipment";
        }
        equipmentService.updateEquipment(id, equipment, config, "reset".equals(cfReset));
        return "redirect:/equipment/" + id;
    }

    @Action(value = GENERATE_PASSWORD)
    @RequestMapping(value = "/generate_password", method = RequestMethod.GET)
    public
    @ResponseBody
    String generatePassword(@RequestParam Integer length) {
        logger.info("Request for generating password, length: " + length);
        length = length != null ? length : Integer.valueOf(generatedPasswordDefaultLength);
        return RandomStringUtils.random(length, true, true);
    }

    @Action(value = LOAD_CONFIG)
    @RequestMapping(value = "/equipment/{id}/load_config", method = RequestMethod.GET)
    public HttpEntity<byte[]> loadConfig(@PathVariable Long id) {
        logger.info("Request for loading equipment config");
        return equipmentService.loadConfig(id);
    }
}
