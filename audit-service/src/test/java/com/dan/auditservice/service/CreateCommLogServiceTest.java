package com.dan.auditservice.service;

import com.dan.auditservice.model.request.CreateActivityLogRequest;
import com.dan.auditservice.model.request.CreateCommLogRequest;
import com.dan.auditservice.repository.CommLogRepository;
import com.dan.auditservice.util.Constants;
import com.dan.shared.sharedlibrary.util.CommonUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
@Slf4j
@RequiredArgsConstructor
class CreateCommLogServiceTest {

    @Mock
    private CommLogRepository commLogRepository;

    @Mock
    private CommonUtility commonUtility;

    @InjectMocks
    private CreateCommLogService createCommLogService;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void doTest_serviceNameRequired(){
        try{
            createCommLogService.execute(CreateCommLogRequest.builder()
                    .serviceName(null)
                    .build());
        } catch (ResponseStatusException rse){
            Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), rse.getStatusCode().value());
            Assertions.assertEquals(Constants.ERR_MSG_SERVICE_NAME_REQUIRED, rse.getReason());
        }
    }

    @Test
    void doTest_urlEndpointRequired(){
        try{
            createCommLogService.execute(CreateCommLogRequest.builder()
                    .serviceName("test")
                    .urlEndpoint(null)
                    .build());
        } catch (ResponseStatusException rse){
            Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), rse.getStatusCode().value());
            Assertions.assertEquals(Constants.ERR_MSG_URL_ENDPOINT_REQUIRED, rse.getReason());
        }
    }

    @Test
    void doTest_requestPayloadRequired(){
        try{
            createCommLogService.execute(CreateCommLogRequest.builder()
                    .serviceName("test")
                    .urlEndpoint("test")
                    .request(null)
                    .build());
        } catch (ResponseStatusException rse){
            Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), rse.getStatusCode().value());
            Assertions.assertEquals(Constants.ERR_MSG_REQUEST_PAYLOAD_REQUIRED, rse.getReason());
        }
    }

    @Test
    void doTest_responsePayloadRequired(){
        try{
            createCommLogService.execute(CreateCommLogRequest.builder()
                    .serviceName("test")
                    .urlEndpoint("test")
                    .request("test")
                    .response(null)
                    .build());
        } catch (ResponseStatusException rse){
            Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), rse.getStatusCode().value());
            Assertions.assertEquals(Constants.ERR_MSG_RESPONSE_PAYLOAD_REQUIRED, rse.getReason());
        }
    }

    @Test
    void doTest_createdDateRequired(){
        try{
            createCommLogService.execute(CreateCommLogRequest.builder()
                    .serviceName("test")
                    .urlEndpoint("test")
                    .request("test")
                    .response("test")
                    .createdDate(null)
                    .build());
        } catch (ResponseStatusException rse){
            Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), rse.getStatusCode().value());
            Assertions.assertEquals(Constants.ERR_MSG_CREATED_DATE_REQUIRED, rse.getReason());
        }
    }

}
