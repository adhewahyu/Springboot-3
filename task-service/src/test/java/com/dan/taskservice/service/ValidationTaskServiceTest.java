package com.dan.taskservice.service;

import com.dan.shared.sharedlibrary.util.CommonUtility;
import com.dan.taskservice.enums.TaskAction;
import com.dan.taskservice.enums.TaskStatus;
import com.dan.taskservice.model.request.ValidateTaskRequest;
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
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
@Slf4j
class ValidationTaskServiceTest {

    @Mock
    private CommonUtility commonUtility;

    @InjectMocks
    private ValidateTaskService validateTaskService;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void doTest_failedModuleRequired(){
        try{
            validateTaskService.execute(ValidateTaskRequest.builder()
                    .id(null)
                    .module(null)
                    .build());
        } catch (ResponseStatusException rse){
            Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), rse.getStatusCode().value());
            Assertions.assertEquals(Constants.ERR_MSG_MODULE_REQUIRED, rse.getReason());
        }
    }

    @Test
    void doTest_failedTaskRequired(){
        try{
            validateTaskService.execute(ValidateTaskRequest.builder()
                    .id(null)
                    .module("test")
                    .taskAfter(null)
                    .build());
        } catch (ResponseStatusException rse){
            Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), rse.getStatusCode().value());
            Assertions.assertEquals("Task after is required", rse.getReason());
        }
    }

    @Test
    void doTest_failedActionRequired(){
        try{
            validateTaskService.execute(ValidateTaskRequest.builder()
                    .id(null)
                    .module("test")
                    .taskAfter("task")
                    .action(null)
                    .build());
        } catch (ResponseStatusException rse){
            Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), rse.getStatusCode().value());
            Assertions.assertEquals(Constants.ERR_MSG_ACTION_REQUIRED, rse.getReason());
        }
    }

    @Test
    void doTest_failedActionInvalid(){
        try{
            validateTaskService.execute(ValidateTaskRequest.builder()
                    .id(null)
                    .module("test")
                    .taskAfter("task")
                    .action("Z")
                    .build());
        } catch (ResponseStatusException rse){
            Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), rse.getStatusCode().value());
            Assertions.assertEquals(Constants.ERR_MSG_ACTION_INVALID, rse.getReason());
        }
    }

    @Test
    void doTest_failedStatusRequired(){
        try{
            validateTaskService.execute(ValidateTaskRequest.builder()
                    .id(null)
                    .module("test")
                    .taskAfter("task")
                    .action(TaskAction.INSERT.getValue())
                    .status(null)
                    .build());
        } catch (ResponseStatusException rse){
            Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), rse.getStatusCode().value());
            Assertions.assertEquals(Constants.ERR_MSG_STATUS_REQUIRED, rse.getReason());
        }
    }

    @Test
    void doTest_failedStatusInvalid(){
        try{
            validateTaskService.execute(ValidateTaskRequest.builder()
                    .id(null)
                    .module("test")
                    .taskAfter("task")
                    .action(TaskAction.INSERT.getValue())
                    .status(99)
                    .build());
        } catch (ResponseStatusException rse){
            Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), rse.getStatusCode().value());
            Assertions.assertEquals(Constants.ERR_MSG_STATUS_INVALID, rse.getReason());
        }
    }

    @Test
    void doTest_successNewTask(){
        boolean success = false;
        try{
            validateTaskService.execute(ValidateTaskRequest.builder()
                    .id(null)
                    .module("test")
                    .taskAfter("task")
                    .action(TaskAction.INSERT.getValue())
                    .status(TaskStatus.NEW.getValue())
                    .build());
            success = true;
        } catch (Exception e){
            log.error(e.getMessage());
        } finally {
            Assertions.assertTrue(success);
        }
    }



    @Test
    void doTest_successExistingTask(){
        boolean success = false;
        try{
            validateTaskService.execute(ValidateTaskRequest.builder()
                    .id("1234")
                    .module("test")
                    .taskAfter("task")
                    .action(TaskAction.UPDATE.getValue())
                    .status(TaskStatus.APPROVED.getValue())
                    .build());
            success = true;
        } catch (Exception e){
            log.error(e.getMessage());
        } finally {
            Assertions.assertTrue(success);
        }
    }

}
