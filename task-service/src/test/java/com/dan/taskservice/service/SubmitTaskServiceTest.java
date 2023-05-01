package com.dan.taskservice.service;

import com.dan.shared.sharedlibrary.model.response.ValidationResponse;
import com.dan.shared.sharedlibrary.util.CommonConstants;
import com.dan.taskservice.adaptor.audit.CreateLogAdaptor;
import com.dan.taskservice.enums.TaskStatus;
import com.dan.taskservice.model.entity.Task;
import com.dan.taskservice.model.request.SubmitTaskRequest;
import com.dan.taskservice.repository.TaskRepository;
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
    void doTest_success(){
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
        Mockito.when(taskRepository.findNewTaskById("1234")).thenReturn(Optional.of(dummyTask));
        Mockito.when(taskRepository.save(Mockito.any())).thenReturn(dummyTask);
        Mockito.when(taskRepository.existsById(Mockito.any())).thenReturn(true);
        Mockito.doNothing().when(createLogAdaptor).execute(Mockito.any());
        ValidationResponse expectedResponse = ValidationResponse.builder().result(true).build();
        ValidationResponse actualResponse = submitTaskService.execute(submitTaskRequest);
        Assertions.assertEquals(expectedResponse, actualResponse);
    }

}
