package kz.atu.uit.afo.repository;

import kz.atu.uit.afo.domain.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity,Long> {
    Page<Activity> findAll(Pageable pageable);
    Page<Activity> findByNameActivityContaining(String nameActivity,Pageable pageable);
}
