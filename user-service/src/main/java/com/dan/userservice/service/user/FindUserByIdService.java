package com.dan.userservice.service.user;

import com.dan.shared.sharedlibrary.service.BaseService;
import com.dan.shared.sharedlibrary.util.CommonConstants;
import com.dan.userservice.enums.TaskAction;
import com.dan.userservice.model.request.FindUserByIdRequest;
import com.dan.userservice.model.request.ValidateUserRequest;
import com.dan.userservice.model.response.UserResponse;
import com.dan.userservice.model.transformer.UserResponseTransformer;
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

@Transactional(propagation = Propagation.REQUIRES_NEW)
@Service
@RequiredArgsConstructor
@Slf4j
public class FindUserByIdService implements BaseService<FindUserByIdRequest, UserResponse> {

    private final ValidateUserService validateUserService;
    private final UserDetailRepository userDetailRepository;
    private final UserResponseTransformer userResponseTransformer;

    @Override
    public UserResponse execute(FindUserByIdRequest input) {
        ValidateUserRequest validateUserRequest = new ValidateUserRequest();
        BeanUtils.copyProperties(input, validateUserRequest);
        validateUserRequest.setTaskAction(TaskAction.VIEW.getValue());
        if (validateUserService.execute(validateUserRequest).getResult()) {
            return userDetailRepository.findByUserId(input.getId())
                    .map(data -> userResponseTransformer.transform(data, input.isSlimResponse())
                    ).orElseThrow(() -> {
                        log.error(Constants.ERR_MSG_USER_NOT_FOUND);
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.ERR_MSG_USER_NOT_FOUND);
                    });
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, CommonConstants.ERR_MSG_SOMETHING_WRONG);
    }

}
