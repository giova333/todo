package com.gladunalexander.todo.persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TaskPersistenceConfiguration {

    @Bean
    TaskPersistenceAdapter taskPersistenceAdapter(TaskJpaRepository jpaRepository) {
        return new TaskPersistenceAdapter(jpaRepository);
    }

}
