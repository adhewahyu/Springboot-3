package com.dan.userservice.service.role;

import com.alibaba.fastjson2.JSON;
import com.dan.shared.sharedlibrary.model.response.ValidationResponse;
import com.dan.shared.sharedlibrary.service.BaseService;
import com.dan.shared.sharedlibrary.util.CommonConstants;
import com.dan.userservice.adaptor.task.CreateTaskAdaptor;
import com.dan.userservice.enums.TaskAction;
import com.dan.userservice.enums.TaskStatus;
import com.dan.userservice.model.request.CreateTaskRequest;
import com.dan.userservice.model.request.UpdateRoleRequest;
import com.dan.userservice.model.request.ValidateRoleRequest;
import com.dan.userservice.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateRoleService implements BaseService<UpdateRoleRequest, ValidationResponse> {

    private final ValidateRoleService validateRoleService;
    private final CreateTaskAdaptor createTaskAdaptor;

    @Override
    public ValidationResponse execute(UpdateRoleRequest input) {
        ValidateRoleRequest validateRoleRequest = new ValidateRoleRequest();
        BeanUtils.copyProperties(input, validateRoleRequest);
        validateRoleRequest.setTaskAction(TaskAction.UPDATE.getValue());
        if(validateRoleService.execute(validateRoleRequest).getResult()){
            createTaskAdaptor.execute(CreateTaskRequest.builder()
                    .module(Constants.ROLE_MODULE)
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
