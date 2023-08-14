package com.dan.taskservice.service;

import com.alibaba.fastjson2.JSON;
import com.dan.shared.sharedlibrary.model.request.FindByIdRequest;
import com.dan.shared.sharedlibrary.model.response.ValidationResponse;
import com.dan.shared.sharedlibrary.util.CommonConstants;
import com.dan.taskservice.adaptor.audit.CreateLogAdaptor;
import com.dan.taskservice.adaptor.user.CreateUserByTaskAdaptor;
import com.dan.taskservice.adaptor.user.DeleteUserByTaskAdaptor;
import com.dan.taskservice.adaptor.user.UpdateUserByTaskAdaptor;
import com.dan.taskservice.enums.TaskAction;
import com.dan.taskservice.enums.TaskStatus;
import com.dan.taskservice.model.entity.Task;
import com.dan.taskservice.model.request.CreateUserRequest;
import com.dan.taskservice.model.request.SubmitTaskRequest;
import com.dan.taskservice.model.request.UpdateUserRequest;
import com.dan.taskservice.repository.TaskRepository;
import com.dan.taskservice.util.Constants;
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
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@Slf4j
class SubmitTaskServiceTest {

    @Mock
    private ValidateTaskService validateTaskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private CreateLogAdaptor createLogAdaptor;

    @Mock
    private CreateUserByTaskAdaptor createUserByTaskAdaptor;

    @Mock
    private UpdateUserByTaskAdaptor updateUserByTaskAdaptor;

    @Mock
    private DeleteUserByTaskAdaptor deleteUserByTaskAdaptor;

