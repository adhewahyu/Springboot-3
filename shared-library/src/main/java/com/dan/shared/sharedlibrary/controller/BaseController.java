package com.dan.shared.sharedlibrary.controller;

import com.dan.shared.sharedlibrary.model.request.PageableRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class BaseController {

    protected Pageable buildPageableFromRequest(PageableRequest input){
        String sortBy = StringUtils.isEmpty(input.getSortBy()) ? "id" : input.getSortBy();
        Sort.Direction sortOrder = StringUtils.isNotEmpty(input.getSortOrder()) && input.getSortOrder().equals("desc") ?
                Sort.Direction.DESC : Sort.Direction.ASC;
        return PageRequest.of(input.getPage(), input.getSize(), Sort.by(sortOrder, sortBy));
    }

}
