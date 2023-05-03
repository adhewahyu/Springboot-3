package com.dan.auditservice.repository;

import com.dan.auditservice.model.entity.CommLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommLogRepository extends JpaRepository<CommLog, String> {

}
