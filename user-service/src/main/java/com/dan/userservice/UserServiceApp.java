package com.dan.userservice;

import com.dan.shared.sharedlibrary.configuration.WebClientConfiguration;
import com.dan.shared.sharedlibrary.util.CommonUtility;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@ComponentScan(value = {"com.dan.userservice", "com.dan.shared.sharedlibrary"},
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {
                CommonUtility.class,
                WebClientConfiguration.class,
        }))
@EntityScan(basePackages = {"com.dan.userservice.model.entity"})
@SpringBootApplication
public class UserServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApp.class, args);
    }

}
