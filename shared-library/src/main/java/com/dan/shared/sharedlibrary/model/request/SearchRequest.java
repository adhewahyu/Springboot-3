package com.dan.shared.sharedlibrary.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest extends BaseRequest {

    private int page;
    private int size;
    private String sortBy;
    private String sortOrder;
    private String textSearch;

}
