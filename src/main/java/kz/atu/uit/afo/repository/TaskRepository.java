package kz.atu.uit.afo.repository;

import kz.atu.uit.afo.domain.Contact;
import kz.atu.uit.afo.domain.Enrollee;
import kz.atu.uit.afo.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.access.prepost.PostFilter;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {
    @Query("select t from Task t  where (1 = ?#{hasAnyAuthority('ADMIN','Куратор')? 1:0} or t.careerCounselor.username = ?#{principal.username})")
    Page<Task> findAll(Pageable pageable);

    @Query("select t from Task t  where (1 = ?#{hasAnyAuthority('ADMIN','Куратор')? 1:0} or t.careerCounselor.username = ?#{principal.username}) and t.nameTask like %?1%")
    Page<Task> findByNameTaskContaining(String filter,Pageable pageable);

    @Query("select t from Task t  where (1 = ?#{hasAnyAuthority('ADMIN','Куратор')? 1:0} or t.careerCounselor.username = ?#{principal.username}) and t.nameTask like %?1% and " +
            "t.contact = ?2")
    Page<Task> findByNameTaskContainingAndContact(String filter, Contact contact, Pageable pageable);

    @Query("select t from Task t  where (1 = ?#{hasAnyAuthority('ADMIN','Куратор')? 1:0} or t.careerCounselor.username = ?#{principal.username}) and t.contact = ?1")
    Page<Task> findByContact(Contact contact, Pageable pageable);

    @Query("select t from Task t  where (1 = ?#{hasAnyAuthority('ADMIN','Куратор')? 1:0} or t.careerCounselor.username = ?#{principal.username}) and t.nameTask like %?1% and " +
            "t.enrollee = ?2")
    Page<Task> findByNameTaskContainingAndEnrollee(String filter, Enrollee enrollee, Pageable pageable);

    @Query("select t from Task t  where (1 = ?#{hasAnyAuthority('ADMIN','Куратор')? 1:0} or t.careerCounselor.username = ?#{principal.username}) and t.enrollee = ?1")
    Page<Task> findByEnrollee(Enrollee enrollee, Pageable pageable);

    @PostFilter("hasAnyAuthority('ADMIN','Куратор') or filterObject.author.username == principal.username")
    List<Task> findAll();
}
