package kz.atu.uit.afo.repository;

import kz.atu.uit.afo.domain.Enrollee;
import kz.atu.uit.afo.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EnrolleeRepository extends JpaRepository<Enrollee,Long> {
    Enrollee findByIin(String iin);
    Page<Enrollee> findAll(Pageable pageable);


    Page<Enrollee> findByIinContaining(String filter, Pageable pageable);
    Page<Enrollee> findByFioContaining(String filter, Pageable pageable);

    List<Enrollee> findByCareerСounselor(User user);

    List<Enrollee> findByCreatedAtBetween(LocalDateTime of, LocalDateTime of1);

    List<Enrollee> findByCreatedAtBetweenAndCareerСounselor(LocalDateTime of, LocalDateTime of1, User user);
}
