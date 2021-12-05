package kz.atu.uit.afo.controller;

import kz.atu.uit.afo.domain.EducationProgramm;
import kz.atu.uit.afo.domain.Enrollee;
import kz.atu.uit.afo.repository.EnrolleeRepository;
import kz.atu.uit.afo.service.EnrolleeService;
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

import javax.validation.Valid;

@Controller
@RequestMapping("/enrollee")
public class EnrolleeController {

    @Autowired
    private EnrolleeService enrolleeService;

    @GetMapping
    public String enrolleeList(
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model,
            @PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<Enrollee> page = enrolleeService.findAll(pageable);
        model.addAttribute("page", page);
        model.addAttribute("pageSort", enrolleeService.getSort(page));
        model.addAttribute("url", enrolleeService.setUrl(filter));
        model.addAttribute("filter",filter);
        return "enrolleeList";
    }

    @GetMapping("enrolleeAdd/{check}")
    public String addEnrollee(
            Model model,
            @RequestParam(required = false,defaultValue = "") String iin,
            @PathVariable String check
    ){
        if("stay".equals(check)){
            model.addAttribute("check",check);
        } else {
            if (enrolleeService.checkIIN(iin)){
                check = "checking";
                model.addAttribute("error", "Поступающий уже соществует");
                model.addAttribute("check", check);
            } else{
                check = "next";
                model.addAttribute("iin", iin);
                model.addAttribute("error", null);
                model.addAttribute("educationProgramms", enrolleeService.getEducationProgramms());
                model.addAttribute("regions", enrolleeService.getRegions());
                model.addAttribute("check", check);
            }
        }
        return "enrolleeAdd";

    }



}
