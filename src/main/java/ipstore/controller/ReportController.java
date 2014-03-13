package ipstore.controller;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ipstore.aspect.Action;
import ipstore.entity.Account;
import ipstore.entity.CommunigateDomain;
import ipstore.entity.Equipment;
import ipstore.service.AccountsService;
import ipstore.service.CommunigateService;
import ipstore.service.EquipmentService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ipstore.aspect.ActionType.ACCOUNTS_EXPORT;
import static ipstore.aspect.ActionType.EQUIPMENT_EXPORT;
import static ipstore.aspect.ActionType.COMMUNIGATE_EXPORT;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
@Controller
public class ReportController {

    @Resource(name = "equipmentService")
    private EquipmentService equipmentService;

    @Resource(name = "accountsService")
    private AccountsService accountsService;

    @Resource(name = "communigateService")
    private CommunigateService communigateService;

    @Action(value = EQUIPMENT_EXPORT)
    @RequestMapping(value = "/equipment/export", method = RequestMethod.GET)
    public ModelAndView loadEquipmentReport() {
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        List<Equipment> equipments = equipmentService.loadEquipments();
        JRDataSource jrDataSource = new JRBeanCollectionDataSource(equipments);
        parameterMap.put("datasource", jrDataSource);
        return new ModelAndView("equipmentReport", parameterMap);
    }

    @Action(value = ACCOUNTS_EXPORT)
    @RequestMapping(value = "/accounts/export", method = RequestMethod.GET)
    public ModelAndView loadAccountsReport() {
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        List<Account> accounts = accountsService.loadAccounts();
        JRDataSource jrDataSource = new JRBeanCollectionDataSource(accounts);
        parameterMap.put("datasource", jrDataSource);
        return new ModelAndView("accountsReport", parameterMap);
    }

    @Action(value = COMMUNIGATE_EXPORT)
    @RequestMapping(value = "/communigate/export", method = RequestMethod.GET)
    public ModelAndView loadCommunigateReport() {
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        List<CommunigateDomain> communigateDomains = communigateService.loadCommunigate();
        JRDataSource jrDataSource = new JRBeanCollectionDataSource(communigateDomains);
        parameterMap.put("datasource", jrDataSource);
        return new ModelAndView("communigateReport", parameterMap);
    }
}
