package utilits.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import utilits.entity.Equipment;
import utilits.service.EquipmentService;

import javax.annotation.Resource;
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
            model.addAttribute("result", isImportSuccess ? "Importing success!" : "Importing error!");
        }

        return "import";
    }

}
