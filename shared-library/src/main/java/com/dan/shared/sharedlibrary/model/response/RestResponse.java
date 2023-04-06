package com.dan.shared.sharedlibrary.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestResponse extends BaseResponse{

    private Object data;
    private String message;
    private String messageCode;
    private Boolean result;

    public RestResponse(Object data, String message, Boolean result){
        this.data = data;
        this.message = message;
        this.result = result;
    }

}
