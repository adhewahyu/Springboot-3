package com.dan.auditservice.job.housekeeping;

import com.dan.auditservice.service.HousekeepCommLogService;
import com.dan.shared.sharedlibrary.model.request.BaseRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class HousekeepCommLogJob {

    private final HousekeepCommLogService housekeepCommLogService;

    @Scheduled(cron = "${config.scheduler.audit.doHousekeepComm}")
    @SchedulerLock(name = "doHousekeepComm", lockAtLeastFor = "15S", lockAtMostFor = "20S")
    public void execute(){
        log.info(">> housekeepCommLogService [start]");
        housekeepCommLogService.execute(new BaseRequest());
        log.info(">> housekeepCommLogService [end]");
    }

}
