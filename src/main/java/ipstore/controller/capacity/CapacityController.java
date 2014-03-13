package ipstore.controller.capacity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ipstore.breadcrumb.Breadcrumb;
import ipstore.entity.capacity.CapacityNumber;
import ipstore.entity.capacity.CapacityType;
import ipstore.service.CapacityService;
import ipstore.spring.BreadcrumbInterceptor;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 3.0, 8/20/13
 */
@Controller
public class CapacityController {

    private static final Logger logger = LoggerFactory.getLogger(CapacityController.class);
    @Resource(name = "capacityService")
    private CapacityService capacityService;

    @Breadcrumb(label = "Capacity")
    @RequestMapping(value = "/capacity", method = RequestMethod.GET)
    public String listCapacity(@Valid CapacityForm capacityForm, @SuppressWarnings("unused") BindingResult result, Model model) {
        logger.info("Received request to load number capacity list");
        Map<String, String> capacityTypes = capacityService.loadAllCapacityNames();
        model.addAttribute("capacityTypes", capacityTypes);
        if (capacityForm.isEmptyCapacityType()) {
            capacityForm.setCapacityType("ALL");
            return "c-list-capacity";
        }
        if (!capacityTypes.containsKey(capacityForm.getCapacityType())) {
            capacityForm.setCapacityType("ALL");
        }
        if (capacityForm.isEmptyKeyword()) {
            String capacityType = capacityForm.getCapacityType();
            if ("ALL".equals(capacityType)) {
                List<CapacityType> list = capacityService.loadAllCapacityTypesWithNumbers();
                model.addAttribute("result", list);
            } else {
                Long id = Long.valueOf(capacityType);
                List<CapacityType> list = capacityService.loadCapacityTypeWithNumbers(id);
                model.addAttribute("result", list);
            }
        }
        return "c-list-capacity";
    }

    @RequestMapping(value = "/capacity/{id}", method = RequestMethod.GET)
    public String loadCapacity(@PathVariable Long id, Model model) {
        CapacityForm capacityForm = new CapacityForm();
        capacityForm.setCapacityType(String.valueOf(id));
        model.addAttribute("capacityForm", capacityForm);
        Map<String, String> capacityTypes = capacityService.loadAllCapacityNames();
        model.addAttribute("capacityTypes", capacityTypes);
        List<CapacityType> list = capacityService.loadCapacityTypeWithNumbers(id);
        model.addAttribute("result", list);
        return "c-list-capacity";
    }

    @Breadcrumb(label = "Add capacity")
    @RequestMapping(value = "/capacity/new", method = RequestMethod.GET)
    public String addCapacityPage(Model model) {
        CapacityType capacityType = new CapacityType();
        model.addAttribute("capacityType", capacityType);
        model.addAttribute("action", "/capacity");
        return "c-edit-capacity";
    }

