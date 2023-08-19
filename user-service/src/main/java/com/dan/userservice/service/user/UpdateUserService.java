package com.dan.userservice.service.user;

import com.alibaba.fastjson2.JSON;
import com.dan.shared.sharedlibrary.model.response.ValidationResponse;
import com.dan.shared.sharedlibrary.service.BaseService;
import com.dan.shared.sharedlibrary.util.CommonConstants;
import com.dan.userservice.adaptor.task.CreateTaskAdaptor;
import com.dan.userservice.enums.TaskAction;
import com.dan.userservice.enums.TaskStatus;
import com.dan.userservice.model.request.CreateTaskRequest;
import com.dan.userservice.model.request.UpdateUserRequest;
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
public class UpdateUserService implements BaseService<UpdateUserRequest, ValidationResponse> {

    private final ValidateUserService validateUserService;
    private final CreateTaskAdaptor createTaskAdaptor;

    @Override
    public ValidationResponse execute(UpdateUserRequest input) {
        ValidateUserRequest validateUserRequest = new ValidateUserRequest();
        BeanUtils.copyProperties(input, validateUserRequest);
        validateUserRequest.setTaskAction(TaskAction.UPDATE.getValue());
        if(validateUserService.execute(validateUserRequest).getResult()){
            createTaskAdaptor.execute(CreateTaskRequest.builder()
                    .module(Constants.USER_MODULE)
                    .action(TaskAction.UPDATE.getValue())
                    .createdBy(CommonConstants.SYSTEM)
                    .createdDate(new Date().getTime())
                    .status(TaskStatus.NEW.getValue())
                    .taskAfter(JSON.toJSONString(input))
                    .build());
        }
        return ValidationResponse.builder().result(true).build();
    }



}
