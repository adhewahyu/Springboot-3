package com.dan.userservice.model.transformer;

import com.dan.shared.sharedlibrary.model.transformer.MessageTransformer;
import com.dan.shared.sharedlibrary.util.CommonConstants;
import com.dan.userservice.enums.RoleStatus;
import com.dan.userservice.enums.UserStatus;
import com.dan.userservice.model.entity.Role;
import com.dan.userservice.model.entity.UserDetail;
import com.dan.userservice.model.response.RoleResponse;
import com.dan.userservice.model.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleResponseTransformer implements MessageTransformer<Role, RoleResponse> {

    @Override
    public RoleResponse transform(Role input) {
        throw new UnsupportedOperationException(CommonConstants.ERR_MSG_UNSUPPORTED_OPERATION);
    }

    public RoleResponse transform(Role input, boolean slimResponse){
        return RoleResponse.builder()
                .id(input.getId())
                .name(input.getName())
                .description(input.getDescription())
                .status(input.getStatus())
                .statusString(RoleStatus.valueOf(input.getStatus()).getMsg())
                .build();
    }

}
