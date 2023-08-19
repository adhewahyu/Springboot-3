package com.dan.userservice.service;

import com.dan.shared.sharedlibrary.model.response.ValidationResponse;
import com.dan.userservice.adaptor.task.CreateTaskAdaptor;
import com.dan.userservice.model.request.CreateTaskRequest;
import com.dan.userservice.model.request.UpdateUserRequest;
import com.dan.userservice.model.request.ValidateUserRequest;
import com.dan.userservice.service.user.UpdateUserService;
import com.dan.userservice.service.user.ValidateUserService;
import com.dan.userservice.util.Constants;
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
class UpdateUserServiceTest {

    @Mock
    private ValidateUserService validateUserService;

    @Mock
    private CreateTaskAdaptor createTaskAdaptor;

    @InjectMocks
    private UpdateUserService updateUserService;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void doTest_failedValidation(){
        ReflectionTestUtils.setField(updateUserService, "validateUserService", validateUserService);
        Mockito.when(validateUserService.execute(Mockito.any(ValidateUserRequest.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ERR_MSG_FIRSTNAME_REQUIRED));
        try{
            updateUserService.execute(UpdateUserRequest.builder()
                    .firstName(null)
                    .middleName("test")
                    .lastName("test")
                    .phoneNo("012345")
                    .officeEmail("a@a.com")
                    .personalEmail("b@b.com")
                    .address("test")
                    .grade("1")
                    .emergencyContactName("test")
                    .emergencyContactPhoneNo("1234")
                    .build());
        }catch (ResponseStatusException rse){
            Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), rse.getStatusCode().value());
            Assertions.assertEquals(Constants.ERR_MSG_FIRSTNAME_REQUIRED, rse.getReason());
        }
    }

    @Test
    void doTest_success(){
        ReflectionTestUtils.setField(updateUserService, "validateUserService", validateUserService);
        ReflectionTestUtils.setField(updateUserService, "createTaskAdaptor", createTaskAdaptor);
        Mockito.when(validateUserService.execute(Mockito.any(ValidateUserRequest.class)))
                .thenReturn(ValidationResponse.builder().result(true).build());
        Mockito.doNothing().when(createTaskAdaptor).execute(Mockito.any(CreateTaskRequest.class));
        ValidationResponse expectedResponse = ValidationResponse.builder().result(true).build();
        ValidationResponse actualResponse = updateUserService.execute(UpdateUserRequest.builder()
                .firstName("test")
                .middleName("test")
                .lastName("test")
                .phoneNo("012345")
                .officeEmail("a@a.com")
                .personalEmail("b@b.com")
                .address("test")
                .grade("1")
                .emergencyContactName("test")
                .emergencyContactPhoneNo("1234")
                .build());
        Assertions.assertEquals(expectedResponse, actualResponse);
    }

}
