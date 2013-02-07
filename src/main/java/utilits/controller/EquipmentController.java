package utilits.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import utilits.aspect.Action;
import utilits.aspect.ActionType;
import utilits.entity.Equipment;
import utilits.service.EquipmentService;

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
public class EquipmentController {

    public static Logger logger = Logger.getLogger(EquipmentController.class);

    private
    @Value("${random_password.default_length}")
    String generatedPasswordDefaultLength;

    @Resource(name = "equipmentService")
    private EquipmentService equipmentService;

    @Action(type = ActionType.EQUIPMENT_LIST)
    @RequestMapping(value = "/equipment", method = RequestMethod.GET)
    public String viewEquipment(@RequestParam(value = "search", required = false) String search, Model model) {
        logger.info("Received request to load equipment, search string: " + search);
        List<Equipment> equipment = equipmentService.loadEquipments(search);
        model.addAttribute("equipment", equipment);
        if (StringUtils.isNotEmpty(search)) {
            model.addAttribute("search", search);
        }
        return "equipment";
    }

    @Action(type = ActionType.ACCESS_IMPORT_PAGE)
    @RequestMapping(value = "/import", method = RequestMethod.GET)
    public String importEquipment() {
        logger.debug("Received request to import equipment...");
        return "import";
    }

    @Action(type = ActionType.EQUIPMENT_IMPORT)
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public String importEquipment(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        logger.info("Importing file " + file.getOriginalFilename());
        if (!file.isEmpty()) {
            logger.info("File size: " + file.getSize());
            try {
                String resultMessage = equipmentService.importFile(file.getInputStream());
                model.addAttribute("success", true);
                model.addAttribute("resultMessage", resultMessage);
            } catch (Exception e) {
                logger.error("Importing error...", e);
                model.addAttribute("success", false);
                model.addAttribute("resultMessage", "Importing error!");
            }
        }
        return "import";
    }

    @Action(type = ActionType.ACCESS_VIEW_PAGE)
    @RequestMapping(value = "/equipment/{id}", method = RequestMethod.GET)
    public String viewEquipment(@PathVariable Long id, Model model) {
        Equipment equipment = equipmentService.loadEquipment(id);
        model.addAttribute("equipment", equipment);
        return "view";
    }

    @Action(type = ActionType.ACCESS_ADD_PAGE)
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addEquipment(Model model) {
        Equipment equipment = new Equipment();
        model.addAttribute("equipment", equipment);
        model.addAttribute("formAction", "/ipstore/save");
        model.addAttribute("defaultPasswordLength", generatedPasswordDefaultLength);
        return "edit";
    }

    @Action(type = ActionType.ACCESS_EDIT_PAGE)
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editEquipment(@PathVariable Long id, Model model) {
        Equipment equipment = equipmentService.loadEquipment(id);
        model.addAttribute("equipment", equipment);
        model.addAttribute("formAction", "/ipstore/save/" + id);
        model.addAttribute("defaultPasswordLength", generatedPasswordDefaultLength);
        return "edit";
    }

    @Action(type = ActionType.EQUIPMENT_DELETE)
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteEquipment(@PathVariable Long id) {
        equipmentService.deleteEquipment(id);
        return "redirect:/ipstore/equipment";
    }

    @Action(type = ActionType.EQUIPMENT_CREATE)
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String createEquipment(@Valid Equipment equipment, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("formAction", "/ipstore/save");
            model.addAttribute("backButtonUrl", "/ipstore/equipment");
            model.addAttribute("defaultPasswordLength", generatedPasswordDefaultLength);
            return "edit";
        }
        Long id = equipmentService.saveEquipment(equipment);
        return "redirect:/ipstore/equipment/" + id;
    }

    @Action(type = ActionType.EQUIPMENT_UPDATE)
    @RequestMapping(value = "/save/{id}", method = RequestMethod.POST)
    public String updateEquipment(@Valid Equipment equipment, BindingResult result, @PathVariable Long id, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("formAction", "/ipstore/save/" + id);
            model.addAttribute("backButtonUrl", "/ipstore/equipment/" + id);
            model.addAttribute("defaultPasswordLength", generatedPasswordDefaultLength);
            return "edit";
        }
        equipmentService.updateEquipment(id, equipment);
        return "redirect:/ipstore/equipment/" + id;
    }

    @Action(type = ActionType.GENERATE_PASSWORD)
    @RequestMapping(value = "/generate_password", method = RequestMethod.GET)
    public
    @ResponseBody
    String generatePassword(@RequestParam Integer length) {
        logger.info("Request fo generating password, length: " + length);
        length = length != null ? length : Integer.valueOf(generatedPasswordDefaultLength);
        return RandomStringUtils.random(length, true, true);
    }
}
