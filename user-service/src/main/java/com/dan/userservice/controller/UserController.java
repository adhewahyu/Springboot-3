package com.dan.userservice.controller;

import com.dan.shared.sharedlibrary.model.response.RestResponse;
import com.dan.shared.sharedlibrary.util.CommonConstants;
import com.dan.userservice.model.request.CreateUserRequest;
import com.dan.userservice.service.CreateUserService;
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
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final CreateUserService createUserService;

    @Operation(summary = "Create New User to Task List", description = "API to create new user and put to task list for approval")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Oops")
    })
    @PostMapping(value = "/v1/create")
    public Mono<ResponseEntity<RestResponse>> createNewUser(@RequestBody CreateUserRequest request){
        return Mono.just(new ResponseEntity<>(new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, createUserService.execute(request).getResult()), HttpStatus.OK));
    }

}
