package com.dan.auditservice.job.housekeeping;

import com.dan.auditservice.service.HousekeepLogService;
import com.dan.shared.sharedlibrary.model.request.BaseRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class HousekeepLogJob {

    private final HousekeepLogService housekeepLogService;

    @Scheduled(cron = "${config.scheduler.audit.doHousekeepAudit}")
    @SchedulerLock(name = "doHousekeepAudit", lockAtLeastFor = "15S", lockAtMostFor = "20S")
    public void execute(){
        log.info(">> housekeepAuditService [start]");
        housekeepLogService.execute(new BaseRequest());
        log.info(">> housekeepAuditService [end]");
    }

}
