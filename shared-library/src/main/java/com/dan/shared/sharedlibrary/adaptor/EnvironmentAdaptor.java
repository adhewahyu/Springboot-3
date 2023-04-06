package com.dan.shared.sharedlibrary.adaptor;

import com.dan.shared.sharedlibrary.model.request.BaseRequest;
import com.dan.shared.sharedlibrary.model.response.BaseResponse;

public interface EnvironmentAdaptor<I extends BaseRequest, O extends BaseResponse> {

    O execute(I input);

}