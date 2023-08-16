package com.dan.userservice.service;

import com.dan.shared.sharedlibrary.model.request.FindByIdRequest;
import com.dan.shared.sharedlibrary.model.response.ValidationResponse;
import com.dan.userservice.adaptor.audit.CreateLogAdaptor;
import com.dan.userservice.model.request.ValidateUserRequest;
import com.dan.userservice.repository.UserRepository;
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
class DeleteUserByTaskServiceTest {

    @Mock
    private ValidateUserService validateUserService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CreateLogAdaptor createLogAdaptor;

    @InjectMocks
    private DeleteUserByTaskService deleteUserByTaskService;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void doTest_failedValidation(){
        ReflectionTestUtils.setField(deleteUserByTaskService, "validateUserService", validateUserService);
        Mockito.when(validateUserService.execute(Mockito.any(ValidateUserRequest.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.ERR_MSG_USER_NOT_FOUND));
        try{
            deleteUserByTaskService.execute(FindByIdRequest.builder()
                    .id("1")
                    .build());
        }catch (ResponseStatusException rse){
            Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), rse.getStatusCode().value());
            Assertions.assertEquals(Constants.ERR_MSG_USER_NOT_FOUND, rse.getReason());
        }
    }

    @Test
    void doTest_success(){
        ReflectionTestUtils.setField(deleteUserByTaskService, "validateUserService", validateUserService);
        ReflectionTestUtils.setField(deleteUserByTaskService, "userRepository", userRepository);
        ReflectionTestUtils.setField(deleteUserByTaskService, "createLogAdaptor", createLogAdaptor);
        Mockito.when(validateUserService.execute(Mockito.any(ValidateUserRequest.class)))
                .thenReturn(ValidationResponse.builder().result(true).build());
        Mockito.doNothing().when(userRepository).doSetUserStatusById(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());
        ValidationResponse expectedResponse = ValidationResponse.builder().result(true).build();
        ValidationResponse actualResponse = deleteUserByTaskService.execute(FindByIdRequest.builder()
                    .id("1")
                    .build());
        Assertions.assertEquals(expectedResponse, actualResponse);
    }

}
