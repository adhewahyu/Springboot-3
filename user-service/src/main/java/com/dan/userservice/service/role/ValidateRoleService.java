package com.dan.userservice.service.role;

import com.dan.shared.sharedlibrary.model.response.ValidationResponse;
import com.dan.shared.sharedlibrary.service.BaseService;
import com.dan.shared.sharedlibrary.util.CommonConstants;
import com.dan.userservice.enums.TaskAction;
import com.dan.userservice.model.request.ValidateRoleRequest;
import com.dan.userservice.repository.RoleRepository;
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
public class ValidateRoleService implements BaseService<ValidateRoleRequest, ValidationResponse> {

    @Value("${config.regex.alphabetOnly}")
    private String regexAlphabetOnly;

    private final ValidatorUtility validatorUtility;
    private final RoleRepository roleRepository;

    @Override
    public ValidationResponse execute(ValidateRoleRequest input) {
        if(input.getTaskAction().equals(TaskAction.INSERT.getValue())){
            doValidateRole(input, true);
        }else if(input.getTaskAction().equals(TaskAction.UPDATE.getValue())){
            doValidateRoleExist(input);
            doValidateRole(input, false);
        }else if(input.getTaskAction().equals(TaskAction.DELETE.getValue())){
            doValidateRoleExist(input);
        }else if(input.getTaskAction().equals(TaskAction.VIEW.getValue())){
            doValidateRoleExist(input);
        }else{
            log.error(CommonConstants.ERR_MSG_EXPECTATION_FAILED);
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, CommonConstants.ERR_MSG_EXPECTATION_FAILED);
        }
        return ValidationResponse.builder().result(true).build();
    }

    private void doValidateRole(ValidateRoleRequest input, boolean isNew){
        validatorUtility.doValidateInput(regexAlphabetOnly, input.getName(), Constants.ERR_MSG_ROLENAME_REQUIRED, Constants.ERR_MSG_ROLENAME_INVALID);
        if(!isNew){
            doCheckDuplicateName(input);
        }
    }

    private void doValidateRoleExist(ValidateRoleRequest input){
        if(roleRepository.findById(input.getId()).isEmpty()){
            log.error(Constants.ERR_MSG_ROLE_NOT_FOUND);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.ERR_MSG_ROLE_NOT_FOUND);
        }
    }

    private void doCheckDuplicateName(ValidateRoleRequest input){
        if(roleRepository.findByNameAndNotDeleted(input.getName().toLowerCase()).isPresent()){
            log.error(Constants.ERR_MSG_ROLENAME_DUPLICATE);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ERR_MSG_ROLENAME_DUPLICATE);
        }
    }

}
