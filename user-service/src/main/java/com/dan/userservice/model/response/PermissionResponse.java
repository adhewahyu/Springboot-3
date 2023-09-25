package com.dan.userservice.model.response;

import com.dan.shared.sharedlibrary.model.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PermissionResponse extends BaseResponse {

    private String id;
    private String name;
    private String description;
    private String api;
    private String category;
    private Integer accessLevel;
    private String accessLevelString;
    private Integer status;
    private String statusString;
    private String parentId;

}
