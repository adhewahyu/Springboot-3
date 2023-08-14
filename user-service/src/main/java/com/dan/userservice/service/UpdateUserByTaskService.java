package com.dan.userservice.service;

import com.dan.shared.sharedlibrary.model.response.ValidationResponse;
import com.dan.shared.sharedlibrary.service.BaseService;
import com.dan.userservice.adaptor.audit.CreateLogAdaptor;
import com.dan.userservice.enums.TaskAction;
import com.dan.userservice.model.request.CreateLogRequest;
import com.dan.userservice.model.request.UpdateUserRequest;
import com.dan.userservice.model.request.ValidateUserRequest;
import com.dan.userservice.model.transformer.UserRequestTransformer;
import com.dan.userservice.repository.UserDetailRepository;
import com.dan.userservice.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class UpdateUserByTaskService implements BaseService<UpdateUserRequest, ValidationResponse> {

    private final ValidateUserService validateUserService;
    private final UserDetailRepository userDetailRepository;
    private final CreateLogAdaptor createLogAdaptor;
    private final UserRequestTransformer userRequestTransformer;

    @Override
    public ValidationResponse execute(UpdateUserRequest input) {
        ValidateUserRequest validateUserRequest = new ValidateUserRequest();
        BeanUtils.copyProperties(input, validateUserRequest);
        validateUserRequest.setTaskAction(TaskAction.UPDATE.getValue());
        if (validateUserService.execute(validateUserRequest).getResult()) {
            userDetailRepository.findByUserId(input.getId())
                    .ifPresentOrElse(userDetail -> {
                                userDetailRepository.save(userRequestTransformer.transform(userDetail, input));
                                createLogAdaptor.execute(CreateLogRequest.builder()
                                        .activity(TaskAction.INSERT.getValue())
                                        .module(Constants.USER_MODULE)
                                        .createdBy(userDetail.getUser().getCreatedBy())
                                        .createdDate(userDetail.getUser().getCreatedDate().getTime())
                                        .build());
                            },
                            () -> {
                                log.error(Constants.ERR_MSG_USER_NOT_FOUND);
                                throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.ERR_MSG_USER_NOT_FOUND);
                            });
        }
        return ValidationResponse.builder().result(true).build();
    }


}
