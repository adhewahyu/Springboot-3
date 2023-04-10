package com.dan.taskservice;

import com.dan.shared.sharedlibrary.configuration.WebClientConfiguration;
import com.dan.shared.sharedlibrary.util.CommonUtility;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@ComponentScan(value = {"com.dan.taskservice", "com.dan.shared.sharedlibrary"},
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {
                CommonUtility.class,
                WebClientConfiguration.class,
        }))
@EntityScan(basePackages = {"com.dan.taskservice.model.entity"})
@SpringBootApplication
public class TaskServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(TaskServiceApp.class, args);
    }

}
