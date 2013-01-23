package utilits.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
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
import javax.servlet.http.HttpServletRequest;
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
    public static int DEFAULT_PASSWORD_LENGTH = 15;

    @Resource(name = "equipmentService")
    private EquipmentService equipmentService;

    @Action(type = ActionType.EQUIPMENT_LIST)
    @RequestMapping(value = "/equipment", method = RequestMethod.GET)
    public String viewEquipment(@RequestParam(value = "search", required = false) String search, Model model,
                                @SuppressWarnings("unused") HttpServletRequest request) {
        logger.info("Received request to load equipment, search string: " + search);
        List<Equipment> equipment = equipmentService.getEquipments(search);
        model.addAttribute("equipment", equipment);
        if (StringUtils.isNotEmpty(search)) {
            model.addAttribute("search", search);
        }
        return "equipment";
    }

    @RequestMapping(value = "/import", method = RequestMethod.GET)
    public String importEquipment() {
        logger.debug("Received request to import equipment...");
        return "import";
    }


    @Action(type = ActionType.EQUIPMENT_IMPORT)
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public String importEquipment(@RequestParam("file") MultipartFile file,
                                  Model model, @SuppressWarnings("unused") HttpServletRequest request) throws IOException {
        logger.info("Importing file " + file.getOriginalFilename());
        if (!file.isEmpty()) {
            logger.info("File size: " + file.getSize());
            boolean isImportSuccess = equipmentService.importFile(file.getInputStream());
            model.addAttribute("success", isImportSuccess);
            model.addAttribute("resultMessage", isImportSuccess ? "Importing success!" : "Importing error!");
        }

        return "import";
    }

    @Action(type = ActionType.EQUIPMENT_VIEW)
    @RequestMapping(value = "/equipment/{id}", method = RequestMethod.GET)
    public String viewEquipment(@PathVariable Long id, Model model,
                                @SuppressWarnings("unused") HttpServletRequest request) {
        Equipment equipment = equipmentService.getEquipment(id);
        model.addAttribute("equipment", equipment);
        return "view";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addEquipment(Model model) {
        Equipment equipment = new Equipment();
        model.addAttribute("equipment", equipment);
        model.addAttribute("formAction", "/ipstore/save");
        model.addAttribute("backButtonUrl", "/ipstore/equipment");
        return "edit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editEquipment(@PathVariable Long id, Model model) {
        Equipment equipment = equipmentService.getEquipment(id);
        model.addAttribute("equipment", equipment);
        model.addAttribute("formAction", "/ipstore/save/" + id);
        model.addAttribute("backButtonUrl", "/ipstore/equipment/" + id);
        return "edit";
    }

    @Action(type = ActionType.EQUIPMENT_DELETE)
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteEquipment(@PathVariable Long id,
                                  @SuppressWarnings("unused") HttpServletRequest request) {
        equipmentService.deleteEquipment(id);
        return "redirect:/ipstore/equipment";
    }

    @Action(type = ActionType.EQUIPMENT_EDIT)
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveEquipment(@Valid Equipment equipment, BindingResult result, Model model,
                                @SuppressWarnings("unused") HttpServletRequest request) {
        if (result.hasErrors()) {
            model.addAttribute("formAction", "/ipstore/save");
            model.addAttribute("backButtonUrl", "/ipstore/equipment");
            return "edit";
        }
        Long id = equipmentService.saveEquipment(equipment);
        return "redirect:/ipstore/equipment/" + id;
    }

    @Action(type = ActionType.EQUIPMENT_EDIT)
    @RequestMapping(value = "/save/{id}", method = RequestMethod.POST)
    public String saveEquipment(@Valid Equipment equipment, BindingResult result, @PathVariable Long id,
                                Model model, @SuppressWarnings("unused") HttpServletRequest request) {
        if (result.hasErrors()) {
            model.addAttribute("formAction", "/ipstore/save/" + id);
            model.addAttribute("backButtonUrl", "/ipstore/equipment/" + id);
            return "edit";
        }
        equipmentService.updateEquipment(id, equipment);
        return "redirect:/ipstore/equipment/" + id;
    }

    @Action(type = ActionType.GENERATE_PASSWORD)
    @RequestMapping(value = "/generate_password", method = RequestMethod.GET)
    public
    @ResponseBody
    String generatePassword(@RequestParam Integer length,
                            @SuppressWarnings("unused") HttpServletRequest request) {
        logger.info("Request fo generating password, length: " + length);
        length = length != null ? length : DEFAULT_PASSWORD_LENGTH;
        return RandomStringUtils.random(length, true, true);
    }
}
