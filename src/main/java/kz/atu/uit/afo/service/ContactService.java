package kz.atu.uit.afo.service;

import kz.atu.uit.afo.domain.Contact;
import kz.atu.uit.afo.domain.Region;
import kz.atu.uit.afo.domain.User;
import kz.atu.uit.afo.repository.ContactRepository;
import kz.atu.uit.afo.repository.RegionRepository;
import kz.atu.uit.afo.service.reportService.ContactExcelReporter;
import kz.atu.uit.afo.service.reportService.UserExcelReporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private RegionRepository regionRepository;


    public Page<Contact> findAll(User user, Pageable pageable, String filter) {

        if (filter != null && !filter.isEmpty()) {
            Page<Contact> contacts = contactRepository.findByIinContaining(filter, pageable);
            if (contacts == null || contacts.isEmpty()) {
                contacts = contactRepository.findByFioContaining(filter, pageable);
                if (contacts == null || contacts.isEmpty()) {
                    return contactRepository.findAll(pageable);
                }
                return contacts;
            } else {
                return contacts;
            }
        } else {
            return contactRepository.findAll(pageable);
        }
    }

    public String getSort(Page<Contact> page) {
        return page.getSort().toString().replace(": ", ",");
    }

    public String setUrl(String filter) {
        if (filter != null && !filter.isEmpty()) {
            return "/contact?filter=" + filter + "&";
        } else {
            return "/contact?";
        }
    }

    public Iterable<Region> getRegions() {
        return regionRepository.findAll();
    }

    public boolean checkIIN(String iin) {
        Contact contact = contactRepository.findByIin(iin);
        if (contact == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean contactAdd(Contact contact, User user, Long contactId, String iin) {
        if (contact.getId() == null && contactId == null) {
            if (checkIIN(contact.getIin())) {
                return false;
            }
            contact.setCareerСounselor(user);
        } else {
            Contact contactFromDb = contactRepository.findById(contactId).get();
            contact.setId(contactFromDb.getId());
            contact.setCareerСounselor(contactFromDb.getCareerСounselor());
            contact.setIin(contactFromDb.getIin());
            contact.setCreatedAt(contactFromDb.getCreatedAt());
            if (!contact.getIin().equals(iin)) {
                if (!checkIIN(iin)) {
                    contact.setIin(iin);
                }
            }
        }
        contactRepository.save(contact);
        return true;
    }

    public void getReportExcel(String dateMin, String dateMax, HttpServletResponse response, User user) throws IOException {
        List<Contact> contactList;
        LocalDate datePartMax;

        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=contact_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        LocalTime time = LocalTime.parse("00:00:00");

        if (dateMin.equals("") && dateMax.equals("") && user == null) {
            contactList = contactRepository.findAll();
        } else if (dateMin.equals("") && dateMax.equals("") && user != null) {
            contactList = contactRepository.findByCareerСounselor(user);
        } else {
            if (dateMax.equals("") || dateMax == null) {
                datePartMax = LocalDate.now();
            } else {
                datePartMax = LocalDate.parse(dateMax);
            }

            if (user == null) {
                LocalDate datePartMin = LocalDate.parse(dateMin);
                contactList = contactRepository.findByCreatedAtBetween(LocalDateTime.of(datePartMin, time), LocalDateTime.of(datePartMax, time));
            } else {
                LocalDate datePartMin = LocalDate.parse(dateMin);
                contactList = contactRepository.findByCreatedAtBetweenAndCareerСounselor(LocalDateTime.of(datePartMin, time), LocalDateTime.of(datePartMax, time), user);
            }
        }


        ContactExcelReporter excelExporter = new ContactExcelReporter(contactList);
        excelExporter.export(response);
    }

    private boolean isAdmin(User user) {
        return user.getRoles().contains("ADMIN");
    }
}
