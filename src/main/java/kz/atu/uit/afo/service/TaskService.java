package kz.atu.uit.afo.service;

import kz.atu.uit.afo.domain.Contact;
import kz.atu.uit.afo.domain.Task;
import kz.atu.uit.afo.domain.util.DomainHelper;
import kz.atu.uit.afo.repository.ActivityRepository;
import kz.atu.uit.afo.repository.ContactRepository;
import kz.atu.uit.afo.repository.EnrolleeRepository;
import kz.atu.uit.afo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public List<DomainHelper> getTypeList(String type) {
        if(type.equals("activity"))
            return activityRepository.findAll();
        if(type.equals("contact"))
            return contactRepository.findAll();
        if(type.equals("enrollee"))
            return Collections.singletonList(enrolleeRepository.findAll());
        return null;
    }

    public String getNowDateFormat(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return formatter.format(LocalDateTime.now());
    }
}
