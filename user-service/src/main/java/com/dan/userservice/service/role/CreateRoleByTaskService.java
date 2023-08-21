package com.dan.userservice.service.role;

import com.dan.shared.sharedlibrary.model.response.ValidationResponse;
import com.dan.shared.sharedlibrary.service.BaseService;
import com.dan.userservice.adaptor.audit.CreateLogAdaptor;
import com.dan.userservice.enums.TaskAction;
import com.dan.userservice.model.entity.Role;
import com.dan.userservice.model.entity.UserDetail;
import com.dan.userservice.model.request.*;
import com.dan.userservice.model.transformer.RoleRequestTransformer;
import com.dan.userservice.model.transformer.UserRequestTransformer;
import com.dan.userservice.repository.RoleRepository;
import com.dan.userservice.repository.UserDetailRepository;
import com.dan.userservice.service.user.ValidateUserService;
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
public class CreateRoleByTaskService implements BaseService<CreateRoleRequest, ValidationResponse> {

    private final ValidateRoleService validateRoleService;
    private final RoleRepository roleRepository;
    private final CreateLogAdaptor createLogAdaptor;
    private final RoleRequestTransformer roleRequestTransformer;

    @Override
    public ValidationResponse execute(CreateRoleRequest input) {
        ValidateRoleRequest validateRoleRequest = new ValidateRoleRequest();
        BeanUtils.copyProperties(input, validateRoleRequest);
        validateRoleRequest.setTaskAction(TaskAction.INSERT.getValue());
        if(validateRoleService.execute(validateRoleRequest).getResult()){
            Role newRole = roleRequestTransformer.transform(input);
            roleRepository.save(newRole);
            createLogAdaptor.execute(CreateLogRequest.builder()
                    .activity(TaskAction.INSERT.getValue())
                    .module(Constants.ROLE_MODULE)
                    .createdBy(newRole.getCreatedBy())
                    .createdDate(newRole.getCreatedDate().getTime())
                    .build());
        }
        return ValidationResponse.builder().result(true).build();
    }



}
