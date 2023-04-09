package com.dan.auditservice;

import com.dan.shared.sharedlibrary.configuration.WebClientConfiguration;
import com.dan.shared.sharedlibrary.util.CommonUtility;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@ComponentScan(value = {"com.dan.auditservice","com.dan.shared.sharedlibrary"},
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {
                CommonUtility.class,
                WebClientConfiguration.class,
        }))
@EntityScan("com.dan.auditservice.model.entity")
@EnableJpaRepositories("com.dan.auditservice.repository")
@SpringBootApplication
public class AuditServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(AuditServiceApp.class, args);
    }

}
