package com.dan.taskservice.service;

import com.dan.shared.sharedlibrary.util.CommonUtility;
import com.dan.taskservice.enums.TaskAction;
import com.dan.taskservice.enums.TaskStatus;
import com.dan.taskservice.model.request.ValidateTaskRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

@Service
@Slf4j
@RequiredArgsConstructor
public class ValidateTaskService {

    private final CommonUtility commonUtility;

    public void execute(ValidateTaskRequest request){
        if(StringUtils.isEmpty(request.getId())){
            doCheckNewTask(request);
        }else{
            doCheckSubmittedTask(request);
        }
    }

    private void doCheckNewTask(ValidateTaskRequest request){
        doCheckModule(request.getModule());
        doCheckTask(false, request.getTaskAfter());
        doCheckAction(request.getAction());
        doCheckStatus(request.getStatus());
        commonUtility.doCheckMakerChecker(true, request.getCreatedBy(), request.getCreatedDate());
    }

    private void doCheckSubmittedTask(ValidateTaskRequest request){
        doCheckId(request.getId());
        doCheckTask(false, request.getTaskAfter());
        doCheckStatus(request.getStatus());
        commonUtility.doCheckMakerChecker(false, request.getUpdatedBy(), request.getUpdatedDate());
    }

    private void doCheckId(String id){
        if(StringUtils.isEmpty(id)){
            log.error("Id is required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id is required");
        }
    }

    private void doCheckModule(String module){
        if(StringUtils.isEmpty(module)){
            log.error("Module is required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Module is required");
        }
    }

    private void doCheckTask(Boolean isBefore, String task){
        if(StringUtils.isEmpty(task)){
            String message = "Task " +(isBefore ? "before" : "after")+ " is required";
            log.error(message);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
    }

    private void doCheckAction(String action){
        if(StringUtils.isEmpty(action)){
            log.error("Action is required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Action is required");
        }
        if(Arrays.stream(TaskAction.values()).noneMatch(data -> data.getValue().equals(action))){
            log.error("Invalid action type");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid action type");
        }
    }

    private void doCheckStatus(Integer status){
        if(ObjectUtils.isEmpty(status)){
            log.error("Status is required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status is required");
        }
        if(Arrays.stream(TaskStatus.values()).noneMatch(data -> data.getValue().compareTo(status) == 0)){
            log.error("Invalid status type");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status type");
        }
    }

}
