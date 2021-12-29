package kz.atu.uit.afo.repository;

import kz.atu.uit.afo.domain.Enrollee;
import kz.atu.uit.afo.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostFilter;

import java.time.LocalDateTime;
import java.util.List;

public interface EnrolleeRepository extends JpaRepository<Enrollee,Long> {
    Enrollee findByIin(String iin);

    @Query("select e from Enrollee e  where (1 = ?#{hasAnyAuthority('ADMIN','Куратор')? 1:0} or e.careerCounselor.username = ?#{principal.username})")
    Page<Enrollee> findAll(Pageable pageable);


    @Query("select e from Enrollee e  where (1 = ?#{hasAnyAuthority('ADMIN','Куратор')? 1:0} or e.careerCounselor.username = ?#{principal.username}) and e.iin like %:filter%")
    Page<Enrollee> findByIinContaining(@Param("filter") String filter, Pageable pageable);
    @Query("select e from Enrollee e  where (1 = ?#{hasAnyAuthority('ADMIN','Куратор')? 1:0} or e.careerCounselor.username = ?#{principal.username}) and e.fio like %:filter%")
    Page<Enrollee> findByFioContaining(@Param("filter") String filter, Pageable pageable);

    @PostFilter("hasAnyAuthority('ADMIN','Куратор') or filterObject.careerCounselor.username == principal.username")
    List<Enrollee> findByCareerCounselor(User user);

    @PostFilter("hasAnyAuthority('ADMIN','Куратор') or filterObject.careerCounselor.username == principal.username")
    List<Enrollee> findByCreatedAtBetween(LocalDateTime of, LocalDateTime of1);

    @PostFilter("hasAnyAuthority('ADMIN','Куратор') or filterObject.careerCounselor.username == principal.username")
    List<Enrollee> findByCreatedAtBetweenAndCareerCounselor(LocalDateTime of, LocalDateTime of1, User user);

    @PostFilter("hasAnyAuthority('ADMIN','Куратор') or filterObject.careerCounselor.username == principal.username")
    List<Enrollee> findAll();
}
