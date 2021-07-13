package com.gladunalexander.todo.application;

import com.gladunalexander.todo.domain.TaskId;
import lombok.Getter;

public class TaskNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Task with id %s not found";

    @Getter
    private final TaskId taskId;

    public TaskNotFoundException(TaskId taskId) {
        super(String.format(MESSAGE, taskId));
        this.taskId = taskId;
    }
}
