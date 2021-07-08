package com.gladunalexander.todo.application;

import lombok.Getter;

import static com.gladunalexander.todo.domain.Task.TaskId;

public class TaskNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Task with id %s not found";

    @Getter
    private final TaskId taskId;

    public TaskNotFoundException(TaskId taskId) {
        super(String.format(MESSAGE, taskId));
        this.taskId = taskId;
    }
}
