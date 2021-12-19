package kz.atu.uit.afo.controller;

import kz.atu.uit.afo.domain.User;
import kz.atu.uit.afo.repository.UserRepository;
import kz.atu.uit.afo.service.ActivityService;
import kz.atu.uit.afo.service.ContactService;
import kz.atu.uit.afo.service.EnrolleeService;
import kz.atu.uit.afo.service.UserService;
import kz.atu.uit.afo.service.reportService.UserExcelReporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private UserService userService;

    @Autowired
    private EnrolleeService enrolleeService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private ActivityService activityService;


    @GetMapping("{type}")
    public String getUserReport(
            @PathVariable String type,
            Model model
            )
    {
        if(!type.equals("user"))
            model.addAttribute("users",userService.findAll());
        model.addAttribute("type",type);
        return "report";
    }

    @GetMapping("/{type}/export")
    public void exportToCSV(
            @PathVariable String type,
            @RequestParam(required = false,defaultValue = "") String dateMin,
            @RequestParam(required = false,name = "userId") User user,
            @RequestParam(required = false,defaultValue = "") String dateMax,
            HttpServletResponse response) throws IOException {

        if(type.equals("user")){
            userService.getReportExcel(dateMin,dateMax,response);
        }else if(type.equals("contact")){
            contactService.getReportExcel(dateMin,dateMax,response,user);
        }else if(type.equals("activity")){
            activityService.getReportExcel(dateMin,dateMax,response,user);
        }else if(type.equals("enrollee")){
            enrolleeService.getReportExcel(dateMin,dateMax,response,user);
        }



    }

}
