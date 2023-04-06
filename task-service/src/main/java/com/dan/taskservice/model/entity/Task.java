package com.dan.taskservice.model.entity;

import com.dan.shared.sharedlibrary.model.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tasks", indexes = {

})
public class Task extends BaseEntity {

    @Id
    @Column(name = "id", length = 100, unique = true, nullable = false)
    private String id;

    @Column(name = "module", length = 100, nullable = false)
    private String module;

    @Column(name = "task_before", columnDefinition = "text")
    private String taskBefore;

    @Column(name = "task_after", columnDefinition = "text")
    private String taskAfter;

    @Column(name = "action", length = 25)
    private String action;

    @Column(name = "status", nullable = false)
    private Integer status;

}
