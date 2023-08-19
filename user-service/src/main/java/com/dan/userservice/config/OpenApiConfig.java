package com.dan.userservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(servers = {
        @Server(url = "/", description = "Local Access"),
        @Server(url = "${swagger.contextPath}", description = "Reverse Proxy Access")
})
public class OpenApiConfig {

    private static final String BASE_PACKAGE = "com.dan.userservice.controller";

    private static final String[] USER_APIS = {
            "/user/v1/create",
            "/user/v1/update",
            "/user/v1/delete",
            "/user/v1/detail"
    };

    private static final String[] ROLE_APIS = {
            "/role/v1/create",
            "/role/v1/update",
            "/role/v1/delete",
            "/role/v1/detail"
    };

    @Bean
    public OpenAPI baseSpringdoc() {
        return new OpenAPI()
                .info(new Info()
                        .title("User Service Docs")
                        .description("This is description")
                        .version("v0.1")
                        .termsOfService("https://swagger.io/terms/")
                        .contact(new io.swagger.v3.oas.models.info.Contact().name("Adhe Wahyu A.").url("").email("adhe.wahyu.ardanto@gmail.com"))
                        .license(new License().name("Apache 2.0").url("https://springdoc.org")));
    }

    @Bean
    public GroupedOpenApi allApis(){
        return GroupedOpenApi.builder()
                .group("All APIs Recipe")
                .displayName("All APIs Recipe")
                .packagesToScan(BASE_PACKAGE)
                .pathsToExclude("/swagger-ui/swagger-ui.css")
                .build();
    }

    @Bean
    public GroupedOpenApi userAPIs(){
        return GroupedOpenApi.builder()
                .group("User Recipe")
                .displayName("User Recipe")
                .packagesToScan(BASE_PACKAGE)
                .pathsToMatch(USER_APIS)
                .build();
    }

    @Bean
    public GroupedOpenApi roleAPIs(){
        return GroupedOpenApi.builder()
                .group("Role Recipe")
                .displayName("Role Recipe")
                .packagesToScan(BASE_PACKAGE)
                .pathsToMatch(ROLE_APIS)
                .build();
    }

}
