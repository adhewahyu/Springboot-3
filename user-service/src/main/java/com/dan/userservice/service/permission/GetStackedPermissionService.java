package com.dan.userservice.service.permission;

import com.alibaba.fastjson2.JSON;
import com.dan.shared.sharedlibrary.model.request.BaseRequest;
import com.dan.shared.sharedlibrary.service.BaseService;
import com.dan.userservice.model.entity.Permission;
import com.dan.userservice.model.response.PermissionResponse;
import com.dan.userservice.model.response.StackedPermissionResponse;
import com.dan.userservice.model.transformer.PermissionResponseTransformer;
import com.dan.userservice.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class GetStackedPermissionService implements BaseService<BaseRequest, StackedPermissionResponse> {

    private final PermissionRepository permissionRepository;
    private final PermissionResponseTransformer permissionResponseTransformer;

    @Override
    public StackedPermissionResponse execute(BaseRequest input) {
        List<PermissionResponse> permissionResponses = getStackedPermissionResponse(true, null);
        return StackedPermissionResponse.builder()
                .permissionResponses(permissionResponses)
                .build();

    }

    private List<PermissionResponse> getStackedPermissionResponse(boolean isParent, String parentId){
        List<Permission> permissions = isParent ? permissionRepository.getActiveRootPermissions()
                : permissionRepository.getActivePermissionsByParentId(parentId);
        if(ObjectUtils.isEmpty(permissions)){
            return Collections.emptyList();
        }else{
            return permissions
                    .stream()
                    .filter(isParent ? permission -> true : permission -> permission.getParentId().equals(parentId))
                    .map(permission -> {
                        PermissionResponse permissionResponse = permissionResponseTransformer.transform(permission, true);
                        if(permissionRepository.countActiveChildPermissionsByParentId(permission.getId()) > 0){
                            permissionResponse.setChildPermissions(getStackedPermissionResponse(false, permission.getId()));
                        }
                        return permissionResponse;
                    }).toList();
        }
    }

}
