package com.gladunalexander.todo.persistence;

import com.gladunalexander.todo.domain.Status;
import com.gladunalexander.todo.domain.Task;

import java.util.UUID;

public class TaskConverter {

    public TaskJpaEntity convert(Task task) {
        return TaskJpaEntity.builder()
                            .id(task.getTaskId().getUuid().toString())
                            .name(task.getName())
                            .status(task.getStatus().name())
                            .build();
    }

    public Task convert(TaskJpaEntity entity) {
        return new Task(Task.TaskId.of(UUID.fromString(entity.getId())),
                        entity.getName(),
                        Status.valueOf(entity.getStatus()));
    }
}
