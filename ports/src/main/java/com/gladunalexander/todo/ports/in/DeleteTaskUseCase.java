package com.gladunalexander.todo.ports.in;

import lombok.Value;

import static com.gladunalexander.todo.domain.Task.TaskId;

public interface DeleteTaskUseCase {

    void deleteTask(DeleteTaskCommand command);

    @Value
    class DeleteTaskCommand {
        TaskId taskId;
    }
}
