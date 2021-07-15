package com.gladunalexander.todo.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TaskPersistenceConfiguration {

    @Bean
    TaskConverter taskConverter(ObjectMapper objectMapper) {
        return new TaskConverter(objectMapper);
    }

    @Bean
    TaskPersistenceAdapter taskPersistenceAdapter(TaskJpaRepository jpaRepository,
                                                  TaskConverter taskConverter) {
        return new TaskPersistenceAdapter(jpaRepository, taskConverter);
    }

}
