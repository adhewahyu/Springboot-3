package com.dan.userservice.controller;

import com.dan.shared.sharedlibrary.model.request.FindByIdRequest;
import com.dan.shared.sharedlibrary.model.response.ValidationResponse;
import com.dan.shared.sharedlibrary.util.CommonConstants;
import com.dan.userservice.model.request.CreateUserRequest;
import com.dan.userservice.model.request.UpdateUserRequest;
import com.dan.userservice.service.*;
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
public class UserControllerTest {

    @Mock
    private CreateUserService createUserService;

    @Mock
    private CreateUserByTaskService createUserByTaskService;

    @Mock
    private UpdateUserService updateUserService;

    @Mock
    private UpdateUserByTaskService updateUserByTaskService;

    @Mock
    private DeleteUserService deleteUserService;

    @Mock
    private DeleteUserByTaskService deleteUserByTaskService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void doTest_createNewUser_success(){
        ReflectionTestUtils.setField(userController, "createUserService", createUserService);
        Mockito.when(createUserService.execute(Mockito.any())).thenReturn(ValidationResponse.builder().result(true).build());
        userController.createNewUser(CreateUserRequest.builder()
                .username("test")
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
                .build()).subscribe(response -> {
            Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
            Assertions.assertEquals(true, response.getBody().getResult());
            Assertions.assertEquals(CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, response.getBody().getMessage());
        });
    }

    @Test
    void doTest_createNewUserByTask_success(){
        ReflectionTestUtils.setField(userController, "createUserByTaskService", createUserByTaskService);
        Mockito.when(createUserByTaskService.execute(Mockito.any())).thenReturn(ValidationResponse.builder().result(true).build());
        userController.createNewUserByTask(CreateUserRequest.builder()
                .username("test")
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
                .build()).subscribe(response -> {
            Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
            Assertions.assertEquals(true, response.getBody().getResult());
            Assertions.assertEquals(CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, response.getBody().getMessage());
        });
    }

    @Test
    void doTest_updateUser_success(){
        ReflectionTestUtils.setField(userController, "updateUserService", updateUserService);
        Mockito.when(updateUserService.execute(Mockito.any())).thenReturn(ValidationResponse.builder().result(true).build());
        userController.updateExistingUser(UpdateUserRequest.builder()
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
                .build()).subscribe(response -> {
            Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
            Assertions.assertEquals(true, response.getBody().getResult());
            Assertions.assertEquals(CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, response.getBody().getMessage());
        });
    }

    @Test
    void doTest_updateUserByTask_success(){
        ReflectionTestUtils.setField(userController, "updateUserByTaskService", updateUserByTaskService);
        Mockito.when(updateUserByTaskService.execute(Mockito.any())).thenReturn(ValidationResponse.builder().result(true).build());
        userController.updateExistingUserByTask(UpdateUserRequest.builder()
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
                .build()).subscribe(response -> {
            Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
            Assertions.assertEquals(true, response.getBody().getResult());
            Assertions.assertEquals(CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, response.getBody().getMessage());
        });
    }

    @Test
    void doTest_deleteUser_success(){
        ReflectionTestUtils.setField(userController, "deleteUserService", deleteUserService);
        Mockito.when(deleteUserService.execute(Mockito.any())).thenReturn(ValidationResponse.builder().result(true).build());
        userController.deleteUser(FindByIdRequest.builder().id("1").build()).subscribe(response -> {
            Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
            Assertions.assertEquals(true, response.getBody().getResult());
            Assertions.assertEquals(CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, response.getBody().getMessage());
        });
    }

    @Test
    void doTest_deleteUserByTask_success(){
        ReflectionTestUtils.setField(userController, "deleteUserByTaskService", deleteUserByTaskService);
        Mockito.when(deleteUserByTaskService.execute(Mockito.any())).thenReturn(ValidationResponse.builder().result(true).build());
        userController.deleteUserByTask(FindByIdRequest.builder().id("1").build()).subscribe(response -> {
            Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
            Assertions.assertEquals(true, response.getBody().getResult());
            Assertions.assertEquals(CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, response.getBody().getMessage());
        });
    }
}
