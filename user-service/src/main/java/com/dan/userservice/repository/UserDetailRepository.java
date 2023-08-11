package com.dan.userservice.repository;

import com.dan.userservice.model.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, String>, JpaSpecificationExecutor<UserDetail> {

}
