package com.dan.userservice.model.response;

import com.dan.shared.sharedlibrary.model.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleResponse extends BaseResponse {

    private String id;
    private String name;
    private String description;
    private Integer status;
    private String statusString;
    private List<PermissionResponse> permissionResponses;

}
