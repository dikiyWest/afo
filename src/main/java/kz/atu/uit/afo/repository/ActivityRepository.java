package kz.atu.uit.afo.repository;

import kz.atu.uit.afo.domain.Activity;
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

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    @Query("select a from Activity a where (1 = ?#{hasAnyAuthority('ADMIN','Куратор')? 1:0} or a.author.username = ?#{principal.username})")
    Page<Activity> findAll(Pageable pageable);

    @Query("select a from Activity a where (1 = ?#{hasAnyAuthority('ADMIN','Куратор')? 1:0} or a.author.username = ?#{principal.username}) and a.nameActivity like %:name%")
    Page<Activity> findByNameActivityContaining(@Param("name") String nameActivity, Pageable pageable);

    @Query("select a from Activity a where (1 = ?#{hasAnyAuthority('ADMIN','Куратор')? 1:0} or a.author.username = ?#{principal.username}) and a.nameActivity like %?1% " +
            "and a.contact = ?2")
    Page<Activity> findByNameActivityContainingAndContact(String filter, Contact contact, Pageable pageable);

    // @Query("select a from Activity a where (1 = ?#{hasAnyAuthority('ADMIN','Куратор')? 1:0} or a.author.username = ?#{principal.username}) and a.contact = ?1")
    @Query("select a from Activity a where (a.author.username like ?#{hasAnyAuthority('ADMIN','Куратор')? '%': principal.username} ) and a.contact = ?1")
    Page<Activity> findByContact(Contact contact, Pageable pageable);

    //@Query("select a from Activity a where (1 = ?#{hasAnyAuthority('ADMIN','Куратор')? 1:0} or a.author.username = ?#{principal.username})")
    @PostFilter("hasAnyAuthority('ADMIN','Куратор') or filterObject.author.username == principal.username")
    List<Activity> findByAuthor(User user);

    //@Query("select a from Activity a where (1 = ?#{hasAnyAuthority('ADMIN','Куратор')? 1:0} or a.author.username = ?#{principal.username})")
    @PostFilter("hasAnyAuthority('ADMIN','Куратор') or filterObject.author.username == principal.username")
    List<Activity> findByCreatedAtBetween(LocalDateTime of, LocalDateTime of1);

    //@Query("select a from Activity a where (1 = ?#{hasAnyAuthority('ADMIN','Куратор')? 1:0} or a.author.username = ?#{principal.username})")
    @PostFilter("hasAnyAuthority('ADMIN','Куратор') or filterObject.author.username == principal.username")
    List<Activity> findByCreatedAtBetweenAndAuthor(LocalDateTime of, LocalDateTime of1, User user);

    @PostFilter("hasAnyAuthority('ADMIN','Куратор') or filterObject.author.username == principal.username")
    List<Activity> findAll();

}
