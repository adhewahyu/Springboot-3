package com.dan.userservice.service.role;

import com.dan.shared.sharedlibrary.model.request.SpecsAndPageRequest;
import com.dan.shared.sharedlibrary.model.response.PageResponse;
import com.dan.shared.sharedlibrary.service.BaseService;
import com.dan.userservice.model.entity.Role;
import com.dan.userservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class GetRolesService implements BaseService<SpecsAndPageRequest, PageResponse> {

    private final RoleRepository roleRepository;

    @Override
    public PageResponse execute(SpecsAndPageRequest input) {
        Page<Role> page = ObjectUtils.isEmpty(input.getSpecification()) ? roleRepository.findAll(input.getPageable())
                : roleRepository.findAll(input.getSpecification(), input.getPageable());
        return PageResponse.builder()
                .contents(page.getContent())
                .currentPage(page.getNumber())
                .totalPage(page.getTotalPages())
                .totalElements(page.getNumberOfElements())
                .build();
    }

}
