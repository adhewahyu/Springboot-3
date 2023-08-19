package com.dan.userservice.service;

import com.dan.shared.sharedlibrary.model.response.ValidationResponse;
import com.dan.userservice.adaptor.audit.CreateLogAdaptor;
import com.dan.userservice.enums.UserStatus;
import com.dan.userservice.model.entity.User;
import com.dan.userservice.model.entity.UserDetail;
import com.dan.userservice.model.request.CreateUserRequest;
import com.dan.userservice.model.request.ValidateUserRequest;
import com.dan.userservice.model.transformer.UserRequestTransformer;
import com.dan.userservice.repository.UserDetailRepository;
import com.dan.userservice.service.user.CreateUserByTaskService;
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

import java.util.Date;

@ExtendWith(MockitoExtension.class)
@Slf4j
class CreateUserByTaskServiceTest {

    @Mock
    private ValidateUserService validateUserService;

    @Mock
    private UserDetailRepository userDetailRepository;

    @Mock
    private CreateLogAdaptor createLogAdaptor;

    @Mock
    private UserRequestTransformer userRequestTransformer;

    @InjectMocks
    private CreateUserByTaskService createUserByTaskService;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void doTest_failedValidation(){
        ReflectionTestUtils.setField(createUserByTaskService, "validateUserService", validateUserService);
        Mockito.when(validateUserService.execute(Mockito.any(ValidateUserRequest.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ERR_MSG_USERNAME_REQUIRED));
        try{
            createUserByTaskService.execute(CreateUserRequest.builder()
                    .username(null)
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
        }catch (ResponseStatusException rse){
            Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), rse.getStatusCode().value());
            Assertions.assertEquals(Constants.ERR_MSG_USERNAME_REQUIRED, rse.getReason());
        }
    }

    @Test
    void doTest_success(){
        ReflectionTestUtils.setField(createUserByTaskService, "validateUserService", validateUserService);
        ReflectionTestUtils.setField(createUserByTaskService, "userRequestTransformer", userRequestTransformer);
        User user = new User();
        user.setId("1");
        user.setSuperUser(false);
        user.setStatus(UserStatus.NEW.getValue());
        user.setUsername("test");
        user.setPassword("test");
        user.setCreatedBy("test");
        user.setCreatedDate(new Date());
        UserDetail userDetail = new UserDetail();
        userDetail.setUser(user);
        userDetail.setGrade("1");
        userDetail.setAddress("test");
        userDetail.setPersonalEmail("a@a.com");
        userDetail.setOfficeEmail("a@a.com");
        userDetail.setPhoneNo("12345");
        userDetail.setLastName("test");
        userDetail.setMiddleName("test");
        userDetail.setFirstName("test");
        userDetail.setEmergencyContactPhoneNo("12345");
        userDetail.setEmergencyContactName("test");
        Mockito.when(validateUserService.execute(Mockito.any(ValidateUserRequest.class)))
                .thenReturn(ValidationResponse.builder().result(true).build());
        Mockito.when(userRequestTransformer.transform(Mockito.any(CreateUserRequest.class))).thenReturn(userDetail);
        ValidationResponse expectedResponse = ValidationResponse.builder().result(true).build();
        ValidationResponse actualResponse = createUserByTaskService.execute(CreateUserRequest.builder()
                .emergencyContactPhoneNo("12345")
                .emergencyContactName("test")
                .grade("1")
                .address("test")
                .officeEmail("a@a.com")
                .personalEmail("a@a.com")
                .lastName("test")
                .middleName("test")
                .firstName("test")
                .username("test")
                .phoneNo("12345")
                .build());
        Assertions.assertEquals(expectedResponse, actualResponse);
    }

}
