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

}
