package com.dan.taskservice.model.request;

import com.dan.shared.sharedlibrary.model.request.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidateTaskRequest extends BaseRequest {

    private String id;
    private String taskBefore;
    private String taskAfter;
    private String createdBy;
    private Long createdDate;
    private String updatedBy;
    private Long updatedDate;

    private String module;
    private String action;
    private Integer status;

}
