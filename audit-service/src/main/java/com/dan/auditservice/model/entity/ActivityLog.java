package com.dan.auditservice.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Data
@Entity
@Table(name = "activity_logs", indexes = {
    @Index(name = "activity_logs_idx_0", columnList = "module")
})
public class ActivityLog {

    @Id
    @Column(name = "id", updatable = false, nullable = false, length = 50)
    private String id;

    @Column(name="module", length = 25)
    private String module;

    @Column(name="activity", columnDefinition = "text")
    private String activity;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "created_by", length = 50)
    private String createdBy;

}
