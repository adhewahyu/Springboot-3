package com.dan.userservice.service;

import com.dan.shared.sharedlibrary.model.response.ValidationResponse;
import com.dan.shared.sharedlibrary.service.BaseService;
import com.dan.shared.sharedlibrary.util.CommonConstants;
import com.dan.userservice.enums.TaskAction;
import com.dan.userservice.model.request.ValidateUserRequest;
import com.dan.userservice.repository.UserRepository;
import com.dan.userservice.util.Constants;
import com.dan.userservice.util.ValidatorUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
@Slf4j
public class ValidateUserService implements BaseService<ValidateUserRequest, ValidationResponse> {

    @Value("${config.regex.alphabetOnly}")
    private String regexAlphabetOnly;

    @Value("${config.regex.numberOnly}")
    private String regexNumberOnly;

    private final ValidatorUtility validatorUtility;
    private final UserRepository userRepository;

    @Override
    public ValidationResponse execute(ValidateUserRequest input) {
        if(input.getTaskAction().equals(TaskAction.INSERT.getValue())){
            doValidateUser(input, true);
        }else if(input.getTaskAction().equals(TaskAction.UPDATE.getValue())){
            doValidateUserExist(input);
            doValidateUser(input, false);
        }else if(input.getTaskAction().equals(TaskAction.DELETE.getValue())){
            doValidateUserExist(input);
        }else if(input.getTaskAction().equals(TaskAction.VIEW.getValue())){
            doValidateUserExist(input);
        }else{
            log.error(CommonConstants.ERR_MSG_EXPECTATION_FAILED);
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, CommonConstants.ERR_MSG_EXPECTATION_FAILED);
        }
        return ValidationResponse.builder().result(true).build();
    }

    private void doValidateUser(ValidateUserRequest input, boolean isNew) {
        if(isNew){
            validatorUtility.doValidateInput(regexAlphabetOnly, input.getUsername(), Constants.ERR_MSG_USERNAME_REQUIRED, Constants.ERR_MSG_USERNAME_INVALID);
        }
        validatorUtility.doValidateInput(regexAlphabetOnly, input.getFirstName(), Constants.ERR_MSG_FIRSTNAME_REQUIRED, Constants.ERR_MSG_FIRSTNAME_INVALID);
        validatorUtility.doValidateInput(regexAlphabetOnly, input.getLastName(), Constants.ERR_MSG_LASTNAME_REQUIRED, Constants.ERR_MSG_LASTNAME_INVALID);
        validatorUtility.doValidateInput(regexNumberOnly, input.getPhoneNo(), Constants.ERR_MSG_PHONENO_REQUIRED, Constants.ERR_MSG_PHONENO_INVALID);
        validatorUtility.doValidateEmail(input.getOfficeEmail(), Constants.ERR_MSG_OFFICEMAIL_REQUIRED, Constants.ERR_MSG_OFFICEMAIL_INVALID);
        validatorUtility.doValidateEmail(input.getPersonalEmail(), Constants.ERR_MSG_PERSONALMAIL_REQUIRED, Constants.ERR_MSG_PERSONALMAIL_INVALID);
        validatorUtility.doValidateInput(regexAlphabetOnly, input.getEmergencyContactName(), Constants.ERR_MSG_EMERGENCY_CONTACT_NAME_REQUIRED, Constants.ERR_MSG_EMERGENCY_CONTACT_NAME_INVALID);
        validatorUtility.doValidateInput(regexNumberOnly, input.getEmergencyContactPhoneNo(), Constants.ERR_MSG_EMERGENCY_CONTACT_PHONENO_REQUIRED, Constants.ERR_MSG_EMERGENCY_CONTACT_PHONENO_INVALID);
    }

    private void doValidateUserExist(ValidateUserRequest input){
        if(userRepository.findById(input.getId()).isEmpty()){
            log.error(Constants.ERR_MSG_USER_NOT_FOUND);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.ERR_MSG_USER_NOT_FOUND);
        }
    }



}
