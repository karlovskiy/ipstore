package utilits.controller.capacity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import utilits.entity.capacity.CapacityNumber;
import utilits.entity.capacity.CapacityType;
import utilits.service.CapacityService;

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

    @RequestMapping(value = "/capacity", method = RequestMethod.GET)
    public String listCapacity(@Valid CapacityForm capacityForm, @SuppressWarnings("unused") BindingResult result, Model model) {
        logger.info("Received request to load number capacity list");
        Map<String, String> capacityTypes = capacityService.loadAllCapacityNames();
        model.addAttribute("capacityTypes", capacityTypes);
        if (capacityForm.isEmptyCapacityType()) {
            capacityForm.setCapacityType("ALL");
            return "capacity";
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
        return "capacity";
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
        return "capacity";
    }

    @RequestMapping(value = "/capacity/add", method = RequestMethod.GET)
    public String addCapacityPage(Model model) {
        CapacityType capacityType = new CapacityType();
        model.addAttribute("capacityType", capacityType);
        model.addAttribute("action", "/ipstore/capacity/add");
        return "edit-capacity";
    }

    @RequestMapping(value = "/capacity/add", method = RequestMethod.POST)
    public String addCapacity(@Valid CapacityType capacityType, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("action", "/ipstore/capacity/add");
            return "edit-capacity";
        }
        String name = capacityType.getName();
        Long existsId = capacityService.loadExistsCapacity(name);
        if (existsId != null) {
            model.addAttribute("existsId", existsId);
            model.addAttribute("action", "/ipstore/capacity/add");
            return "edit-capacity";
        }
        capacityService.createCapacityType(capacityType);
        return "redirect:/ipstore/capacity";
    }

    @RequestMapping(value = "/capacity/edit/{capacityId}", method = RequestMethod.GET)
    public String editCapacityType(@PathVariable Long capacityId, Model model) {
        CapacityType capacityType = capacityService.loadCapacityType(capacityId);
        model.addAttribute("capacityType", capacityType);
        model.addAttribute("action", "/ipstore/capacity/edit/" + capacityId);
        return "edit-capacity";
    }

    @RequestMapping(value = "/capacity/edit/{capacityId}", method = RequestMethod.POST)
    public String updateCapacityType(@Valid CapacityType capacityType, BindingResult result, @PathVariable Long capacityId, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("action", "/ipstore/capacity/edit/" + capacityId);
            return "edit-capacity";
        }
        String name = capacityType.getName();
        Long existsId = capacityService.loadExistsCapacity(name);
        if (existsId != null && !existsId.equals(capacityId)) {
            model.addAttribute("existsId", existsId);
            model.addAttribute("action", "/ipstore/capacity/edit/" + capacityId);
            return "edit-capacity";
        }
        capacityService.updateCapacityType(capacityId, capacityType);
        return "redirect:/ipstore/capacity";
    }

    @RequestMapping(value = "/capacity/{capacityId}/add", method = RequestMethod.GET)
    public String addCapacityNumberPage(@PathVariable Long capacityId, Model model) {
        CapacityType capacityType = capacityService.loadCapacityType(capacityId);
        model.addAttribute("capacityType", capacityType);
        CapacityNumber capacityNumber = new CapacityNumber();
        model.addAttribute("capacityNumber", capacityNumber);
        model.addAttribute("action", "/ipstore/capacity/" + capacityId + "/add");
        return "edit-capactity-number";
    }

    @RequestMapping(value = "/capacity/{capacityId}/add", method = RequestMethod.POST)
    public String addCapacityNumber(@Valid CapacityNumber capacityNumber, BindingResult result, @PathVariable Long capacityId,
                                    Model model) {
        if (result.hasErrors()) {
            model.addAttribute("action", "/ipstore/capacity/" + capacityId + "/add");
            return "edit-capactity-number";
        }
        String number = capacityNumber.getNumber();
        Long existsId = capacityService.loadExistsCapacityNumber(number);
        if (existsId != null) {
            model.addAttribute("existsId", existsId);
            model.addAttribute("action", "/ipstore/capacity/" + capacityId + "/add");
            return "edit-capactity-number";
        }
        Long capacityNumberId = capacityService.createCapacityNumber(capacityNumber, capacityId);
        return "redirect:/ipstore/capacity/number/view/" + capacityNumberId;
    }

    @RequestMapping(value = "/capacity/number/view/{id}", method = RequestMethod.GET)
    public String viewCapacityNumber(@PathVariable Long id, Model model) {
        CapacityNumber capacityNumber = capacityService.loadCapacityNumber(id);
        model.addAttribute("capacityNumber", capacityNumber);
        return "view-capactity-number";
    }

    @RequestMapping(value = "/capacity/number/edit/{id}", method = RequestMethod.GET)
    public String editCapacityNumber(@PathVariable Long id, Model model) {
        CapacityNumber capacityNumber = capacityService.loadCapacityNumber(id);
        model.addAttribute("capacityNumber", capacityNumber);
        model.addAttribute("action", "/ipstore/capacity/number/edit/" + id);
        return "edit-capactity-number";
    }

    @RequestMapping(value = "/capacity/number/edit/{id}", method = RequestMethod.POST)
    public String updateCapacityNumber(@Valid CapacityNumber capacityNumber, BindingResult result, @PathVariable Long id, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("action", "/ipstore/capacity/number/edit/" + id);
            return "edit-capactity-number";
        }
        String number = capacityNumber.getNumber();
        Long existsId = capacityService.loadExistsCapacityNumber(number);
        if (existsId != null && !existsId.equals(id)) {
            model.addAttribute("existsId", existsId);
            model.addAttribute("action", "/ipstore/capacity/number/edit/" + id);
            return "edit-capactity-number";
        }
        capacityService.updateCapacityNumber(id, capacityNumber);
        return "redirect:/ipstore/capacity/number/view/" + id;
    }

    @RequestMapping(value = "/capacity/number/free/{id}", method = RequestMethod.GET)
    public String freeCapacityNumber(@PathVariable Long id) {
        capacityService.updateCapacityNumberStatus(id, CapacityNumberStatus.FREE);
        return "redirect:/ipstore/capacity/number/view/" + id;
    }

    @RequestMapping(value = "/capacity/number/sell/{id}", method = RequestMethod.GET)
    public String sellCapacityNumber(@PathVariable Long id) {
        capacityService.updateCapacityNumberStatus(id, CapacityNumberStatus.SELL);
        return "redirect:/ipstore/capacity/number/view/" + id;
    }

    @RequestMapping(value = "/capacity/number/reserve/{id}", method = RequestMethod.GET)
    public String reserveCapacityNumber(@PathVariable Long id) {
        capacityService.updateCapacityNumberStatus(id, CapacityNumberStatus.RESERVED);
        return "redirect:/ipstore/capacity/number/view/" + id;
    }

    @RequestMapping(value = "/capacity/number/unreserve/{id}", method = RequestMethod.GET)
    public String unreserveCapacityNumber(@PathVariable Long id) {
        capacityService.updateCapacityNumberStatus(id, CapacityNumberStatus.FREE);
        return "redirect:/ipstore/capacity/number/view/" + id;
    }

    @RequestMapping(value = "/capacity/number/test/{id}", method = RequestMethod.GET)
    public String testCapacityNumber(@PathVariable Long id) {
        capacityService.updateCapacityNumberStatus(id, CapacityNumberStatus.TEST);
        return "redirect:/ipstore/capacity/number/view/" + id;
    }

    @RequestMapping(value = "/capacity/number/transfer/{id}", method = RequestMethod.GET)
    public String transferCapacityNumber(@PathVariable Long id) {
        capacityService.updateCapacityNumberStatus(id, CapacityNumberStatus.TRANSFERED);
        return "redirect:/ipstore/capacity/number/view/" + id;
    }
}
