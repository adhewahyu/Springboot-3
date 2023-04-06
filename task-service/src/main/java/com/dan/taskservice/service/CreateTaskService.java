package com.dan.taskservice.service;

import com.dan.shared.sharedlibrary.model.response.ValidationResponse;
import com.dan.shared.sharedlibrary.service.BaseService;
import com.dan.shared.sharedlibrary.util.CommonUtility;
import com.dan.taskservice.enums.TaskStatus;
import com.dan.taskservice.model.entity.Task;
import com.dan.taskservice.model.request.CreateTaskRequest;
import com.dan.taskservice.model.request.ValidateTaskRequest;
import com.dan.taskservice.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateTaskService implements BaseService<CreateTaskRequest, ValidationResponse> {

    private final TaskRepository taskRepository;
    private final CommonUtility commonUtility;
    private final ValidateTaskService validateTaskService;

    @Override
    public ValidationResponse execute(CreateTaskRequest input) {
        validateTaskService.execute(ValidateTaskRequest.builder()
                .taskAfter(input.getTaskAfter())
                .createdBy(input.getCreatedBy())
                .createdDate(input.getCreatedDate())
                .module(input.getModule())
                .action(input.getAction())
                .status(input.getStatus())
                .build());
        Task task = new Task();
        task.setId(commonUtility.getRandomUUID());
        task.setModule(input.getModule());
        task.setTaskAfter(input.getTaskAfter());
        task.setAction(input.getAction());
        task.setStatus(TaskStatus.NEW.getValue());
        task.setCreatedBy(input.getCreatedBy());
        task.setCreatedDate(new Date(input.getCreatedDate()));
        taskRepository.save(task);
        return ValidationResponse.builder().result(true).build();
    }

}
