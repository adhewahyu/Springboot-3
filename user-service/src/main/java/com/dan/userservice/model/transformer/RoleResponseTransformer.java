package com.dan.userservice.model.transformer;

import com.dan.shared.sharedlibrary.model.transformer.MessageTransformer;
import com.dan.shared.sharedlibrary.util.CommonConstants;
import com.dan.userservice.enums.RoleStatus;
import com.dan.userservice.model.entity.Role;
import com.dan.userservice.model.response.PermissionResponse;
import com.dan.userservice.model.response.RoleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RoleResponseTransformer implements MessageTransformer<Role, RoleResponse> {

    private final PermissionResponseTransformer permissionResponseTransformer;

    @Override
    public RoleResponse transform(Role input) {
        throw new UnsupportedOperationException(CommonConstants.ERR_MSG_UNSUPPORTED_OPERATION);
    }

    public RoleResponse transform(Role input, boolean slimResponse){
        RoleResponse roleResponse = RoleResponse.builder()
                .id(input.getId())
                .name(input.getName())
                .description(input.getDescription())
                .status(input.getStatus())
                .statusString(RoleStatus.valueOf(input.getStatus()).getMsg())
                .build();
        if(!slimResponse){
            roleResponse.setPermissionResponses(transformPermission(input));
        }
        return roleResponse;
    }

    private List<PermissionResponse> transformPermission(Role input){
        return input.getPermissions().stream()
                .map(permission -> permissionResponseTransformer.transform(permission, true))
                .toList();
    }

}
