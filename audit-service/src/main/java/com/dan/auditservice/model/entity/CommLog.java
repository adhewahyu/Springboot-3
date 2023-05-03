package com.dan.auditservice.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "comm_logs", indexes = {
        @Index(name = "comm_log_idx_0", columnList = "service_name")
})
public class CommLog extends LogBaseEntity{

    @Id
    @Column(name = "id", updatable = false, nullable = false, length = 50)
    private String id;

    @Column(name = "service_name", length = 50, nullable = false)
    private String serviceName;

    @Column(name = "url_endpoint", length = 200, nullable = false)
    private String urlEndpoint;

    @Column(name = "request", columnDefinition = "text")
    private String request;

    @Column(name = "response", columnDefinition = "text")
    private String response;

    @Column(name = "http_response_code", length = 3)
    private String httpResponseCode;

}
