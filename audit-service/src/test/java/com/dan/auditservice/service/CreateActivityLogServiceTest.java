package com.dan.auditservice.service;

import com.dan.auditservice.model.entity.ActivityLog;
import com.dan.auditservice.model.request.CreateActivityLogRequest;
import com.dan.auditservice.repository.ActivityLogRepository;
import com.dan.auditservice.util.Constants;
import com.dan.shared.sharedlibrary.model.response.ValidationResponse;
import com.dan.shared.sharedlibrary.util.CommonUtility;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
@Slf4j
@RequiredArgsConstructor
class CreateActivityLogServiceTest {

    @Mock
    private ActivityLogRepository activityLogRepository;

    @Mock
    private CommonUtility commonUtility;

    @InjectMocks
    private CreateActivityLogService createActivityLogService;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void doTest_failedModuleRequired(){
        try{
            createActivityLogService.execute(CreateActivityLogRequest.builder()
                    .module(null)
                    .build());
        } catch (ResponseStatusException rse){
            Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), rse.getStatusCode().value());
            Assertions.assertEquals(Constants.ERR_MSG_MODULE_REQUIRED, rse.getReason());
        }
    }

    @Test
    void doTest_failedActivityRequired(){
        try{
            createActivityLogService.execute(CreateActivityLogRequest.builder()
                    .module("test")
                    .activity(null)
                    .build());
        } catch (ResponseStatusException rse){
            Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), rse.getStatusCode().value());
            Assertions.assertEquals(Constants.ERR_MSG_ACTIVITY_REQUIRED, rse.getReason());
        }
    }

    @Test
    void doTest_failedCreatedDateRequired(){
        try{
            createActivityLogService.execute(CreateActivityLogRequest.builder()
                    .module("test")
                    .activity("test")
                    .createdDate(null)
                    .build());
        } catch (ResponseStatusException rse){
            Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), rse.getStatusCode().value());
            Assertions.assertEquals(Constants.ERR_MSG_CREATED_DATE_REQUIRED, rse.getReason());
        }
    }

    @Test
    void doTest_failedCreatedByRequired(){
        try{
            createActivityLogService.execute(CreateActivityLogRequest.builder()
                    .module("test")
                    .activity("test")
                    .createdDate(0L)
                    .createdBy(null)
                    .build());
        } catch (ResponseStatusException rse){
            Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), rse.getStatusCode().value());
            Assertions.assertEquals(Constants.ERR_MSG_CREATED_BY_REQUIRED, rse.getReason());
        }
    }

    @Test
    void doTest_success(){
        ReflectionTestUtils.setField(createActivityLogService, "activityLogRepository", activityLogRepository);
        ReflectionTestUtils.setField(createActivityLogService, "commonUtility", commonUtility);
        Mockito.when(commonUtility.getRandomUUID()).thenReturn("1234");
        Mockito.when(activityLogRepository.save(Mockito.any())).thenReturn(new ActivityLog());
        ValidationResponse expectedResponse = ValidationResponse.builder().result(true).build();
        ValidationResponse actualResponse = createActivityLogService.execute(CreateActivityLogRequest.builder()
                .module("test")
                .activity("test")
                .createdDate(0L)
                .createdBy("tester")
                .build());
        Assertions.assertEquals(expectedResponse, actualResponse);
    }





}
