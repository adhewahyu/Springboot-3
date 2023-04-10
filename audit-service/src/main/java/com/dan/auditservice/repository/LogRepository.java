package com.dan.auditservice.repository;

import com.dan.auditservice.model.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, String>, JpaSpecificationExecutor<Log> {

}
