package com.gladunalexander.todo.application;

import com.gladunalexander.todo.ports.in.CreateTaskUseCase;
import com.gladunalexander.todo.ports.in.GetTasksQuery;
import com.gladunalexander.todo.ports.in.UpdateTaskStatusUseCase;
import com.gladunalexander.todo.ports.out.TaskFetcher;
import com.gladunalexander.todo.ports.out.TaskPersister;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TaskServiceFactory {

    @Bean
    CreateTaskUseCase createTaskUseCase(TaskPersister taskPersister) {
        return new CreateTaskService(taskPersister);
    }

    @Bean
    GetTasksQuery getTasksQuery(TaskFetcher taskFetcher) {
        return new GetTasksService(taskFetcher);
    }

    @Bean
    UpdateTaskStatusUseCase updateTaskStatusUseCase(TaskPersister taskPersister,
                                                    TaskFetcher taskFetcher) {
        return new UpdateTaskStatusService(taskPersister, taskFetcher);
    }
}
