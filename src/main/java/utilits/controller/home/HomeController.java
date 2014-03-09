package utilits.controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import utilits.Utils;
import utilits.aspect.Action;
import utilits.breadcrumb.Breadcrumb;
import utilits.controller.users.Authority;
import utilits.service.HomeService;

import javax.annotation.Resource;

import static utilits.aspect.ActionType.HOME_PAGE;

/**
 * Here will be javadoc
 *
 * @author ravenstar
 * @since 4.0, 2/27/14
 */
@Controller
public class HomeController {

    @Resource(name = "homeService")
    private HomeService homeService;

    @Breadcrumb(label = "Home")
    @Action(value = HOME_PAGE)
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        if (Utils.hasRole(Authority.EQUIPMENT_VIEW)) {
            EquipmentWidget equipmentWidget = homeService.loadEquipmentsWidget();
            model.addAttribute("equipmentWidget", equipmentWidget);
        }
        if (Utils.hasRole(Authority.ACCOUNT_VIEW)) {
            AccountsWidget accountsWidget = homeService.loadAccountsWidget();
            model.addAttribute("accountsWidget", accountsWidget);
        }
        if (Utils.hasRole(Authority.COMMUNIGATE_VIEW)) {
            CommunigateWidget communigateWidget = homeService.loadCommunigateWidget();
            model.addAttribute("communigateWidget", communigateWidget);
        }
        if (Utils.hasRole(Authority.ROOT)) {
            ActionsWidget actionsWidget = homeService.loadActionsWidget();
            model.addAttribute("actionsWidget", actionsWidget);
        }
        return "c-home-main";
    }

}
