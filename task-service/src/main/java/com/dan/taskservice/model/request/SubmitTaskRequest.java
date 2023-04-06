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
public class SubmitTaskRequest extends BaseRequest {

    private String id;
    private String taskAfter;
    private String updatedBy;
    private Long updatedDate;

    private Integer status;

}
