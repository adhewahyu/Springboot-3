package com.dan.userservice.model.entity;

import com.dan.shared.sharedlibrary.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users", indexes = {
        @Index(name = "user_idx_0", columnList = "username"),
        @Index(name = "user_idx_1", columnList = "status"),
})
public class User extends BaseEntity {

    @Id
    @Column(name = "id", length = 100, unique = true, nullable = false)
    private String id;

    @Column(name = "username", length = 50, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "super_user", nullable = false)
    private Boolean superUser;

}
