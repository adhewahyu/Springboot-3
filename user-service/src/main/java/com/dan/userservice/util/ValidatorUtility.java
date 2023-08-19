package com.dan.userservice.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
public class ValidatorUtility {

    private Matcher getMatcherResultFromPattern(String patternRegex, String input){
        Pattern pattern = Pattern.compile(patternRegex);
        return pattern.matcher(input);
    }

    public void doValidateInput(String regex, String input, String errorMessageOnEmpty, String errorMessageOnInvalid) {
        if (StringUtils.isEmpty(input)) {
            log.error(errorMessageOnEmpty);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessageOnEmpty);
        }
        if (!getMatcherResultFromPattern(regex, input).matches()) {
            log.error(errorMessageOnInvalid);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessageOnInvalid);
        }
    }

    public void doValidateEmail(String input, String errorMessageOnEmpty, String errorMessageOnInvalid) {
        if (StringUtils.isEmpty(input)) {
            log.error(errorMessageOnEmpty);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessageOnEmpty);
        }
        if (!EmailValidator.getInstance().isValid(input)) {
            log.error(errorMessageOnInvalid);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessageOnInvalid);
        }
    }

}
