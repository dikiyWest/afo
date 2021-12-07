package kz.atu.uit.afo.service;

import kz.atu.uit.afo.domain.Activity;
import kz.atu.uit.afo.domain.Region;
import kz.atu.uit.afo.repository.ActivityRepository;
import kz.atu.uit.afo.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
}
