package kz.atu.uit.afo.service;

import kz.atu.uit.afo.domain.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ContactService {
    public Page<Contact> findAll(Pageable pageable, String filter) {

    }
}
