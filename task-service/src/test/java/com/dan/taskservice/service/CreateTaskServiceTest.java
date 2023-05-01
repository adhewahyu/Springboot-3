package com.dan.taskservice.service;

import com.alibaba.fastjson2.JSON;
import com.dan.shared.sharedlibrary.model.response.ValidationResponse;
import com.dan.shared.sharedlibrary.util.CommonUtility;
import com.dan.taskservice.adaptor.audit.CreateLogAdaptor;
import com.dan.taskservice.enums.TaskAction;
import com.dan.taskservice.enums.TaskStatus;
import com.dan.taskservice.model.entity.Task;
import com.dan.taskservice.model.request.CreateTaskRequest;
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
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
@Slf4j
class CreateTaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private CommonUtility commonUtility;

    @Mock
    private ValidateTaskService validateTaskService;

    @Mock
    private CreateLogAdaptor createLogAdaptor;

    @InjectMocks
    private CreateTaskService createTaskService;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void doTest_success(){
        ReflectionTestUtils.setField(createTaskService, "validateTaskService", validateTaskService);
        ReflectionTestUtils.setField(createTaskService, "commonUtility", commonUtility);
        ReflectionTestUtils.setField(createTaskService, "taskRepository", taskRepository);
        ReflectionTestUtils.setField(createTaskService, "createLogAdaptor", createLogAdaptor);
        CreateTaskRequest createTaskRequest = CreateTaskRequest.builder()
                .module("module")
                .taskAfter("task")
                .action(TaskAction.INSERT.getValue())
                .status(TaskStatus.NEW.getValue())
                .createdBy("tester")
                .createdDate(0L)
                .build();
        Task dummyTask = new Task();
        BeanUtils.copyProperties(createTaskRequest, dummyTask);
        dummyTask.setId("1234");
        log.info("data = {}", JSON.toJSONString(dummyTask));
        Mockito.when(taskRepository.save(Mockito.any())).thenReturn(dummyTask);
        Mockito.when(taskRepository.existsById(Mockito.any())).thenReturn(true);
        Mockito.doNothing().when(createLogAdaptor).execute(Mockito.any());
        ValidationResponse expectedResponse = ValidationResponse.builder().result(true).build();
        ValidationResponse actualResponse = createTaskService.execute(createTaskRequest);
        Assertions.assertEquals(expectedResponse, actualResponse);
    }
}
