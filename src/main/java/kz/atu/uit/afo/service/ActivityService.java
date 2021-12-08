package kz.atu.uit.afo.service;

import kz.atu.uit.afo.domain.Activity;
import kz.atu.uit.afo.domain.Region;
import kz.atu.uit.afo.domain.User;
import kz.atu.uit.afo.repository.ActivityRepository;
import kz.atu.uit.afo.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private RegionRepository regionRepository;

    public Page<Activity> findAll(Pageable pageable, String filter) {
        if (filter != null && !filter.isEmpty()) {
            Page<Activity> enrollees = activityRepository.findByNameActivityContaining(filter, pageable);
            if (enrollees == null || enrollees.isEmpty()) {
                    return activityRepository.findAll(pageable);
            }else {
                return enrollees;
            }
        } else {
            return activityRepository.findAll(pageable);
        }
    }

    public Object getSort(Page<Activity> page) {
        return page.getSort().toString().replace(": ", ",");
    }

    public Object setUrl(String filter) {
        if (filter != null && !filter.isEmpty()) {
            return "/activity?filter=" + filter + "&";
        } else {
            return "/activity?";
        }
    }

    public Iterable<Region> getRegions() {
        return regionRepository.findAll();
    }

    public String getNowDateFormat(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return formatter.format(LocalDateTime.now());
    }

    public boolean activitySave(Activity activity, User user, Long activityId, String dateActivity, String timeActivity,MultipartFile file) {
    if(activity.getId()==null && activityId == null){
        System.out.println(timeActivity);
        setDateActivity(activity,dateActivity,timeActivity);
        activity.setAuthor(user);
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


}
