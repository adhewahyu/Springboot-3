package com.dan.auditservice.service;

import com.dan.shared.sharedlibrary.model.request.BaseRequest;
import com.dan.shared.sharedlibrary.model.response.ValidationResponse;
import com.dan.shared.sharedlibrary.service.BaseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class HousekeepActivityLogService implements BaseService<BaseRequest, ValidationResponse> {

    @Value("${config.housekeep.interval.audit}")
    private String intervalHousekeepAudit;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public ValidationResponse execute(BaseRequest input) {
        String query = " DELETE FROM logs WHERE created_date < (CURRENT_DATE - interval '"+ intervalHousekeepAudit + "') ";
        jdbcTemplate.execute(query);
        return ValidationResponse.builder().result(true).build();
    }
    
}
