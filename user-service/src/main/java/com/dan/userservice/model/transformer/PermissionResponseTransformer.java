package com.dan.userservice.model.transformer;

import com.dan.shared.sharedlibrary.model.transformer.MessageTransformer;
import com.dan.shared.sharedlibrary.util.CommonConstants;
import com.dan.userservice.enums.PermissionAccessLevel;
import com.dan.userservice.enums.PermissionStatus;
import com.dan.userservice.enums.RoleStatus;
import com.dan.userservice.model.entity.Permission;
import com.dan.userservice.model.entity.Role;
import com.dan.userservice.model.response.PermissionResponse;
import com.dan.userservice.model.response.RoleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PermissionResponseTransformer implements MessageTransformer<Permission, PermissionResponse> {

    @Override
    public PermissionResponse transform(Permission input) {
        throw new UnsupportedOperationException(CommonConstants.ERR_MSG_UNSUPPORTED_OPERATION);
    }

    public PermissionResponse transform(Permission input, boolean slimResponse){
        PermissionResponse permissionResponse = PermissionResponse.builder()
                .id(input.getId())
                .name(input.getName())
                .description(input.getDescription())
                .build();
        if(!slimResponse){
            permissionResponse.setApi(input.getApi());
            permissionResponse.setCategory(input.getCategory());
            permissionResponse.setAccessLevel(input.getAccessLevel());
            permissionResponse.setAccessLevelString(PermissionAccessLevel.valueOf(input.getAccessLevel()).getMsg());
            permissionResponse.setStatus(input.getStatus());
            permissionResponse.setStatusString(PermissionStatus.valueOf(input.getStatus()).getMsg());
            permissionResponse.setParentId(input.getParentId());
        }
        return permissionResponse;
    }

}
