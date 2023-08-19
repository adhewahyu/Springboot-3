package com.dan.userservice.service.user;

import com.dan.shared.sharedlibrary.model.response.ValidationResponse;
import com.dan.shared.sharedlibrary.service.BaseService;
import com.dan.userservice.adaptor.audit.CreateLogAdaptor;
import com.dan.userservice.enums.TaskAction;
import com.dan.userservice.model.entity.UserDetail;
import com.dan.userservice.model.request.CreateLogRequest;
import com.dan.userservice.model.request.CreateUserRequest;
import com.dan.userservice.model.request.ValidateUserRequest;
import com.dan.userservice.model.transformer.UserRequestTransformer;
import com.dan.userservice.repository.UserDetailRepository;
import com.dan.userservice.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class CreateUserByTaskService implements BaseService<CreateUserRequest, ValidationResponse> {

    private final ValidateUserService validateUserService;
    private final UserDetailRepository userDetailRepository;
    private final CreateLogAdaptor createLogAdaptor;
    private final UserRequestTransformer userRequestTransformer;

    @Override
    public ValidationResponse execute(CreateUserRequest input) {
        ValidateUserRequest validateUserRequest = new ValidateUserRequest();
        BeanUtils.copyProperties(input, validateUserRequest);
        validateUserRequest.setTaskAction(TaskAction.INSERT.getValue());
        if(validateUserService.execute(validateUserRequest).getResult()){
            UserDetail newUser = userRequestTransformer.transform(input);
            userDetailRepository.save(newUser);
            createLogAdaptor.execute(CreateLogRequest.builder()
                    .activity(TaskAction.INSERT.getValue())
                    .module(Constants.USER_MODULE)
                    .createdBy(newUser.getUser().getCreatedBy())
                    .createdDate(newUser.getUser().getCreatedDate().getTime())
                    .build());
        }
        return ValidationResponse.builder().result(true).build();
    }



}
