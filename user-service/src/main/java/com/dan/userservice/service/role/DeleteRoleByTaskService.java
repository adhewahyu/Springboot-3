package com.dan.userservice.service.role;

import com.dan.shared.sharedlibrary.model.request.FindByIdRequest;
import com.dan.shared.sharedlibrary.model.response.ValidationResponse;
import com.dan.shared.sharedlibrary.service.BaseService;
import com.dan.shared.sharedlibrary.util.CommonConstants;
import com.dan.userservice.adaptor.audit.CreateLogAdaptor;
import com.dan.userservice.enums.RoleStatus;
import com.dan.userservice.enums.TaskAction;
import com.dan.userservice.model.request.CreateLogRequest;
import com.dan.userservice.model.request.ValidateRoleRequest;
import com.dan.userservice.repository.RoleRepository;
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
public class DeleteRoleByTaskService implements BaseService<FindByIdRequest, ValidationResponse> {

    private final ValidateRoleService validateRoleService;
    private final RoleRepository roleRepository;
    private final CreateLogAdaptor createLogAdaptor;

    @Override
    public ValidationResponse execute(FindByIdRequest input) {
        ValidateRoleRequest validateRoleRequest = new ValidateRoleRequest();
        BeanUtils.copyProperties(input, validateRoleRequest);
        validateRoleRequest.setTaskAction(TaskAction.DELETE.getValue());
        if(validateRoleService.execute(validateRoleRequest).getResult()){
            roleRepository.doSetRoleStatusById(input.getId(), RoleStatus.DELETED.getValue(), CommonConstants.SYSTEM, new Date());
            createLogAdaptor.execute(CreateLogRequest.builder()
                    .activity(TaskAction.DELETE.getValue())
                    .module(Constants.ROLE_MODULE)
                    .createdBy(CommonConstants.SYSTEM)
                    .createdDate(new Date().getTime())
                    .build());
        }
        return ValidationResponse.builder().result(true).build();
    }
}
