package kz.atu.uit.afo.service;

import kz.atu.uit.afo.domain.EducationProgramm;
import kz.atu.uit.afo.domain.Enrollee;
import kz.atu.uit.afo.domain.Region;
import kz.atu.uit.afo.domain.User;
import kz.atu.uit.afo.repository.EducationProgrammRepository;
import kz.atu.uit.afo.repository.EnrolleeRepository;
import kz.atu.uit.afo.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EnrolleeService {
    @Autowired
    private EnrolleeRepository enrolleeRepository;

    @Autowired
    private EducationProgrammRepository educationProgrammRepository;

    @Autowired
    private RegionRepository regionRepository;

    public String getSort(Page<Enrollee> page) {
        return page.getSort().toString().replace(": ", ",");
    }


    public Page<Enrollee> findAll(Pageable pageable, String filter) {
        if (filter != null && !filter.isEmpty()) {
            Page<Enrollee> enrollees = enrolleeRepository.findByIinContaining(filter, pageable);
            if (enrollees == null || enrollees.isEmpty()) {
                enrollees = enrolleeRepository.findByFioContaining(filter, pageable);
                if (enrollees == null || enrollees.isEmpty()) {
                    return enrolleeRepository.findAll(pageable);
                }
                return  enrollees;
            }else {
                return enrollees;
            }
        } else {
            return enrolleeRepository.findAll(pageable);
        }
    }

    public String setUrl(String filter) {
        if (filter != null && !filter.isEmpty()) {
            return "/enrollee?filter=" + filter + "&";
        } else {
            return "/enrollee?";
        }
    }

    public Iterable<EducationProgramm> getEducationProgramms() {
        return educationProgrammRepository.findAll();
    }

    public Iterable<Region> getRegions() {
        return regionRepository.findAll();
    }


    public boolean checkIIN(String iin) {
        Enrollee enrollee = enrolleeRepository.findByIin(iin);
        if (enrollee == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean enrolleeAdd(Enrollee enrollee, User user, Region region, EducationProgramm educationProgramm, Long enrolleeId, String iin) {
        if (enrollee.getId() == null && enrolleeId == null) {
            if (checkIIN(enrollee.getIin())) {
                return false;
            }
            enrollee.setCareerСounselor(user);
        } else {
            Enrollee enrolleeFromDb = enrolleeRepository.findById(enrolleeId).get();
            enrollee.setId(enrolleeFromDb.getId());
            enrollee.setCareerСounselor(enrolleeFromDb.getCareerСounselor());
            enrollee.setIin(enrolleeFromDb.getIin());
            enrollee.setCreatedAt(enrolleeFromDb.getCreatedAt());
            if (!enrollee.getIin().equals(iin)) {
                if (!checkIIN(iin)) {
                    enrollee.setIin(iin);
                }
            }
        }
        enrollee.setRegion(region);
        enrollee.setEducationProgramm(educationProgramm);
        enrolleeRepository.save(enrollee);
        return true;
    }
}
