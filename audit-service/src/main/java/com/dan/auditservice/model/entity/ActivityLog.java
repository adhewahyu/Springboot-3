package com.dan.auditservice.model.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "activity_logs", indexes = {
    @Index(name = "activity_logs_idx_0", columnList = "module")
})
public class ActivityLog extends LogBaseEntity{

    @Id
    @Column(name = "id", updatable = false, nullable = false, length = 50)
    private String id;

    @Column(name="module", length = 25)
    private String module;

    @Column(name="activity", columnDefinition = "text")
    private String activity;

}
