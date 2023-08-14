package com.dan.userservice.repository;

import com.dan.userservice.model.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, String>, JpaSpecificationExecutor<UserDetail> {

    @Query(value = " select * from user_details where user_id = :userId ", nativeQuery = true)
    Optional<UserDetail> findByUserId(@Param("userId") String userId);

}
