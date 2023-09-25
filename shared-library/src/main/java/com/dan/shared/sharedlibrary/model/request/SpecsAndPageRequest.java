package com.dan.shared.sharedlibrary.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecsAndPageRequest extends BaseRequest {

    private Specification specification;
    private Pageable pageable;

}
