package com.dan.auditservice.controller;

import com.dan.auditservice.model.request.CreateLogRequest;
import com.dan.auditservice.service.CreateLogService;
import com.dan.shared.sharedlibrary.controller.BaseController;
import com.dan.shared.sharedlibrary.model.response.RestResponse;
import com.dan.shared.sharedlibrary.util.CommonConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/log")
@RequiredArgsConstructor
@Slf4j
public class LogController extends BaseController {

    private final CreateLogService createLogService;

    @Operation(summary = "Create New Log", description = "API to create new log")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Oops")
    })
    @PostMapping("/v1/create")
    public Mono<ResponseEntity<RestResponse>> createLog(
            @RequestBody CreateLogRequest request){
        return Mono.just(new ResponseEntity<>(new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, createLogService.execute(request).getResult()), HttpStatus.OK));
    }



}
