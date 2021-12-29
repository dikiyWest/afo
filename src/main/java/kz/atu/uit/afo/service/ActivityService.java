package kz.atu.uit.afo.service;

import kz.atu.uit.afo.domain.Activity;
import kz.atu.uit.afo.domain.Contact;
import kz.atu.uit.afo.domain.Region;
import kz.atu.uit.afo.domain.User;
import kz.atu.uit.afo.repository.ActivityRepository;
import kz.atu.uit.afo.repository.ContactRepository;
import kz.atu.uit.afo.repository.RegionRepository;
import kz.atu.uit.afo.repository.UserRepository;
import kz.atu.uit.afo.service.reportService.ActivityExcelReporter;
import kz.atu.uit.afo.service.reportService.ContactExcelReporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserRepository userRepository;

    public Page<Activity> findAll(Pageable pageable, String filter) {
        if (filter != null && !filter.isEmpty()) {
            Page<Activity> activitys = activityRepository.findByNameActivityContaining(filter, pageable);
            if (activitys == null || activitys.isEmpty()) {
                    return activityRepository.findAll(pageable);
            }else {
                return activitys;
            }
        } else {
            return activityRepository.findAll(pageable);
        }
    }

    public Object getSort(Page<Activity> page) {
        return page.getSort().toString().replace(": ", ",");
    }

    public Object setUrl(String filter, HttpServletRequest request) {
        if (filter != null && !filter.isEmpty()) {
            return request.getRequestURI() + "?filter=" + filter + "&";
        } else {
            return request.getRequestURI() +"?";
        }
    }

    public Iterable<Region> getRegions() {
        return regionRepository.findAll();
    }

    public String getNowDateFormat(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return formatter.format(LocalDateTime.now());
    }

    public boolean activitySave(Activity activity, User user, User toUser, Long activityId, String dateActivity, String timeActivity, MultipartFile file) {
    if(activity.getId()==null && activityId == null){
        setDateActivity(activity,dateActivity,timeActivity);
        activity.setAuthor(user);
        if(toUser!=null)
            activity.setAuthor(toUser);
        activityRepository.save(activity);
        return true;
    }else {
        Activity activityFromDb = activityRepository.getById(activityId);
        if(checkDateChange(activityFromDb.getDateActivity(),dateActivity,timeActivity)){
            setDateActivity(activity,dateActivity,timeActivity);
        }else {
            activity.setDateActivity(activityFromDb.getDateActivity());
        }
        if (file == null || file.getOriginalFilename().isEmpty()) {
            activity.setFileName(activityFromDb.getFileName());
        }
        activity.setId(activityFromDb.getId());
        activity.setCreatedAt(activityFromDb.getCreatedAt());
        activity.setAuthor(activityFromDb.getAuthor());

        if(activityFromDb.getAuthor() != toUser)
            activity.setAuthor(toUser);

        activityRepository.save(activity);
        return true;
    }


    }

    private void setDateActivity(Activity activity,String dateActivity,String timeActivity){
        if(dateActivity != null && timeActivity != null){
            LocalDate datePart = LocalDate.parse(dateActivity);
            LocalTime timePart = LocalTime.parse(timeActivity);
            activity.setDateActivity(LocalDateTime.of(datePart, timePart));
        }
    }

    private boolean checkDateChange(LocalDateTime dateTimeActivity,String dateActivity, String timeActivity ){
        if(dateActivity != null && timeActivity != null) {
            LocalDate datePart = LocalDate.parse(dateActivity);
            LocalTime timePart = LocalTime.parse(timeActivity);
            if ((!datePart.equals(dateTimeActivity.toLocalDate()) || datePart != dateTimeActivity.toLocalDate()) ||
                    !timePart.equals(dateTimeActivity.toLocalTime())){
                if(timeActivity.equals("00:00")){
                    return false;
                }
                return true;
            }
        }
        return false;
    }


    public void getReportExcel(String dateMin, String dateMax, HttpServletResponse response, User user) throws IOException {
        List<Activity> activities;
        LocalDate datePartMax;

        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=activity_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        LocalTime time = LocalTime.parse("00:00:00");

        if (dateMin.equals("") && dateMax.equals("") && user == null) {
            activities = activityRepository.findAll();
        } else if(dateMin.equals("") && dateMax.equals("") && user != null){
            activities = activityRepository.findByAuthor(user);
        }else {
            if (dateMax.equals("") || dateMax == null) {
                datePartMax = LocalDate.now();
            } else {
                datePartMax = LocalDate.parse(dateMax);
            }

            if (user == null) {
                LocalDate datePartMin = LocalDate.parse(dateMin);
                activities = activityRepository.findByCreatedAtBetween(LocalDateTime.of(datePartMin, time), LocalDateTime.of(datePartMax, time));
            } else {
                LocalDate datePartMin = LocalDate.parse(dateMin);
                activities = activityRepository.findByCreatedAtBetweenAndAuthor(LocalDateTime.of(datePartMin, time), LocalDateTime.of(datePartMax, time), user);
            }
        }


        ActivityExcelReporter excelExporter = new ActivityExcelReporter(activities);
        excelExporter.export(response);
    }

    public List<Contact> getContacts() {
        return contactService.getContacts();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','Куратор')")
    public List<User> getUsers() {
        return userRepository.findByActiveIsTrue();
    }

    public Page<Activity> findByContact(Pageable pageable, String filter,Contact contact) {
        if (filter != null && !filter.isEmpty()) {
            Page<Activity> activitys = activityRepository.findByNameActivityContainingAndContact(filter,contact, pageable);
            if (activitys == null || activitys.isEmpty()) {
                return activityRepository.findByContact(contact,pageable);
            }else {
                return activitys;
            }
        } else {
            return activityRepository.findByContact(contact,pageable);
        }
    }
}
