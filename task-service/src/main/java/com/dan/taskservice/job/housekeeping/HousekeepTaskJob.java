package com.dan.taskservice.job.housekeeping;

import com.dan.shared.sharedlibrary.model.request.BaseRequest;
import com.dan.taskservice.service.HousekeepTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class HousekeepTaskJob {

    private final HousekeepTaskService housekeepTaskService;

    @Scheduled(cron = "${config.scheduler.audit.doHousekeepTask}")
    @SchedulerLock(name = "doHousekeepComm", lockAtLeastFor = "15S", lockAtMostFor = "20S")
    public void execute(){
        log.info(">> housekeepCommLogService [start]");
        housekeepTaskService.execute(new BaseRequest());
        log.info(">> housekeepCommLogService [end]");
    }

}
