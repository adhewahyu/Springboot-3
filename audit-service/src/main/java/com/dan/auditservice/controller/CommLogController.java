package com.dan.auditservice.controller;

import com.dan.auditservice.model.request.CreateActivityLogRequest;
import com.dan.auditservice.model.request.CreateCommLogRequest;
import com.dan.auditservice.service.CreateActivityLogService;
import com.dan.auditservice.service.CreateCommLogService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/log/comm")
@RequiredArgsConstructor
@Slf4j
public class CommLogController extends BaseController {

    private final CreateCommLogService createCommLogService;

    @Operation(summary = "Create New Comm Log", description = "API to create new comm log")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Oops")
    })
    @PostMapping("/v1/create")
    public Mono<ResponseEntity<RestResponse>> createLog(
            @RequestBody CreateCommLogRequest request){
        return Mono.just(new ResponseEntity<>(new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, createCommLogService.execute(request).getResult()), HttpStatus.OK));
    }



}
