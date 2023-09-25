package com.dan.userservice.service.role;

import com.dan.shared.sharedlibrary.service.BaseService;
import com.dan.shared.sharedlibrary.util.CommonConstants;
import com.dan.userservice.enums.TaskAction;
import com.dan.userservice.model.request.FindUserByIdRequest;
import com.dan.userservice.model.request.ValidateRoleRequest;
import com.dan.userservice.model.response.RoleResponse;
import com.dan.userservice.model.transformer.RoleResponseTransformer;
import com.dan.userservice.repository.PermissionRepository;
import com.dan.userservice.repository.RoleRepository;
import com.dan.userservice.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Set;

@Transactional(propagation = Propagation.REQUIRES_NEW)
@Service
@RequiredArgsConstructor
@Slf4j
public class FindRoleByIdService implements BaseService<FindUserByIdRequest, RoleResponse> {

    private final ValidateRoleService validateRoleService;
    private final RoleRepository roleRepository;
    private final RoleResponseTransformer roleResponseTransformer;
    private final PermissionRepository permissionRepository;

    @Override
    public RoleResponse execute(FindUserByIdRequest input) {
        ValidateRoleRequest validateRoleRequest = new ValidateRoleRequest();
        BeanUtils.copyProperties(input, validateRoleRequest);
        validateRoleRequest.setTaskAction(TaskAction.VIEW.getValue());
        if (validateRoleService.execute(validateRoleRequest).getResult()) {
            return roleRepository.findById(input.getId())
                    .map(data -> {
                        data.setPermissions(new HashSet<>(permissionRepository.getPermissionByRoleId(data.getId())));
                        return roleResponseTransformer.transform(data, input.isSlimResponse());
                    }).orElseThrow(() -> {
                        log.error(Constants.ERR_MSG_ROLE_NOT_FOUND);
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.ERR_MSG_ROLE_NOT_FOUND);
                    });
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, CommonConstants.ERR_MSG_SOMETHING_WRONG);
    }

}
