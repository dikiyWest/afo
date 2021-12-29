package kz.atu.uit.afo.repository;

import kz.atu.uit.afo.domain.Contact;
import kz.atu.uit.afo.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostFilter;

import java.time.LocalDateTime;
import java.util.List;

public interface ContactRepository extends JpaRepository<Contact,Long> {

    Contact findByIin(String iin);


    @Query("select c from Contact c  where (1 = ?#{hasAnyAuthority('ADMIN','Куратор')? 1:0} or c.careerCounselor.username = ?#{principal.username})")
    Page<Contact> findAll(Pageable pageable);

    @Query("select c from Contact c  where (1 = ?#{hasAnyAuthority('ADMIN','Куратор')? 1:0} or c.careerCounselor.username = ?#{principal.username}) and c.iin like %:filter%")
    Page<Contact> findByIinContaining(@Param("filter") String filter, Pageable pageable);
    @Query("select c from Contact c  where (1 = ?#{hasAnyAuthority('ADMIN','Куратор')? 1:0} or c.careerCounselor.username = ?#{principal.username}) and c.fio like %:filter%")
    Page<Contact> findByFioContaining(@Param("filter") String filter, Pageable pageable);

    //@Query("select c from Contact c  where (1 = ?#{hasAnyAuthority('ADMIN','Куратор')? 1:0} or c.careerCounselor.username = ?#{principal.username})")
    @PostFilter("hasAnyAuthority('ADMIN','Куратор') or filterObject.careerCounselor.username == principal.username")
    List<Contact> findByCreatedAtBetween(LocalDateTime of, LocalDateTime of1);

    //@Query("select c from Contact c  where (1 = ?#{hasAnyAuthority('ADMIN','Куратор')? 1:0} or c.careerCounselor.username = ?#{principal.username})")
    @PostFilter("hasAnyAuthority('ADMIN','Куратор') or filterObject.careerCounselor.username == principal.username")
    List<Contact> findByCreatedAtBetweenAndCareerCounselor(LocalDateTime of, LocalDateTime of1, User user);

    //@Query("select c from Contact c  where (1 = ?#{hasAnyAuthority('ADMIN','Куратор')? 1:0} or c.careerCounselor.username = ?#{principal.username})")
    @PostFilter("hasAnyAuthority('ADMIN','Куратор') or filterObject.careerCounselor.username == principal.username")
    List<Contact> findByCareerCounselor(User user);

    @PostFilter("hasAnyAuthority('ADMIN','Куратор') or filterObject.careerCounselor.username == principal.username")
    List<Contact> findByEnableIsTrue();

    @PostFilter("hasAnyAuthority('ADMIN','Куратор') or filterObject.careerCounselor.username == principal.username")
    List<Contact> findAll();
}
