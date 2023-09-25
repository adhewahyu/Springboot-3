package com.dan.userservice.util;

import com.dan.userservice.model.entity.Permission;
import com.dan.userservice.model.entity.Role;
import com.dan.userservice.model.request.ValidatePermissionRequest;
import com.dan.userservice.repository.PermissionRepository;
import com.dan.userservice.service.permission.ValidatePermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class PermissionUtility {

    private final PermissionRepository permissionRepository;
    private final ValidatePermissionService validatePermissionService;

    public void doApplyPermissionSet(Role role, List<String> permissionIds){
        if(ObjectUtils.isNotEmpty(permissionIds) && !validatePermissionIds(permissionIds)){
            log.error(Constants.ERR_MSG_PERMISSIONS_ID_INVALID);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ERR_MSG_PERMISSIONS_ID_INVALID);
        }else{
            Set<Permission> permissionSet = new HashSet<>(permissionRepository.findAllById(permissionIds));
            role.setPermissions(permissionSet);
        }
    }

    private boolean validatePermissionIds(List<String> permissionIds){
        return validatePermissionService.execute(ValidatePermissionRequest.builder().permissionIds(permissionIds).build()).getResult();
    }

}