    @InjectMocks
    private SubmitTaskService submitTaskService;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void doTest_failedDataNotFound(){
        ReflectionTestUtils.setField(submitTaskService, "validateTaskService", validateTaskService);
        ReflectionTestUtils.setField(submitTaskService, "taskRepository", taskRepository);
        ReflectionTestUtils.setField(submitTaskService, "createLogAdaptor", createLogAdaptor);
        SubmitTaskRequest submitTaskRequest = SubmitTaskRequest.builder()
                .id("1234")
                .taskAfter("task")
                .status(TaskStatus.APPROVED.getValue())
                .updatedBy("tester")
                .updatedDate(0L)
                .build();
        Task dummyTask = new Task();
        BeanUtils.copyProperties(submitTaskRequest, dummyTask);
        Mockito.when(taskRepository.findNewTaskById("1234")).thenReturn(Optional.empty());
        try{
            submitTaskService.execute(submitTaskRequest);
        } catch (ResponseStatusException rse){
            Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), rse.getStatusCode().value());
            Assertions.assertEquals(CommonConstants.ERR_MSG_DATA_NOT_FOUND, rse.getReason());
        }
    }

    @Test
    void doTest_failedUnknownModule(){
        ReflectionTestUtils.setField(submitTaskService, "validateTaskService", validateTaskService);
        ReflectionTestUtils.setField(submitTaskService, "taskRepository", taskRepository);
        ReflectionTestUtils.setField(submitTaskService, "createLogAdaptor", createLogAdaptor);
        ReflectionTestUtils.setField(submitTaskService, "createUserByTaskAdaptor", createUserByTaskAdaptor);
        ReflectionTestUtils.setField(submitTaskService, "updateUserByTaskAdaptor", updateUserByTaskAdaptor);
        ReflectionTestUtils.setField(submitTaskService, "deleteUserByTaskAdaptor", deleteUserByTaskAdaptor);
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .username("test")
                .firstName("test")
                .middleName("test")
                .lastName("test")
                .phoneNo("1")
                .officeEmail("a@a.com")
                .personalEmail("b@b.com")
                .address("test")
                .grade("1")
                .emergencyContactName("test")
                .emergencyContactPhoneNo("2")
                .build();
        SubmitTaskRequest submitTaskRequest = SubmitTaskRequest.builder()
                .id("1234")
                .taskAfter(JSON.toJSONString(createUserRequest))
                .updatedBy("tester")
                .updatedDate(0L)
                .status(TaskStatus.APPROVED.getValue())
                .build();
        Task dummyTask = new Task();
        dummyTask.setId("1234");
        dummyTask.setTaskBefore(null);
        dummyTask.setTaskAfter(JSON.toJSONString(createUserRequest));
        dummyTask.setStatus(TaskStatus.NEW.getValue());
        dummyTask.setModule("anything");
        dummyTask.setAction(TaskAction.INSERT.getValue());
        Mockito.when(taskRepository.findNewTaskById("1234")).thenReturn(Optional.of(dummyTask));
        try{
            submitTaskService.execute(submitTaskRequest);
        }catch (ResponseStatusException rse){
            Assertions.assertEquals(HttpStatus.EXPECTATION_FAILED.value(), rse.getStatusCode().value());
            Assertions.assertEquals(CommonConstants.ERR_MSG_EXPECTATION_FAILED, rse.getReason());
        }
    }

    @Test
    void doTest_successCreateUserByTask(){
        ReflectionTestUtils.setField(submitTaskService, "validateTaskService", validateTaskService);
        ReflectionTestUtils.setField(submitTaskService, "taskRepository", taskRepository);
        ReflectionTestUtils.setField(submitTaskService, "createLogAdaptor", createLogAdaptor);
        ReflectionTestUtils.setField(submitTaskService, "createUserByTaskAdaptor", createUserByTaskAdaptor);
        ReflectionTestUtils.setField(submitTaskService, "updateUserByTaskAdaptor", updateUserByTaskAdaptor);
        ReflectionTestUtils.setField(submitTaskService, "deleteUserByTaskAdaptor", deleteUserByTaskAdaptor);
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .username("test")
                .firstName("test")
                .middleName("test")
                .lastName("test")
                .phoneNo("1")
                .officeEmail("a@a.com")
                .personalEmail("b@b.com")
                .address("test")
                .grade("1")
                .emergencyContactName("test")
                .emergencyContactPhoneNo("2")
                .build();
        SubmitTaskRequest submitTaskRequest = SubmitTaskRequest.builder()
                .id("1234")
                .taskAfter(JSON.toJSONString(createUserRequest))
                .updatedBy("tester")
                .updatedDate(0L)
                .status(TaskStatus.APPROVED.getValue())
                .build();
        Task dummyTask = new Task();
        dummyTask.setId("1234");
        dummyTask.setTaskBefore(null);
        dummyTask.setTaskAfter(JSON.toJSONString(createUserRequest));
        dummyTask.setStatus(TaskStatus.NEW.getValue());
        dummyTask.setModule(Constants.USER_MODULE);
        dummyTask.setAction(TaskAction.INSERT.getValue());
        Mockito.when(taskRepository.findNewTaskById("1234")).thenReturn(Optional.of(dummyTask));
        Mockito.when(taskRepository.save(Mockito.any())).thenReturn(dummyTask);
        Mockito.when(taskRepository.existsById(Mockito.any())).thenReturn(true);
        Mockito.doNothing().when(createLogAdaptor).execute(Mockito.any());
        ValidationResponse expectedResponse = ValidationResponse.builder().result(true).build();
        ValidationResponse actualResponse = submitTaskService.execute(submitTaskRequest);
        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void doTest_successUpdateUserByTask(){
        ReflectionTestUtils.setField(submitTaskService, "validateTaskService", validateTaskService);
        ReflectionTestUtils.setField(submitTaskService, "taskRepository", taskRepository);
        ReflectionTestUtils.setField(submitTaskService, "createLogAdaptor", createLogAdaptor);
        ReflectionTestUtils.setField(submitTaskService, "createUserByTaskAdaptor", createUserByTaskAdaptor);
        ReflectionTestUtils.setField(submitTaskService, "updateUserByTaskAdaptor", updateUserByTaskAdaptor);
        ReflectionTestUtils.setField(submitTaskService, "deleteUserByTaskAdaptor", deleteUserByTaskAdaptor);
        UpdateUserRequest updateUserRequest = UpdateUserRequest.builder()
                .firstName("test")
                .middleName("test")
                .lastName("test")
                .phoneNo("1")
                .officeEmail("a@a.com")
                .personalEmail("b@b.com")
                .address("test")
                .grade("1")
                .emergencyContactName("test")
                .emergencyContactPhoneNo("2")
                .build();
        SubmitTaskRequest submitTaskRequest = SubmitTaskRequest.builder()
                .id("1234")
                .taskAfter(JSON.toJSONString(updateUserRequest))
                .updatedBy("tester")
                .updatedDate(0L)
                .status(TaskStatus.APPROVED.getValue())
                .build();
        Task dummyTask = new Task();
        dummyTask.setId("1234");
        dummyTask.setTaskBefore(null);
        dummyTask.setTaskAfter(JSON.toJSONString(updateUserRequest));
        dummyTask.setStatus(TaskStatus.NEW.getValue());
        dummyTask.setModule(Constants.USER_MODULE);
        dummyTask.setAction(TaskAction.UPDATE.getValue());
        Mockito.when(taskRepository.findNewTaskById("1234")).thenReturn(Optional.of(dummyTask));
        Mockito.when(taskRepository.save(Mockito.any())).thenReturn(dummyTask);
        Mockito.when(taskRepository.existsById(Mockito.any())).thenReturn(true);
        Mockito.doNothing().when(createLogAdaptor).execute(Mockito.any());
        ValidationResponse expectedResponse = ValidationResponse.builder().result(true).build();
        ValidationResponse actualResponse = submitTaskService.execute(submitTaskRequest);
        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void doTest_successDeleteUserByTask(){
        ReflectionTestUtils.setField(submitTaskService, "validateTaskService", validateTaskService);
        ReflectionTestUtils.setField(submitTaskService, "taskRepository", taskRepository);
        ReflectionTestUtils.setField(submitTaskService, "createLogAdaptor", createLogAdaptor);
        ReflectionTestUtils.setField(submitTaskService, "createUserByTaskAdaptor", createUserByTaskAdaptor);
        ReflectionTestUtils.setField(submitTaskService, "updateUserByTaskAdaptor", updateUserByTaskAdaptor);
        ReflectionTestUtils.setField(submitTaskService, "deleteUserByTaskAdaptor", deleteUserByTaskAdaptor);
        FindByIdRequest findByIdRequest = FindByIdRequest.builder().id("1").build();
        SubmitTaskRequest submitTaskRequest = SubmitTaskRequest.builder()
                .id("1234")
                .taskAfter(JSON.toJSONString(findByIdRequest))
                .updatedBy("tester")
                .updatedDate(0L)
                .status(TaskStatus.APPROVED.getValue())
                .build();
        Task dummyTask = new Task();
        dummyTask.setId("1234");
        dummyTask.setTaskBefore(null);
        dummyTask.setTaskAfter(JSON.toJSONString(findByIdRequest));
        dummyTask.setStatus(TaskStatus.NEW.getValue());
        dummyTask.setModule(Constants.USER_MODULE);
        dummyTask.setAction(TaskAction.DELETE.getValue());
        Mockito.when(taskRepository.findNewTaskById("1234")).thenReturn(Optional.of(dummyTask));
        Mockito.when(taskRepository.save(Mockito.any())).thenReturn(dummyTask);
        Mockito.when(taskRepository.existsById(Mockito.any())).thenReturn(true);
        Mockito.doNothing().when(createLogAdaptor).execute(Mockito.any());
        ValidationResponse expectedResponse = ValidationResponse.builder().result(true).build();
        ValidationResponse actualResponse = submitTaskService.execute(submitTaskRequest);
        Assertions.assertEquals(expectedResponse, actualResponse);
    }

}
