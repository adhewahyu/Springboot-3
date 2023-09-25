package com.dan.shared.sharedlibrary.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse extends BaseResponse {

    private transient List<?> contents;
    private int currentPage;
    private int totalPage;
    private int totalElements;

}
