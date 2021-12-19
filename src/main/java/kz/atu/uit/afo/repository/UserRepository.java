package kz.atu.uit.afo.repository;

import kz.atu.uit.afo.domain.Message;
import kz.atu.uit.afo.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
    Page<User> findAll(Pageable pageable);
    List<User> findByCreatedAtBetween(
            @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime beforeLocalDateTime, @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime afterLocalDateTime);


    Page<User> findByIinContaining(String filter, Pageable pageable);
    Page<User> findByFioContaining(String filter, Pageable pageable);
}
