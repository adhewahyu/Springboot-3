package com.dan.auditservice.job.housekeeping;

import com.dan.auditservice.service.HousekeepActivityLogService;
import com.dan.shared.sharedlibrary.model.request.BaseRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class HousekeepActivityLogJob {

    private final HousekeepActivityLogService housekeepActivityLogService;

    @Scheduled(cron = "${config.scheduler.audit.doHousekeepActivity}")
    @SchedulerLock(name = "doHousekeepActivity", lockAtLeastFor = "15S", lockAtMostFor = "20S")
    public void execute(){
        log.info(">> housekeepActivityLogService [start]");
        housekeepActivityLogService.execute(new BaseRequest());
        log.info(">> housekeepActivityLogService [end]");
    }

}
