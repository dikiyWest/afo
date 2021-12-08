package kz.atu.uit.afo.controller;

import kz.atu.uit.afo.domain.Activity;
import kz.atu.uit.afo.domain.User;
import kz.atu.uit.afo.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Controller
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;


    @Value("${upload.path}")
    private String uploadPath;

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

    @GetMapping("{activity}")
    public String activityAdd(
            @PathVariable Activity activity,
            Model model
    ){
        model.addAttribute("activity",activity);
        model.addAttribute("regions", activityService.getRegions());
        model.addAttribute("minDate",activityService.getNowDateFormat());
        return "activityAdd";
    }


    @PostMapping
    public String activitySave(
            @AuthenticationPrincipal User user,
            @Valid Activity activity,
            @RequestParam("file") MultipartFile file,
            @RequestParam(required = false, name = "activityId") Long activityId,
            @RequestParam("dateValueActivity")String dateActivity,
            @RequestParam("timeValueActivity")String timeActivity
            ) throws IOException {
        saveFile(activity,file);

        if(!activityService.activitySave(activity,user,activityId,dateActivity,timeActivity,file)){
            return "activityAdd";
        }

        return "redirect:/activity";
    }

    private void saveFile(@Valid Activity activity, @RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "_" + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFileName));
            activity.setFileName(resultFileName);
        }
    }
}
