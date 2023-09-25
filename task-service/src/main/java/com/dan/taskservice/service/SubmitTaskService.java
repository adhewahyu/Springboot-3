package com.dan.taskservice.service;

import com.alibaba.fastjson2.JSON;
import com.dan.shared.sharedlibrary.model.request.FindByIdRequest;
import com.dan.shared.sharedlibrary.model.response.ValidationResponse;
import com.dan.shared.sharedlibrary.service.BaseService;
import com.dan.shared.sharedlibrary.util.CommonConstants;
import com.dan.taskservice.adaptor.audit.CreateLogAdaptor;
import com.dan.taskservice.adaptor.user.*;
import com.dan.taskservice.enums.TaskAction;
import com.dan.taskservice.model.entity.Task;
import com.dan.taskservice.model.request.*;
import com.dan.taskservice.repository.TaskRepository;
import com.dan.taskservice.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Transactional(propagation = Propagation.REQUIRES_NEW)
@Service
@Slf4j
@RequiredArgsConstructor
public class SubmitTaskService implements BaseService<SubmitTaskRequest, ValidationResponse> {

    private final ValidateTaskService validateTaskService;
    private final TaskRepository taskRepository;
    private final CreateLogAdaptor createLogAdaptor;
    private final CreateUserByTaskAdaptor createUserByTaskAdaptor;
    private final UpdateUserByTaskAdaptor updateUserByTaskAdaptor;
    private final DeleteUserByTaskAdaptor deleteUserByTaskAdaptor;
    private final CreateRoleByTaskAdaptor createRoleByTaskAdaptor;
    private final UpdateRoleByTaskAdaptor updateRoleByTaskAdaptor;
    private final DeleteRoleByTaskAdaptor deleteRoleByTaskAdaptor;

    @Override
    public ValidationResponse execute(SubmitTaskRequest input) {
        validateTaskService.execute(ValidateTaskRequest.builder()
                .id(input.getId())
                .taskAfter(input.getTaskAfter())
                .status(input.getStatus())
                .updatedBy(input.getUpdatedBy())
                .updatedDate(input.getUpdatedDate())
                .build());
        Task updatedTask = taskRepository.findNewTaskById(input.getId()).map(task -> {
            task.setTaskBefore(task.getTaskAfter());
            task.setTaskAfter(input.getTaskAfter());
            task.setStatus(input.getStatus());
            task.setUpdatedBy(input.getUpdatedBy());
            task.setUpdatedDate(new Date(input.getUpdatedDate()));
            return task;
        }).orElseThrow(()->{
            log.error(CommonConstants.ERR_MSG_DATA_NOT_FOUND);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, CommonConstants.ERR_MSG_DATA_NOT_FOUND);
        });
        doExecuteTask(updatedTask);
        taskRepository.save(updatedTask);
        if(taskRepository.existsById(updatedTask.getId())){
            createLogAdaptor.execute(CreateLogRequest.builder()
                    .activity(updatedTask.getAction())
                    .module(updatedTask.getModule())
                    .createdBy(input.getUpdatedBy())
                    .createdDate(input.getUpdatedDate())
                    .build());
        }
        return ValidationResponse.builder().result(true).build();
    }

    private void doExecuteTask(Task updatedTask){
        switch (updatedTask.getModule()){
            case Constants.USER_MODULE:
                if(updatedTask.getAction().equals(TaskAction.INSERT.getValue())){
                    CreateUserRequest createUserRequest = JSON.parseObject(updatedTask.getTaskAfter(), CreateUserRequest.class);
                    createUserByTaskAdaptor.execute(createUserRequest);
                }
                if(updatedTask.getAction().equals(TaskAction.UPDATE.getValue())){
                    UpdateUserRequest updateUserRequest = JSON.parseObject(updatedTask.getTaskAfter(), UpdateUserRequest.class);
                    updateUserByTaskAdaptor.execute(updateUserRequest);
                }
                if(updatedTask.getAction().equals(TaskAction.DELETE.getValue())){
                    FindByIdRequest findByIdRequest = JSON.parseObject(updatedTask.getTaskAfter(), FindByIdRequest.class);
                    deleteUserByTaskAdaptor.execute(findByIdRequest);
                }
                break;
            case Constants.ROLE_MODULE:
                if(updatedTask.getAction().equals(TaskAction.INSERT.getValue())){
                    CreateRoleRequest createRoleRequest = JSON.parseObject(updatedTask.getTaskAfter(), CreateRoleRequest.class);
                    createRoleByTaskAdaptor.execute(createRoleRequest);
                }
                if(updatedTask.getAction().equals(TaskAction.UPDATE.getValue())){
                    UpdateRoleRequest updateRoleRequest = JSON.parseObject(updatedTask.getTaskAfter(), UpdateRoleRequest.class);
                    updateRoleByTaskAdaptor.execute(updateRoleRequest);
                }
                if(updatedTask.getAction().equals(TaskAction.DELETE.getValue())){
                    FindByIdRequest findByIdRequest = JSON.parseObject(updatedTask.getTaskAfter(), FindByIdRequest.class);
                    deleteRoleByTaskAdaptor.execute(findByIdRequest);
                }
                break;
            default:
                log.error(CommonConstants.ERR_MSG_EXPECTATION_FAILED);
                throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, CommonConstants.ERR_MSG_EXPECTATION_FAILED);
        }
    }

}
