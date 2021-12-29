package kz.atu.uit.afo.service;

import kz.atu.uit.afo.domain.*;
import kz.atu.uit.afo.repository.EducationProgrammRepository;
import kz.atu.uit.afo.repository.EnrolleeRepository;
import kz.atu.uit.afo.repository.RegionRepository;
import kz.atu.uit.afo.repository.UserRepository;
import kz.atu.uit.afo.service.reportService.EnrolleeExcelReporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Service
public class EnrolleeService {
    @Autowired
    private EnrolleeRepository enrolleeRepository;

    @Autowired
    private EducationProgrammRepository educationProgrammRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private UserRepository userRepository;

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
                return enrollees;
            } else {
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

    public boolean enrolleeAdd(Enrollee enrollee, User user, Region region, EducationProgramm educationProgramm, Long enrolleeId, String iin, User toUser) {
        if (enrollee.getId() == null && enrolleeId == null) {
            if (checkIIN(enrollee.getIin())) {
                return false;
            }
            enrollee.setCareerCounselor(user);
            if (toUser != null)
                enrollee.setCareerCounselor(toUser);
        } else {
            Enrollee enrolleeFromDb = enrolleeRepository.findById(enrolleeId).get();
            enrollee.setId(enrolleeFromDb.getId());
            enrollee.setCareerCounselor(enrolleeFromDb.getCareerCounselor());
            enrollee.setIin(enrolleeFromDb.getIin());
            enrollee.setCreatedAt(enrolleeFromDb.getCreatedAt());
            if (enrolleeFromDb.getCareerCounselor() != toUser)
                enrollee.setCareerCounselor(toUser);
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

    public void getReportExcel(String dateMin, String dateMax, HttpServletResponse response, User user) throws IOException {
        List<Enrollee> enrolleeList;
        LocalDate datePartMax;

        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=enrollee_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        LocalTime time = LocalTime.parse("00:00:00");

        if (dateMin.equals("") && dateMax.equals("") && user == null) {
            enrolleeList = enrolleeRepository.findAll();
        } else if (dateMin.equals("") && dateMax.equals("") && user != null) {
            enrolleeList = enrolleeRepository.findByCareerCounselor(user);
        } else {
            if (dateMax.equals("") || dateMax == null) {
                datePartMax = LocalDate.now();
            } else {
                datePartMax = LocalDate.parse(dateMax);
            }

            if (user == null) {
                LocalDate datePartMin = LocalDate.parse(dateMin);
                enrolleeList = enrolleeRepository.findByCreatedAtBetween(LocalDateTime.of(datePartMin, time), LocalDateTime.of(datePartMax, time));
            } else {
                LocalDate datePartMin = LocalDate.parse(dateMin);
                enrolleeList = enrolleeRepository.findByCreatedAtBetweenAndCareerCounselor(LocalDateTime.of(datePartMin, time), LocalDateTime.of(datePartMax, time), user);
            }
        }


        EnrolleeExcelReporter excelExporter = new EnrolleeExcelReporter(enrolleeList);
        excelExporter.export(response);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','Куратор')")
    public List<User> getUsers() {
        return userRepository.findByActiveIsTrue();
    }
}
