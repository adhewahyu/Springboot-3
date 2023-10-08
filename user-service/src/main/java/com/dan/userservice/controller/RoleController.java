package com.dan.userservice.controller;

import com.dan.shared.sharedlibrary.controller.BaseController;
import com.dan.shared.sharedlibrary.enums.MessageCode;
import com.dan.shared.sharedlibrary.model.request.FindByIdRequest;
import com.dan.shared.sharedlibrary.model.request.PageableRequest;
import com.dan.shared.sharedlibrary.model.request.SpecsAndPageRequest;
import com.dan.shared.sharedlibrary.model.response.RestResponse;
import com.dan.shared.sharedlibrary.util.CommonConstants;
import com.dan.userservice.model.request.*;
import com.dan.userservice.service.role.*;
import com.dan.userservice.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/role")
@Tag(name = Constants.TAG_ROLE_API, description = Constants.TAG_ROLE_API_DESCRIPTION)
@RequiredArgsConstructor
@Slf4j
public class RoleController extends BaseController {

    private final CreateRoleService createRoleService;
    private final CreateRoleByTaskService createRoleByTaskService;
    private final UpdateRoleService updateRoleService;
    private final UpdateRoleByTaskService updateRoleByTaskService;
    private final DeleteRoleService deleteRoleService;
    private final DeleteRoleByTaskService deleteRoleByTaskService;
    private final FindRoleByIdService findRoleByIdService;
    private final GetRolesService getRolesService;

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

    @Operation(summary = "Update Role from Task List",
            description = "API to update role from task list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Oops")
    })
    @PutMapping(value = "/v1/update-by-task")
    public Mono<ResponseEntity<RestResponse>> updateRoleByTask(@RequestBody UpdateRoleRequest request){
        return Mono.just(new ResponseEntity<>(
                new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED,
                        MessageCode.OK.getValue(),
                        updateRoleByTaskService.execute(request).getResult()), HttpStatus.OK));
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

    @Operation(summary = "Delete Role from Task List",
            description = "API to delete role from task list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Oops")
    })
    @DeleteMapping(value = "/v1/delete-by-task")
    public Mono<ResponseEntity<RestResponse>> deleteUserFromTask(@RequestBody FindByIdRequest request){
        return Mono.just(new ResponseEntity<>(
                new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED,
                        MessageCode.OK.getValue(),
                        deleteRoleByTaskService.execute(request).getResult()), HttpStatus.OK));
    }

    @Operation(summary = "Find Role with basic info",
            description = "API to find role basic information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Oops")
    })
    @GetMapping(value = "/v1/info/{id}")
    public Mono<ResponseEntity<RestResponse>> getRoleBasicInfo(@PathVariable("id") String id){
        return Mono.just(new ResponseEntity<>(
                new RestResponse(findRoleByIdService.execute(FindUserByIdRequest.builder()
                        .id(id)
                        .slimResponse(true)
                        .build()),
                        CommonConstants.SUCCESS_MSG_DATA_FOUND, MessageCode.OK.getValue(), true), HttpStatus.OK));
    }

    @Operation(summary = "Find Role with detailed info",
            description = "API to find role detailed information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Oops")
    })
    @GetMapping(value = "/v1/info-detailed/{id}")
    public Mono<ResponseEntity<RestResponse>> getRoleDetailedInfo(@PathVariable("id") String id){
        return Mono.just(new ResponseEntity<>(
                new RestResponse(findRoleByIdService.execute(FindUserByIdRequest.builder()
                        .id(id)
                        .slimResponse(false)
                        .build()),
                        CommonConstants.SUCCESS_MSG_DATA_FOUND, MessageCode.OK.getValue(), true), HttpStatus.OK));
    }

    @Operation(summary = "Get Roles",
            description = "API to get roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Oops")
    })
    @GetMapping("/v1/search")
    public Mono<ResponseEntity<RestResponse>> getRoles(PageableRequest pageableRequest){
        SpecsAndPageRequest specsAndPageRequest = SpecsAndPageRequest.builder()
                .specification(null)
                .pageable(buildPageableFromRequest(pageableRequest))
                .build();
        return Mono.just(new ResponseEntity<>(
                new RestResponse(getRolesService.execute(specsAndPageRequest),
                        CommonConstants.SUCCESS_MSG_DATA_FOUND, MessageCode.OK.getValue(), true), HttpStatus.OK));
    }

}
