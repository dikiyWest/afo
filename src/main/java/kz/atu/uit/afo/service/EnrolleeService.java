package kz.atu.uit.afo.service;

import kz.atu.uit.afo.domain.EducationProgramm;
import kz.atu.uit.afo.domain.Enrollee;
import kz.atu.uit.afo.domain.Region;
import kz.atu.uit.afo.repository.EducationProgrammRepository;
import kz.atu.uit.afo.repository.EnrolleeRepository;
import kz.atu.uit.afo.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public Page<Enrollee> findAll(Pageable pageable) {
        return enrolleeRepository.findAll(pageable);
    }

    public String setUrl(String filter) {
        if (filter != null && !filter.isEmpty()) {
            return "/enrolee?filter="+filter+"&";
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
       Enrollee  enrollee =  enrolleeRepository.findByIin(iin);
        if (enrollee == null) {
            return false;
        } else {
            return true;
        }
    }
}
