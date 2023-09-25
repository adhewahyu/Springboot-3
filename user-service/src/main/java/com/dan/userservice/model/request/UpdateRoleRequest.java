package com.dan.userservice.model.request;

import com.dan.shared.sharedlibrary.model.request.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRoleRequest extends BaseRequest {

    private String id;
    private String name;
    private String description;
    private Integer status;
    private List<String> permissionIds;

}
