package com.dan.auditservice.service;

import com.dan.auditservice.model.entity.CommLog;
import com.dan.auditservice.model.request.CreateCommLogRequest;
import com.dan.auditservice.repository.CommLogRepository;
import com.dan.auditservice.util.Constants;
import com.dan.shared.sharedlibrary.model.response.ValidationResponse;
import com.dan.shared.sharedlibrary.service.BaseService;
import com.dan.shared.sharedlibrary.util.CommonConstants;
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
public class CreateCommLogService implements BaseService<CreateCommLogRequest, ValidationResponse> {

    private final CommLogRepository commLogRepository;
    private final CommonUtility commonUtility;

    @Override
    public ValidationResponse execute(CreateCommLogRequest input) {
        log.info("Create Comm Log - called");
        doValidateRequest(input);
        CommLog logs = new CommLog();
        BeanUtils.copyProperties(input,logs);
        logs.setId(commonUtility.getRandomUUID());
        logs.setCreatedDate(new Date(input.getCreatedDate()));
        logs.setCreatedBy(CommonConstants.SYSTEM);
        commLogRepository.save(logs);
        log.info("Create Comm Log - saved successfully");
        return ValidationResponse.builder().result(true).build();
    }

    private void doValidateRequest(CreateCommLogRequest input){
        if(StringUtils.isEmpty(input.getServiceName())){
            log.error(Constants.ERR_MSG_SERVICE_NAME_REQUIRED);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ERR_MSG_SERVICE_NAME_REQUIRED);
        }
        if(StringUtils.isEmpty(input.getUrlEndpoint())){
            log.error(Constants.ERR_MSG_URL_ENDPOINT_REQUIRED);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ERR_MSG_URL_ENDPOINT_REQUIRED);
        }
        if(StringUtils.isEmpty(input.getRequest())){
            log.error(Constants.ERR_MSG_REQUEST_PAYLOAD_REQUIRED);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ERR_MSG_REQUEST_PAYLOAD_REQUIRED);
        }
        if(StringUtils.isEmpty(input.getResponse())){
            log.error(Constants.ERR_MSG_RESPONSE_PAYLOAD_REQUIRED);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ERR_MSG_RESPONSE_PAYLOAD_REQUIRED);
        }
        if(ObjectUtils.isEmpty(input.getCreatedDate())){
            log.error(Constants.ERR_MSG_CREATED_DATE_REQUIRED);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ERR_MSG_CREATED_DATE_REQUIRED);
        }
    }

}
