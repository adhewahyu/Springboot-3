package com.dan.taskservice.service;

import com.dan.shared.sharedlibrary.model.response.ValidationResponse;
import com.dan.shared.sharedlibrary.service.BaseService;
import com.dan.shared.sharedlibrary.util.CommonConstants;
import com.dan.taskservice.model.entity.Task;
import com.dan.taskservice.model.request.SubmitTaskRequest;
import com.dan.taskservice.model.request.ValidateTaskRequest;
import com.dan.taskservice.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubmitTaskService implements BaseService<SubmitTaskRequest, ValidationResponse> {

    private final ValidateTaskService validateTaskService;
    private final TaskRepository taskRepository;

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
        taskRepository.save(updatedTask);
        return ValidationResponse.builder().result(true).build();
    }

}
