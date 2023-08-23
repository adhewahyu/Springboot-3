package com.dan.userservice.model.response;

import com.dan.shared.sharedlibrary.model.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse extends BaseResponse {

    private String id;
    private String name;
    private String description;
    private Integer status;
    private String statusString;

}
