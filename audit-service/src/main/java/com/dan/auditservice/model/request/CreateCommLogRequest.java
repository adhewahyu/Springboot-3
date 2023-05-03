package com.dan.auditservice.model.request;

import com.dan.shared.sharedlibrary.model.request.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommLogRequest extends BaseRequest {

    private String serviceName;
    private String urlEndpoint;
    private String request;
    private String response;
    private String httpResponseCode;

    private Long createdDate;

}
