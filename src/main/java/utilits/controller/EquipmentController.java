package utilits.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import utilits.aspect.Action;
import utilits.entity.Equipment;
import utilits.service.EquipmentService;
import utilits.service.SearchService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;

import static utilits.aspect.ActionType.*;
import static utilits.aspect.change.ChangeType.EQUIPMENT;
import static utilits.aspect.change.ChangeMode.IMPORT;
import static utilits.aspect.change.ChangeMode.UPDATE;


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
        return "equipment";
    }

    @Action(value = EQUIPMENT_IMPORT_PAGE)
    @RequestMapping(value = "/equipment/import", method = RequestMethod.GET)
    public String importEquipment() {
        logger.debug("Received request to import equipment...");
        return "import-equipment";
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
        return "import-equipment";
    }

    @Action(value = EQUIPMENT_VIEW_PAGE)
    @RequestMapping(value = "/equipment/view/{id}", method = RequestMethod.GET)
    public String viewEquipment(@PathVariable Long id, Model model) {
        Equipment equipment = equipmentService.loadEquipment(id);
        model.addAttribute("equipment", equipment);
        return "view-equipment";
    }

    @Action(value = EQUIPMENT_ADD_PAGE)
    @RequestMapping(value = "/equipment/add", method = RequestMethod.GET)
    public String addEquipment(Model model) {
        Equipment equipment = new Equipment();
        model.addAttribute("equipment", equipment);
        model.addAttribute("formAction", "/ipstore/equipment/save");
        model.addAttribute("defaultPasswordLength", generatedPasswordDefaultLength);
        return "edit-equipment";
    }

    @Action(value = EQUIPMENT_EDIT_PAGE)
    @RequestMapping(value = "/equipment/edit/{id}", method = RequestMethod.GET)
    public String editEquipment(@PathVariable Long id, Model model) {
        Equipment equipment = equipmentService.loadEquipment(id);
        model.addAttribute("equipment", equipment);
        model.addAttribute("formAction", "/ipstore/equipment/save/" + id);
        model.addAttribute("defaultPasswordLength", generatedPasswordDefaultLength);
        return "edit-equipment";
    }

    @Action(value = EQUIPMENT_DELETE, changeType = EQUIPMENT, changeMode = UPDATE)
    @RequestMapping(value = "/equipment/delete/{id}", method = RequestMethod.GET)
    public String deleteEquipment(@PathVariable Long id) {
        equipmentService.deleteEquipment(id);
        return "redirect:/ipstore/equipment";
    }

    @Action(value = EQUIPMENT_ACTIVATE, changeType = EQUIPMENT, changeMode = UPDATE)
    @RequestMapping(value = "/equipment/activate/{id}", method = RequestMethod.GET)
    public String activateEquipment(@PathVariable Long id) {
        equipmentService.activateEquipment(id);
        return "redirect:/ipstore/equipment/view/" + id;
    }

    @RequestMapping(value = "/equipment/save", method = RequestMethod.POST)
    public String createEquipment(@Valid Equipment equipment, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("formAction", "/ipstore/equipment/save");
            model.addAttribute("defaultPasswordLength", generatedPasswordDefaultLength);
            return "edit-equipment";
        }
        String ipAddress = equipment.getIpAddress();
        Equipment existsEquipment = equipmentService.loadEquipment(ipAddress);
        if (existsEquipment != null) {
            model.addAttribute("formAction", "/ipstore/equipment/save");
            model.addAttribute("defaultPasswordLength", generatedPasswordDefaultLength);
            model.addAttribute("existsEquipment", existsEquipment);
            return "edit-equipment";
        }
        Long id = equipmentService.createEquipment(equipment);
        return "redirect:/ipstore/equipment/view/" + id;
    }

    @Action(value = EQUIPMENT_UPDATE, changeType = EQUIPMENT, changeMode = UPDATE)
    @RequestMapping(value = "/equipment/save/{id}", method = RequestMethod.POST)
    public String updateEquipment(@Valid Equipment equipment, BindingResult result, @PathVariable Long id, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("formAction", "/ipstore/equipment/save/" + id);
            model.addAttribute("defaultPasswordLength", generatedPasswordDefaultLength);
            return "edit-equipment";
        }
        equipmentService.updateEquipment(id, equipment);
        return "redirect:/ipstore/equipment/view/" + id;
    }

    @Action(value = GENERATE_PASSWORD)
    @RequestMapping(value = "/generate_password", method = RequestMethod.GET)
    public
    @ResponseBody
    String generatePassword(@RequestParam Integer length) {
        logger.info("Request fo generating password, length: " + length);
        length = length != null ? length : Integer.valueOf(generatedPasswordDefaultLength);
        return RandomStringUtils.random(length, true, true);
    }
}
