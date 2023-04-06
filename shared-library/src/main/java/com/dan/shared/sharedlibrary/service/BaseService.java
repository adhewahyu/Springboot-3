package com.dan.shared.sharedlibrary.service;

import com.dan.shared.sharedlibrary.model.request.BaseRequest;
import com.dan.shared.sharedlibrary.model.response.BaseResponse;


public interface BaseService<I extends BaseRequest, O extends BaseResponse>{

    O execute(I input);

}
