package com.dan.userservice.service;

import com.dan.shared.sharedlibrary.model.response.ValidationResponse;
import com.dan.shared.sharedlibrary.service.BaseService;
import com.dan.userservice.model.request.ValidateUserRequest;
import com.dan.userservice.util.Constants;
import com.dan.userservice.util.ValidatorUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class ValidateUserService implements BaseService<ValidateUserRequest, ValidationResponse> {

    @Value("${config.regex.alphabetOnly}")
    private String regexAlphabetOnly;

    @Value("${config.regex.numberOnly}")
    private String regexNumberOnly;

    private final ValidatorUtility validatorUtility;

    @Override
    public ValidationResponse execute(ValidateUserRequest input) {
        doValidateNewUser(input);
        return ValidationResponse.builder().result(true).build();
    }

    private void doValidateNewUser(ValidateUserRequest input) {
        validatorUtility.doValidateInput(regexAlphabetOnly, input.getUsername().trim(), Constants.ERR_MSG_USERNAME_REQUIRED, Constants.ERR_MSG_UNSERNAME_INVALID);
        validatorUtility.doValidateInput(regexAlphabetOnly, input.getFirstName().trim(), Constants.ERR_MSG_FIRSTNAME_REQUIRED, Constants.ERR_MSG_FIRSTNAME_INVALID);
        validatorUtility.doValidateInput(regexAlphabetOnly, input.getLastName().trim(), Constants.ERR_MSG_LASTNAME_REQUIRED, Constants.ERR_MSG_LASTNAME_INVALID);
        validatorUtility.doValidateInput(regexNumberOnly, input.getPhoneNo(), Constants.ERR_MSG_PHONENO_REQUIRED, Constants.ERR_MSG_PHONENO_INVALID);
        validatorUtility.doValidateEmail(input.getOfficeEmail().trim(), Constants.ERR_MSG_OFFICEMAIL_REQUIRED, Constants.ERR_MSG_OFFICEMAIL_INVALID);
        validatorUtility.doValidateEmail(input.getPersonalEmail().trim(), Constants.ERR_MSG_PERSONALMAIL_REQUIRED, Constants.ERR_MSG_PERSONALMAIL_INVALID);
        validatorUtility.doValidateInput(regexAlphabetOnly, input.getEmergencyContactName().trim(), Constants.ERR_MSG_EMERGENCY_CONTACT_NAME_REQUIRED, Constants.ERR_MSG_EMERGENCY_CONTACT_NAME_INVALID);
        validatorUtility.doValidateInput(regexNumberOnly, input.getEmergencyContactPhoneNo().trim(), Constants.ERR_MSG_EMERGENCY_CONTACT_PHONENO_REQUIRED, Constants.ERR_MSG_EMERGENCY_CONTACT_PHONENO_INVALID);
    }



}
