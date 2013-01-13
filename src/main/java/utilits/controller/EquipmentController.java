package utilits.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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

    protected static Logger logger = Logger.getLogger(EquipmentController.class);

    @Resource(name = "equipmentService")
    private EquipmentService equipmentService;

    @RequestMapping(value = "/equipment", method = RequestMethod.GET)
    public String viewEquipment(Model model) {
        logger.debug("Received request to load equipment...");
        List<Equipment> equipment = equipmentService.getEquipment();
        model.addAttribute("equipment", equipment);
        return "equipment";
    }

    @RequestMapping(value = "/import", method = RequestMethod.GET)
    public String importEquipment() {
        logger.debug("Received request to import equipment...");
        return "import";
    }


    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public String importEquipment(@RequestParam("file") MultipartFile file,
                                  Model model) throws IOException {
        logger.info("Importing file " + file.getOriginalFilename());
        if (!file.isEmpty()) {
            logger.info("File size: " + file.getSize());
            boolean isImportSuccess = equipmentService.importFile(file.getInputStream());
            model.addAttribute("success", isImportSuccess);
            model.addAttribute("resultMessage", isImportSuccess ? "Importing success!" : "Importing error!");
        }

        return "import";
    }

    @RequestMapping(value = "/equipment/{id}", method = RequestMethod.GET)
    public String viewEquipment(@PathVariable Long id, Model model) {
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

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteEquipment(@PathVariable Long id) {
        equipmentService.deleteEquipment(id);
        return "redirect:/ipstore/equipment";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveEquipment(@Valid Equipment equipment, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("formAction", "/ipstore/save");
            model.addAttribute("backButtonUrl", "/ipstore/equipment");
            return "edit";
        }
        Long id = equipmentService.saveEquipment(equipment);
        return "redirect:/ipstore/equipment/" + id;
    }

    @RequestMapping(value = "/save/{id}", method = RequestMethod.POST)
    public String saveEquipment(@Valid Equipment equipment, BindingResult result, @PathVariable Long id, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("formAction", "/ipstore/save/" + id);
            model.addAttribute("backButtonUrl", "/ipstore/equipment/" + id);
            return "edit";
        }
        equipmentService.updateEquipment(id, equipment);
        return "redirect:/ipstore/equipment/" + id;
    }


}
