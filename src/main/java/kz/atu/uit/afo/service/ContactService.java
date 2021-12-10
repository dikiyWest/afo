package kz.atu.uit.afo.service;

import kz.atu.uit.afo.domain.Contact;
import kz.atu.uit.afo.domain.Region;
import kz.atu.uit.afo.domain.User;
import kz.atu.uit.afo.repository.ContactRepository;
import kz.atu.uit.afo.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private RegionRepository regionRepository;

    public Page<Contact> findAll(Pageable pageable, String filter) {
        if (filter != null && !filter.isEmpty()) {
            Page<Contact> contacts = contactRepository.findByIinContaining(filter, pageable);
            if (contacts == null || contacts.isEmpty()) {
                contacts = contactRepository.findByFioContaining(filter, pageable);
                if (contacts == null || contacts.isEmpty()) {
                    return contactRepository.findAll(pageable);
                }
                return  contacts;
            }else {
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

}
