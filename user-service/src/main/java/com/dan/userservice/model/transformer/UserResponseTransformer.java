package com.dan.userservice.model.transformer;

import com.dan.shared.sharedlibrary.model.transformer.MessageTransformer;
import com.dan.shared.sharedlibrary.util.CommonConstants;
import com.dan.userservice.enums.UserStatus;
import com.dan.userservice.model.entity.UserDetail;
import com.dan.userservice.model.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserResponseTransformer implements MessageTransformer<UserDetail, UserResponse> {

    @Override
    public UserResponse transform(UserDetail input) {
        throw new UnsupportedOperationException(CommonConstants.ERR_MSG_UNSUPPORTED_OPERATION);
    }

    public UserResponse transform(UserDetail input, boolean slimResponse){
        StringBuilder fullNameStringBuilder = new StringBuilder();
        fullNameStringBuilder.append(input.getFirstName()).append(StringUtils.SPACE);
        fullNameStringBuilder.append(input.getMiddleName()).append(StringUtils.SPACE);
        fullNameStringBuilder.append(input.getLastName());
        UserResponse userResponse = UserResponse.builder()
                .id(input.getUser().getId())
                .username(input.getUser().getUsername())
                .status(input.getUser().getStatus())
                .statusString(UserStatus.valueOf(input.getUser().getStatus()).getMsg())
                .fullName(fullNameStringBuilder.toString())
                .phoneNo(input.getPhoneNo())
                .officeEmail(input.getOfficeEmail())
                .personalEmail(input.getPersonalEmail())
                .build();
        if(!slimResponse){
            userResponse.setSuperUser(input.getUser().getSuperUser());
            userResponse.setFirstName(input.getFirstName());
            userResponse.setMiddleName(input.getMiddleName());
            userResponse.setLastName(input.getLastName());
            userResponse.setAddress(input.getAddress());
            userResponse.setGrade(input.getGrade());
            userResponse.setEmergencyContactName(input.getEmergencyContactName());
            userResponse.setEmergencyContactPhoneNo(input.getEmergencyContactPhoneNo());
        }
        return userResponse;
    }

}
