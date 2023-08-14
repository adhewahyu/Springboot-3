package com.dan.userservice.service;

import com.dan.shared.sharedlibrary.util.CommonConstants;
import com.dan.userservice.enums.TaskAction;
import com.dan.userservice.model.entity.User;
import com.dan.userservice.model.request.ValidateUserRequest;
import com.dan.userservice.repository.UserRepository;
import com.dan.userservice.util.Constants;
import com.dan.userservice.util.ValidatorUtility;
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

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@Slf4j
class ValidateUserServiceTest {

    @Mock
    private ValidatorUtility validatorUtility;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ValidateUserService validateUserService;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void doTest_failedUnknownTaskAction(){
        ValidateUserRequest validateUserRequest = ValidateUserRequest.builder()
                .taskAction("anything")
                .build();
        try{
            validateUserService.execute(validateUserRequest);
        }catch (ResponseStatusException rse){
            Assertions.assertEquals(HttpStatus.EXPECTATION_FAILED.value(), rse.getStatusCode().value());
            Assertions.assertEquals(CommonConstants.ERR_MSG_EXPECTATION_FAILED, rse.getReason());
        }
    }

    @Test
    void doTest_failedCreateUser_usernameEmpty(){
        ValidateUserRequest validateUserRequest = ValidateUserRequest.builder()
                .taskAction(TaskAction.INSERT.getValue())
                .username(null)
                .build();
        try{
            validateUserService.execute(validateUserRequest);
        }catch (ResponseStatusException rse){
            Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), rse.getStatusCode().value());
            Assertions.assertEquals(Constants.ERR_MSG_USERNAME_REQUIRED, rse.getReason());
        }
    }

    @Test
    void doTest_failedCreateUser_usernameInvalid(){
        ValidateUserRequest validateUserRequest = ValidateUserRequest.builder()
                .taskAction(TaskAction.INSERT.getValue())
                .username("1")
                .build();
        try{
            validateUserService.execute(validateUserRequest);
        }catch (ResponseStatusException rse){
            Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), rse.getStatusCode().value());
            Assertions.assertEquals(Constants.ERR_MSG_USERNAME_INVALID, rse.getReason());
        }
    }

    @Test
    void doTest_failedUpdateUser_notExist(){
        ValidateUserRequest validateUserRequest = ValidateUserRequest.builder()
                .taskAction(TaskAction.UPDATE.getValue())
                .id("1")
                .username("1")
                .build();
        try{
            validateUserService.execute(validateUserRequest);
        }catch (ResponseStatusException rse){
            Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), rse.getStatusCode().value());
            Assertions.assertEquals(Constants.ERR_MSG_USER_NOT_FOUND, rse.getReason());
        }
    }

    @Test
    void doTest_failedUpdateUser_firstNameEmpty(){
        ReflectionTestUtils.setField(validateUserService, "userRepository", userRepository);
        User user = new User();
        user.setId("1");
        Mockito.when(userRepository.findById("1")).thenReturn(Optional.of(user));
        ValidateUserRequest validateUserRequest = ValidateUserRequest.builder()
                .taskAction(TaskAction.UPDATE.getValue())
                .id("1")
                .firstName(null)
                .build();
        try{
            validateUserService.execute(validateUserRequest);
        }catch (ResponseStatusException rse){
            Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), rse.getStatusCode().value());
            Assertions.assertEquals(Constants.ERR_MSG_FIRSTNAME_REQUIRED, rse.getReason());
        }
    }

    @Test
    void doTest_failedUpdateUser_firstNameInvalid(){
        ReflectionTestUtils.setField(validateUserService, "userRepository", userRepository);
        User user = new User();
        user.setId("1");
        Mockito.when(userRepository.findById("1")).thenReturn(Optional.of(user));
        ValidateUserRequest validateUserRequest = ValidateUserRequest.builder()
                .taskAction(TaskAction.UPDATE.getValue())
                .id("1")
                .firstName("1")
                .build();
        try{
            validateUserService.execute(validateUserRequest);
        }catch (ResponseStatusException rse){
            Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), rse.getStatusCode().value());
            Assertions.assertEquals(Constants.ERR_MSG_FIRSTNAME_INVALID, rse.getReason());
        }
    }

    @Test
    void doTest_failedDeleteUser_notExist(){
        ValidateUserRequest validateUserRequest = ValidateUserRequest.builder()
                .taskAction(TaskAction.DELETE.getValue())
                .id("1")
                .username("1")
                .build();
        try{
            validateUserService.execute(validateUserRequest);
        }catch (ResponseStatusException rse){
            Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), rse.getStatusCode().value());
            Assertions.assertEquals(Constants.ERR_MSG_USER_NOT_FOUND, rse.getReason());
        }
    }

}
