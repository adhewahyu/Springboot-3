package com.dan.userservice.model.entity;

import com.dan.shared.sharedlibrary.model.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "roles", indexes = {
        @Index(name = "role_idx_0", columnList = "status"),
})
public class Role extends BaseEntity {

    @Id
    @Column(name = "id", length = 100, unique = true, nullable = false)
    private String id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "description", length = 250)
    private String description;

    @Column(name = "status", nullable = false)
    private Integer status;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name="roles_permissions",
            joinColumns=@JoinColumn(name="role_id", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="permission_id", referencedColumnName="id"))
    private Set<Permission> permissions;

}
