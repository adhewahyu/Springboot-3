package com.dan.shared.sharedlibrary.exception;

import com.dan.shared.sharedlibrary.util.CommonConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class CustomExceptionHandler {

    @ExceptionHandler(value = ResponseStatusException.class)
    protected void handleResponseStatusException(ResponseStatusException ex) {
        throw ex;
    }

    @ExceptionHandler(value = RuntimeException.class)
    protected void handleRuntimeException(RuntimeException ex) {
        log.error("Custom Handler Runtime Exception : {}", ex.getMessage());
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, CommonConstants.ERR_MSG_SOMETHING_WRONG);
    }

}