    @RequestMapping(value = "/capacity", method = RequestMethod.POST)
    public String addCapacity(@Valid CapacityType capacityType, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("action", "/capacity");
            return "c-edit-capacity";
        }
        String name = capacityType.getName();
        Long existsId = capacityService.loadExistsCapacity(name);
        if (existsId != null) {
            model.addAttribute("existsId", existsId);
            model.addAttribute("action", "/capacity");
            return "c-edit-capacity";
        }
        capacityService.createCapacityType(capacityType);
        return "redirect:/capacity";
    }

    @Breadcrumb
    @RequestMapping(value = "/capacity/{capacityId}/edit", method = RequestMethod.GET)
    public String editCapacityType(@PathVariable Long capacityId, Model model) {
        CapacityType capacityType = capacityService.loadCapacityType(capacityId);
        model.addAttribute("capacityType", capacityType);
        model.addAttribute("action", "/capacity/" + capacityId);
        model.addAttribute(BreadcrumbInterceptor.TITLE_ATTRIBUTE, "Edit capacity ( " + capacityType.getName() + " )");
        return "c-edit-capacity";
    }

    @RequestMapping(value = "/capacity/{capacityId}", method = RequestMethod.POST)
    public String updateCapacityType(@Valid CapacityType capacityType, BindingResult result, @PathVariable Long capacityId, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("action", "/capacity/" + capacityId);
            return "c-edit-capacity";
        }
        String name = capacityType.getName();
        Long existsId = capacityService.loadExistsCapacity(name);
        if (existsId != null && !existsId.equals(capacityId)) {
            model.addAttribute("existsId", existsId);
            model.addAttribute("action", "/capacity/" + capacityId);
            return "c-edit-capacity";
        }
        capacityService.updateCapacityType(capacityId, capacityType);
        return "redirect:/capacity";
    }

    @Breadcrumb(label = "Add capacity number")
    @RequestMapping(value = "/capacity/{capacityId}/numbers/new", method = RequestMethod.GET)
    public String addCapacityNumberPage(@PathVariable Long capacityId, Model model) {
        CapacityType capacityType = capacityService.loadCapacityType(capacityId);
        model.addAttribute("capacityType", capacityType);
        CapacityNumber capacityNumber = new CapacityNumber();
        model.addAttribute("capacityNumber", capacityNumber);
        model.addAttribute("action", "/capacity/" + capacityId + "/numbers");
        return "c-edit-capacity_number";
    }

    @RequestMapping(value = "/capacity/{capacityId}/numbers", method = RequestMethod.POST)
    public String addCapacityNumber(@Valid CapacityNumber capacityNumber, BindingResult result, @PathVariable Long capacityId,
                                    Model model) {
        if (result.hasErrors()) {
            model.addAttribute("action", "/capacity/" + capacityId + "/numbers");
            return "c-edit-capacity_number";
        }
        String number = capacityNumber.getNumber();
        Long existsId = capacityService.loadExistsCapacityNumber(number);
        if (existsId != null) {
            model.addAttribute("existsId", existsId);
            model.addAttribute("action", "/capacity/" + capacityId + "/numbers");
            return "c-edit-capacity_number";
        }
        Long capacityNumberId = capacityService.createCapacityNumber(capacityNumber, capacityId);
        return "redirect:/capacity/" + capacityId + "/numbers/" + capacityNumberId;
    }

    @Breadcrumb
    @RequestMapping(value = "/capacity/{capacityId}/numbers/{id}", method = RequestMethod.GET)
    public String viewCapacityNumber(@PathVariable Long capacityId, @PathVariable Long id, Model model) {
        CapacityNumber capacityNumber = capacityService.loadCapacityNumber(id);
        model.addAttribute("capacityNumber", capacityNumber);
        model.addAttribute(BreadcrumbInterceptor.TITLE_ATTRIBUTE, "View capacity number ( " + capacityNumber.getNumber() + " )");
        return "c-view-capacity_number";
    }

    @Breadcrumb
    @RequestMapping(value = "/capacity/{capacityId}/numbers/{id}/edit", method = RequestMethod.GET)
    public String editCapacityNumber(@PathVariable Long capacityId, @PathVariable Long id, Model model) {
        CapacityNumber capacityNumber = capacityService.loadCapacityNumber(id);
        model.addAttribute("capacityNumber", capacityNumber);
        model.addAttribute("action", "/capacity/" + capacityId + "/numbers/" + id);
        model.addAttribute(BreadcrumbInterceptor.TITLE_ATTRIBUTE, "Edit capacity number ( " + capacityNumber.getNumber() + " )");
        return "c-edit-capacity_number";
    }

    @RequestMapping(value = "/capacity/{capacityId}/numbers/{id}", method = RequestMethod.POST)
    public String updateCapacityNumber(@Valid CapacityNumber capacityNumber, BindingResult result,
                                       @PathVariable Long capacityId, @PathVariable Long id, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("action", "/capacity/" + capacityId + "/numbers/" + id);
            return "c-edit-capacity_number";
        }
        String number = capacityNumber.getNumber();
        Long existsId = capacityService.loadExistsCapacityNumber(number);
        if (existsId != null && !existsId.equals(id)) {
            model.addAttribute("existsId", existsId);
            model.addAttribute("action", "/capacity/" + capacityId + "/numbers/" + id);
            return "c-edit-capacity_number";
        }
        capacityService.updateCapacityNumber(id, capacityNumber);
        return "redirect:/capacity/" + capacityId + "/numbers/" + id;
    }

    @RequestMapping(value = "/capacity/{capacityId}/numbers/{id}/free", method = RequestMethod.GET)
    public String freeCapacityNumber(@PathVariable Long capacityId, @PathVariable Long id) {
        capacityService.updateCapacityNumberStatus(id, CapacityNumberStatus.FREE);
        return "redirect:/capacity/" + capacityId + "/numbers/" + id;
    }

    @RequestMapping(value = "/capacity/{capacityId}/numbers/{id}/sell", method = RequestMethod.GET)
    public String sellCapacityNumber(@PathVariable Long capacityId, @PathVariable Long id) {
        capacityService.updateCapacityNumberStatus(id, CapacityNumberStatus.SELL);
        return "redirect:/capacity/" + capacityId + "/numbers/" + id;
    }

    @RequestMapping(value = "/capacity/{capacityId}/numbers/{id}/reserve", method = RequestMethod.GET)
    public String reserveCapacityNumber(@PathVariable Long capacityId, @PathVariable Long id) {
        capacityService.updateCapacityNumberStatus(id, CapacityNumberStatus.RESERVED);
        return "redirect:/capacity/" + capacityId + "/numbers/" + id;
    }

    @RequestMapping(value = "/capacity/{capacityId}/numbers/{id}/unreserve", method = RequestMethod.GET)
    public String unreserveCapacityNumber(@PathVariable Long capacityId, @PathVariable Long id) {
        capacityService.updateCapacityNumberStatus(id, CapacityNumberStatus.FREE);
        return "redirect:/capacity/" + capacityId + "/numbers/" + id;
    }

    @RequestMapping(value = "/capacity/{capacityId}/numbers/{id}/test", method = RequestMethod.GET)
    public String testCapacityNumber(@PathVariable Long capacityId, @PathVariable Long id) {
        capacityService.updateCapacityNumberStatus(id, CapacityNumberStatus.TEST);
        return "redirect:/capacity/" + capacityId + "/numbers/" + id;
    }

    @RequestMapping(value = "/capacity/{capacityId}/numbers/{id}/transfer", method = RequestMethod.GET)
    public String transferCapacityNumber(@PathVariable Long capacityId, @PathVariable Long id) {
        capacityService.updateCapacityNumberStatus(id, CapacityNumberStatus.TRANSFERED);
        return "redirect:/capacity/" + capacityId + "/numbers/" + id;
    }
}
