package com.dan.userservice.model.transformer;

import com.dan.shared.sharedlibrary.model.transformer.MessageTransformer;
import com.dan.shared.sharedlibrary.util.CommonConstants;
import com.dan.shared.sharedlibrary.util.CommonUtility;
import com.dan.userservice.enums.UserStatus;
import com.dan.userservice.model.entity.User;
import com.dan.userservice.model.entity.UserDetail;
import com.dan.userservice.model.request.CreateUserRequest;
import com.dan.userservice.model.request.UpdateUserRequest;
import com.dan.userservice.model.request.ValidateUserRequest;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class UserRequestTransformer implements MessageTransformer<CreateUserRequest, UserDetail> {

    @Value("${config.default.password}")
    private String defaultPassword;

    private final CommonUtility commonUtility;

    @Override
    public UserDetail transform(CreateUserRequest input) {
        ValidateUserRequest validateUserRequest = new ValidateUserRequest();
        BeanUtils.copyProperties(validateUserRequest, input);
        User user = new User();
        user.setId(commonUtility.getRandomUUID());
        user.setUsername(validateUserRequest.getUsername());
        user.setPassword(Base64Util.encode(defaultPassword));
        user.setStatus(UserStatus.NEW.getValue());
        user.setSuperUser(false);
        user.setCreatedBy(CommonConstants.SYSTEM);
        user.setCreatedDate(new Date());
        UserDetail userDetail = new UserDetail();
        userDetail.setUser(user);
        userDetail.setId(commonUtility.getRandomUUID());
        return getUserDetail(userDetail, validateUserRequest);
    }

    public UserDetail transform(UserDetail userDetail, UpdateUserRequest input){
        ValidateUserRequest validateUserRequest = new ValidateUserRequest();
        BeanUtils.copyProperties(validateUserRequest, input);
        return getUserDetail(userDetail, validateUserRequest);
    }

    private UserDetail getUserDetail(UserDetail userDetail, ValidateUserRequest input) {
        userDetail.setFirstName(input.getFirstName());
        userDetail.setMiddleName(input.getMiddleName());
        userDetail.setLastName(input.getLastName());
        userDetail.setPhoneNo(input.getPhoneNo());
        userDetail.setOfficeEmail(input.getOfficeEmail());
        userDetail.setPersonalEmail(input.getPersonalEmail());
        userDetail.setAddress(input.getAddress());
        userDetail.setGrade(input.getGrade());
        userDetail.setEmergencyContactName(input.getEmergencyContactName());
        userDetail.setEmergencyContactPhoneNo(input.getEmergencyContactPhoneNo());
        return userDetail;
    }
}
