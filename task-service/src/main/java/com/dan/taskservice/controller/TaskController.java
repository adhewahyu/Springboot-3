package com.dan.taskservice.controller;

import com.dan.shared.sharedlibrary.controller.BaseController;
import com.dan.shared.sharedlibrary.model.response.RestResponse;
import com.dan.shared.sharedlibrary.util.CommonConstants;
import com.dan.taskservice.model.request.CreateTaskRequest;
import com.dan.taskservice.model.request.SubmitTaskRequest;
import com.dan.taskservice.service.CreateTaskService;
import com.dan.taskservice.service.SubmitTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController extends BaseController {

    private final CreateTaskService createTaskService;
    private final SubmitTaskService submitTaskService;

    @Operation(summary = "Create New Task", description = "API to create new task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Oops")
    })
    @PostMapping(value = "/v1/create")
    public Mono<ResponseEntity<RestResponse>> createNewTask(@RequestBody CreateTaskRequest request){
        return Mono.just(new ResponseEntity<>(new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, createTaskService.execute(request).getResult()), HttpStatus.OK));
    }

    @Operation(summary = "Submit Task", description = "API to submit task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Oops")
    })
    @PutMapping(value = "/v1/submit")
    public Mono<ResponseEntity<RestResponse>> submitTask(@RequestBody SubmitTaskRequest request){
        return Mono.just(new ResponseEntity<>(new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, submitTaskService.execute(request).getResult()), HttpStatus.OK));
    }

}
