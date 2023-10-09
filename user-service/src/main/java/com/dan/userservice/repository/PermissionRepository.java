package com.dan.userservice.repository;

import com.dan.userservice.model.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String>, JpaSpecificationExecutor<Permission> {

    @Query(value = " select count(id) from permissions where id in (:permissionIds) and status = 1 ", nativeQuery = true)
    int countValidAndActivePermissionByIds(@Param("permissionIds") List<String> permissionIds);

    @Query(value = " select p.* from permissions p " +
            " inner join roles_permissions rp on p.id = rp.permission_id " +
            " where rp.role_id = :roleId ", nativeQuery = true)
    List<Permission> getPermissionByRoleId(@Param("roleId") String roleId);

    @Query(value = " select * from permissions where parent_id = :parentId and status = 1 ", nativeQuery = true)
    List<Permission> getActivePermissionsByParentId(@Param("parentId") String parentId);

    @Query(value = " select * from permissions where access_level = 0 and status = 1", nativeQuery = true)
    List<Permission> getActiveRootPermissions();

    @Query(value = " select count(id) from permissions where parent_id = :parentId and status = 1 ", nativeQuery = true)
    Integer countActiveChildPermissionsByParentId(@Param("parentId") String parentId);

}
