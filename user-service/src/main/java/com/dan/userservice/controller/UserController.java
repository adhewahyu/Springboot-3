package com.dan.userservice.controller;

import com.dan.shared.sharedlibrary.model.request.FindByIdRequest;
import com.dan.shared.sharedlibrary.model.response.RestResponse;
import com.dan.shared.sharedlibrary.util.CommonConstants;
import com.dan.userservice.model.request.CreateUserRequest;
import com.dan.userservice.model.request.UpdateUserRequest;
import com.dan.userservice.service.CreateUserByTaskService;
import com.dan.userservice.service.CreateUserService;
import com.dan.userservice.service.DeleteUserService;
import com.dan.userservice.service.UpdateUserService;
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
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final CreateUserService createUserService;
    private final CreateUserByTaskService createUserByTaskService;
    private final UpdateUserService updateUserService;
    private final DeleteUserService deleteUserService;

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

    @Operation(summary = "Create New User from Task List", description = "API to create new user from task list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Oops")
    })
    @PostMapping(value = "/v1/create-by-task")
    public Mono<ResponseEntity<RestResponse>> createNewUserByTask(@RequestBody CreateUserRequest request){
        return Mono.just(new ResponseEntity<>(new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, createUserByTaskService.execute(request).getResult()), HttpStatus.OK));
    }

    @Operation(summary = "Update Existing User to Task List", description = "API to update existing user and put to task list for approval")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Oops")
    })
    @PostMapping(value = "/v1/update")
    public Mono<ResponseEntity<RestResponse>> updateExistingUser(@RequestBody UpdateUserRequest request){
        return Mono.just(new ResponseEntity<>(new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, updateUserService.execute(request).getResult()), HttpStatus.OK));
    }

    @Operation(summary = "Delete User to Task List", description = "API to delete user and put to task list for approval")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Oops")
    })
    @PostMapping(value = "/v1/delete")
    public Mono<ResponseEntity<RestResponse>> deleteUser(@RequestBody FindByIdRequest request){
        return Mono.just(new ResponseEntity<>(new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, deleteUserService.execute(request).getResult()), HttpStatus.OK));
    }

}
