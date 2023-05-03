package com.dan.auditservice.repository;

import com.dan.auditservice.model.entity.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, String>, JpaSpecificationExecutor<ActivityLog> {

}
