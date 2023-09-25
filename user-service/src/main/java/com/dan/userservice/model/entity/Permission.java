package com.dan.userservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "permissions", indexes = {
        @Index(name = "permission_idx_0", columnList = "category"),
        @Index(name = "permission_idx_1", columnList = "access_level"),
        @Index(name = "permission_idx_2", columnList = "status"),
        @Index(name = "permission_idx_3", columnList = "parent_id"),
})
public class Permission {

    @Id
    @Column(name = "id", length = 100, unique = true, nullable = false)
    private String id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "description", length = 250)
    private String description;

    @Column(name = "api", length = 150, nullable = false)
    private String api;

    @Column(name = "category", length = 100, nullable = false)
    private String category;

    @Column(name = "access_level", nullable = false)
    private Integer accessLevel;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "parent_id", length = 100)
    private String parentId;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.ALL
            },
            mappedBy = "permissions")
    @JsonIgnore
    private Set<Role> roles;

}
