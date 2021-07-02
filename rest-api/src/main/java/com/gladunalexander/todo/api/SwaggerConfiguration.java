package com.gladunalexander.todo.api;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
class SwaggerConfiguration {

    private final BuildProperties buildProperties;

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                             .group("api")
                             .packagesToScan("com.gladunalexander.todo.api")
                             .pathsToMatch("/**")
                             .build();
    }

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                              .version(buildProperties.getVersion())
                              .title("Todo Service"));
    }
}