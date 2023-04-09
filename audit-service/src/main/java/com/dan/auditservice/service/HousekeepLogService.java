package com.dan.auditservice.service;

import com.dan.auditservice.repository.LogRepository;
import com.dan.shared.sharedlibrary.model.request.BaseRequest;
import com.dan.shared.sharedlibrary.model.response.ValidationResponse;
import com.dan.shared.sharedlibrary.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HousekeepLogService implements BaseService<BaseRequest, ValidationResponse> {

    private final LogRepository logRepository;

    @Value("${config.housekeep.interval.audit}")
    private String intervalHousekeepAudit;

    @Override
    public ValidationResponse execute(BaseRequest input) {
        logRepository.doHouskeepAudit(intervalHousekeepAudit);
        return ValidationResponse.builder().result(true).build();
    }
    
}
