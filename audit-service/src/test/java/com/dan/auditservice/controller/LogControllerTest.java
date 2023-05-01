package com.dan.auditservice.controller;

import com.dan.auditservice.model.request.CreateLogRequest;
import com.dan.auditservice.service.CreateLogService;
import com.dan.shared.sharedlibrary.model.response.RestResponse;
import com.dan.shared.sharedlibrary.model.response.ValidationResponse;
import com.dan.shared.sharedlibrary.util.CommonConstants;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.core.publisher.Mono;

import java.util.Objects;

@ExtendWith(MockitoExtension.class)
@Slf4j
class LogControllerTest {

    @Mock
    private CreateLogService createLogService;

    @InjectMocks
    private LogController logController;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void doTest_createLog_success(){
        ReflectionTestUtils.setField(logController, "createLogService", createLogService);
        CreateLogRequest createLogRequest = CreateLogRequest.builder()
                .module("test")
                .activity("test")
                .createdBy("tester")
                .createdDate(0L)
                .build();
        Mockito.when(createLogService.execute(Mockito.any())).thenReturn(ValidationResponse.builder().result(true).build());
        logController.createLog(createLogRequest).subscribe(response ->{
           Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
           Assertions.assertEquals(true, response.getBody().getResult());
           Assertions.assertEquals(CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, response.getBody().getMessage());
        });
    }

}
