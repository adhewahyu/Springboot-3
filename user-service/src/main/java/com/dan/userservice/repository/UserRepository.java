package com.dan.userservice.repository;

import com.dan.userservice.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    @Modifying
    @Query(value = " update users set status = :status, updated_by = :updatedBy, updated_date = :updatedDate where id = :id ", nativeQuery = true)
    void doSetUserStatusById(@Param("id") String id,
                              @Param("status") Integer status,
                              @Param("updatedBy") String updatedBy,
                              @Param("updatedDate") Date updatedDate);

}
