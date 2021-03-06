package com.gladunalexander.todo.application;

import com.gladunalexander.todo.ports.in.CreateTaskUseCase;
import com.gladunalexander.todo.ports.in.GetTasksQuery;
import com.gladunalexander.todo.ports.in.UpdateTaskStatusUseCase;
import com.gladunalexander.todo.ports.out.TaskFetcher;
import com.gladunalexander.todo.ports.out.TaskWriteOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TaskServiceFactory {

    @Bean
    CreateTaskUseCase createTaskUseCase(TaskWriteOperations taskWriteOperations) {
        return new CreateTaskService(taskWriteOperations);
    }

    @Bean
    GetTasksQuery getTasksQuery(TaskFetcher taskFetcher) {
        return new GetTasksService(taskFetcher);
    }

    @Bean
    UpdateTaskStatusUseCase updateTaskStatusUseCase(TaskWriteOperations taskWriteOperations,
                                                    TaskFetcher taskFetcher) {
        return new UpdateTaskStatusService(taskWriteOperations, taskFetcher);
    }

    @Bean
    DeleteTaskService deleteTaskService(TaskFetcher taskFetcher,
                                        TaskWriteOperations taskWriteOperations) {
        return new DeleteTaskService(taskFetcher, taskWriteOperations);
    }
}
