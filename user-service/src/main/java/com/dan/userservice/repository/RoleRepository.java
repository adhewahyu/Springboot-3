package com.dan.userservice.repository;

import com.dan.userservice.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String>, JpaSpecificationExecutor<Role> {

    @Query(value = " select * from roles where lower(name) like '%:name%' and status <> 3 ", nativeQuery = true)
    Optional<Role> findByNameAndNotDeleted(@Param("name") String name);

}
