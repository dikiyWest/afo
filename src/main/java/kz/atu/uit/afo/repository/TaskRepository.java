package kz.atu.uit.afo.repository;

import kz.atu.uit.afo.domain.Activity;
import kz.atu.uit.afo.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Long> {
    Page<Task> findAll(Pageable pageable);
    Page<Task> findByNameTaskContaining(String filter,Pageable pageable);



}
