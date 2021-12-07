package kz.atu.uit.afo.repository;

import kz.atu.uit.afo.domain.Enrollee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrolleeRepository extends JpaRepository<Enrollee,Long> {
    Enrollee findByIin(String iin);
    Page<Enrollee> findAll(Pageable pageable);


    Page<Enrollee> findByIinContaining(String filter, Pageable pageable);
    Page<Enrollee> findByFioContaining(String filter, Pageable pageable);
}
