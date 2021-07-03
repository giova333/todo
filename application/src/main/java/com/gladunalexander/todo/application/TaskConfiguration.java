package com.gladunalexander.todo.application;

import com.gladunalexander.todo.ports.out.TaskFetcher;
import com.gladunalexander.todo.ports.out.TaskPersister;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TaskConfiguration {

    @Bean
    CreateTaskService createTaskService(TaskPersister taskPersister) {
        return new CreateTaskService(taskPersister);
    }

    @Bean
    GetTasksService getTasksService(TaskFetcher taskFetcher) {
        return new GetTasksService(taskFetcher);
    }
}
