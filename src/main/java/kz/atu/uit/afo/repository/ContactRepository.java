package kz.atu.uit.afo.repository;

import kz.atu.uit.afo.domain.Contact;
import kz.atu.uit.afo.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDateTime;
import java.util.List;

public interface ContactRepository extends JpaRepository<Contact,Long> {
    @PreAuthorize("hasAuthority('ADMIN')")
    Contact findByIin(String iin);


    @Query("select c from Contact c  where 1 = ?#{ ('ROLE_ADMIN') ? 1 : 0}")
    Page<Contact> findAll(Pageable pageable);

    Page<Contact> findByActveAndCareerСounselor(Boolean active,User user,Pageable pageable);
    Page<Contact> findByActve(Boolean active,Pageable pageable);


    Page<Contact> findByIinContaining(String filter, Pageable pageable);
    Page<Contact> findByFioContaining(String filter, Pageable pageable);

    Page<Contact> findByIinContainingAndCareerСounselor(String filter,User user, Pageable pageable);
    Page<Contact> findByFioContainingAndCareerСounselor(String filter,User user, Pageable pageable);

    List<Contact> findByCreatedAtBetween(LocalDateTime of, LocalDateTime of1);

    List<Contact> findByCreatedAtBetweenAndCareerСounselor(LocalDateTime of, LocalDateTime of1, User user);

    List<Contact> findByCareerСounselor(User user);
}
