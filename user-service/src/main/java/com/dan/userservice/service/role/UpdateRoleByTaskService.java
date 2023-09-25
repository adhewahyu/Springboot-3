package com.dan.userservice.service.role;

import com.dan.shared.sharedlibrary.model.response.ValidationResponse;
import com.dan.shared.sharedlibrary.service.BaseService;
import com.dan.userservice.adaptor.audit.CreateLogAdaptor;
import com.dan.userservice.enums.TaskAction;
import com.dan.userservice.model.entity.Permission;
import com.dan.userservice.model.request.*;
import com.dan.userservice.model.transformer.RoleRequestTransformer;
import com.dan.userservice.repository.PermissionRepository;
import com.dan.userservice.repository.RoleRepository;
import com.dan.userservice.service.permission.ValidatePermissionService;
import com.dan.userservice.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Set;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class UpdateRoleByTaskService implements BaseService<UpdateRoleRequest, ValidationResponse> {

    private final ValidateRoleService validateRoleService;
    private final RoleRepository roleRepository;
    private final CreateLogAdaptor createLogAdaptor;
    private final RoleRequestTransformer roleRequestTransformer;
    private final ValidatePermissionService validatePermissionService;
    private final PermissionRepository permissionRepository;

    @Override
    public ValidationResponse execute(UpdateRoleRequest input) {
        ValidateRoleRequest validateRoleRequest = new ValidateRoleRequest();
        BeanUtils.copyProperties(input, validateRoleRequest);
        validateRoleRequest.setTaskAction(TaskAction.UPDATE.getValue());
        if (validateRoleService.execute(validateRoleRequest).getResult()) {
            roleRepository.findById(input.getId())
                    .ifPresentOrElse(role -> {
                                role = roleRequestTransformer.transform(role, input);
                                if(ObjectUtils.isNotEmpty(input.getPermissionIds()) && !validatePermissionIds(input)){
                                    log.error(Constants.ERR_MSG_PERMISSIONS_ID_INVALID);
                                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ERR_MSG_PERMISSIONS_ID_INVALID);
                                }else{
                                    Set<Permission> permissionSet = new HashSet<>(permissionRepository.findAllById(input.getPermissionIds()));
                                    role.setPermissions(permissionSet);
                                }
                                roleRepository.save(role);
                                createLogAdaptor.execute(CreateLogRequest.builder()
                                        .activity(TaskAction.UPDATE.getValue())
                                        .module(Constants.ROLE_MODULE)
                                        .createdBy(role.getCreatedBy())
                                        .createdDate(role.getCreatedDate().getTime())
                                        .build());
                            },
                            () -> {
                                log.error(Constants.ERR_MSG_ROLE_NOT_FOUND);
                                throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.ERR_MSG_ROLE_NOT_FOUND);
                            });
        }
        return ValidationResponse.builder().result(true).build();
    }

    private boolean validatePermissionIds(UpdateRoleRequest input){
        return validatePermissionService.execute(ValidatePermissionRequest.builder().permissionIds(input.getPermissionIds()).build()).getResult();
    }
}
