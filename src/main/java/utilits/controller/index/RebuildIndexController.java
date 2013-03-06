package utilits.controller.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import utilits.aspect.Action;
import utilits.aspect.ActionType;
import utilits.service.SearchService;

import javax.annotation.Resource;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
@Controller
public class RebuildIndexController {


    @Resource(name = "searchService")
    private SearchService searchService;

    @Action(type = ActionType.REBUILD_INDEX)
    @RequestMapping(value = "/rebuild", method = RequestMethod.GET)
    @ResponseBody
    public String rebuild() {
        return searchService.rebuildIndex();
    }

}
