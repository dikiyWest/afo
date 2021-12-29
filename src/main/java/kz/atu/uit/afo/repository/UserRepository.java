package kz.atu.uit.afo.repository;

import kz.atu.uit.afo.domain.Message;
import kz.atu.uit.afo.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDateTime;
import java.util.List;


public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
    @PreAuthorize("hasAnyAuthority('ADMIN','Куратор')")
    Page<User> findAll(Pageable pageable);
    @PreAuthorize("hasAnyAuthority('ADMIN','Куратор')")
    List<User> findByCreatedAtBetween(
            @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime beforeLocalDateTime, @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime afterLocalDateTime);



    @PreAuthorize("hasAnyAuthority('ADMIN','Куратор')")
    List<User> findByActiveIsTrue();
    @PreAuthorize("hasAnyAuthority('ADMIN','Куратор')")
    Page<User> findByIinContaining(String filter, Pageable pageable);
    @PreAuthorize("hasAnyAuthority('ADMIN','Куратор')")
    Page<User> findByFioContaining(String filter, Pageable pageable);
}
