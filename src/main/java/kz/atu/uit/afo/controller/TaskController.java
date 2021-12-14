package kz.atu.uit.afo.controller;

import kz.atu.uit.afo.domain.Activity;
import kz.atu.uit.afo.domain.Contact;
import kz.atu.uit.afo.domain.Task;
import kz.atu.uit.afo.domain.User;
import kz.atu.uit.afo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public String getTaskList(
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model,
            @PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<Task> page = taskService.findAll(pageable, filter);
        model.addAttribute("page", page);
        model.addAttribute("pageSort", taskService.getSort(page));
        model.addAttribute("url", taskService.setUrl(filter));
        model.addAttribute("filter", filter);
        model.addAttribute("nowLocalDateTime", LocalDateTime.now());
        return "taskList";
    }

    @GetMapping("{type}/{task}")
    public String viewType(
            @PathVariable Task task,
            @PathVariable String type,
            Model model
    ){
        model.addAttribute("type", type);
        model.addAttribute("task",task);
        model.addAttribute("minDate", taskService.getNowDateFormat());
        model.addAttribute("typeList", taskService.getTypeList(type));
        return "viewType";
    }

    @GetMapping("{task}")
    public String editTask(
            @PathVariable Task task,
            Model model
    ){
        model.addAttribute("type", taskService.getType(task));
        model.addAttribute("task",task);
        model.addAttribute("minDate", taskService.getNowDateFormat());
        model.addAttribute("typeList", taskService.getTypeList(taskService.getType(task)));
        return "taskAdd";
    }

    @GetMapping("{type}/taskAdd")
    public String addTask(
            @PathVariable String type,
            Model model
    ) {
        model.addAttribute("type", type);
        model.addAttribute("minDate", taskService.getNowDateFormat());
        model.addAttribute("typeList", taskService.getTypeList(type));
        return "taskAdd";
    }

    @PostMapping
    public String saveTask(
            @AuthenticationPrincipal User user,
            @Valid Task task,
            @RequestParam(required = false, name = "taskId") Long taskId,
            @RequestParam("dateValueTask") String dateValueTask,
            @RequestParam("timeValueTask") String timeValueTask
    ) {
        if(!taskService.taskSave(task,user,taskId,dateValueTask,timeValueTask)){
            return "taskAdd";
        }

        return "redirect:/task";
    }


}
