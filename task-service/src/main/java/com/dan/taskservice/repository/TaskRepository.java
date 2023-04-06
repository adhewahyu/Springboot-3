package com.dan.taskservice.repository;

import com.dan.taskservice.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, String>, JpaSpecificationExecutor<Task> {

    @Query(value = " select * from tasks where id = :id and status = 0 ", nativeQuery = true)
    Optional<Task> findNewTaskById(@Param("id") String id);

}
