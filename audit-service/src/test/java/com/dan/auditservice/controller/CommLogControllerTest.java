package com.dan.auditservice.controller;

import com.dan.auditservice.model.request.CreateActivityLogRequest;
import com.dan.auditservice.model.request.CreateCommLogRequest;
import com.dan.auditservice.service.CreateActivityLogService;
import com.dan.auditservice.service.CreateCommLogService;
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
import org.springframework.test.util.ReflectionTestUtils;


@ExtendWith(MockitoExtension.class)
@Slf4j
class CommLogControllerTest {

    @Mock
    private CreateCommLogService createCommLogService;

    @InjectMocks
    private CommLogController commLogController;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void doTest_createLog_success(){
        ReflectionTestUtils.setField(commLogController, "createCommLogService", createCommLogService);
        CreateCommLogRequest createCommLogRequest = CreateCommLogRequest.builder()
                .serviceName("test")
                .urlEndpoint("test")
                .request("test")
                .response("test")
                .httpResponseCode("test")
                .createdDate(0L)
                .build();
        Mockito.when(createCommLogService.execute(Mockito.any())).thenReturn(ValidationResponse.builder().result(true).build());
        commLogController.createLog(createCommLogRequest).subscribe(response ->{
           Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
           Assertions.assertEquals(true, response.getBody().getResult());
           Assertions.assertEquals(CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, response.getBody().getMessage());
        });
    }

}
