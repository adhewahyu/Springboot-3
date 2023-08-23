package com.dan.userservice.model.transformer;

import com.dan.shared.sharedlibrary.model.transformer.MessageTransformer;
import com.dan.shared.sharedlibrary.util.CommonConstants;
import com.dan.shared.sharedlibrary.util.CommonUtility;
import com.dan.userservice.enums.RoleStatus;
import com.dan.userservice.model.entity.Role;
import com.dan.userservice.model.request.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class RoleRequestTransformer implements MessageTransformer<CreateRoleRequest, Role> {

    private final CommonUtility commonUtility;

    @Override
    public Role transform(CreateRoleRequest input) {
        ValidateRoleRequest validateRoleRequest = new ValidateRoleRequest();
        BeanUtils.copyProperties(input, validateRoleRequest);
        Role role = new Role();
        role.setId(commonUtility.getRandomUUID());
        role.setName(validateRoleRequest.getName());
        role.setDescription(validateRoleRequest.getDescription());
        role.setStatus(RoleStatus.NEW.getValue());
        role.setCreatedBy(CommonConstants.SYSTEM);
        role.setCreatedDate(new Date());
        return role;
    }

    public Role transform(Role role, UpdateRoleRequest input){
        ValidateRoleRequest validateRoleRequest = new ValidateRoleRequest();
        BeanUtils.copyProperties(input, validateRoleRequest);
        role.setName(input.getName());
        role.setDescription(input.getDescription());
        role.setUpdatedBy(CommonConstants.SYSTEM);
        role.setUpdatedDate(new Date());
        return role;
    }
}
