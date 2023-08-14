package com.dan.userservice.service;

import com.dan.shared.sharedlibrary.model.request.FindByIdRequest;
import com.dan.shared.sharedlibrary.model.response.ValidationResponse;
import com.dan.shared.sharedlibrary.service.BaseService;
import com.dan.shared.sharedlibrary.util.CommonConstants;
import com.dan.userservice.adaptor.audit.CreateLogAdaptor;
import com.dan.userservice.enums.TaskAction;
import com.dan.userservice.enums.UserStatus;
import com.dan.userservice.model.request.CreateLogRequest;
import com.dan.userservice.model.request.ValidateUserRequest;
import com.dan.userservice.repository.UserRepository;
import com.dan.userservice.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class DeleteUserByTaskService implements BaseService<FindByIdRequest, ValidationResponse> {

    private final ValidateUserService validateUserService;
    private final UserRepository userRepository;
    private final CreateLogAdaptor createLogAdaptor;

    @Override
    public ValidationResponse execute(FindByIdRequest input) {
        ValidateUserRequest validateUserRequest = new ValidateUserRequest();
        BeanUtils.copyProperties(input, validateUserRequest);
        validateUserRequest.setTaskAction(TaskAction.DELETE.getValue());
        if(validateUserService.execute(validateUserRequest).getResult()){
            userRepository.doSetUserStatusById(input.getId(), UserStatus.DELETED.getValue(), CommonConstants.SYSTEM, new Date());
            createLogAdaptor.execute(CreateLogRequest.builder()
                    .activity(TaskAction.DELETE.getValue())
                    .module(Constants.USER_MODULE)
                    .createdBy(CommonConstants.SYSTEM)
                    .createdDate(new Date().getTime())
                    .build());
        }
        return ValidationResponse.builder().result(true).build();
    }
}
