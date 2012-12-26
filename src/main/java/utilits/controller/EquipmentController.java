package utilits.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import utilits.entity.Equipment;
import utilits.service.EquipmentService;

import javax.annotation.Resource;
import java.util.List;


/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
@Controller
public class EquipmentController {

    protected static Logger logger = Logger.getLogger("controller");

    @Resource(name="equipmentService")
    private EquipmentService equipmentService;

    @RequestMapping(value = "/view", method = RequestMethod.GET)
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


}
