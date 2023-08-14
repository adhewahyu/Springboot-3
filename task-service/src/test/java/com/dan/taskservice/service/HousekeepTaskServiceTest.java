package com.dan.taskservice.service;

import com.dan.shared.sharedlibrary.model.request.BaseRequest;
import com.dan.shared.sharedlibrary.model.response.ValidationResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
@Slf4j
class HousekeepTaskServiceTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private HousekeepTaskService housekeepTaskService;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void doTest_success(){
        String intervalHousekeepTask = "7 days";
        ReflectionTestUtils.setField(housekeepTaskService, "intervalHousekeepTask", intervalHousekeepTask);
        ReflectionTestUtils.setField(housekeepTaskService, "jdbcTemplate", jdbcTemplate);
        Mockito.doNothing().when(jdbcTemplate).execute(Mockito.anyString());
        ValidationResponse expectedResponse = ValidationResponse.builder().result(true).build();
        ValidationResponse actualResponse = housekeepTaskService.execute(new BaseRequest());
        Assertions.assertEquals(expectedResponse, actualResponse);
    }

}
