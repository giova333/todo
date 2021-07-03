package com.gladunalexander.todo.persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TaskPersistenceConfiguration {

    @Bean
    TaskConverter taskConverter() {
        return new TaskConverter();
    }

    @Bean
    TaskPersistenceAdapter taskPersistenceAdapter(TaskJpaRepository jpaRepository,
                                                  TaskConverter taskConverter) {
        return new TaskPersistenceAdapter(jpaRepository, taskConverter);
    }

}
