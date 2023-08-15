package com.dan.userservice.service;

import com.dan.shared.sharedlibrary.model.request.FindByIdRequest;
import com.dan.shared.sharedlibrary.model.response.ValidationResponse;
import com.dan.userservice.adaptor.task.CreateTaskAdaptor;
import com.dan.userservice.model.request.CreateTaskRequest;
import com.dan.userservice.model.request.ValidateUserRequest;
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
class DeleteUserServiceTest {

    @Mock
    private ValidateUserService validateUserService;

    @Mock
    private CreateTaskAdaptor createTaskAdaptor;

    @InjectMocks
    private DeleteUserService deleteUserService;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void doTest_failedValidation(){
        ReflectionTestUtils.setField(deleteUserService, "validateUserService", validateUserService);
        Mockito.when(validateUserService.execute(Mockito.any(ValidateUserRequest.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.ERR_MSG_USER_NOT_FOUND));
        try{
            deleteUserService.execute(FindByIdRequest.builder()
                    .id("1")
                    .build());
        }catch (ResponseStatusException rse){
            Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), rse.getStatusCode().value());
            Assertions.assertEquals(Constants.ERR_MSG_USER_NOT_FOUND, rse.getReason());
        }
    }

    @Test
    void doTest_success(){
        ReflectionTestUtils.setField(deleteUserService, "validateUserService", validateUserService);
        ReflectionTestUtils.setField(deleteUserService, "createTaskAdaptor", createTaskAdaptor);
        Mockito.when(validateUserService.execute(Mockito.any(ValidateUserRequest.class)))
                .thenReturn(ValidationResponse.builder().result(true).build());
        Mockito.doNothing().when(createTaskAdaptor).execute(Mockito.any(CreateTaskRequest.class));
        ValidationResponse expectedResponse = ValidationResponse.builder().result(true).build();
        ValidationResponse actualResponse = deleteUserService.execute(FindByIdRequest.builder()
                .id("1")
                .build());
        Assertions.assertEquals(expectedResponse, actualResponse);
    }

}
