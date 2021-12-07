package kz.atu.uit.afo.controller;

import kz.atu.uit.afo.domain.Activity;
import kz.atu.uit.afo.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping
    public String activityList(
            @RequestParam(required = false,defaultValue = "") String filter,
            Model model,
            @PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable
            ){
        Page<Activity> page = activityService.findAll(pageable,filter);
        model.addAttribute("page",page);
        model.addAttribute("pageSort", activityService.getSort(page));
        model.addAttribute("url", activityService.setUrl(filter));
        model.addAttribute("filter", filter);
        return "activityList";
    }

    @GetMapping("activityAdd")
    public String activityAdd(
        Model model
    ){
        model.addAttribute("regions", activityService.getRegions());
        model.addAttribute("minDate",activityService.getNowDateFormat());
        return "activityAdd";
    }
}
