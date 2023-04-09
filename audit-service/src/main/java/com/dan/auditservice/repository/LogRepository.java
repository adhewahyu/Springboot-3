package com.dan.auditservice.repository;

import com.dan.auditservice.model.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, String>, JpaSpecificationExecutor<Log> {

    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM AUDITS WHERE CREATED_DATE <  (CURRENT_DATE - interval :interval)", nativeQuery = true)
    void doHouskeepAudit(@Param("interval") String interval);

}
