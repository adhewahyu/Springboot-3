package com.dan.shared.sharedlibrary.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecificationRequest extends BaseRequest{

    private String textSearch;

    private boolean showDeleted;
    private String deletedColumn;
    private String deletedValue;

}
