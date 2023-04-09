package com.dan.auditservice.model.request;

import com.dan.shared.sharedlibrary.model.request.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateLogRequest extends BaseRequest {

    @Schema(description = "module", example = "MY-MODULE")
    private String module;

    @Schema(description = "activity", example = "Approve from MY-MODULE")
    private String activity;

    @Schema(description = "Created Date", example = "Date as long milisecond(s)")
    private Long createdDate;

    @Schema(description = "Created By", example = "User Admin / admin@admin.net")
    private String createdBy;



}
