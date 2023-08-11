package com.dan.userservice.model.request;

import com.dan.shared.sharedlibrary.model.request.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskRequest extends BaseRequest {

    private String taskAfter;
    private String createdBy;
    private Long createdDate;

    private String module;
    private String action;
    private Integer status;
}
