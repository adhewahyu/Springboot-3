package com.dan.shared.sharedlibrary.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Component
@Slf4j
public final class CommonUtility {

    public final String getRandomUUID(){
        return UUID.randomUUID().toString();
    }

    public final void doCheckMakerChecker(Boolean isNew, String person, Long timemillis){
        if(StringUtils.isEmpty(person)){
            String message = (isNew ? "Created by" : "Updated by") + " is required";
            log.error(message);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
        if(ObjectUtils.isEmpty(timemillis)){
            String message = (isNew ? "Created date" : "Updated date") + " is required";
            log.error(message);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
    }

}
