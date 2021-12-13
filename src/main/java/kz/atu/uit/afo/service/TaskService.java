package kz.atu.uit.afo.service;

import kz.atu.uit.afo.domain.*;
import kz.atu.uit.afo.domain.util.DomainHelper;
import kz.atu.uit.afo.repository.ActivityRepository;
import kz.atu.uit.afo.repository.ContactRepository;
import kz.atu.uit.afo.repository.EnrolleeRepository;
import kz.atu.uit.afo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private EnrolleeRepository enrolleeRepository;

    public Page<Task> findAll(Pageable pageable, String filter) {
        if (filter != null && !filter.isEmpty()) {
            Page<Task> tasks = taskRepository.findByNameTaskContaining(filter, pageable);
            if (tasks == null || tasks.isEmpty()) {
                return taskRepository.findAll(pageable);
            }else {
                return tasks;
            }
        } else {
            return taskRepository.findAll(pageable);
        }
    }

    public Object getSort(Page<Task> page) {
        return page.getSort().toString().replace(": ", ",");
    }

    public Object setUrl(String filter) {
        if (filter != null && !filter.isEmpty()) {
            return "/task?filter=" + filter + "&";
        } else {
            return "/task?";
        }
    }

    public Iterable<? extends DomainHelper> getTypeList(String type) {
        if(type.equals("activity"))
            return (Iterable<Activity>)activityRepository.findAll();
        if(type.equals("contact"))
            return (Iterable<Contact>)contactRepository.findAll();
        if(type.equals("enrollee"))
            return (Iterable<Enrollee>)enrolleeRepository.findAll();
        return null;
    }

    public String getNowDateFormat(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return formatter.format(LocalDateTime.now());
    }

    public boolean taskSave(Task task, User user, Long taskId, String dateValueTask, String timeValueTask) {
        if(task.getId()==null && taskId == null){
            setDateActivity(task,dateValueTask,timeValueTask);
            task.setCareerСounselor(user);
            taskRepository.save(task);
            return true;
        }else {
            Task taskFromDb = taskRepository.getById(taskId);
            if(checkDateChange(taskFromDb.getDateTask(),dateValueTask,timeValueTask)){
                setDateActivity(task,dateValueTask,timeValueTask);
            }else {
                task.setDateTask(taskFromDb.getDateTask());
            }
            task.setId(taskFromDb.getId());
            task.setCreatedAt(taskFromDb.getCreatedAt());
            task.setCareerСounselor(taskFromDb.getCareerСounselor());
            taskRepository.save(task);
            return true;
        }
    }

    private void setDateActivity(Task task,String dateActivity,String timeActivity){
        if(dateActivity != null && timeActivity != null){
            LocalDate datePart = LocalDate.parse(dateActivity);
            LocalTime timePart = LocalTime.parse(timeActivity);
            task.setDateTask(LocalDateTime.of(datePart, timePart));
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
