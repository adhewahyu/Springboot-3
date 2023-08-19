package com.dan.userservice.controller;

import com.dan.shared.sharedlibrary.enums.MessageCode;
import com.dan.shared.sharedlibrary.model.request.FindByIdRequest;
import com.dan.shared.sharedlibrary.model.response.RestResponse;
import com.dan.shared.sharedlibrary.util.CommonConstants;
import com.dan.userservice.model.request.CreateUserRequest;
import com.dan.userservice.model.request.FindUserByIdRequest;
import com.dan.userservice.model.request.UpdateUserRequest;
import com.dan.userservice.service.user.*;
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
    private final UpdateUserByTaskService updateUserByTaskService;
    private final DeleteUserService deleteUserService;
    private final DeleteUserByTaskService deleteUserByTaskService;
    private final FindUserByIdService findUserByIdService;

    @Operation(summary = "Create New User to Task List",
            description = "API to create new user and put to task list for approval")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Oops")
    })
    @PostMapping(value = "/v1/create")
    public Mono<ResponseEntity<RestResponse>> createNewUser(@RequestBody CreateUserRequest request){
        return Mono.just(new ResponseEntity<>(
                new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED,
                        MessageCode.OK.getValue(),
                        createUserService.execute(request).getResult()), HttpStatus.OK));
    }

    @Operation(summary = "Create New User from Task List",
            description = "API to create new user from task list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Oops")
    })
    @PostMapping(value = "/v1/create-by-task")
    public Mono<ResponseEntity<RestResponse>> createNewUserByTask(@RequestBody CreateUserRequest request){
        return Mono.just(new ResponseEntity<>(
                new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED,
                        MessageCode.OK.getValue(),
                        createUserByTaskService.execute(request).getResult()), HttpStatus.OK));
    }

    @Operation(summary = "Update Existing User to Task List",
            description = "API to update existing user and put to task list for approval")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Oops")
    })
    @PostMapping(value = "/v1/update")
    public Mono<ResponseEntity<RestResponse>> updateExistingUser(@RequestBody UpdateUserRequest request){
        return Mono.just(new ResponseEntity<>(
                new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED,
                        MessageCode.OK.getValue(),
                        updateUserService.execute(request).getResult()), HttpStatus.OK));
    }

    @Operation(summary = "Update Existing User from Task List",
            description = "API to update existing user from task list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Oops")
    })
    @PostMapping(value = "/v1/update-by-task")
    public Mono<ResponseEntity<RestResponse>> updateExistingUserByTask(@RequestBody UpdateUserRequest request){
        return Mono.just(new ResponseEntity<>(new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED,
                MessageCode.OK.getValue(),
                updateUserByTaskService.execute(request).getResult()), HttpStatus.OK));
    }

    @Operation(summary = "Delete User to Task List",
            description = "API to delete user and put to task list for approval")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Oops")
    })
    @PostMapping(value = "/v1/delete")
    public Mono<ResponseEntity<RestResponse>> deleteUser(@RequestBody FindByIdRequest request){
        return Mono.just(new ResponseEntity<>(
                new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED,
                        MessageCode.OK.getValue(),
                        deleteUserService.execute(request).getResult()), HttpStatus.OK));
    }

    @Operation(summary = "Delete Existing User from Task List",
            description = "API to delete existing user from task list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Oops")
    })
    @PostMapping(value = "/v1/delete-by-task")
    public Mono<ResponseEntity<RestResponse>> deleteUserByTask(@RequestBody FindByIdRequest request){
        return Mono.just(new ResponseEntity<>(
                new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED,
                        MessageCode.OK.getValue(),
                        deleteUserByTaskService.execute(request).getResult()), HttpStatus.OK));
    }

    @Operation(summary = "Find User with basic info",
            description = "API to find user basic information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Oops")
    })
    @GetMapping(value = "/v1/info/{id}")
    public Mono<ResponseEntity<RestResponse>> getUserBasicInfo(@PathVariable("id") String id){
        return Mono.just(new ResponseEntity<>(
                new RestResponse(findUserByIdService.execute(FindUserByIdRequest.builder()
                        .id(id)
                        .slimResponse(true)
                        .build()),
                        CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, MessageCode.OK.getValue(), true), HttpStatus.OK));
    }

    @Operation(summary = "Find User with detailed info",
            description = "API to find user basic information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Oops")
    })
    @GetMapping(value = "/v1/detail/{id}")
    public Mono<ResponseEntity<RestResponse>> getUserDetailedInfo(@PathVariable("id") String id){
        return Mono.just(new ResponseEntity<>(
                new RestResponse(findUserByIdService.execute(FindUserByIdRequest.builder()
                        .id(id)
                        .slimResponse(false)
                        .build()),
                        CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, MessageCode.OK.getValue(), true), HttpStatus.OK));
    }

}
