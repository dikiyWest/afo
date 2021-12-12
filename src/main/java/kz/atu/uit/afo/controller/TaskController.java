package kz.atu.uit.afo.controller;

import kz.atu.uit.afo.domain.Activity;
import kz.atu.uit.afo.domain.Task;
import kz.atu.uit.afo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private  TaskService taskService;

    @GetMapping
    public String getTaskList(
            @RequestParam(required = false,defaultValue = "") String filter,
            Model model,
            @PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable
    ){
        Page<Task> page = taskService.findAll(pageable,filter);
        model.addAttribute("page",page);
        model.addAttribute("pageSort", taskService.getSort(page));
        model.addAttribute("url", taskService.setUrl(filter));
        model.addAttribute("filter", filter);
      return "taskList";
    }

    @GetMapping("{type}/taskAdd")
    public String addTask(
            @PathVariable String type,
            Model model
    ){
        taskService.getTypeList("activity").forEach(n-> System.out.println(n));
        model.addAttribute("type",type);
        model.addAttribute("minDate",taskService.getNowDateFormat());
        model.addAttribute("typeList",taskService.getTypeList(type));
      return "taskAdd";
    }
}
