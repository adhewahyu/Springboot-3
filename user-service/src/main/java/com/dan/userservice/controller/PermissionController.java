package com.dan.userservice.controller;

import com.dan.shared.sharedlibrary.controller.BaseController;
import com.dan.shared.sharedlibrary.enums.MessageCode;
import com.dan.shared.sharedlibrary.model.request.BaseRequest;
import com.dan.shared.sharedlibrary.model.response.RestResponse;
import com.dan.shared.sharedlibrary.util.CommonConstants;
import com.dan.userservice.service.permission.GetStackedPermissionService;
import com.dan.userservice.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/permissions")
@Tag(name = Constants.TAG_PERMISSION_API, description = Constants.TAG_PERMISSION_API_DESCRIPTION)
@RequiredArgsConstructor
@Slf4j
public class PermissionController extends BaseController {

    private final GetStackedPermissionService getStackedPermissionService;

    @Operation(summary = "Get Stacked Permission(s)",
            description = "API to get stacked permission(s)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Oops")
    })
    @GetMapping(value = "/v1/stacked")
    public Mono<ResponseEntity<RestResponse>> getStackedPermissions(){
        return Mono.just(new ResponseEntity<>(
                new RestResponse(getStackedPermissionService.execute(new BaseRequest()),
                        CommonConstants.SUCCESS_MSG_DATA_FOUND, MessageCode.OK.getValue(), true), HttpStatus.OK));
    }

}
