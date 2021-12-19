package kz.atu.uit.afo.repository;

import kz.atu.uit.afo.domain.Activity;
import kz.atu.uit.afo.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity,Long> {
    Page<Activity> findAll(Pageable pageable);
    Page<Activity> findByNameActivityContaining(String nameActivity,Pageable pageable);

    List<Activity> findByAuthor(User user);

    List<Activity> findByCreatedAtBetween(LocalDateTime of, LocalDateTime of1);

    List<Activity> findByCreatedAtBetweenAndAuthor(LocalDateTime of, LocalDateTime of1, User user);
}
