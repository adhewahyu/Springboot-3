package com.dan.taskservice.controller;

import com.dan.shared.sharedlibrary.model.response.ValidationResponse;
import com.dan.shared.sharedlibrary.util.CommonConstants;
import com.dan.taskservice.model.request.CreateTaskRequest;
import com.dan.taskservice.model.request.SubmitTaskRequest;
import com.dan.taskservice.service.CreateTaskService;
import com.dan.taskservice.service.SubmitTaskService;
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
class TaskControllerTest {

    @Mock
    private CreateTaskService createTaskService;

    @Mock
    private SubmitTaskService submitTaskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void doTest_createNewTask_success(){
        ReflectionTestUtils.setField(taskController, "createTaskService", createTaskService);
        Mockito.when(createTaskService.execute(Mockito.any())).thenReturn(ValidationResponse.builder().result(true).build());
        taskController.createNewTask(CreateTaskRequest.builder()
                .module("module")
                .action("I")
                .status(0)
                .taskAfter("task")
                .createdBy("tester")
                .createdDate(0L)
                .build()).subscribe(response -> {
            Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
            Assertions.assertEquals(true, response.getBody().getResult());
            Assertions.assertEquals(CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, response.getBody().getMessage());
        });
    }

    @Test
    void doTest_submitTask_success(){
        ReflectionTestUtils.setField(taskController, "submitTaskService", submitTaskService);
        Mockito.when(submitTaskService.execute(Mockito.any())).thenReturn(ValidationResponse.builder().result(true).build());
        taskController.submitTask(SubmitTaskRequest.builder()
                .id("1234")
                .taskAfter("task")
                .status(1)
                .updatedBy("tester")
                .updatedDate(0L)
                .build()).subscribe(response -> {
            Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
            Assertions.assertEquals(true, response.getBody().getResult());
            Assertions.assertEquals(CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, response.getBody().getMessage());
        });
    }

}
