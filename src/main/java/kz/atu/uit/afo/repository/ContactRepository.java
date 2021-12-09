package kz.atu.uit.afo.repository;

import kz.atu.uit.afo.domain.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact,Long> {
    Contact findByIin(String iin);
    Page<Contact> findAll(Pageable pageable);


    Page<Contact> findByIinContaining(String filter, Pageable pageable);
    Page<Contact> findByFioContaining(String filter, Pageable pageable);
}
