package com.dan.taskservice.service;

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
public class HousekeepTaskService implements BaseService<BaseRequest, ValidationResponse> {

    @Value("${config.housekeep.interval.task}")
    private String intervalHousekeepTask;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public ValidationResponse execute(BaseRequest input) {
        String query = " DELETE FROM tasks WHERE created_date < (CURRENT_DATE - interval '"+ intervalHousekeepTask + "') AND status = 0";
        jdbcTemplate.execute(query);
        return ValidationResponse.builder().result(true).build();
    }
}
