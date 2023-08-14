package com.dan.userservice.service;

import com.alibaba.fastjson2.JSON;
import com.dan.shared.sharedlibrary.model.request.FindByIdRequest;
import com.dan.shared.sharedlibrary.model.response.ValidationResponse;
import com.dan.shared.sharedlibrary.service.BaseService;
import com.dan.shared.sharedlibrary.util.CommonConstants;
import com.dan.userservice.adaptor.task.CreateTaskAdaptor;
import com.dan.userservice.enums.TaskAction;
import com.dan.userservice.enums.TaskStatus;
import com.dan.userservice.model.request.CreateTaskRequest;
import com.dan.userservice.model.request.ValidateUserRequest;
import com.dan.userservice.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteUserService implements BaseService<FindByIdRequest, ValidationResponse> {

    private final ValidateUserService validateUserService;
    private final CreateTaskAdaptor createTaskAdaptor;

    @Override
    public ValidationResponse execute(FindByIdRequest input) {
        ValidateUserRequest validateUserRequest = new ValidateUserRequest();
        BeanUtils.copyProperties(input, validateUserRequest);
        validateUserRequest.setTaskAction(TaskAction.DELETE.getValue());
        if(validateUserService.execute(validateUserRequest).getResult()){
            createTaskAdaptor.execute(CreateTaskRequest.builder()
                    .module(Constants.USER_MODULE)
                    .action(TaskAction.DELETE.getValue())
                    .createdBy(CommonConstants.SYSTEM)
                    .createdDate(new Date().getTime())
                    .status(TaskStatus.NEW.getValue())
                    .taskAfter(JSON.toJSONString(input))
                    .build());
        }
        return ValidationResponse.builder().result(true).build();
    }
}
