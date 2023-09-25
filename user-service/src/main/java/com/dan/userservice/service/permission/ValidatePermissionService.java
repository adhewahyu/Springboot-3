package com.dan.userservice.service.permission;

import com.dan.shared.sharedlibrary.model.response.ValidationResponse;
import com.dan.shared.sharedlibrary.service.BaseService;
import com.dan.userservice.model.request.ValidatePermissionRequest;
import com.dan.userservice.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ValidatePermissionService implements BaseService<ValidatePermissionRequest, ValidationResponse> {

    private final PermissionRepository permissionRepository;

    @Override
    public ValidationResponse execute(ValidatePermissionRequest input) {
        int totalPermissionIds = input.getPermissionIds().size();
        int validPermissionIds = permissionRepository.countValidAndActivePermissionByIds(input.getPermissionIds());
        return ValidationResponse.builder().result(validPermissionIds == totalPermissionIds).build();
    }

}
