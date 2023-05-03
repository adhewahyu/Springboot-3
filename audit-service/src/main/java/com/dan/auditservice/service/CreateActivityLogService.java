package com.dan.auditservice.service;

import com.dan.auditservice.model.entity.ActivityLog;
import com.dan.auditservice.model.request.CreateActivityLogRequest;
import com.dan.auditservice.repository.ActivityLogRepository;
import com.dan.auditservice.util.Constants;
import com.dan.shared.sharedlibrary.model.response.ValidationResponse;
import com.dan.shared.sharedlibrary.service.BaseService;
import com.dan.shared.sharedlibrary.util.CommonUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateActivityLogService implements BaseService<CreateActivityLogRequest, ValidationResponse> {

    private final ActivityLogRepository activityLogRepository;
    private final CommonUtility commonUtility;

    @Override
    public ValidationResponse execute(CreateActivityLogRequest input) {
        log.info("Create Log - called");
        doValidateRequest(input);
        ActivityLog logs = new ActivityLog();
        BeanUtils.copyProperties(input,logs);
        logs.setId(commonUtility.getRandomUUID());
        logs.setCreatedDate(new Date(input.getCreatedDate()));
        activityLogRepository.save(logs);
        log.info("Create Log - saved successfully");
        return ValidationResponse.builder().result(true).build();
    }

    private void doValidateRequest(CreateActivityLogRequest input) {
        if(StringUtils.isEmpty(input.getModule())){
            log.error(Constants.ERR_MSG_MODULE_REQUIRED);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ERR_MSG_MODULE_REQUIRED);
        }
        if(StringUtils.isEmpty(input.getActivity())){
            log.error(Constants.ERR_MSG_ACTIVITY_REQUIRED);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ERR_MSG_ACTIVITY_REQUIRED);
        }
        if(ObjectUtils.isEmpty(input.getCreatedDate())){
            log.error(Constants.ERR_MSG_CREATED_DATE_REQUIRED);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ERR_MSG_CREATED_DATE_REQUIRED);
        }
        if(StringUtils.isEmpty(input.getCreatedBy())){
            log.error(Constants.ERR_MSG_CREATED_BY_REQUIRED);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ERR_MSG_CREATED_BY_REQUIRED);
        }
    }
}
