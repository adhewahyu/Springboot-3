package com.dan.userservice.controller;

import com.dan.shared.sharedlibrary.enums.MessageCode;
import com.dan.shared.sharedlibrary.model.request.FindByIdRequest;
import com.dan.shared.sharedlibrary.model.response.RestResponse;
import com.dan.shared.sharedlibrary.util.CommonConstants;
import com.dan.userservice.model.request.CreateRoleRequest;
import com.dan.userservice.model.request.UpdateRoleRequest;
import com.dan.userservice.service.role.CreateRoleByTaskService;
import com.dan.userservice.service.role.CreateRoleService;
import com.dan.userservice.service.role.DeleteRoleService;
import com.dan.userservice.service.role.UpdateRoleService;
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
@RequestMapping("/role")
@RequiredArgsConstructor
@Slf4j
public class RoleController {

    private final CreateRoleService createRoleService;
    private final CreateRoleByTaskService createRoleByTaskService;
    private final UpdateRoleService updateRoleService;
    private final DeleteRoleService deleteRoleService;

    @Operation(summary = "Create New Role to Task List",
            description = "API to create new role and put to task list for approval")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Oops")
    })
    @PostMapping(value = "/v1/create")
    public Mono<ResponseEntity<RestResponse>> createNewUser(@RequestBody CreateRoleRequest request){
        return Mono.just(new ResponseEntity<>(
                new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED,
                        MessageCode.OK.getValue(),
                        createRoleService.execute(request).getResult()), HttpStatus.OK));
    }

    @Operation(summary = "Create New Role from Task List",
            description = "API to create new role from task list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Oops")
    })
    @PostMapping(value = "/v1/create-by-task")
    public Mono<ResponseEntity<RestResponse>> createNewRoleByTask(@RequestBody CreateRoleRequest request){
        return Mono.just(new ResponseEntity<>(
                new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED,
                        MessageCode.OK.getValue(),
                        createRoleByTaskService.execute(request).getResult()), HttpStatus.OK));
    }

    @Operation(summary = "Update Existing Role to Task List",
            description = "API to update existing role and put to task list for approval")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Oops")
    })
    @PostMapping(value = "/v1/update")
    public Mono<ResponseEntity<RestResponse>> updateExistingUser(@RequestBody UpdateRoleRequest request){
        return Mono.just(new ResponseEntity<>(
                new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED,
                        MessageCode.OK.getValue(),
                        updateRoleService.execute(request).getResult()), HttpStatus.OK));
    }

    @Operation(summary = "Delete Role to Task List",
            description = "API to delete role and put to task list for approval")
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
                        deleteRoleService.execute(request).getResult()), HttpStatus.OK));
    }

}
